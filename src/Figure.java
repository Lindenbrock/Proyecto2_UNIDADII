import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class Figure {
	GeneralPath PFig2D;
	Shape Fig2D;
	double Coordinates[];
	Point2D Points[];
	Color one,two;
	
	
	public Figure() {
		PFig2D = new GeneralPath();
		
		//Llenar vector con los puntos X y Y de la figura
		Coordinates = new double[] {
				273,51, 289,51, 297,56, 302,56, 312,52, 325,47, 338,41, 349,41, 362,42, 374,46, 380,56, 388,59, 391,67, 397,75, 397,81, 
				398,93, 398,102, 393,111, 388,116, 384,118, 379,121, 381,130, 389,140, 394,149, 400,160, 392,177, 397,191, 407,204, 407,208, 
				404,213, 399,215, 395,215, 404,226, 400,233, 397,240, 389,243, 395,247, 397,252, 397,259, 398,269, 400,272, 400,278, 401,288, 
				398,295, 391,301, 385,306, 379,312, 373,312, 366,312, 358,315, 349,317, 460,384, 468,392, 473,401, 476,412, 481,423, 484,435, 
				487,447, 489,459, 490,467, 491,479, 22,479, 23,465, 26,449, 29,432, 34,415, 41,400, 47,387, 50,378, 58,364, 65,354, 77,346, 
				98,337, 117,328, 135,321, 165,313, 185,304, 199,296, 209,285, 216,270, 226,263, 230,260, 224,248, 214,232, 207,221, 205,211, 
				198,196, 196,180, 193,173, 195,162, 193,151, 195,140, 192,132, 197,119, 205,104, 214,92, 225,82, 233,72, 242,61, 255,53
		};
		
		Points = new Point2D[Coordinates.length/2];
		
		//Asigar a los puntos (Point2D) las coordenadas del vector Coordinates
		for(int pos=0,i=0,j=1;pos<Points.length;pos++,i+=2,j+=2)
			Points[pos] = new Point2D.Double(Coordinates[i],Coordinates[j]);
		
		//		CREAR LA FIGURA CON EL VECTOR DE COORDENADAS
			PFig2D.moveTo(Points[0].getX(),Points[0].getY());
			for(int i=1;i<Points.length;i++)
				PFig2D.lineTo(Points[i].getX(), Points[i].getY());
			PFig2D.closePath();
			Fig2D = PFig2D;
		
		one = new Color(48,48,48);
		two = Color.WHITE;
	}
	
	//		DIBUJAR LA FIGURA
	public void drawShape(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.draw(Fig2D);
	}
	
	//		DEVOLVER EL MÁXIMO EN X DE LA FIGURA
	public Point2D getMaxMinXFigure(){		
		double xmax = Fig2D.getBounds().getMaxX(),xmin = Fig2D.getBounds().getMinX();
		Point2D p = new Point2D.Double(xmax,xmin);
		return p;
	}
		
	//		DEVOLVER EL MÍNIMO EN X DE LA FIGURA
	public Point2D getMaxMinYFigure(){
		double ymax = Fig2D.getBounds().getMaxY(),ymin = Fig2D.getBounds().getMinY();
		Point2D p = new Point2D.Double(ymax,ymin);
		return p;
	}
	
	/*		-----TRANSFORMACIONES-----		*/
	
	//		ROTAR LA FIGURA
	public void rotateFig(double deg) {
		AffineTransform at = new AffineTransform();
		at.rotate(Math.toRadians(deg), Fig2D.getBounds().getCenterX(), Fig2D.getBounds().getCenterY());
		Fig2D=at.createTransformedShape(Fig2D);
	}
	
	//		ESCALAR LA FIGURA
	public void scaleFig(double esc) {
		AffineTransform at = new AffineTransform();
		at.translate(Fig2D.getBounds2D().getCenterX(),Fig2D.getBounds2D().getCenterY());
		at.scale(esc, esc);
		at.translate(-Fig2D.getBounds2D().getCenterX(),-Fig2D.getBounds2D().getCenterY());
		Fig2D=at.createTransformedShape(Fig2D);
	}
	
	//		TRASLADAR LA FIGURA
	public void translateFig(double tX, double tY) {
		AffineTransform at = new AffineTransform();
		at.translate(tX,tY);
		Fig2D=at.createTransformedShape(Fig2D);
	}
}