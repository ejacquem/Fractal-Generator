
public class Line2D {
	private final Point2D p1;
	private final Point2D p2;
	
	public Line2D(Point2D p1, Point2D p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public Point2D getP1() {
		return p1;
	}

	public Point2D getP2() {
		return p2;
	}
	
	public void multiply(double d) {
		p1.multiply(d);
		p2.multiply(d);
	}
	
	public void add(Point2D p) {
		p1.add(p);
		p2.add(p);
	}
	
	public void substract(Point2D p) {
		p1.substract(p);
		p2.substract(p);
	}

}
