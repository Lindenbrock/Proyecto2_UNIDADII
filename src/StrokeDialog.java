//Hecho por Luis José Ixta Zamudio 17420565
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.GeneralPath;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class StrokeDialog extends JDialog implements KeyListener{
	int StrokeData[] = new int[] {3,1,1,0,0,0,0,0,0,0,0,0};
	float StrokeDash[];
	JLabel lblWidth,lblCap,lblJoin,lblDash;
	JTextField tfWidth,tfDash1,tfDash2,tfDash3,tfDash4,tfColor;
	JCheckBox ckbEnableDash,ckbColor;
	JButton btnAllow,btnCancel,btnColor;
	JComboBox cbCap,cbJoin;
	String cbOptCap[] = new String[] {"Butt","Round","Square"};
	String cbOptJoin[] = new String[] {"Bevel","Miter","Round"};
	JPanel examplePanel,optionPanel,allcanPanel;
	BasicStroke bst;
	
	public StrokeDialog(UserInterface w,boolean modal) {
		super(w.W,modal);
		setTitle("Aplicar efectos de borde");
		setSize(580,350);
		setLayout(new GridLayout(3,1));
		setLocationRelativeTo(w.W);
		this.setBackground(w.one);
		
		Image icon = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Resources/icon.png"));
		setIconImage(icon);
		
		optionPanel = new JPanel();
		add(optionPanel);
		
		lblWidth = new JLabel("Ancho del borde");
		lblWidth.setForeground(w.two);
		tfWidth = new JTextField(3);
		tfWidth.setText("3");
		
		lblCap = new JLabel("Terminación del borde");
		lblCap.setForeground(w.two);
		cbCap = new JComboBox(cbOptCap);
		cbCap.setBackground(w.three);
		cbCap.setForeground(w.one);
		
		lblJoin = new JLabel("Terminación del borde");
		lblJoin.setForeground(w.two);
		cbJoin = new JComboBox(cbOptJoin);
		cbJoin.setBackground(w.three);
		cbJoin.setForeground(w.one);
		
		ckbEnableDash = new JCheckBox("Habilitar punteado");
		ckbEnableDash.setForeground(w.two);
		lblDash = new JLabel("Personaliza el punteado");
		lblDash.setForeground(w.two);
		lblDash.setEnabled(false);
		tfDash1 = new JTextField(4);
		tfDash1.setBackground(w.one);
		tfDash1.setEnabled(false);
		tfDash1.setText("1");
		tfDash2 = new JTextField(4);
		tfDash2.setBackground(w.one);
		tfDash2.setEnabled(false);
		tfDash2.setText("1");
		tfDash3 = new JTextField(4);
		tfDash3.setBackground(w.one);
		tfDash3.setEnabled(false);
		tfDash3.setText("0");
		tfDash4 = new JTextField(4);
		tfDash4.setBackground(w.one);
		tfDash4.setEnabled(false);
		tfDash4.setText("0");
		
		ckbColor = new JCheckBox("Colorear borde");
		ckbColor.setForeground(w.two);
		btnColor = new JButton("Seleccionar color");
		btnColor.setForeground(w.one);
		btnColor.setBackground(w.three);
		btnColor.setEnabled(false);
		tfColor = new JTextField(10);
		tfColor.setBackground(w.one);
		tfColor.setEnabled(false);
		tfColor.setText("255,0,0");
		
		examplePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
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
				
				g2.setColor(new Color(StrokeData[7],StrokeData[8],StrokeData[9]));
				
				g2.setStroke(bst);
				
				GeneralPath line = new GeneralPath();
				line.moveTo(15, 20);
				line.lineTo(examplePanel.getBounds().getWidth()-15,examplePanel.getBounds().getHeight()-15);
				line.lineTo(examplePanel.getBounds().getMaxX()-15, 15);
				
				g2.draw(line);
			}
		};
		
		examplePanel.setSize(580,50);
		TitledBorder tbExpl = new TitledBorder("Ejemplo del borde");
		tbExpl.setTitleJustification(TitledBorder.CENTER);
		examplePanel.setBorder(tbExpl);
		
		allcanPanel = new JPanel();
		add(allcanPanel);
		btnAllow = new JButton("Aceptar");
		btnAllow.setBackground(w.three);
		btnAllow.setForeground(w.one);
		btnCancel = new JButton("Cancelar");
		btnCancel.setBackground(w.three);
		btnCancel.setForeground(w.one);
		
		optionPanel.add(lblWidth); optionPanel.add(tfWidth); optionPanel.add(lblCap); optionPanel.add(cbCap);
		optionPanel.add(lblJoin); optionPanel.add(cbJoin);
		optionPanel.add(ckbEnableDash); optionPanel.add(lblDash); optionPanel.add(tfDash1);
		optionPanel.add(tfDash2); optionPanel.add(tfDash3); optionPanel.add(tfDash4);
		optionPanel.add(ckbColor); optionPanel.add(btnColor); optionPanel.add(tfColor);
		add(examplePanel,FlowLayout.CENTER);
		allcanPanel.add(btnAllow); allcanPanel.add(btnCancel);
		
		tfWidth.addKeyListener(this);
		
		ckbEnableDash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ckbEnableDash.isSelected()) {
					lblDash.setEnabled(true);
					tfDash1.setEnabled(true);
					StrokeData[3]=Integer.parseInt(tfDash1.getText());
					tfDash2.setEnabled(true);
					StrokeData[4]=Integer.parseInt(tfDash2.getText());
					tfDash3.setEnabled(true);
					tfDash4.setEnabled(true);
				}else {
					lblDash.setEnabled(false);
					tfDash1.setEnabled(false);
					tfDash1.setText("1");
					StrokeData[3]=0;
					tfDash2.setEnabled(false);
					tfDash2.setText("1");
					StrokeData[4]=0;
					tfDash3.setEnabled(false);
					tfDash3.setText("0");
					StrokeData[5]=0;
					tfDash4.setEnabled(false);
					tfDash4.setText("0");
					StrokeData[6]=0;
				}
				examplePanel.repaint();
			}
		});
		
		cbCap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int opt = cbCap.getSelectedIndex();
				if(opt == 0)
					StrokeData[1] = 1;
				else
					if(opt == 1)
						StrokeData[1] = 2;
					else
						if(opt == 2)
							StrokeData[1] = 3;
				examplePanel.repaint();
			}
		});
		
		cbJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int opt = cbJoin.getSelectedIndex();
				if(opt == 0)
					StrokeData[2] = 1;
				else
					if(opt == 1)
						StrokeData[2] = 2;
					else
						if(opt == 2)
							StrokeData[2] = 3;
				examplePanel.repaint();
			}
		});
		
		tfDash1.addKeyListener(this);
		tfDash2.addKeyListener(this);
		tfDash3.addKeyListener(this);
		tfDash4.addKeyListener(this);
		
		ckbColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ckbColor.isSelected()) {
					btnColor.setEnabled(true);
					tfColor.setEnabled(true);
					StrokeData[7]=255; StrokeData[8]=0; StrokeData[9]=0;
					StrokeData[10] = 1;
				}else {
					btnColor.setEnabled(false);
					tfColor.setEnabled(false);
					StrokeData[7]=0; StrokeData[8]=0; StrokeData[9]=0;
					tfColor.setText("255,0,0");
					StrokeData[10] = 0;
				}
				examplePanel.repaint();
			}
		});
		
		btnColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int RGB[] = new SelectColorDialog(StrokeDialog.this,true).showDialog();
				tfColor.setText(String.valueOf(RGB[0])+","+String.valueOf(RGB[1])+","+String.valueOf(RGB[2]));
				StrokeData[7] = RGB[0];
				StrokeData[8] = RGB[1];
				StrokeData[9] = RGB[2];
				examplePanel.repaint();
			}
		});
		
		btnAllow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StrokeData[11] = 1;
				setVisible(false);
				dispose();
			}	
		});
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StrokeData[11] = 0;
				setVisible(false);
				dispose();
			}
		});
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}

	@Override
	public void keyReleased(KeyEvent ev) {
		if(ev.getSource() == tfWidth)
			if(tfWidth.getText().isEmpty())
				StrokeData[0] = 0;
			else
				StrokeData[0] = Integer.parseInt(tfWidth.getText());
		else
			if(ev.getSource() == tfDash1)
				if(tfDash1.getText().isEmpty())
					StrokeData[3] = 0;
				else
					StrokeData[3] = Integer.parseInt(tfDash1.getText());
			else
				if(ev.getSource() == tfDash2)
					if(tfDash2.getText().isEmpty())
						StrokeData[4] = 0;
					else
						StrokeData[4] = Integer.parseInt(tfDash2.getText());
				else
					if(ev.getSource() == tfDash3)
						if(tfDash3.getText().isEmpty())
							StrokeData[5] = 0;
						else
							StrokeData[5] = Integer.parseInt(tfDash3.getText());
					else
						if(ev.getSource() == tfDash4)
							if(tfDash4.getText().isEmpty())
								StrokeData[6] = 0;
							else
								StrokeData[6] = Integer.parseInt(tfDash4.getText());
		examplePanel.repaint();
	}

	public void keyTyped(KeyEvent arg0) {
	}
	public void keyPressed(KeyEvent arg0) {	
	}
	
	public int[] showDialog() {
		setVisible(true);
		return StrokeData;
	}
}
