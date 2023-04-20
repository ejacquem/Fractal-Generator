
public class Point2D {
	private double x;
	private double y;
	
	Point2D(){
		this(0,0);
	}
	
	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public Point2D copyOf() {
		return new Point2D(getX(),getY());
	}
	
	public void multiply(double d) {
		x*=d;
		y*=d;
	}
	
	public void add(Point2D d) {
		x+=d.getX();
		y+=d.getY();
	}
	
	public void substract(Point2D d) {
		x-=d.getX();
		y-=d.getY();
	}
	
	@Override
	public String toString() {
		return x +", "+y+"\n";
	}
}
