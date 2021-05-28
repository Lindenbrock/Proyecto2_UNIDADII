import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class UserInterface extends JFrame implements ActionListener, MouseWheelListener{
	JFrame W;
	JPanel paintPanel;
	JMenuBar menuBar;
	JMenuItem menuOptions[];
	JMenu menu1,menu2;
	JToolBar toolBar;
	String mOptions[];
	Color one,two,three,four;
	JTabbedPane pestañas;
	URL ruta;
	Figure Fig;
	Image Background,icon;
	boolean move=false;
	
	public UserInterface() {
		setSize(1000,600);
		setLocationRelativeTo(this);
		setResizable(false);
		setTitle("Affine Transform y API Java 2D");

		
		//Icono de la interfaz
		icon = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Resources/icon.png"));
		setIconImage(icon);
		
		one = new Color(247,247,247);
		two = new Color(57,62,70);
		three = new Color(92,99,110);
		four = new Color(248,181,0);
		
		buildMenu();
		buildToolBar();
		buildPaintPanel();
		
		Fig = new Figure();
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//CONSTRUYENDO EL PANEL DONDE SE DIBUJA LA FIGURA
	public void buildPaintPanel() {		
		ruta = getClass().getResource("/Resources/grid1.jpg");
		Background = new ImageIcon(ruta).getImage();
		
		paintPanel = new JPanel(){
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Background, 0, 0,paintPanel.getWidth(),paintPanel.getHeight(),paintPanel);
				g.setColor(two);
				Fig.drawShape(g);
			}
		};
		
		add(paintPanel);
		
		addMouseWheelListener(this); //Evento para editar tamaño con la rueda del ratón
		
		//EVENTOS PARA ROTAR FIGURA CON CLICKS
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				double cX = e.getX(), cY = e.getY();
				Point2D pfx,pfy;
				pfx = Fig.getMaxMinXFigure();
				pfy = Fig.getMaxMinYFigure();
					
				if(cX > pfx.getX() && e.getClickCount() >= 2)
					Fig.rotateFig(5);
				else
					if(cX < pfx.getY() && e.getClickCount() >= 2)
						Fig.rotateFig(-5);
				
				if((cX > pfx.getY() && cX < pfx.getX()) && (cY > pfy.getY() && cY < pfy.getX()))
					move = true;
				else
					move = false;
					
				paintPanel.repaint(); 
			}
		});
		
		//EVENTO PARA MOVER LA FIGURA CON EL ARRASTRE DEL RATÓN
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				int cX = e.getX(), cY = e.getY(), tX, tY, pcX, pcY;
				
				if(move) {
					pcX = (int) Fig.Fig2D.getBounds().getCenterX();
					pcY = (int) Fig.Fig2D.getBounds().getCenterY();
					
					tX = cX - pcX;
					tY = cY - pcY;
					
					Fig.translateFig(tX, tY);
					
					paintPanel.repaint();
				}
			}
		});
	}
	
	//CONSTRUYENDO LA BARRA DE HERRAMIENTAS
	public void buildToolBar() {
		toolBar = new JToolBar("",JToolBar.HORIZONTAL);
		toolBar.setBackground(one);
		toolBar.setFloatable(false);
		add(toolBar,BorderLayout.SOUTH);
		
		ruta = getClass().getResource("/Resources/undo.png"); //Botón de restaurar
		Action A1=new AbstractAction("", new ImageIcon(ruta)){
            public void actionPerformed(ActionEvent arg0) {
            	boolean ans = new RestoreDialog(UserInterface.this,true).showDialog();
    			if(ans)
    				Fig.restoreFig();
            	paintPanel.repaint();
            }};
        A1.putValue(Action.SHORT_DESCRIPTION,"Devuelve la figura a su estado original");
        JButton btnA1 = new JButton(A1);
        btnA1.setBorder(null);
        btnA1.setBackground(one);
        toolBar.add(btnA1);
        
        ruta = getClass().getResource("/Resources/rotate-left.png"); //Botón de girar a la izquierda
		Action A2=new AbstractAction("", new ImageIcon(ruta)){
            public void actionPerformed(ActionEvent arg0) {
            	Fig.rotateFig(-10);
            	paintPanel.repaint();
            }};
        A2.putValue(Action.SHORT_DESCRIPTION,"Gira la figura a la izquerda");
        JButton btnA2 = new JButton(A2);
        btnA2.setBorder(null);
        btnA2.setBackground(one);
        toolBar.add(btnA2);
        
        ruta = getClass().getResource("/Resources/rotate-right.png"); //Botón de girar a al derecha
		Action A3=new AbstractAction("", new ImageIcon(ruta)){
            public void actionPerformed(ActionEvent arg0) {
            	Fig.rotateFig(10);
            	paintPanel.repaint();
            }};
        A3.putValue(Action.SHORT_DESCRIPTION,"Rota la figura a la derecha");
        JButton btnA3 = new JButton(A3);
        btnA3.setBorder(null);
        btnA3.setBackground(one);
        toolBar.add(btnA3);
        
        ruta = getClass().getResource("/Resources/refx.png"); //Botón de reflejar respecto al eje X
		Action A4=new AbstractAction("", new ImageIcon(ruta)){
            public void actionPerformed(ActionEvent arg0) {
            	Fig.reflectFig(1, -1);
            	paintPanel.repaint();
            }};
        A4.putValue(Action.SHORT_DESCRIPTION,"Refleja la figura con respecto al eje X");
        JButton btnA4 = new JButton(A4);
        btnA4.setBorder(null);
        btnA4.setBackground(one);
        toolBar.add(btnA4);
		
        ruta = getClass().getResource("/Resources/refy.png"); //Botón de reflejar respecto al eje Y
        Action A5=new AbstractAction("", new ImageIcon(ruta)){
            public void actionPerformed(ActionEvent arg0) {
            	Fig.reflectFig(-1, 1);
            	paintPanel.repaint();
            }};
        A5.putValue(Action.SHORT_DESCRIPTION,"Refleja la figura con respecto al eje Y");
        JButton btnA5 = new JButton(A5);
        btnA5.setBorder(null);
        btnA5.setBackground(one);
        toolBar.add(btnA5);
        
        ruta = getClass().getResource("/Resources/refxy.png"); //Botón de reflejar respecto a ambos ejes
        Action A6=new AbstractAction("", new ImageIcon(ruta)){
            public void actionPerformed(ActionEvent arg0) {
            	Fig.reflectFig(-1, -1);
            	paintPanel.repaint();
            }};
        A6.putValue(Action.SHORT_DESCRIPTION,"Refleja la figura sobre ambos ejes");
        JButton btnA6 = new JButton(A6);
        btnA6.setBorder(null);
        btnA6.setBackground(one);
        toolBar.add(btnA6);
        
        ruta = getClass().getResource("/Resources/close.png"); //Botón de aumentar tamaño
        Action A7=new AbstractAction("", new ImageIcon(ruta)){
            public void actionPerformed(ActionEvent arg0) {
            	Fig.scaleFig(1.1);
            	paintPanel.repaint();
            }};
        A7.putValue(Action.SHORT_DESCRIPTION,"Aumenta el tamaño de la figura");
        JButton btnA7 = new JButton(A7);
        btnA7.setBorder(null);
        btnA7.setBackground(one);
        toolBar.add(btnA7);
        
        ruta = getClass().getResource("/Resources/far.png"); //Botón de disminuir tamaño
        Action A8=new AbstractAction("", new ImageIcon(ruta)){
            public void actionPerformed(ActionEvent arg0) {
            	Fig.scaleFig(.9);
            	paintPanel.repaint();
            }};
        A8.putValue(Action.SHORT_DESCRIPTION,"Disminuye el tamaño de la figura");
        JButton btnA8 = new JButton(A8);
        btnA8.setBorder(null);
        btnA8.setBackground(one);
        toolBar.add(btnA8);
        
        ruta = getClass().getResource("/Resources/gradient.png"); //Botón de relleno gradiente
		Action A10=new AbstractAction("", new ImageIcon(ruta)){
            public void actionPerformed(ActionEvent arg0) {
            	int GradientData[] = new ColorGradientDialog(UserInterface.this,true).showDialog();
            	if(GradientData[8] == 1) {
            		Fig.setGradientPaintData(GradientData);
            		paintPanel.repaint();
            	}
            }};
        A10.putValue(Action.SHORT_DESCRIPTION,"Rellena la figura con color grandiente");
        JButton btnA10 = new JButton(A10);
        btnA10.setBorder(null);
        btnA10.setBackground(one);
        toolBar.add(btnA10);
        
        ruta = getClass().getResource("/Resources/image.png"); //Botón de relleno con imagen
		Action A11=new AbstractAction("", new ImageIcon(ruta)){
            public void actionPerformed(ActionEvent arg0) {
            	String imgRoute[] = new ImageFillDialog(UserInterface.this,true).showDialog();
            	if(Integer.parseInt(imgRoute[1]) == 1) {
            		Fig.setImageData(imgRoute);
            		paintPanel.repaint();
            	}
            }};
        A11.putValue(Action.SHORT_DESCRIPTION,"Rellena con una imagen");
        JButton btnA11 = new JButton(A11);
        btnA11.setBorder(null);
        btnA11.setBackground(one);
        toolBar.add(btnA11);
        
        ruta = getClass().getResource("/Resources/border.png"); //Botón de aplicar efectos de borde
		Action A12=new AbstractAction("", new ImageIcon(ruta)){
            public void actionPerformed(ActionEvent arg0) {
            	int StrokeData[] = new StrokeDialog(UserInterface.this,true).showDialog();
            	if(StrokeData[11] == 1) {
            		Fig.setStrokeData(StrokeData);
            		paintPanel.repaint();
            	}
            }};
        A12.putValue(Action.SHORT_DESCRIPTION,"Aplica efectos de borde");
        JButton btnA12 = new JButton(A12);
        btnA12.setBorder(null);
        btnA12.setBackground(one);
        toolBar.add(btnA12);
        
        ruta = getClass().getResource("/Resources/exit.png"); //Salir del programa
        Action A9=new AbstractAction("", new ImageIcon(ruta)){
            public void actionPerformed(ActionEvent arg0) {
            	boolean ans = new ExitDialog(UserInterface.this,true).showDialog();
				if(ans)
					System.exit(0);
            }};
        A9.putValue(Action.SHORT_DESCRIPTION,"Salir del programa");
        JButton btnA9 = new JButton(A9);
        btnA9.setBorder(null);
        btnA9.setBackground(one);
        toolBar.add(btnA9);
	}

	//CONSTRUYENDO EL MENU
	private void buildMenu() {
		menuBar = new JMenuBar();
		menuBar.setBackground(four);
		menuBar.setBorder(null);
		setJMenuBar(menuBar);
		
		menu1 = new JMenu("Trasformaciones");
		menu1.setForeground(one);
		
		menu2 = new JMenu("Acerca de");
		menu2.setForeground(one);
		
		menuBar.add(menu1); menuBar.add(menu2);
		
		mOptions = new String[] {"Restaurar","Escalar","Deformar","Rotar","Trasladar",  //Se crea arreglo de nombres de las opciones del menu
								"Reflejar","Salir","Autor","Ayuda"};		   //para no hacer el
		menuOptions = new JMenuItem[9];	//Se crea arreglo de pciones
		
		for(int i=0;i<mOptions.length;i++) {		//Ciclo para crear cada uno de los item del menú en un ciclo
			menuOptions[i] = new JMenuItem(mOptions[i]);
			menuOptions[i].setBackground(one);
			menuOptions[i].setForeground(two);
			menuOptions[i].setBorder(null);
		}
		for(int i=0;i<7;i++) {//Ciclo para añadir los items al menu
			menu1.add(menuOptions[i]);
		}
		menu2.add(menuOptions[7]); menu2.add(menuOptions[8]); //Los últimos items se agregan al otro menu
		
		menuOptions[0].addActionListener(this);  //Opción para restaurar
		menuOptions[0].setToolTipText("Restaura la figura a su forma originaL");
		ruta = getClass().getResource("/Resources/undo.png");
		menuOptions[0].setIcon(new ImageIcon(ruta));
		menuOptions[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,InputEvent.ALT_MASK));
		
		menuOptions[1].addActionListener(this);  //Opción para escalar
		menuOptions[1].setToolTipText("Cambia el tamaño de la figura");
		ruta = getClass().getResource("/Resources/scale.png");
		menuOptions[1].setIcon(new ImageIcon(ruta));
		menuOptions[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.ALT_MASK));
		
		menuOptions[2].addActionListener(this);  //Opción para deformar
		menuOptions[2].setToolTipText("Cambia la forma de la figura");
		ruta = getClass().getResource("/Resources/sheary.png");
		menuOptions[2].setIcon(new ImageIcon(ruta));
		menuOptions[2].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.ALT_MASK));
		
		menuOptions[3].addActionListener(this);  //Opción para girar
		menuOptions[3].setToolTipText("Gira la figura");
		ruta = getClass().getResource("/Resources/rotate-right.png");
		menuOptions[3].setIcon(new ImageIcon(ruta));
		menuOptions[3].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.ALT_MASK));
		
		menuOptions[4].addActionListener(this);  //Opción para trasladar
		menuOptions[4].setToolTipText("Traslada la figura a un pundo deseado");
		ruta = getClass().getResource("/Resources/move.png");
		menuOptions[4].setIcon(new ImageIcon(ruta));
		menuOptions[4].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,InputEvent.ALT_MASK));
		
		menuOptions[5].addActionListener(this); //Opción para felejar
		menuOptions[5].setToolTipText("Refleja la figura");
		ruta = getClass().getResource("/Resources/refy.png");
		menuOptions[5].setIcon(new ImageIcon(ruta));
		menuOptions[5].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,InputEvent.ALT_MASK));
		
		menuOptions[6].addActionListener(this);  //Opción para salir del programa
		menuOptions[6].setToolTipText("Salir del programa");
		ruta = getClass().getResource("/Resources/exit.png");
		menuOptions[6].setIcon(new ImageIcon(ruta));
		menuOptions[6].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.ALT_MASK));
		
		menuOptions[7].addActionListener(this);  //Opción para información del desarrollador
		menuOptions[7].setToolTipText("Muestra la información del desarrollador");
		ruta = getClass().getResource("/Resources/developer.png");
		menuOptions[7].setIcon(new ImageIcon(ruta));
		menuOptions[7].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.ALT_MASK));
		
		menuOptions[8].addActionListener(this); //Opción para ventana de ayuda
		menuOptions[8].setToolTipText("Muestra una ayudar sobre el funcionamiento del programa");
		ruta = getClass().getResource("/Resources/help.png");
		menuOptions[8].setIcon(new ImageIcon(ruta));
		menuOptions[8].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,InputEvent.ALT_MASK));
	}
	
	//EVENTOS OPCIONES DE LA BARRA DE MENU
	public void actionPerformed(ActionEvent ev) {
		if(ev.getSource() == menuOptions[0]) {  //Evento menu Restaurar
			boolean ans = new RestoreDialog(UserInterface.this,true).showDialog();
			if(ans)
				Fig.restoreFig();
		} else
			if(ev.getSource() == menuOptions[1]) {  //Evento menu Escalar
				double esc = new ScaleDialog(UserInterface.this,true).showDialog();
				Fig.scaleFig(esc);
			}else
				if(ev.getSource() == menuOptions[2]) {  //Evento menu Deformar
					double[] she = new ShearyDialog(UserInterface.this,true).showDialog();
					Fig.shearyFig(she[0],she[1]);
				}else
					if(ev.getSource() == menuOptions[3]) {  //Evento menu Rotar
						int[] rot = new RotateDialog(UserInterface.this,true).showDialog();
						if(rot[1] == 1)
							Fig.rotateFig(-rot[0]);
						else
							Fig.rotateFig(rot[0]);
					}else
						if(ev.getSource() == menuOptions[4]) {  //Evento menu Trasladar
							int[] move= new TranslateDialog(UserInterface.this,true).showDialog();
							Fig.translateFig(move[0],move[1]);
						}else
							if(ev.getSource() == menuOptions[5]) {  //Evento menu Reflejar
								int ans = new ReflectDialog(UserInterface.this,true).showDialog(),refx,refy;
								if(ans == 1) {
									refx = 1; refy = -1;
								} else 
									if(ans == 2) {
										refx = -1; refy = 1;
									} else 
										if(ans == 3) {
											refx = -1; refy = -1;
										}
									
										else {
											refx = 1; refy = 1;
										}
								
								Fig.reflectFig(refx,refy);
							}else
								if(ev.getSource() == menuOptions[6]) {  //Evento menu Salir
									boolean ans = new ExitDialog(UserInterface.this,true).showDialog();
									if(ans)
										System.exit(0);
								}else
									if(ev.getSource() == menuOptions[7]) //Evento menu Información del desarrollado
										new AboutDialog(UserInterface.this,true).showDialog();
									//else
										if(ev.getSource() == menuOptions[8]) //Evento menu Ayuda
											new HelpDialog(UserInterface.this,true).showDialog();
		paintPanel.repaint();
	}
	
	//EVENTO DE RUEDA DEL RATÓN PARA AUMENTAR O DISMINUIR EL TAMAÑO
	@Override
	public void mouseWheelMoved(MouseWheelEvent ev) {
		int wSense = ev.getWheelRotation();
		double esc;
		if(wSense < 0)
			esc = 0.95;
		else
			esc = 1.05;
		Fig.scaleFig(esc);
		paintPanel.repaint();
	}
	
	//MÉTODO MAIN PARA INICIAR PROGRAMA
	public static void main(String[] args) {
		new UserInterface();
	}
}
