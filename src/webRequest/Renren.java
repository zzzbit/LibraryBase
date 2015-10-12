package webRequest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Renren {
	public static void main(String[] args) {
		try {
			// TODO Auto-generated method stub
			String loginurl = "http://bit.edu.cn/?q=login.do";
			String username = "20091743@bit.edu.cn";
			String password = "791121";
//			posturl(loginurl, username, password);
//			geturl("http://www.renren.com/callback.do?t=49639ca222c406bbc7025a4f24caa44f2&origURL=http%3A%2F%2Fwww.renren.com%2Fhome&autoLogin=true&needNotify=false");
			geturl("http://bit.edu.cn/user/?q=login&furl=%2Fuser%2F%3Fq%3Dbase%26module%3Dlistmail%26_data%3Dlistmail%253D%25253Ffid%25253D1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void posturl(String loginurl, String username,String password) {
		String charset = "utf-8";
		String filePath = "C:\\test.html";
		HttpPost httppost = new HttpPost(loginurl);
		String str = "";
		try {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("user", username));
			formparams.add(new BasicNameValuePair("password", password));
			formparams.add(new BasicNameValuePair("go", "http://bit.edu.cn/?q=base"));
			formparams.add(new BasicNameValuePair("referer", "http://bit.edu.cn/"));
			httppost.setEntity(new UrlEncodedFormEntity(formparams,"utf-8"));
			
			String login_success = new DefaultHttpClient().execute(httppost).getFirstHeader("Location").getValue();
			System.out.println(login_success);
			HttpGet httpget = new HttpGet(login_success);
			str = EntityUtils.toString(new DefaultHttpClient().execute(httpget).getEntity());
			httppost.abort();
			httpget.abort();
			OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(
					filePath), charset);
			w.write(str);
			w.flush();
			w.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void geturl(String login_success) {
		String charset = "utf-8";
		String filePath = "C:\\test.html";
		String str = "";
		try {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			HttpGet httpget = new HttpGet(login_success);
			str = EntityUtils.toString(new DefaultHttpClient().execute(httpget).getEntity());
			httpget.abort();
			OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(
					filePath), charset);
			w.write(str);
			w.flush();
			w.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}