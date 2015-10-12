package webRequest;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class yanhui {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testGet();
	}
	public static void testGet() {
		try {
			String charset = "gb2312";
			String filePath = "C:\\test.html";
			URL url = new URL("http://bityan.bitnp.com/admins/edit.php");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setFollowRedirects(true);
			connection.setDoOutput(true); // 需要向服务器写数据
			connection.setDoInput(true); //
			connection.setUseCaches(false); // 获得服务器最新的信息
			connection.setAllowUserInteraction(false);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			connection.setRequestProperty("Accept-Encoding","gzip,deflate,sdch");
			connection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8");
			connection.setRequestProperty("Cookie","PHPSESSID=0dgs013nj0ohoae26i7bqqvl02");
			connection.setRequestProperty("Host","bityan.bitnp.com");
			connection.setRequestProperty("Proxy-Connection","keep-alive");
			connection.setRequestProperty("Referer","http://bityan.bitnp.com/admins/login.php");
			connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.35 (KHTML, like Gecko) Chrome/27.0.1448.0 Safari/537.35");
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
}
