package webRequest;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TestGetPost {
	public static void testPost() throws IOException {
		String charset = "gbk";
		String filePath = "C:\\test.html";
		URL url = new URL("http://www.xiaohuangji.com/ajax.php");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		connection.addRequestProperty("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
		connection.addRequestProperty("Accept-Encoding", "gzip,deflate");
		connection.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
		connection.addRequestProperty("Connection", "keep-alive");
//		connection.addRequestProperty("Cache-Control", "max-age=0");
//		connection.addRequestProperty("Content-Length", "155");
		connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//		connection.addRequestProperty("Cookie", "__utma=158042917.2084951745.1363157732.1363157732.1364475518.2; __utmz=158042917.1363157732.1.1.utmcsr=bitren.com|utmccn=(referral)|utmcmd=referral|utmcct=/; BIGipServerPool_mail_80=1415498954.20480.0000; EMPHPSID=lj3t5lin881hhrp689qgqqktg0; empos=0; emLoginUser=20091743%40bit.edu.cn");
		connection.addRequestProperty("Host", "www.xiaohuangji.com");
		connection.addRequestProperty("Origin", "http://www.xiaohuangji.com");
		connection.addRequestProperty("Referer", "http://www.xiaohuangji.com");
		connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 SE 2.X MetaSr 1.0");
		
		OutputStreamWriter out = new OutputStreamWriter(
				connection.getOutputStream(), charset);
		String tmp = "para=no";
		String msg = new String(tmp.getBytes("Unicode"), "UTF-8");
		out.write(msg); // 向页面传递数据。post的关键所在！
		// remember to clean up
		out.flush();
		out.close();

		BufferedReader bReader;
		String rLine;
		StringBuffer stringBuffer = new StringBuffer();
		// 一旦发送成功，用以下方法就可以得到服务器的回应：
		bReader = new BufferedReader(new InputStreamReader(url.openStream(),
				charset));
		while ((rLine = bReader.readLine()) != null) {
			stringBuffer.append(rLine + "\r\n");
		}

		if (bReader != null) {
			bReader.close();
		}
		OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(
				filePath), charset);
		w.write(stringBuffer.toString());
		w.flush();
		w.close();
	}

	public static void testGet() {
		try {
			String charset = "utf-8";
			String filePath = "C:\\test.html";
			URL url = new URL("http://www.renrendai.com");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
//			connection.setDoOutput(true);
			connection.setRequestMethod("GET");

			BufferedReader bReader;
			String rLine;
			StringBuffer stringBuffer = new StringBuffer();
			// 一旦发送成功，用以下方法就可以得到服务器的回应：
			bReader = new BufferedReader(new InputStreamReader(
					url.openStream(), charset));
			while ((rLine = bReader.readLine()) != null) {
				stringBuffer.append(rLine + "\r\n");
			}

			if (bReader != null) {
				bReader.close();
			}
			OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(
					filePath), charset);
			w.write(stringBuffer.toString());
			w.flush();
			w.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		System.setProperty("proxySet", "true");
		System.setProperty("http.proxyHost", "10.108.13.240");
		System.setProperty("http.proxyPort", "8080");
//		 testPost();
		testGet();
	}
}