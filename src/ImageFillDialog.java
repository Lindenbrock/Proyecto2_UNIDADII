//Hecho por Luis José Ixta Zamudio 17420565
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageFillDialog extends JDialog{
	String ImgRoute[] = new String[] {"",""};
	JLabel lbl1;
	JTextField tf1;
	JButton btnAllow,btnCancel,btnSelect;
	
	public ImageFillDialog(UserInterface w,boolean modal) {
		super(w.W,modal);
		setTitle("Rellenar la figura con imagen");
		setSize(580,110);
		setLayout(new FlowLayout());
		setLocationRelativeTo(w.W);
		this.setBackground(w.one);
		
		Image icon = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Resources/icon.png"));
		setIconImage(icon);
		
		lbl1 = new JLabel("Seleccionar imagen:");
		lbl1.setForeground(w.two);
		tf1 = new JTextField(30);
		btnSelect = new JButton("Buscar");
		btnSelect.setBackground(w.three);
		btnSelect.setForeground(w.one);
		btnAllow = new JButton("Aceptar");
		btnAllow.setBackground(w.three);
		btnAllow.setForeground(w.one);
		btnCancel = new JButton("Cancelar");
		btnCancel.setBackground(w.three);
		btnCancel.setForeground(w.one);
		
		add(lbl1); add(tf1);  add(btnSelect);
		add(btnAllow); add(btnCancel);
		
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser SelectRoute = new JFileChooser();
				FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("JPG, PNG, GIF", "jpg","png","gif"); 
				SelectRoute.setFileFilter(imgFilter);
				SelectRoute.showOpenDialog(null);
				ImgRoute[0] = SelectRoute.getSelectedFile().getAbsolutePath();
				tf1.setText(ImgRoute[0]);	
			}
		});
		
		btnAllow.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				ImgRoute[1] = "1";
				if(!ImgRoute[0].equalsIgnoreCase("0")) {
					setVisible(false);
					dispose();
				}
			}	
		});
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImgRoute[1] = "0";
				setVisible(false);
				dispose();
			}
		});
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}
	
	public String[] showDialog() {
		setVisible(true);
		return ImgRoute;
	}
}
