package webRequest;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class CheckUrl {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.setProperty("proxySet", "true");
			System.setProperty("http.proxyHost", "10.108.13.240");
			System.setProperty("http.proxyPort", "8080");
			
			String urlString = "http://www.renrendai.com/lend/lendPage.action";
			String charset = "utf-8";
			String filePath = "C:\\test.html";
			URL url = new URL(urlString);
			URLConnection conn;
			BufferedReader bReader;
			String rLine;
			StringBuffer stringBuffer = new StringBuffer();

			// 得到源码
			conn = url.openConnection();
			conn.setDoOutput(true);
			
//			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "8859_1");     
//	        out.write("wd=hello"); //向页面传递数据。post的关键所在！      
//	        out.flush();     
//	        out.close();
			
			
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
		} catch (Exception e) {
			System.out.println("得到Url页面内容出错");
		}
	}

}
