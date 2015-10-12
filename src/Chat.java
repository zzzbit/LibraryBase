

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Color;

public class Chat {

	private JFrame frame;
	private JTextField input;
	private JTextArea result;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat window = new Chat();
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
	public Chat() {
		initialize();
		System.setProperty("proxySet", "true");
		System.setProperty("http.proxyHost", "cs.bit.edu.cn");
		System.setProperty("http.proxyPort", "2804");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		input = new JTextField();
		input.setBounds(22, 21, 223, 21);
		frame.getContentPane().add(input);
		input.setColumns(10);
		
		JButton ok = new JButton("\u786E\u5B9A");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new upload()).start();
			}
		});
		ok.setBounds(276, 20, 93, 23);
		frame.getContentPane().add(ok);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setText("\u5E2E\u52A9\uFF1A\r\n1.\u56DE\u590D\u201C\u7FFB\u8BD1\u201D\u4E2D\u82F1\u4E92\u8BD1\r\n2.\u56DE\u590D\u201C\u7B11\u8BDD\u201D\u67E5\u770B\u7B11\u8BDD\r\n3.\u56DE\u590D\u201C\u6717\u8BFB\u201D\u542C\u5185\u5BB9\r\n4.\u63D0\u95EE+\u95EE\u9898\r\n5.\u56DE\u590D\u201C\u5FEB\u9012\u201D\u67E5\u7269\u6D41\r\n6.\u56DE\u590D \u201C\u5317\u4EAC\u5929\u6C14\u201D\u770B\u9884\u62A5\r\n7.\u56DE\u590D\u201Cnba\u201D\u770B\u8D5B\u4E8B\r\n8.\u56DE\u590D\u201C\u706B\u8F66\u201D\u770B\u65F6\u523B\u8868\r\n10.\u56DE\u590D\u201C\u661F\u5EA7\u201D\u770B\u8FD0\u52BF\r\n11.\u56DE\u590D\u4EFB\u610F\u5185\u5BB9\u8C03\u620F\u6211");
		textArea.setBounds(247, 52, 177, 200);
		frame.getContentPane().add(textArea);
		
		result = new JTextArea();
		result.setEditable(false);
		result.setBounds(22, 78, 203, 137);
		frame.getContentPane().add(result);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setText("\u7ED3\u679C\uFF1A");
		textArea_1.setEditable(false);
		textArea_1.setBounds(22, 52, 113, 23);
		frame.getContentPane().add(textArea_1);
	}
	public class upload implements Runnable{

		@Override
		public void run() {
			try {
				result.setText("");
//				System.setProperty("proxySet", "true");
//				System.setProperty("http.proxyHost", "cs.bit.edu.cn");
//				System.setProperty("http.proxyPort", "2804");
				String url = "http://www.xiaohuangji.com/ajax.php";
				HttpClient httpClient = new HttpClient();
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
				PostMethod post = new PostMethod(url);
				post.setRequestHeader("Accept", "*/*");
				post.setRequestHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
				post.setRequestHeader("Accept-Encoding", "gzip,deflate");
				post.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
				post.setRequestHeader("Connection", "keep-alive");
				post.setRequestHeader("Content-Type",
						"application/x-www-form-urlencoded");
				post.setRequestHeader("Host", "www.xiaohuangji.com");
				post.setRequestHeader("Origin", "http://www.xiaohuangji.com");
				post.setRequestHeader("Referer", "http://www.xiaohuangji.com");
				post.setRequestHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 SE 2.X MetaSr 1.0");
				String inputString = input.getText();
				System.out.println(inputString);
				NameValuePair[] param = { new NameValuePair("para", inputString), };
				post.setRequestBody(param);
				post.releaseConnection();
				httpClient.executeMethod(post);
				byte[] responseBody = post.getResponseBody();
				// ��������
				String response = new String(responseBody,"utf-8");
				result.setText(response);
			} catch (Exception e) {
				result.setText("���ӳ�ʱ");
			}
		}
		
	}
}
