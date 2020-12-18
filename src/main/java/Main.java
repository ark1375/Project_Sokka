/*
		 * ArrayList<Boolean> lineIntersections=new ArrayList<Boolean>();
		 * ArrayList<Point2D.Double> clone=(ArrayList<Double>) p.clone();
		 * clone.add(p.get(0)); /// making a closed loop for the next for loop double
		 * ray1Slope; double ray2Slope; double edgeSlope; double x1; double x2; double
		 * y1; double y2; double temp1=0; double temp2=0; int counter=0; for(int
		 * i=0;i<p.size();i++) {
		 * ray1Slope=(criticalEdge1.y-transmitterLocation.y)/(criticalEdge1.x-
		 * transmitterLocation.x);
		 * ray2Slope=(criticalEdge2.y-transmitterLocation.y)/(criticalEdge2.x-
		 * transmitterLocation.x);
		 * edgeSlope=(clone.get(i).y-clone.get(i+1).y)/(clone.get(i).x-clone.get(i+1).x)
		 * ; x1=(criticalEdge1.y-transmitterLocation.y+ray1Slope*transmitterLocation.x-
		 * edgeSlope*criticalEdge1.x)/(ray1Slope-edgeSlope);
		 * y1=ray1Slope*(x1-transmitterLocation.x)+transmitterLocation.y;
		 * x2=(criticalEdge2.y-transmitterLocation.y+ray2Slope*transmitterLocation.x-
		 * edgeSlope*criticalEdge2.x)/(ray2Slope-edgeSlope);
		 * y2=ray2Slope*(x2-transmitterLocation.x)+transmitterLocation.y;
		 * System.out.println(x1+" "+y1); System.out.println(x2+" "+y2);
		 * if(clone.get(i).x<clone.get(i+1).x) { temp1=clone.get(i).x;
		 * temp2=clone.get(i+1).x; } else { temp1=clone.get(i+1).x;
		 * temp2=clone.get(i).x; } if((ray1Slope>0 && transmitterLocation.x>x1 &&
		 * transmitterLocation.x>x2) || (ray2Slope>0 && transmitterLocation.x>x1 &&
		 * transmitterLocation.x>x2)) { lineIntersections.add(false); break; }
		 * if((ray1Slope<0 && transmitterLocation.x<x1 && transmitterLocation.x<x2) ||
		 * (ray2Slope<0 && transmitterLocation.x<x1 && transmitterLocation.x<x2)) {
		 * lineIntersections.add(false); break; } lineIntersections.add((x1>temp1 &&
		 * x1<temp2) || (x2>temp1 && x2<temp2)); } for(int
		 * i=0;i<lineIntersections.size();i++) { if(lineIntersections.get(i)) counter
		 * ++; }
		 * 
		 * return counter;
		 */
import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import javafx.scene.canvas.GraphicsContext;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import javax.sound.midi.Soundbank;
import javax.swing.JFrame;
public final class Main extends JFrame {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		  Point2D.Double v1=new Double(346, 499);
		  Point2D.Double v2=new Double(539, 511);
		  Point2D.Double v3=new Double(544,365);
		  Point2D.Double v4=new Double(666, 281);
		  Point2D.Double v5=new Double(523, 190);
		  Point2D.Double v6=new Double(743, 156);
		  Point2D.Double v7=new Double(713, 302);
		  Point2D.Double v8=new Double(862,406);
		  Point2D.Double v9=new Double(883, 164);
		  Point2D.Double v10=new Double(572, 104);
		  Point2D.Double v11=new Double(411, 130);
		  Point2D.Double v12=new Double(285,191);
		  Point2D.Double v13=new Double(286, 364);
		  Point2D.Double v14=new Double(373, 342);
		  Point2D.Double v15=new Double(378, 246);
		  Point2D.Double v16=new Double(473, 262);
		  Point2D.Double v17=new Double(476, 356);
		  Point2D.Double x=new Double(502,421);
		  ArrayList<Point2D.Double> p=new ArrayList<Point2D.Double>();
		  p.add(v1); p.add(v2); p.add(v3); p.add(v4); p.add(v5);p.add(v6);p.add(v7);p.add(v8);
		  p.add(v9);p.add(v10);p.add(v11);p.add(v12);p.add(v13);p.add(v14);p.add(v15);p.add(v16);p.add(v17);
		
