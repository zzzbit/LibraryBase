package webRequest;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class RenrenDai {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
//			System.setProperty("proxySet", "true");
//			System.setProperty("http.proxyHost", "10.108.12.75");
//			System.setProperty("http.proxyPort", "8080");
			
			HttpClient httpClient = new HttpClient();
			HttpMethod method = login("zhangzhizhibit@163.com", "izzz0928");
			httpClient.executeMethod(method);
			byte[] responseBody = method.getResponseBody();
			String response = new String(responseBody,"utf-8");
			
			OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(
					"C:\\test.html"), "utf-8");
			w.write(response);
			w.flush();
			w.close();
		} catch (Exception e) {
			System.out.println("连接超时");
		}
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
	private static HttpMethod login(String username,String password) throws IOException {
		String url = "https://www.renrendai.com/j_spring_security_check";
		PostMethod post = new PostMethod(url);
		post.setRequestHeader("Accept", "*/*");
		post.setRequestHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
		post.setRequestHeader("Accept-Encoding", "gzip,deflate,sdch");
		post.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
		post.setRequestHeader("Connection", "keep-alive");
		post.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		post.setRequestHeader("Cookie", new String("JSESSIONID=8a31e7df08ef2059796f8b226c0d; Hm_lvt_71ce3105a964d0c3748e04584e5af0b9=1380440023; Hm_lpvt_71ce3105a964d0c3748e04584e5af0b9=1380440351; Hm_lvt_efa929f55e0b175f5657fd1d1d809be6=1380440025; Hm_lpvt_efa929f55e0b175f5657fd1d1d809be6=1380440352; __utma=215176340.324371145.1380440025.1380440025.1380440025.1; __utmb=215176340.3.10.1380440025; __utmc=215176340; __utmz=215176340.1380440025.1.1.utmcsr=baidu|utmccn=百度品牌专区|utmcmd=cpc|utmctr=0001|utmcct=主标题".getBytes(),"ISO8859-1"));
		post.setRequestHeader("Host", "www.renrendai.com");
		post.setRequestHeader("Origin", "https://www.renrendai.com");
		post.setRequestHeader("Referer", "https://www.renrendai.com/loginPage.action?error=false");
		post.setRequestHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 SE 2.X MetaSr 1.0");
		NameValuePair[] param = { new NameValuePair("targetUrl", ""),new NameValuePair("j_username",new String(username.getBytes(),"ISO8859-1") ),new NameValuePair("j_password",password),new NameValuePair("rememberme", "on"),new NameValuePair("returnUrl", "")};
		post.setRequestBody(param);
		post.releaseConnection();
		return post;
	}
}
