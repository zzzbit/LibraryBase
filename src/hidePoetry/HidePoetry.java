package hidePoetry;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HidePoetry {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextPane result;
	private JTextPane txtpnii;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HidePoetry window = new HidePoetry();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HidePoetry() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setTitle("\u53E4\u8BD7\u7684\u79D8\u5BC6");
		frame.setBounds(100, 100, 379, 497);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(29, 10, 288, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				result.setText("");
				result.setText(SearchDb.cts(textField.getText(), textField_1.getText()));
			}
		});
		button.setBounds(247, 49, 66, 23);
		frame.getContentPane().add(button);
		
		result = new JTextPane();
		result.setBounds(69, 94, 155, 336);
		frame.getContentPane().add(result);
		
		textField_1 = new JTextField();
		textField_1.setBounds(171, 50, 66, 21);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel label = new JLabel("\u89C4\u5219\uFF08\u7A7A\u4E3A\u85CF\u5934\u8BD7\uFF09");
		label.setBounds(39, 53, 122, 15);
		frame.getContentPane().add(label);
		
		txtpnii = new JTextPane();
		txtpnii.setText("\u89C4\u5219\u7684\u8BF4\u660E\uFF1A\r\n\u9ED8\u8BA4\u4E3A\u85CF\u5934\u8BD7\uFF0C\u53EF\u586B\u51650-6\u7684\u6709\u6548\u6570\u503C\uFF0C\u4F8B\u5982\u201C3\u201D\u3002\r\n\u6216\u8005\u662F\u5E26\u6709i\u7684\u6570\u5B66\u8868\u8FBE\u5F0F\uFF0C\u4F8B\u5982\u201C2*i+1\u201D\uFF0C\u5176\u4E2Di\u4EE3\u8868\u884C\u6570\uFF0C\u56E0\u6B64\u53EF\u4EE5\u5F97\u5230\u5F62\u5F0F\u591A\u53D8\u7684\u6548\u679C\u3002");
		txtpnii.setBounds(247, 90, 93, 210);
		frame.getContentPane().add(txtpnii);
	}
}
