import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;

public class Intro extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private boolean play = false;

	public boolean play() {
		return play;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Intro dialog = new Intro();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Intro() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(1);
			}
		});
		setResizable(false);
		setTitle("The Binding of Lucian");
		setModal(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				do_this_windowClosing(arg0);
			}
		});
		setBounds(100, 100, 634, 406);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.BLACK);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton btnInstructions = new JButton("Instructions");
		btnInstructions.setFont(new Font("Viner Hand ITC", Font.PLAIN, 20));
		btnInstructions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				do_btnInstructions_mouseClicked(e);
			}
		});
		btnInstructions.setBounds(236, 310, 202, 46);
		contentPanel.add(btnInstructions);
		
		JButton btnPlay = new JButton("PLAY");
		btnPlay.setBackground(Color.WHITE);
		btnPlay.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				do_btnPlay_mouseClicked(e);
			}
		});
		btnPlay.setFont(new Font("Viner Hand ITC", Font.PLAIN, 35));
		btnPlay.setBounds(215, 212, 237, 87);
		contentPanel.add(btnPlay);
		
		JLabel lblNewLabel = new JLabel("The Binding of Lucian");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Viner Hand ITC", Font.PLAIN, 50));
		lblNewLabel.setBounds(55, 31, 529, 159);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("Play");
				okButton.setBounds(376, 5, 53, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	protected void do_btnPlay_mouseClicked(MouseEvent e) {
		play = true;
		this.setVisible(false);

	}
	protected void do_btnInstructions_mouseClicked(MouseEvent e) {
		Instructions it = new Instructions();
		it.setLocationRelativeTo(this);
		it.setVisible(true);
		
		
	}
	protected void do_this_windowClosing(WindowEvent arg0) {
	}
}
