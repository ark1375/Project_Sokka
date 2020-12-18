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

import java.lang.Math;
import java.util.Arrays;
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
		  p.add(v1);
		  p.add(v2);
		  p.add(v3);
		  p.add(v4);
		  p.add(v5);
		  p.add(v6);
		  p.add(v7);
		  p.add(v8);
		  p.add(v9);
		  p.add(v10);
		  p.add(v11);
		  p.add(v12);
		  p.add(v13);
		  p.add(v14);
		  p.add(v15);
		  p.add(v16);
		  p.add(v17);

		 ArrayList<Point2D.Double> criticalVertecies=new ArrayList<Point2D.Double>();


		//checking if the first and last nodes are count as critical vertecies
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

		 ArrayList<Integer> labels = label_vertices(p, x , criticalVertecies);
		 System.out.println(Arrays.toString(labels.toArray()));

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
		double previousVertexHalfPlane = previousVertex.y - currentVertex.y - slope * (previousVertex.x - currentVertex.x); ///calculating if v_i-1 is on + or - half-plane
		double nextVertexHalfPlane=nextVertex.y-currentVertex.y - slope*(nextVertex.x-currentVertex.x);    ///////calculating if v_i+1 is on + or - half-plane
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
                                     
	public static ArrayList<Integer> label_vertices(
								ArrayList<Point2D.Double> polygon ,
								 	Point2D.Double trmCord ,
									ArrayList<Point2D.Double> criticalVertecies
								){
		ArrayList<Integer> labels = new ArrayList<Integer>();

		for ( int i=0 ; i < criticalVertecies.size() ; i++){

			/* Hypothesis:
				In this part we are determining if the v_i-1, v_i, v_i+1 is a right-hand or left-hand corner.
				I suspect that considering the angle that this corner makes if the sin of this angle is 0 or positive,
				this is a left-turn corner and if the sin is 0 or negative, the corner is a right-hand corner.

				For that we use the law of cosin
				Note: This didn't work because the corners with the angle greater than 180d are R-h but we cannot make a triangle with
						an angle greater than 180d

				Hypothesis 2: Using a linear equation we can determin if a point is above or below a line, so usign v_i and v_i-1
				we create a linear equation (y = mx+b) and turn it into an inequality (y-mx-b <= 0 for the point's bellow the line
				and y-mx-b >= 0 for the points above the line) using this inequlity we can determine if the third point is above or
				bellow the line

				Hypothesis 3: We can use the cross-product of the vectors v_i-1,v_i and v_i,v_i+1. This should indicate that the second
				vector is on the right hand of the first vector (an RH turn) or in the left land of the first vector (a LH turn) 
				Note that instead of the cross product we can also use the determinant of the below matrix
			    	   _                   _ 
					  | vec_1.x   vec_2.x  |  
			   	  det|	vec_1.y   vec_2.y |
					|_                  _|

			*/

			/* Report:
				It detects the left turn and the righ turn accuratly
			*/
			
			int indx_pre_crit_vert , indx_post_crit_vert;
			int indx_crit_vert = polygon.indexOf( criticalVertecies.get(i) );

			// Because the suitable ds for saving the nodes is a circular array and we have only normal array,
			// we check the end and the beggining node manualy (as they need to be compared to each other)

			if (indx_crit_vert == 0){
				indx_post_crit_vert = 1;
				indx_pre_crit_vert = polygon.size() - 1;
			} 
			
			else if (indx_crit_vert == polygon.size() -1){
				indx_post_crit_vert = 0;
				indx_pre_crit_vert = polygon.size() - 2;
			}

			else {
				indx_post_crit_vert = indx_crit_vert-1;
				indx_pre_crit_vert = indx_crit_vert+1;
			}
			
			Point2D.Double vector_1 = new Point2D.Double(
				polygon.get(indx_pre_crit_vert).x - polygon.get(indx_crit_vert).x, 
				polygon.get(indx_pre_crit_vert).y - polygon.get(indx_crit_vert).y
				);

			Point2D.Double vector_2 = new Point2D.Double(
				polygon.get(indx_crit_vert).x - polygon.get(indx_post_crit_vert).x, 
				polygon.get(indx_crit_vert).y - polygon.get(indx_post_crit_vert).y
				);
				
			double determinant = (vector_1.x * vector_2.y) - (vector_1.y * vector_2.x); 

			// The left hand corner
			if (determinant >= 0){
				double slope = (criticalVertecies.get(i).y - trmCord.y) / (criticalVertecies.get(i).x - trmCord.x);
				double equation_value = (polygon.get(indx_pre_crit_vert).y - trmCord.y) - (slope*(polygon.get(indx_pre_crit_vert).x - trmCord.x));
				
				// V_i-1 is on the negative side of ray
				if (equation_value <= 0)
					labels.add(1);
				else
					labels.add(-1);

			}
			
			// The right-hand corner
			else{
				double slope = (criticalVertecies.get(i).y - trmCord.y) / (criticalVertecies.get(i).x - trmCord.x);
				double equation_value = (polygon.get(indx_pre_crit_vert).y - trmCord.y) - (slope*(polygon.get(indx_pre_crit_vert).x - trmCord.x));
				
				// V_i-1 is on the negative side of ray
				if (equation_value <= 0)
					labels.add(-1);
				else
					labels.add(1);

			}
				
		}

		return labels;

	}

	public static double distance(Point2D.Double a , Point2D.Double b){

		return Math.sqrt( Math.pow(a.x - b.x , 2) + Math.pow(a.y - b.y , 2));
		
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
	     for(int i=0;i<input.size();i++)
	     {
	    	 System.out.println((input.get(i).y-x.y)/(input.get(i).x-x.x) );
	     }
		return input;
		
	}

}

