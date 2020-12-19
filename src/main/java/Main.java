
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import javax.swing.JFrame;
public final class Main extends JFrame {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		  Point2D.Double v1=new Double(97, 47);
		  Point2D.Double v2=new Double(290, 39);
		  Point2D.Double v3=new Double(295,184);
		  Point2D.Double v4=new Double(416, 265);
		  Point2D.Double v5=new Double(274, 359);
		  Point2D.Double v6=new Double(494, 392);
		  Point2D.Double v7=new Double(464, 247);
		  Point2D.Double v8=new Double(612,142);
		  Point2D.Double v9=new Double(637, 383);
		  Point2D.Double v10=new Double(323, 445);
		  Point2D.Double v11=new Double(162, 416);
		  Point2D.Double v12=new Double(36,356);
		  Point2D.Double v13=new Double(36, 184);
		  Point2D.Double v14=new Double(125, 205);
		  Point2D.Double v15=new Double(129, 301);
		  Point2D.Double v16=new Double(225, 286);
		  Point2D.Double v17=new Double(226, 192);
		  Point2D.Double x=new Double(252,125);

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
		 ArrayList<Integer> labels=new ArrayList<Integer>();
		 criticalVertecies(x, p, criticalVertecies, labels);
	//	 for(int i=0;i<criticalVertecies.size();i++)
	//	 {
		//	 System.out.print(criticalVertecies.get(i)+"   "+labels.get(i));
		//	 System.out.println();
		// }
		 DrawPolygon dp=new DrawPolygon(p,criticalVertecies,x);
	//	 dp.main(p,criticalVertecies,x);
		 ArrayList<Integer> intersectionLabels=new ArrayList<Integer>();
		 ArrayList<Point2D.Double> allIntersections=new ArrayList<Point2D.Double>();
		 ArrayList<Integer> allIntersectionLabels=new ArrayList<Integer>();
		 ArrayList<Point2D.Double> temp=new ArrayList<Point2D.Double>();
		 for(int i=0;i<criticalVertecies.size();i++)
		 {
			 temp=intersectionPoints(p, criticalVertecies.get(i), x,intersectionLabels);
			 allIntersections.addAll(temp);
			 allIntersectionLabels.addAll(intersectionLabels);
		 }
		 for(int i=0;i<allIntersections.size();i++)
		 {
			 System.out.println(allIntersections.get(i)+"  "+allIntersectionLabels.get(i));
		 }
	}
	
	public static void criticalVertecies(Point2D.Double x,ArrayList<Point2D.Double> p,ArrayList<Point2D.Double> criticalVertecies, ArrayList<Integer> labels)
	{
		 if(isCritical(x, p.get(0), p.get(p.size()-1), p.get(1)))
		 {
			 criticalVertecies.add(p.get(0));
			 labels.add(labeling(x, p.get(0), p.get(p.size()-1),p.get(1)));
		 }
		 if(isCritical(x, p.get(p.size()-1), p.get(p.size()-2), p.get(0)))
		 {
			 criticalVertecies.add(p.get(p.size()));
			 labels.add(labeling(x, p.get(p.size()-1), p.get(p.size()-2), p.get(0)));
		 }

		 for(int i=1;i<p.size()-1;i++)
		 {
			if(isCritical(x, p.get(i), p.get(i-1), p.get(i+1)))
			{
				criticalVertecies.add(p.get(i));
				labels.add(labeling(x, p.get(i), p.get(i-1), p.get(i+1)));
			}
		 }
	}
	public static boolean isCritical(Point2D.Double transmitterLocation,Point2D.Double currentVertex,Point2D.Double previousVertex,Point2D.Double nextVertex)
	{
		double slope=(currentVertex.y-transmitterLocation.y)/(currentVertex.x-transmitterLocation.x);   //calculating slope of xv_i vector 
		double previousVertexHalfPlane = previousVertex.y - currentVertex.y - slope * (previousVertex.x - currentVertex.x); ///calculating if v_i-1 is on + or - half-plane
		double nextVertexHalfPlane=nextVertex.y-currentVertex.y - slope*(nextVertex.x-currentVertex.x);    ///////calculating if v_i+1 is on + or - half-plane
		return (previousVertexHalfPlane*nextVertexHalfPlane>0);		///it means both were above or below half-plane and therefore critical 
	}
	public static ArrayList<Point2D.Double> intersectionPoints(ArrayList<Point2D.Double> p,Point2D.Double criticalVertex,Point2D.Double x,ArrayList<Integer> labels)
	{	
		////print blocks in this method is just for debugging and should be removed...
		ArrayList<Line> edges = new ArrayList<Line>();
		ArrayList<Point2D.Double> validIntersections=new ArrayList<Point2D.Double>();
		ArrayList<Point2D.Double> intersections=new ArrayList<Point2D.Double>();
		
		Point2D.Double temp=new Double();
		for(int i=0;i<p.size()-1;i++)
			edges.add(new Line(p.get(i),p.get(i+1)));     //// creates an array of lines containing edges of polygon using Line class
		edges.add(new Line(p.get(p.size()-1), p.get(0)));    
		Line ray=new Line(x, criticalVertex);
		double vjHalfPlane=0;
		double viHalfPlane=0;
		for(Line l:edges)     ///find the intersection of each edge "l" with ray(line) from x to critical edge
		{
			    ///added for not computing function two times 
			   ////making sure we don't add criticalVertex as an intersection,we already have this...
			intersections.add(Line.intersection(l, ray));   
		}
		for(int i=0;i<intersections.size();i++)
		{
		if( intersections.get(i).x<Math.max(p.get(i).x,p.get((i+1)%p.size()).x) && intersections.get(i).x>Math.min(p.get(i).x,p.get((i+1)%p.size()).x) && (Math.abs(intersections.get(i).x-criticalVertex.x)>0.0000001 || Math.abs(intersections.get(i).y-criticalVertex.y)>0.0000001))
		{
			validIntersections.add(intersections.get(i));
			vjHalfPlane=p.get(i).y-ray.slope*p.get(i).x-ray.Yintercept;
			int position=p.indexOf(criticalVertex); ///finding the position of critical vertex in polygon
			if(position==0)
				temp=p.get(p.size()-1);
			else
				temp=p.get(position-1);
			viHalfPlane=temp.y-ray.slope*temp.x-ray.Yintercept;
			if(viHalfPlane*vjHalfPlane>0)
				labels.add(-2);
			else
				labels.add(2);
			
		}
		}
		if(criticalVertex.x>x.x)   ////following if conditions removes non-valid intersection points where intersection happened before critical edge
		{
			for(int i=0;i<validIntersections.size();i++)
			{
				if(validIntersections.get(i).x<criticalVertex.x )
				{
					validIntersections.remove(i);
					labels.remove(i);
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
					labels.remove(i);
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
					labels.remove(i);
				}
			}
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

			/* 
				Writne: Ark1375
				Hypothesis 3: We can use the cross-product of the vectors v_i-1,v_i and v_i,v_i+1. This should indicate that the second
				vector is on the right hand of the first vector (an RH turn) or in the left land of the first vector (a LH turn) 
				Note that instead of the cross product we can also use the determinant of the below matrix
			    	   _                   _ 
					  | vec_1.x   vec_2.x  |  
			   	  det|	vec_1.y   vec_2.y |
					|_                  _|

			*/

			/* Report:
				It works correctly
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
				//Adding a sloopy fix for the last two points, this code dosent support any logical sense be aware: code 11
				int label;
				// V_i-1 is on the negative side of ray
				if (equation_value <= 0)
					label = 1;
				else
					label = -1;

				//code 11: slope should be lesser than zero, another slopy fix
				if (slope > 0)
					labels.add(-1* label);
				else
					labels.add(label);

				
			}
			
			// The right-hand corner
			else{
				double slope = (criticalVertecies.get(i).y - trmCord.y) / (criticalVertecies.get(i).x - trmCord.x);
				double equation_value = (polygon.get(indx_pre_crit_vert).y - trmCord.y) - (slope*(polygon.get(indx_pre_crit_vert).x - trmCord.x));
				//Adding a sloopy fix for the last two points, this code dosent support any logical sense be aware: code 11
				int label;
				// V_i-1 is on the negative side of ray
				if (equation_value <= 0)
					label = -1;
				else
					label= 1;

				//code 11: slope should be lesser than zero, another slopy fix
				if (slope > 0)
					labels.add(-1* label);
				else
					labels.add(label);

			}
				
		}

		return labels;

	}

	public static int labeling(Point2D.Double x,Point2D.Double criticalVertex, Point2D.Double previousVertex, Point2D.Double nextVertex)
	{
		Boolean rightTurn=false;
	
		 ///Refer to https://math.stackexchange.com/questions/2121112/how-do-i-visualize-if-three-points-represent-a-right-or-left-turn
		rightTurn= ((nextVertex.x-previousVertex.x)*(criticalVertex.y-previousVertex.y)-(nextVertex.y-previousVertex.y)*(criticalVertex.x-previousVertex.x)<0);  
		///Refer to https://math.stackexchange.com/questions/274712/calculate-on-which-side-of-a-straight-line-is-a-given-point-located
		int label= (int) Math.signum((previousVertex.x-x.x)*(criticalVertex.y-x.y)-(previousVertex.y-x.y)*(criticalVertex.x-x.x));
		if(rightTurn)
			return -label;
		else 
			return label;
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

