package webRequest;

import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class WebLogin {
	public static void main(String[] args) throws Exception {
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.executeMethod(login());
		} catch (Exception e) {
			System.out.println("Á¬½Ó³¬Ê±");
		}
	}
	private static HttpMethod login() throws IOException {
		String url = "http://10.0.0.55/cgi-bin/do_login";
		PostMethod post = new PostMethod(url);
		post.setRequestHeader("Accept", "*/*");
		post.setRequestHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
		post.setRequestHeader("Accept-Encoding", "gzip,deflate,sdch");
		post.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
		post.setRequestHeader("Connection", "keep-alive");
		post.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		post.setRequestHeader("Host", "10.0.0.55");
		post.setRequestHeader("Origin", "10.0.0.55");
		post.setRequestHeader("Referer", "http://10.0.0.55/index.html?http://www.baidu.com/");
		post.setRequestHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 SE 2.X MetaSr 1.0");
		NameValuePair[] param = { new NameValuePair("username", "2120131096"),new NameValuePair("password", "925d804cb6416f48"),new NameValuePair("drop", "0"),new NameValuePair("type", "1"),new NameValuePair("n", "100") };
		post.setRequestBody(param);
		post.releaseConnection();
		return post;
	}
	private static HttpMethod logout() throws IOException {
		String url = "http://10.0.0.55/cgi-bin/force_logout";
		PostMethod post = new PostMethod(url);
		post.setRequestHeader("Accept", "*/*");
		post.setRequestHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
		post.setRequestHeader("Accept-Encoding", "gzip,deflate,sdch");
		post.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
		post.setRequestHeader("Connection", "keep-alive");
		post.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		post.setRequestHeader("Host", "10.0.0.55");
		post.setRequestHeader("Origin", "10.0.0.55");
		post.setRequestHeader("Referer", "http://10.0.0.55/index.html?http://www.baidu.com/");
		post.setRequestHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 SE 2.X MetaSr 1.0");
		NameValuePair[] param = { new NameValuePair("username", "2120131096"),new NameValuePair("password", "20091743"),new NameValuePair("drop", "0"),new NameValuePair("type", "1"),new NameValuePair("n", "1") };
		post.setRequestBody(param);
		post.releaseConnection();
		return post;
	}
}