import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class RestoreDialog extends JDialog implements ActionListener{
	boolean ans;
	JLabel lbl,lblimg;
	JButton btnAllow,btnCancel;
	
	public RestoreDialog(UserInterface w,boolean modal) {
		super(w.W,modal);
		setTitle("Restaurar la figura");
		setSize(350,180);
		setLayout(new FlowLayout());
		setLocationRelativeTo(w.W);
		this.setBackground(w.one);
		
		Image icon = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Resources/icon.png"));
		setIconImage(icon);
		
		lbl = new JLabel("�Est�s seguro de que deseas restaurar la figura?");
		lbl.setForeground(w.two);
		btnAllow = new JButton("Aceptar");
		btnAllow.setBackground(w.three);
		btnAllow.setForeground(w.one);
		btnCancel = new JButton("Cancelar");
		btnCancel.setBackground(w.three);
		btnCancel.setForeground(w.one);
		
		URL ruta = getClass().getResource("/Resources/undog.gif");
		lblimg = new JLabel(new ImageIcon(ruta));
		
		add(lbl); add(lblimg); add(btnAllow); add(btnCancel);
		
		btnAllow.addActionListener(this);
		
		btnCancel.addActionListener(this);
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}
	
	public boolean showDialog() {
		setVisible(true);
		return ans;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnAllow)
			ans = true;
		else
			ans = false;
		setVisible(false);
		dispose();
	}
}
