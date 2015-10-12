package webRequest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpClientTest {

	public static void main(String[] args) throws Exception {
		String url = "http://bityan.bitnp.com/admins/edit.php";
		// String host = "www.webxml.com.cn";
		// String param = "startCity="+URLEncoder.encode("杭州",
		// "utf-8")+"&lastCity=&theDate=&userID=";
		String param = "";
		HttpClient httpClient = new HttpClient();
		// httpClient.getHostConfiguration().setHost(host, 80, "http");

		HttpMethod method = getMethod(url);
		//HttpMethod method = postMethod(url);

		httpClient.executeMethod(method);

//		String response = method.getResponseBodyAsString();
		byte[] responseBody = method.getResponseBody();
		// 处理内容
		String response = new String(responseBody,"utf-8");
		System.out.println(response);

		// String response = new
		// String(method.getResponseBodyAsString().getBytes("ISO-8859-1"));
		// System.out.println(response);
		OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(
				"C:\\test.html"));
		w.write(response);
		w.flush();
		w.close();
	}

	private static HttpMethod getMethod(String url)
			throws IOException {
		GetMethod get = new GetMethod(url);
		get.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		get.setRequestHeader("Accept-Encoding","gzip,deflate,sdch");
		get.setRequestHeader("Accept-Language","zh-CN,zh;q=0.8");
		get.setRequestHeader("Cookie","PHPSESSID=0dgs013nj0ohoae26i7bqqvl02");
		get.setRequestHeader("Host","bityan.bitnp.com");
		get.setRequestHeader("Proxy-Connection","keep-alive");
		get.setRequestHeader("Referer","http://bityan.bitnp.com/admins/login.php");
		get.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.35 (KHTML, like Gecko) Chrome/27.0.1448.0 Safari/537.35");
		get.releaseConnection();
		return get;
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
		NameValuePair[] param = { new NameValuePair("para", "张志智"), };
		post.setRequestBody(param);
		post.releaseConnection();
		return post;
	}
}