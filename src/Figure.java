import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Figure {
	GeneralPath PFig2D;
	Shape Fig2D;
	double Coordinates[];
	Point2D Points[];
	Color one,two;
	int GPData[] = new int[] {0,0,0,0,0,0,0,0,0};
	/*	GPData almacena datos para pintar con gradiente la digura 
	 	Si el ultimo elemento del vector es 0, no se aplica gradiente*/
	String ImgData[] = new String[] {"0","0"};
	/*	ImgData almacena la routa de la imagen para rellenar
	 	Si el ultimo elemento del vector es 0, no se aplica el relleno*/
	int StrokeData[] = new int[] {3,1,1,0,0,0,0,0,0,0,0,0};
	/*	StrokeData recibe los argumentos para crear un borde en la figura
	 	Si el penultimo elemento del vector es 0, no se aplica color
	 	Si el ultimo elemento del vector es 0 no se aplican efectos de borde */
	float StrokeDash[];
	// Para convertir los datos para el dash de entero a flotante
	BasicStroke bst;
	
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
		
		if(GPData[8] == 1)
			paintGradientFig(g);
		
		if(Integer.parseInt(ImgData[1]) == 1)
			imageFillFig(g);
		
		if(StrokeData[11] == 1) {
			setStrokeFig(g);
		}
		
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
	
	//		RESTAURAR LA FIGURA
	public void restoreFig() {
		PFig2D.moveTo(Points[0].getX(),Points[0].getY());
		for(int i=1;i<Points.length;i++)
			PFig2D.lineTo(Points[i].getX(), Points[i].getY());
		PFig2D.closePath();
		Fig2D = PFig2D;
		
		GPData = new int[] {0,0,0,0,0,0,0,0,0};
		ImgData = new String[] {"0","0"};
		StrokeData = new int[] {3,1,1,0,0,0,0,0,0,0,0,0};
	}
	
	//		ESCALAR LA FIGURA
	public void scaleFig(double s) {
		AffineTransform at = new AffineTransform();
		at.translate(Fig2D.getBounds2D().getCenterX(),Fig2D.getBounds2D().getCenterY());
		at.scale(s, s);
		at.translate(-Fig2D.getBounds2D().getCenterX(),-Fig2D.getBounds2D().getCenterY());
		Fig2D = at.createTransformedShape(Fig2D);
	}
	
	//		DEFORMAR LA FIGURA
	public void shearyFig(double shX, double shY) {
		AffineTransform at = new AffineTransform();
		at.translate(Fig2D.getBounds2D().getCenterX(),Fig2D.getBounds2D().getCenterY());
		at.shear(shX, shY);
		at.translate(-Fig2D.getBounds2D().getCenterX(),-Fig2D.getBounds2D().getCenterY());
		Fig2D = at.createTransformedShape(Fig2D);
	}
	
	//		ROTAR LA FIGURA
	public void rotateFig(double deg) {
		AffineTransform at = new AffineTransform();
		at.rotate(Math.toRadians(deg), Fig2D.getBounds().getCenterX(), Fig2D.getBounds().getCenterY());
		Fig2D = at.createTransformedShape(Fig2D);
	}
	
	//		TRASLADAR LA FIGURA
	public void translateFig(double tX, double tY) {
		AffineTransform at = new AffineTransform();
		at.translate(tX,tY);
		Fig2D = at.createTransformedShape(Fig2D);
	}
	
	//		REFLEJAR FIGURA
	public void reflectFig(double rX, double rY) {
		AffineTransform at = new AffineTransform();
		at.translate(Fig2D.getBounds2D().getCenterX(),Fig2D.getBounds2D().getCenterY());
		at.scale(rX, rY);
		at.translate(-Fig2D.getBounds2D().getCenterX(),-Fig2D.getBounds2D().getCenterY());
		Fig2D = at.createTransformedShape(Fig2D);
	}
	
	/*		-----API JAVA2D-----	   */
	
	//	--PINTAR LA FIUGRA CON GRADIENTE--
	//Recibe datos para pintar la figura
	public void setGradientPaintData(int[] gpData) {
		GPData = gpData;
		ImgData = new String[] {"0","0"};
	}
	
	//Pinta con gradiente la figura
	public void paintGradientFig(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		// Asignar puntos donde inicia y termina el gradiente
		double begX,begY,endX,endY;
		if(GPData[6] == 1) {
			begX = Fig2D.getBounds().getMinX();
			begY = Fig2D.getBounds().getCenterY();
			endX = Fig2D.getBounds().getMaxX();
			endY = begY;
		}else
			if(GPData[6] == 2) {
				begX = Fig2D.getBounds().getCenterX();
				begY = Fig2D.getBounds().getMinY();
				endX = begX;
				endY = Fig2D.getBounds().getMaxY();
			}else
				if(GPData[6] == 3) {
					begX = Fig2D.getBounds().getMinX();
					begY = Fig2D.getBounds().getMinY();
					endX = Fig2D.getBounds().getMaxX();
					endY = Fig2D.getBounds().getMaxY();
				}else {
					begX = Fig2D.getBounds().getMinX();
					begY = Fig2D.getBounds().getMaxY();
					endX = Fig2D.getBounds().getMaxX();
					endY = Fig2D.getBounds().getMinY();
				}
		Point2D begP = new Point2D.Double(begX,begY);
		Point2D endP = new Point2D.Double(endX,endY);
		
		// Crear los colores seleccionados por el usuario
		Color begC,endC;
		if(GPData[7]!= 0) {
			begC = new Color(GPData[0],GPData[1],GPData[2],GPData[7]);
			endC = new Color(GPData[3],GPData[4],GPData[5],GPData[7]);
		}else {
			begC = new Color(GPData[0],GPData[1],GPData[2]);
			endC = new Color(GPData[3],GPData[4],GPData[5]);
		}
		
		GradientPaint gp = new GradientPaint(begP, begC, endP, endC);
		g2.setPaint(gp);
		g2.fill(Fig2D);
	}
	
	//	--RELLENAR LA FIGURA CON UNA IMAGEN--
	//Recibe la routa de la imagen para rellenar
	public void setImageData(String[] imgData) {
		ImgData = imgData;
		GPData = new int[] {0,0,0,0,0,0,0,0,0};
	}
	
	//Rellena la figura con una imagen
	public void imageFillFig(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		BufferedImage img = null;
		try {
			File image = new File(ImgData[0]);
			img = ImageIO.read(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Rectangle2D r2d = new Rectangle2D.Double(Fig2D.getBounds().getMinX(), Fig2D.getBounds().getMaxY(), Fig2D.getBounds().getWidth(), Fig2D.getBounds().getHeight());
		TexturePaint texture = new TexturePaint(img,r2d);
		g2.setPaint(texture);
		g2.fill(Fig2D);
 	}
	
	//	--MODIFICAR EL ANCHO DEL BORDE DE LA FIGURA--
	//Recibe argumentos del borde
	public void setStrokeData(int[] stData) {
		StrokeData = stData;
	}
	//Alicar efectos de stroke
	public void setStrokeFig(Graphics g) {
		Graphics2D g2= (Graphics2D)g;
		
		StrokeDash = new float[] {StrokeData[3],StrokeData[4],StrokeData[5],StrokeData[6]};
		
		//if(StrokeData[11] == 0)
			if(StrokeData[3] == 0) {
				if(StrokeData[1] == 1 && StrokeData[2] == 1)
					bst = new BasicStroke(StrokeData[0], BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
				else
					if(StrokeData[1] == 1 && StrokeData[2] == 2)
						bst = new BasicStroke(StrokeData[0], BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
					else
						if(StrokeData[1] == 1 && StrokeData[2] == 3)
							bst = new BasicStroke(StrokeData[0], BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);
						else
							if(StrokeData[1] == 2 && StrokeData[2] == 1)
								bst = new BasicStroke(StrokeData[0], BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
							else
								if(StrokeData[1] == 2 && StrokeData[2] == 2)
									bst = new BasicStroke(StrokeData[0], BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
								else
									if(StrokeData[1] == 2 && StrokeData[2] == 3)
										bst = new BasicStroke(StrokeData[0], BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
									else
										if(StrokeData[1] == 3 && StrokeData[2] == 1)
											bst = new BasicStroke(StrokeData[0], BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);
										else
											if(StrokeData[1] == 3 && StrokeData[2] == 2)
												bst = new BasicStroke(StrokeData[0], BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);
											else
												if(StrokeData[1] == 3 && StrokeData[2] == 3)
													bst = new BasicStroke(StrokeData[0], BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND);
			}else {
				if(StrokeData[1] == 1 && StrokeData[2] == 1)
					bst = new BasicStroke(StrokeData[0], BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,1.0f,StrokeDash,0);
				else
					if(StrokeData[1] == 1 && StrokeData[2] == 2)
						bst = new BasicStroke(StrokeData[0], BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,1.0f,StrokeDash,0);
					else
						if(StrokeData[1] == 1 && StrokeData[2] == 3)
							bst = new BasicStroke(StrokeData[0], BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND,1.0f,StrokeDash,0);
						else
							if(StrokeData[1] == 2 && StrokeData[2] == 1)
								bst = new BasicStroke(StrokeData[0], BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL,1.0f,StrokeDash,0);
							else
								if(StrokeData[1] == 2 && StrokeData[2] == 2)
									bst = new BasicStroke(StrokeData[0], BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER,1.0f,StrokeDash,0);
								else
									if(StrokeData[1] == 2 && StrokeData[2] == 3)
										bst = new BasicStroke(StrokeData[0], BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,1.0f,StrokeDash,0);
									else
										if(StrokeData[1] == 3 && StrokeData[2] == 1)
											bst = new BasicStroke(StrokeData[0], BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL,1.0f,StrokeDash,0);
										else
											if(StrokeData[1] == 3 && StrokeData[2] == 2)
												bst = new BasicStroke(StrokeData[0], BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,1.0f,StrokeDash,0);
											else
												if(StrokeData[1] == 3 && StrokeData[2] == 3)
													bst = new BasicStroke(StrokeData[0], BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND,1.0f,StrokeDash,0);
			}
			
		if(StrokeData[10] == 1)		
			g2.setColor(new Color(StrokeData[7],StrokeData[8],StrokeData[9]));
		
		g2.setStroke(bst);
		
		g2.draw(Fig2D);
	}
}