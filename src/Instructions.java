import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Instructions extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Instructions dialog = new Instructions();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Instructions() {
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
		setBounds(100, 100, 498, 336);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JTextArea txtrQwe = new JTextArea();
		txtrQwe.setEditable(false);
		txtrQwe.setText("WASD : move in respective direction\r\n"
				+ "Arrow Keys : shoot in respective directions\r\n"
				+ "N key : Hit this button to start the waves on your floor in the middle \r\n"
				+ "room. This will block access to all other rooms until you kill all enemies \r\n"
				+ "and get to the next floor. At the end of each floor will be a boss. On the\r\n"
				+ "last floor will be difficult waves followed by the final boss. \r\n"
				+ "\r\n"
				+ "Collect money by killing enemies and buy items which will boost your\r\n"
				+ "stats or provide useful effects\r\n"
				+ "The room on the left is the cursed room. Be careful, entering this room will \r\n"
				+ "damage you, but may reward you with good items.\r\n"
				+ "The room on the right will unlock after completing a floor, revealing a \r\n"
				+ "special reward.\r\n"
				+ "The room on the top is the shop. Trade your money for items and \r\n"
				+ "consumables.\r\n"
				+ "		Have Fun!"
				+ "\r\n\r\n\r\n");
		txtrQwe.setBounds(10, 11, 462, 263);
		contentPanel.add(txtrQwe);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {


					@Override
					public void mouseClicked(MouseEvent e) {
						do_okButton_mouseClicked(e);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	protected void do_okButton_mouseClicked(MouseEvent e) {
		
		this.setVisible(false);
	}
}