		 ArrayList<Point2D.Double> criticalVertecies=criticalVertecies(p, x);
		 System.out.println(sortAngularly(criticalVertecies, x));
		 DrawPolygon dp=new DrawPolygon(p,criticalVertecies,x);
		 dp.main(p,criticalVertecies,x);
		 for(int i=0;i<criticalVertecies.size();i++)
		 {
			 intersectionPoints(p, criticalVertecies.get(i), x);
		 }
		//intersectionPoints(p, v2, x);
	}
	public static boolean isCritical(Point2D.Double transmitterLocation,Point2D.Double currentVertex,Point2D.Double previousVertex,Point2D.Double nextVertex)
	{
		double slope=(currentVertex.y-transmitterLocation.y)/(currentVertex.x-transmitterLocation.x);   //calculating slope of xv_i vector 
		double previousVertexHalfPlane=previousVertex.y-currentVertex.y-slope*(previousVertex.x-currentVertex.x); ///calculating if v_i-1 is on + or - half-plane
		double nextVertexHalfPlane=nextVertex.y-currentVertex.y-slope*(nextVertex.x-currentVertex.x);    ///////calculating if v_i+1 is on + or - half-plane
		return (previousVertexHalfPlane*nextVertexHalfPlane>0);		///it means both were above or below half-plane and therefore critical 
	}
	public static ArrayList<Point2D.Double> intersectionPoints(ArrayList<Point2D.Double> p,Point2D.Double criticalVertex,Point2D.Double x)
	{
		
		////print blocks in this method is just for debugging and should be removed...
		ArrayList<Line> edges = new ArrayList<Line>();
		ArrayList<Point2D.Double> validIntersections=new ArrayList<Point2D.Double>();
		ArrayList<Point2D.Double> intersections=new ArrayList<Point2D.Double>();
		for(int i=0;i<p.size()-1;i++)
			edges.add(new Line(p.get(i),p.get(i+1)));     //// creates an array of lines containing edges of polygon using Line class
		edges.add(new Line(p.get(p.size()-1), p.get(0)));    
		Line ray=new Line(x, criticalVertex);
		for(Line l:edges)     ///find the intersection of each edge "l" with ray(line) from x to critical edge
		{
			intersections.add(Line.intersection(l, ray));   
		}

		for(int i=0;i<intersections.size()-1;i++)
		{
		if( intersections.get(i).x<=Math.max(p.get(i).x,p.get((i+1)%p.size()).x) && intersections.get(i).x>=Math.min(p.get(i).x,p.get((i+1)%p.size()).x))
		{
			validIntersections.add(intersections.get(i));
		}
		}
		if(criticalVertex.x>x.x)   ////following if conditions removes non-valid intersection points where intersection happened before critical edge
		{
			for(int i=0;i<validIntersections.size();i++)
			{
				if(validIntersections.get(i).x<criticalVertex.x )
				{
					validIntersections.remove(i);
					i--;
				}
			}
		}
		else
		{
			for(int i=0;i<validIntersections.size();i++)
			{
				if(validIntersections.get(i).x>criticalVertex.x)
				{
					validIntersections.remove(i);
					i--;
				}
			}
		}
		for(int i=0;i<validIntersections.size();i++)     /////////these two for blocks removes duplicates
		{
			for(int j=i+1;j<validIntersections.size();j++)
			{
				if(validIntersections.get(i).equals(validIntersections.get(j)))
				{
					validIntersections.remove(j);
				}
			}
		}
		for(int i=0;i<validIntersections.size();i++)   
		{
			System.out.println(validIntersections.get(i));
		}
		
		return validIntersections;
	}
	public static ArrayList<Point2D.Double> criticalVertecies(ArrayList<Point2D.Double> p,Point2D.Double x)
	{
		 ArrayList<Point2D.Double> criticalVertecies=new ArrayList<Point2D.Double>();
		 if(isCritical(x, p.get(0), p.get(p.size()-1), p.get(1)))
		 {
			 criticalVertecies.add(p.get(0));
		 }
		 if(isCritical(x, p.get(p.size()-1), p.get(p.size()-2), p.get(0)))
		 {
			 criticalVertecies.add(p.get(p.size()));
	
		 }
		 for(int i=1;i<p.size()-1;i++)
		 {
			if(isCritical(x, p.get(i), p.get(i-1), p.get(i+1)))
			{
				criticalVertecies.add(p.get(i));
			}
		 }
		 return criticalVertecies;
	}
	public static ArrayList<Point2D.Double> sortAngularly(ArrayList<Point2D.Double> vertecies,Point2D.Double x)
	{
		ArrayList<Point2D.Double> firstQuarter=new ArrayList<Point2D.Double>();
		ArrayList<Point2D.Double> secondQuarter=new ArrayList<Point2D.Double>();
		ArrayList<Point2D.Double> thirdQuarter=new ArrayList<Point2D.Double>();
		ArrayList<Point2D.Double> forthQuarter=new ArrayList<Point2D.Double>();
		ArrayList<Point2D.Double> output=new ArrayList<Point2D.Double>();
		///to sort point angularly around x absolute value of tangent isn't enough,we find which quarter point places
		///by the following for
		for(int i=0;i<vertecies.size();i++)
		{
			if(vertecies.get(i).x>=x.x && vertecies.get(i).y>=vertecies.get(i).y)
				firstQuarter.add(vertecies.get(i));
			else if(vertecies.get(i).x<=x.x && vertecies.get(i).y>=vertecies.get(i).y)
				secondQuarter.add(vertecies.get(i));
			else if(vertecies.get(i).x<=x.x && vertecies.get(i).y<=vertecies.get(i).y)
				thirdQuarter.add(vertecies.get(i));
			else 
				forthQuarter.add(vertecies.get(i));
		}
	///absolute value of tangent is ascending descending ascending descending respective to the quarters so we do the following
		firstQuarter=sortByTangent(firstQuarter, x, true);
		secondQuarter=sortByTangent(secondQuarter, x, false);
		thirdQuarter=sortByTangent(thirdQuarter, x, true);
		forthQuarter=sortByTangent(forthQuarter, x, false);
	///at the end we add all the quarters,origin of starting point doesn't matter,only the order of quarters matter
		for(int i=0;i<firstQuarter.size();i++)
			output.add(firstQuarter.get(i));
		for(int i=0;i<secondQuarter.size();i++)
			output.add(secondQuarter.get(i));
		for(int i=0;i<thirdQuarter.size();i++)
			output.add(thirdQuarter.get(i));
		for(int i=0;i<forthQuarter.size();i++)
			output.add(forthQuarter.get(i));
		return output;
	}
	public static ArrayList<Point2D.Double> sortByTangent(ArrayList<Point2D.Double> input,Point2D.Double x, boolean ascending)
	{
	///sort points angularly with the respect to absolute value of the tangent with origin of x
		///it uses bubble sort and probably could be improved...
		Point2D.Double temp = new Point2D.Double(0,0);  
	     if (ascending)
		     {
	         for(int i=0; i < input.size(); i++)
	         		{  
	                 for(int j=1; j < (input.size()-i); j++)
	                 		{  
	                          if(Math.abs((input.get(j-1).y-x.y)/(input.get(j-1).x-x.x)) > Math.abs((input.get(j).y-x.y)/(input.get(j).x-x.x)))
							{  
	                                 //swap elements  
	                                 temp = input.get(j-1);  
	                                 input.set(j-1,input.get(j)); 
	                                 input.set(j,temp);  
	                         }  
	                          
	                 		} 
	                 
	         		}  
		     }
	     else
	     {
	         for(int i=0; i < input.size(); i++)
	         		{  
	                 for(int j=1; j < (input.size()-i); j++)
	                 		{  
	                          if(Math.abs((input.get(j-1).y-x.y)/(input.get(j-1).x-x.x)) < Math.abs((input.get(j).y-x.y)/(input.get(j).x-x.x)))
							{  
	                                 //swap elements  
	                                 temp = input.get(j-1);  
	                                 input.set(j-1,input.get(j)); 
	                                 input.set(j,temp);  
	                         }  
	                          
	                 		} 
	                 
	         		}  
		     }
	    return input;
	}
}

