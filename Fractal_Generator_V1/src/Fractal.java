
import java.util.LinkedList;

public class Fractal {
	
	private double y, x;
	private double angle;
	double rotationAngle=60;	
	int numberOfIteration=2; //order
	//koch
	int numberOfSegment=2; 
	int numberOfSide=1;
	boolean mirror = false;
	boolean koch = true;
	double skewAngle = 0;
	double BothAngle = 0;
	//tree
	int numberOfBranch=2; 
	double lengthOfBranch=0.5; // Proportioned to the 'root', value between 0 and 1
	boolean trunk = true;
	boolean leaves = true;
	boolean trunkA = true;
	boolean leavesA = true;
	boolean centerToTrunk;
	Branch[] branches;
	
	String[] rules = new String[26];
	String StartL = "";
	
	double zoom = 1;
	Point2D max = new Point2D();
	Point2D boardCenter = new Point2D();
	int limitOfLine=100_000;
	
	public LinkedList<Line2D> lines = new LinkedList<>();

	Fractal(){
		x = 0;y = 0;angle =0;
	}
	
	public void Koch(int order) {
		if(lines.size() == limitOfLine)return;
		if(order == 1)addLine();//addline
		else {
			Koch(order -1);
			
			rotate(rotationAngle);
			for(int i = 1; i < numberOfSegment; i++) {
				Koch(order -1);
				rotate((-rotationAngle*2+skewAngle)/(numberOfSegment-1));
			}
			Koch(order -1);
			
			rotate(rotationAngle-skewAngle);
			Koch(order -1);
		}
	}
	//Koch Mirror, adds segments on both side
	public void KochM(int order) {
		if(lines.size() == limitOfLine)return;
		if(order == 1)addLine();
		else {
			KochM(order -1);
			rotate(rotationAngle);
			
			for(int i = 1; i < numberOfSegment; i++) {
				KochM(order -1);
				rotate((-rotationAngle*2+skewAngle)/(numberOfSegment-1));
			}
			KochM(order -1);
			rotate(-180 + 2*rotationAngle-skewAngle);
			
			for(int i = 1; i < numberOfSegment; i++) {
				KochM(order -1);
				rotate((-rotationAngle*2+skewAngle)/(numberOfSegment-1));
			}
			KochM(order -1);
			rotate(-180 + 2*rotationAngle-skewAngle);
			
			for(int i = 1; i < numberOfSegment; i++) {
				if(order == 2) dontaddLine();
				else KochM(order -1);
				rotate((-rotationAngle*2+skewAngle)/(numberOfSegment-1));
			}
			if(order == 2) dontaddLine();
			else KochM(order -1);
			
			rotate(rotationAngle-skewAngle);
			KochM(order -1);
		}
	}
		
	
    /*
     * Apply formula to x and y of a point
     * 
     * @param test
     * 
     * @return yes
     */
    private Point2D doMath(Point2D p, double length, double angle) {
    	return new Point2D(
    			p.getX() +length*Math.cos(Math.toRadians(angle)),
    			p.getY() +length*Math.sin(Math.toRadians(angle))
    			);
    }
   
    public void TreeSimple(int n, double startAngle, double length ,Point2D start) {
    	if(lines.size() == limitOfLine)return;
    	if(n == 0)return;
    	
    	Point2D end = doMath(start,length,startAngle);
		if(n == numberOfIteration && !trunk);
		else if(n > 1 && leaves);
		else lines.add(new Line2D(start.copyOf(), end.copyOf())) ;
    	 
		if(numberOfBranch == 1) startAngle-=rotationAngle;
		else startAngle -= (numberOfBranch -1)/2. * rotationAngle;
		
    	for(int i = 0 ; i < numberOfBranch;i++) {
    		TreeSimple(n-1 , startAngle, length*lengthOfBranch, end.copyOf());
    		startAngle+=rotationAngle;
    	}
    }
 
    public void TreeAdvanced(int n, double startAngle, double length ,Point2D start) {
    	if(lines.size() == limitOfLine)return;
    	if(n == 0)return;
    	
		if(n == numberOfIteration && !trunkA);
		else if(n > 1 && leavesA);
		else lines.add(new Line2D(start.copyOf(), doMath(start,length,startAngle))) ;
		
		//angle from the root
		//b.length length proportioned to the root
		//b.position start point relative to root
		//length and position are in %, therefore divided by 100 to get the proper ratio
    	for(Branch b : branches) {
    		if(b == null)break;
    		TreeAdvanced(n-1 , startAngle+b.angle, length*b.length/100, doMath(start,length*b.position/100,startAngle));
    	}
    }
    
    
	private void LSystemReapeat(int order, String rule) {
		if(rule == null)return;
		for(char c : rule.toCharArray()) {
			if(c == 'F')LSystemAddLine(order-1,rules['F'-'A']);
			else if(Character.isUpperCase(c))LSystem(order-1,rules[c-'A']);
			else switch (c) {
				case '+':rotate(rotationAngle);break;
				case '-':rotate(-rotationAngle);break;
				case '|':rotate(180);break;
				default:break;
			}
		}
	}
	
	private void LSystemAddLine(int order,String rule) {
		if(lines.size() == limitOfLine)return;
		if(order == 0)addLine();
		else LSystemReapeat(order, rule);
	}
	
