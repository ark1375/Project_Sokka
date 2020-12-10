import java.awt.geom.Point2D;
public class Line {
	double slope;
	double Yintercept;
	boolean isVertical=false;
	double x;
	public Line(Point2D.Double first,Point2D.Double second)
	{
		double deltaY=second.y-first.y;
		double deltaX=second.x-first.x;
		if(deltaX==0)
		{
			isVertical=true;
			this.x=first.x;
			return;
		}
		this.slope=deltaY/deltaX;
		this.Yintercept=first.y-slope*first.x;
	}
	public String toString()
	{
		if(Yintercept!=0)
			return("y="+slope+"x"+"+"+Yintercept);
		if(isVertical)
			return("x="+x);
			return("y="+slope+"x");
			
	}
	public static Point2D.Double intersection(Line l1,Line l2)
	{
		if((l1.isVertical && l2.isVertical) || (l1.slope==l2.slope))
		{
			System.err.println("invalid case");
			System.exit(0);
		}
		if(l1.isVertical)
		{
			System.out.println("case 1");
			double x=l1.x;
			double y=x*l2.slope+l2.Yintercept;
			return new Point2D.Double(x, y);
		}
		else if(l2.isVertical)
		{
			System.out.println("case 2");
			double x=l2.x;
			double y=x*l1.slope+l1.Yintercept;
			return new Point2D.Double(x, y);
		}
		System.out.println("case 3");
		double x=(l1.Yintercept-l2.Yintercept)/(l2.slope-l1.slope);
		double y=x*l1.slope+l1.Yintercept;
		return new Point2D.Double(x, y);
	}

}
