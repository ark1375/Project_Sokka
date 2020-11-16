import java.awt.geom.Point2D;
public class Line {
	double slope;
	double Yintercept;
	public Line(Point2D.Double first,Point2D.Double second)
	{
		this.slope=(first.y-second.y)/(first.x-second.x);
		this.Yintercept=first.y-slope*first.x;
	}
	public String toString()
	{
		if(Yintercept!=0)
			return("y="+slope+"x"+"+"+Yintercept);
		else 
			return("y="+slope+"x");
	}
	public static Point2D.Double intersection(Line l1,Line l2)
	{
		double x=(l1.Yintercept-l2.Yintercept)/(l2.slope-l1.slope);
		double y=x*l1.slope+l1.Yintercept;
		return new Point2D.Double(x, y);
	}

}