	private void LSystem(int order,String rule) {
		if(lines.size() == limitOfLine)return;
		if(order == 0)return;
		else LSystemReapeat(order, rule);
	}
	
	
	public void Koch() {
		rotate(getStartAngle());
		for(int i = 0; i< numberOfSide; i++) {
			if(mirror) KochM(numberOfIteration);
			else Koch(numberOfIteration);
			rotate(-360.0/numberOfSide);			
		}
	}
	public void TreeSimple() {
		if(numberOfIteration == 0)return;
		TreeSimple(numberOfIteration,-90,1,new Point2D());
	}
	public void TreeAdvanced() {
		TreeAdvanced(numberOfIteration,  -90 ,1, new Point2D());//start length doesn't matter but mustn't be 0
	}
	private void LSystemStart() {
		if(StartL.isEmpty())LSystemAddLine(numberOfIteration-1,rules['F'-'A']);
		else LSystemReapeat(numberOfIteration, StartL);
	}

	private void rotate(double a) {angle -= a; }

	public void generateFractal(String name) {
		clear();
		switch (name) {
			case "Koch": Koch();break;
			case "Tree": TreeSimple();break;
			case "TreeAdvanced": TreeAdvanced();break;
			case "LSystem": LSystemStart();break;
			default:
				throw new IllegalArgumentException("(This should not happen)Unexpected value: " + name);
		}
		resizeFractal();
//		centerToTrunk = false;
	}
	
	private void addLine() {
		double nx = x + Math.cos(Math.toRadians(angle));
		double ny = y + Math.sin(Math.toRadians(angle));
		lines.add(new Line2D(new Point2D(x,y), new Point2D(nx,ny)));
		x = nx;
		y = ny;
	}
	private void dontaddLine() {
		x = x + Math.cos(Math.toRadians(angle));
		y = y + Math.sin(Math.toRadians(angle));
	}
	
	private double getStartAngle() {
		Koch(numberOfIteration);
		double x = lines.get(lines.size()-1).getP2().getX() - lines.get(0).getP1().getX();
		double y = lines.get(lines.size()-1).getP2().getY() - lines.get(0).getP1().getY();
		clear();
		int a = x<0?180:0;
		System.out.println("Start Angle :"+(Math.toDegrees(Math.atan(y/x))+a));
		return Math.toDegrees(Math.atan(y/x))+a;
	}

	public Point2D center() {
		if(centerToTrunk) return new Point2D(lines.get(0).getP1().getX(),lines.get(0).getP1().getY()-1);
		double xMax=0,xMin=100000,yMax=0,yMin=100000;
		if(lines != null && lines.size() > 0) {
			for (Line2D l : lines) {
				if(l == null)break;
				xMax = Math.max(xMax,l.getP2().getX());
				xMin = Math.min(xMin,l.getP2().getX());
				yMax = Math.max(yMax,l.getP2().getY());
				yMin = Math.min(yMin,l.getP2().getY());
			}
			xMax = Math.max(xMax,lines.get(0).getP1().getX());
			xMin = Math.min(xMin,lines.get(0).getP1().getX());
			yMax = Math.max(yMax,lines.get(0).getP1().getY());
			yMin = Math.min(yMin,lines.get(0).getP1().getY());
		}
		max.setX(Math.max(xMax,Math.abs(xMin)));
		max.setY(Math.max(yMax,Math.abs(yMin)));
		
		return new Point2D((xMax+xMin)/2,(yMax+yMin)/2);
	}
	
	public void centerTo00() {
		//substract the center to all point 
		Point2D center = center();
		for(Line2D l : lines) {
			l.substract(center);
		}
	}

	public void resizeFractal() {
		centerTo00();
			
		double scale = zoom * 500 / Math.max(max.getX(), max.getY());
		for (Line2D l : lines) {
			l.multiply(scale);
			l.add(boardCenter);
		}
//		recenterFractal();
	}
	
	public void recenterFractal() {
//		Point2D boardCenter = new Point2D(Program.DPANEL_SIZE,Program.DPANEL_SIZE);
		
		for(Line2D l : lines) {
			l.add(boardCenter);
		}
	}
	
	public void clear() {
		lines = new LinkedList<>();
		angle = 0; x = 0; y = 0;
	}
	
	public void clearRules() {
		rules = new String[26];
//		rules['F'-'A'] = "F";

	}
	
	public void setValue(int numberOfIteration, int rotationAngle,int numberOfSide, int numberOfSegment) {
		this.numberOfIteration = numberOfIteration;
		this.rotationAngle = rotationAngle;
		this.numberOfSide = numberOfSide;
		this.numberOfSegment = numberOfSegment;
	}
	public void setValueTree(int numberOfIteration, int rotationAngle,int numberOfBranch, double lengthOfBranch) {
		this.numberOfIteration = numberOfIteration;
		this.rotationAngle = rotationAngle;
		this.numberOfBranch = numberOfBranch;
		this.lengthOfBranch = lengthOfBranch;
	}
	public void setValueTreeA(int numberOfIteration) {
		this.numberOfIteration = numberOfIteration;
	}
	
	public int getNumberOfIteration() {
		return numberOfIteration;
	}
	public int getNumberOfSide() {
		return numberOfSide;
	}
	public int getNumberOfSegment() {
		return numberOfSegment;
	}
	public double getAngle() {
		return rotationAngle;
	}
	public int getNumberOfLine() {
		return lines.size();
	}
	public int getNumberOfBranch() {
		return numberOfBranch;
	}
	public double getLengthOfBranch() {
		return lengthOfBranch;
	}
}
