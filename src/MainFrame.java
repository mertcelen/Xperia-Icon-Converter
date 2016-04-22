import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class MainFrame extends JFrame {
	private JPanel contentPane;
	public static JLabel statusLabel;
	public static JComboBox applicationList;
	public static JTextArea textArea;
	private JButton btnConvert;
	private JLabel apkStatus;
	private JScrollPane scrollPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 507, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnConnectPhone = new JButton("Connect Phone");

		btnConnectPhone.setBounds(6, 6, 155, 29);
		contentPane.add(btnConnectPhone);
		
		statusLabel = new JLabel("Status : Ready");
		statusLabel.setBounds(16, 35, 246, 16);
		contentPane.add(statusLabel);
		
		applicationList = new JComboBox();
		applicationList.setBounds(6, 72, 256, 27);
		contentPane.add(applicationList);
		
		btnConvert = new JButton("Convert");

		btnConvert.setBounds(265, 71, 117, 29);
		contentPane.add(btnConvert);
		
		apkStatus = new JLabel("");
		apkStatus.setBounds(16, 111, 246, 16);
		contentPane.add(apkStatus);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 111, 486, 347);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		btnConnectPhone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConvertSystem.init();
				apkStatus.setText("Status : Ready to download");
			}
		});
		
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ConvertSystem.convert(applicationList.getSelectedItem().toString())){
					apkStatus.setText("Status : APK downloaded!");
				}else{
					apkStatus.setText("Status : ERROR!");
				}
					
			}
		});
		
	}
}
