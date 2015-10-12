package webRequest;

import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class Xiaohuangji {
	public static void main(String[] args) throws Exception {
		try {
			System.setProperty("proxySet", "true");
			System.setProperty("http.proxyHost", "10.4.60.164");
			System.setProperty("http.proxyPort", "8080");
			
			String url = "http://www.xiaohuangji.com/ajax.php";
			HttpClient httpClient = new HttpClient();
			HttpMethod method = postMethod(url);
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);  
			httpClient.executeMethod(method);
			byte[] responseBody = method.getResponseBody();
			// 处理内容
			String response = new String(responseBody,"utf-8");
			System.out.println(response);
		} catch (Exception e) {
			System.out.println("连接超时");
		}
	}
	private static HttpMethod postMethod(String url) throws IOException {
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
		NameValuePair[] param = { new NameValuePair("para", new String("星座".getBytes(),"ISO8859-1")) };
		post.setRequestBody(param);
		post.releaseConnection();
		return post;
	}
}