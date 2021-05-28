//Hecho por Luis José Ixta Zamudio 17420565
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SelectColorDialog extends JDialog{
	int RGB[] = new int[3];
	JLabel lbl1,lbl2,lbl3,lblimg;
	JTextField tf1,tf2,tf3;
	JRadioButton rb1,rb2;
	ButtonGroup rbg;
	JButton btnAllow,btnCancel;
	JColorChooser cSelector;
	
	public SelectColorDialog(ColorGradientDialog w,boolean modal) {
		super(w,modal);
		setTitle("Seleccionar color");
		setSize(640,400);
		setLayout(new FlowLayout());
		setLocationRelativeTo(w);
		this.setBackground(Color.white);
		
		init();
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}
	
	public SelectColorDialog(StrokeDialog w,boolean modal) {
		super(w,modal);
		setTitle("Rotar la figura");
		setSize(640,400);
		setLayout(new FlowLayout());
		setLocationRelativeTo(w);
		this.setBackground(Color.white);
		
		init();
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}
	
	public void init() {
		cSelector = new JColorChooser();
		add(cSelector);
		
		Image icon = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Resources/icon.png"));
		setIconImage(icon);
	
		btnAllow = new JButton("Aceptar");
		btnAllow.setBackground(Color.gray);
		btnAllow.setForeground(Color.white);
		btnCancel = new JButton("Cancelar");
		btnCancel.setBackground(Color.gray);
		btnCancel.setForeground(Color.white);
		
		add(btnAllow); add(btnCancel);
		
		btnAllow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {					
				RGB[0] = cSelector.getColor().getRed();
				RGB[1] = cSelector.getColor().getGreen();
				RGB[2] = cSelector.getColor().getBlue();
					
				setVisible(false);
				dispose();
			}	
		});
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RGB[0]=0;
				RGB[1]=0;
				RGB[2]=0;
				setVisible(false);
				dispose();
			}
		});
	}
	
	public int[] showDialog() {
		setVisible(true);
		return RGB;
	}
}
