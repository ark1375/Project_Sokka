import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D.Double;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
public class DrawPolygon extends JFrame {
	ArrayList<Point2D.Double> p;
	ArrayList<Point2D.Double> criticalVertecies;
	Point2D x;
   public DrawPolygon(ArrayList<Point2D.Double> p , ArrayList<Point2D.Double> criticalVertecies,Point2D x) {
	   this.p=p;
	   this.criticalVertecies=criticalVertecies;
	   this.x=x;
      JPanel panel = new JPanel();
      getContentPane().add(panel);
      setSize(1000, 1000);
   }
   public void paint(Graphics gp) { 
	   super.paint(gp); Graphics2D graphics = (Graphics2D) gp;
	   graphics.setStroke(new BasicStroke(3));
	   ArrayList<Line2D> lines=new ArrayList<Line2D>();
      lines.add(new Line2D.Double(p.get(0),p.get(p.size()-1)));
      for(int i=0;i<p.size()-1;i++)
    	  lines.add(new Line2D.Double(p.get(i),p.get(i+1)));
      for(Line2D line:lines)
    	  graphics.draw(line);
      graphics.setPaint(Color.RED);
      for(Point2D vertex:criticalVertecies)
    	  graphics.draw(new Ellipse2D.Double(vertex.getX(),vertex.getY(),5,5));
      graphics.setPaint(Color.GREEN);
      graphics.draw(new Ellipse2D.Double(x.getX(),x.getY(),5,5));
      
   }
   public static void main(ArrayList<Point2D.Double> p,ArrayList<Point2D.Double> criticalVertecies,Point2D x) {
      DrawPolygon demo = new DrawPolygon(p,criticalVertecies, x);
      demo.setVisible(true);
   }
}
