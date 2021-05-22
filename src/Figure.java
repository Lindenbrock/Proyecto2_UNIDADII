import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

public class Figure {
	GeneralPath Fig2D,FigMap;
	double Coordinates[],MapCoordinates[];
	Color one,two;
	
	
	public Figure() {
		Fig2D = new GeneralPath();
		
		Coordinates = new double[] {
				273,51, 289,51, 297,56, 302,56, 312,52, 325,47, 338,41, 349,41, 362,42, 374,46, 380,56, 388,59, 391,67, 397,75, 397,81, 
				398,93, 398,102, 393,111, 388,116, 384,118, 379,121, 381,130, 389,140, 394,149, 400,160, 392,177, 397,191, 407,204, 407,208, 
				404,213, 399,215, 395,215, 404,226, 400,233, 397,240, 389,243, 395,247, 397,252, 397,259, 398,269, 400,272, 400,278, 401,288, 
				398,295, 391,301, 385,306, 379,312, 373,312, 366,312, 358,315, 349,317, 460,384, 468,392, 473,401, 476,412, 481,423, 484,435, 
				487,447, 489,459, 490,467, 491,479, 22,479, 23,465, 26,449, 29,432, 34,415, 41,400, 47,387, 50,378, 58,364, 65,354, 77,346, 
				98,337, 117,328, 135,321, 165,313, 185,304, 199,296, 209,285, 216,270, 226,263, 230,260, 224,248, 214,232, 207,221, 205,211, 
				198,196, 196,180, 193,173, 195,162, 193,151, 195,140, 192,132, 197,119, 205,104, 214,92, 225,82, 233,72, 242,61, 255,53
		};
		
		MapCoordinates = new double[Coordinates.length];
		
		createFigure();
		
		one = new Color(48,48,48);
		two = Color.WHITE;
	}
	
	//	CREAR LA FIGURA CON EL VECTOR DE COORDENADAS
	public void createFigure() {
		Fig2D.moveTo(Coordinates[0],Coordinates[1]);
		for(int i=2,j=3;i<Coordinates.length-1;i+=2,j+=2)
			Fig2D.lineTo(Coordinates[i],Coordinates[j]);
		Fig2D.closePath();
	}
	
	//	MAPEO DE LA FIGURA CREADA
	public void windowMapping(int xvmax, int xvmin, int yvmax, int yvmin, int xwmax, int ywmax, Graphics g) {
		double sx = (double)(xvmax - xvmin) / (double)(xwmax - 0),  sy = (double)(yvmax - yvmin) / (double)(ywmax - 0);

		FigMap = new GeneralPath();
		
		g.setColor(two);
		for(int i=0,j=1;i<MapCoordinates.length-1;i+=2,j+=2) {
			MapCoordinates[i] = Coordinates[i] * sx;
			MapCoordinates[j] = Coordinates[j] * sy;
			MapCoordinates[i] += xvmin;
			MapCoordinates[j] += yvmin;
		}
		FigMap.moveTo(MapCoordinates[0],MapCoordinates[1]);
		for(int i=2,j=3;i<MapCoordinates.length-1;i+=2,j+=2)
			FigMap.lineTo(MapCoordinates[i],MapCoordinates[j]);
		FigMap.closePath();
		
		Graphics2D g2 = (Graphics2D)g;
		g2.fill(FigMap);
	}
	
	//	DIBUJAR LA FIGURA
	public void drawShape(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.fill(Fig2D);
	}
}
