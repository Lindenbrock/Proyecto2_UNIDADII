//Hecho por Luis José Ixta Zamudio 17420565
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorGradientDialog extends JDialog implements ChangeListener{
	int GradientData[] = new int[] {0,0,0,0,0,0,0,0,0};
	JLabel lbl1,lbl2,lbl3,lbl4,lbl5,lbl6,lbl7;
	JTextField tf1,tf2;
	JRadioButton rb1,rb2,rb3,rb4;
	ButtonGroup rbg;
	JCheckBox ckbTrans;
	JSlider sldTrans;
	
	JButton btnAllow,btnCancel;
	
	public ColorGradientDialog(UserInterface w,boolean modal) {
		super(w.W,modal);
		setTitle("Pintar la figura con gradiente");
		setSize(380,260);
		setLayout(new FlowLayout());
		setLocationRelativeTo(w.W);
		this.setBackground(w.one);
		w.setBackground(w.one);
		
		Image icon = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Resources/icon.png"));
		setIconImage(icon);
		
		lbl1 = new JLabel("Color 1");
		lbl1.setForeground(w.two);
		tf1 = new JTextField(10);
		tf1.setText("vacío");
		lbl2 = new JLabel("Color 2");
		lbl2.setForeground(w.two);
		tf2 = new JTextField(10);
		tf2.setText("vacío");
		rbg = new ButtonGroup();
		URL ruta = getClass().getResource("/Resources/gradienth.png");
		lbl3 = new JLabel(new ImageIcon(ruta));
		rb1 = new JRadioButton("En eje horizontal");
		rb1.setForeground(w.two);
		rb1.setSelected(true);
		ruta = getClass().getResource("/Resources/gradientv.png");
		lbl4 = new JLabel(new ImageIcon(ruta));
		rb2 = new JRadioButton("En eje vertical");
		rb2.setForeground(w.two);
		ruta = getClass().getResource("/Resources/gradientd2.png");
		lbl5 = new JLabel(new ImageIcon(ruta));
		rb3 = new JRadioButton("En eje diagonal 1");
		rb3.setForeground(w.two);
		ruta = getClass().getResource("/Resources/gradientd1.png");
		lbl6 = new JLabel(new ImageIcon(ruta));
		rb4 = new JRadioButton("En eje diagonal 2");
		rb4.setForeground(w.two);
		rbg.add(rb1); rbg.add(rb2); rbg.add(rb3); rbg.add(rb4);
		btnAllow = new JButton("Aceptar");
		btnAllow.setBackground(w.three);
		btnAllow.setForeground(w.one);
		btnCancel = new JButton("Cancelar");
		btnCancel.setBackground(w.three);
		btnCancel.setForeground(w.one);
		
		ckbTrans = new JCheckBox("Transparencia");
		ckbTrans.setForeground(w.two);
		sldTrans = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
		sldTrans.setBackground(w.one);
		sldTrans.setForeground(w.two);
		sldTrans.setMajorTickSpacing(51);
		sldTrans.setPaintTicks(true);
		sldTrans.setPaintLabels(true);
		sldTrans.setEnabled(false);
		lbl7 = new JLabel("0%");
		lbl7.setForeground(w.two);
		lbl7.setEnabled(false);
		
		add(lbl1); add(tf1); add(lbl2); add(tf2);
		add(lbl3); add(rb1); add(lbl4); add(rb2);
		add(lbl5); add(rb3); add(lbl6); add(rb4);
		add(ckbTrans); add(sldTrans); add(lbl7);
		add(btnAllow); add(btnCancel);
		
		tf1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int RGB[] = new SelectColorDialog(ColorGradientDialog.this,true).showDialog();
				tf1.setText(String.valueOf(RGB[0])+","+String.valueOf(RGB[1])+","+String.valueOf(RGB[2]));
				GradientData[0] = RGB[0];
				GradientData[1] = RGB[1];
				GradientData[2] = RGB[2];
			}
		});
		
		tf2.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int RGB[] = new SelectColorDialog(ColorGradientDialog.this,true).showDialog();
				tf2.setText(String.valueOf(RGB[0])+","+String.valueOf(RGB[1])+","+String.valueOf(RGB[2]));
				GradientData[3] = RGB[0];
				GradientData[4] = RGB[1];
				GradientData[5] = RGB[2];
			}
		});
		
		ckbTrans.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if(ckbTrans.isSelected()) {
					sldTrans.setEnabled(true);
					lbl7.setEnabled(true);
				}else {
					sldTrans.setEnabled(false);
					lbl7.setEnabled(false);
					lbl7.setText("0%");
					sldTrans.setValue(0);
					GradientData[7]=0;
				}
			}
		});
		
		sldTrans.addChangeListener(this);
		
		btnAllow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!tf1.getText().equals("vacío") && !tf2.getText().equals("vacío")) {
					if(rb1.isSelected())
						GradientData[6] = 1;
					else
						if(rb2.isSelected())
							GradientData[6] = 2;
						else
							if(rb3.isSelected())
								GradientData[6] = 3;
							else
								GradientData[6] = 4;
					GradientData[8] = 1;
					setVisible(false);
					dispose();
				}
			}	
		});
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}
	
	public int[] showDialog() {
		setVisible(true);
		return GradientData;
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		int cant = sldTrans.getValue();
		GradientData[7] = cant;
		lbl7.setText(String.valueOf(cant)+"%");
	}
}
