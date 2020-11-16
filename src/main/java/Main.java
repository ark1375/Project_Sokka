import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import javax.sound.midi.Soundbank; 
public final class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		 * Point2D.Double v1=new Double(0, 0); Point2D.Double v2=new Double(1, 0);
		 * Point2D.Double v3=new Double(1/2,1/2); Point2D.Double v4=new Double(1, 1);
		 * Point2D.Double v5=new Double(0, 1); Point2D.Double TL=new Double(0.7,0.8);
		 * ArrayList<Point2D.Double> p=new ArrayList<Point2D.Double>(); p.add(v1);
		 * p.add(v2); p.add(v3); p.add(v4); p.add(v5);
		 * //System.out.println(isCritical(TL, v5, v1, v4)); test();
		 */
		Line l1=new Line(new Double(1,1),new Double(0,0));
		Line l2=new Line(new Double(0,1),new Double(1,0));
		System.out.println(l2);
		System.out.println(Line.intersection(l1, l2));
	}
	public static boolean isCritical(Point2D.Double transmitterLocation,Point2D.Double currentEdge,Point2D.Double previousEdge,Point2D.Double nextEdge)
	{
		double slope=(currentEdge.y-transmitterLocation.y)/(currentEdge.x-transmitterLocation.x);   //calculating slope of xv_i vector 
		double previousEdgeHalfPlane=previousEdge.y-currentEdge.y-slope*(previousEdge.x-currentEdge.x); ///calculating if v_i-1 is on + or - half-plane
		double nextEdgeHalfPlane=nextEdge.y-currentEdge.y-slope*(nextEdge.x-currentEdge.x);    ///////calculating if v_i+1 is on + or - half-plane
		return (previousEdgeHalfPlane*nextEdgeHalfPlane>0);		///it means both were above or below half-plane and they are critical 
	}
	public static int intersectionPoints(ArrayList<Point2D.Double> p,Point2D.Double criticalEdge1,Point2D.Double criticalEdge2,Point2D.Double transmitterLocation)
	{
		ArrayList<Boolean> lineIntersections=new ArrayList<Boolean>();
		ArrayList<Point2D.Double> clone=(ArrayList<Double>) p.clone();
		clone.add(p.get(0)); /// making a closed loop for the next for loop
		double ray1Slope;
		double ray2Slope;
		double edgeSlope;
		double x1;
		double x2;
		double y1;
		double y2;
		double temp1=0;
		double temp2=0;
		int counter=0;
		for(int i=0;i<p.size();i++)
		{
			ray1Slope=(criticalEdge1.y-transmitterLocation.y)/(criticalEdge1.x-transmitterLocation.x);
			ray2Slope=(criticalEdge2.y-transmitterLocation.y)/(criticalEdge2.x-transmitterLocation.x);
			edgeSlope=(clone.get(i).y-clone.get(i+1).y)/(clone.get(i).x-clone.get(i+1).x);
			x1=(criticalEdge1.y-transmitterLocation.y+ray1Slope*transmitterLocation.x-edgeSlope*criticalEdge1.x)/(ray1Slope-edgeSlope);
			y1=ray1Slope*(x1-transmitterLocation.x)+transmitterLocation.y;
			x2=(criticalEdge2.y-transmitterLocation.y+ray2Slope*transmitterLocation.x-edgeSlope*criticalEdge2.x)/(ray2Slope-edgeSlope);
			y2=ray2Slope*(x2-transmitterLocation.x)+transmitterLocation.y;
			System.out.println(x1+" "+y1);
			System.out.println(x2+" "+y2);
			if(clone.get(i).x<clone.get(i+1).x)
			{
				temp1=clone.get(i).x;
				temp2=clone.get(i+1).x;
			}
			else {
				 temp1=clone.get(i+1).x;
				 temp2=clone.get(i).x;
				}
			if((ray1Slope>0 && transmitterLocation.x>x1 && transmitterLocation.x>x2) || (ray2Slope>0 && transmitterLocation.x>x1 && transmitterLocation.x>x2))
			{
				lineIntersections.add(false);
				break;
			}
			if((ray1Slope<0 && transmitterLocation.x<x1 && transmitterLocation.x<x2) || (ray2Slope<0 && transmitterLocation.x<x1 && transmitterLocation.x<x2))
			{
				lineIntersections.add(false);
				break;
			}
			lineIntersections.add((x1>temp1 && x1<temp2) || (x2>temp1 && x2<temp2));
		}
		for(int i=0;i<lineIntersections.size();i++)
		{
			if(lineIntersections.get(i))
				counter ++;
		}
		
		return counter;
	}
	public static void test()
	{
		Double criticalEdge1=new Double(2,1);
		Double criticalEdge2=new Double(0,1);
		Double transmitterLocation=new Double(0.7,0.8);
		double ray1Slope=(criticalEdge1.y-transmitterLocation.y)/(criticalEdge1.x-transmitterLocation.x);
		double ray2Slope=(criticalEdge2.y-transmitterLocation.y)/(criticalEdge2.x-transmitterLocation.x);
		System.out.println(ray1Slope);
		System.out.println(ray2Slope);
		double edgeSlope=3;
		double x1=(criticalEdge1.y-transmitterLocation.y+ray1Slope*transmitterLocation.x-edgeSlope*criticalEdge1.x)/(ray1Slope-edgeSlope);
		double y1=ray1Slope*(x1-transmitterLocation.x)+transmitterLocation.y;
		double x2=(criticalEdge2.y-transmitterLocation.y+ray2Slope*transmitterLocation.x-edgeSlope*criticalEdge2.x)/(ray2Slope-edgeSlope);
		double y2=ray2Slope*(x2-transmitterLocation.x)+transmitterLocation.y;
		System.out.println(x1+" "+y1);
		System.out.println(x2+" "+y2);
	}
}
