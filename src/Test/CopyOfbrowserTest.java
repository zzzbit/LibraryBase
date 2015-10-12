package Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class CopyOfbrowserTest {


public static void main(String args[]) {


// String URL1 = "http://192.168.0.17:50070";
//
// try {
// String result = null;
// HttpClient client = new DefaultHttpClient();
// HttpPost post = new HttpPost(URL1);
//
// HttpResponse response = client.execute(post);
// if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
// result = EntityUtils.toString(response.getEntity(), "UTF-8");
// }
//
// } catch (Exception e) {
// e.printStackTrace();
// throw new RuntimeException("post请求失败");
// }
try {
readContentFromGet();
} catch (IOException e) {

e.printStackTrace();
}
}


public static final String GET_URL = "http://192.168.0.17:50070/dfshealth.jsp";


public static final String POST_URL = "http://192.168.0.17:50070";


public static void readContentFromGet() throws IOException {
// 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
String getURL = GET_URL;
URL getUrl = new URL(getURL);
// 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
HttpURLConnection connection = (HttpURLConnection) getUrl
.openConnection();
// 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
// 服务器
connection.connect();
// 取得输入流，并使用Reader读取
BufferedReader reader = new BufferedReader(new InputStreamReader(
connection.getInputStream(), "utf-8"));// 设置编码,否则中文乱码
System.out.println("=============================");
System.out.println("Contents of get request");
System.out.println("=============================");
String lines;
while ((lines = reader.readLine()) != null) {
// lines = new String(lines.getBytes(), "utf-8");
System.out.println(lines);
}
reader.close();
// 断开连接
connection.disconnect();
System.out.println("=============================");
System.out.println("Contents of get request ends");
System.out.println("=============================");
}


public static void readContentFromPost() throws IOException {
// Post请求的url，与get不同的是不需要带参数
URL postUrl = new URL(POST_URL);
// 打开连接
HttpURLConnection connection = (HttpURLConnection) postUrl
.openConnection();
// Output to the connection. Default is
// false, set to true because post
// method must write something to the
// connection
// 设置是否向connection输出，因为这个是post请求，参数要放在
// http正文内，因此需要设为true
connection.setDoOutput(true);
// Read from the connection. Default is true.
connection.setDoInput(true);
// Set the post method. Default is GET
connection.setRequestMethod("POST");
// Post cannot use caches
// Post 请求不能使用缓存
connection.setUseCaches(false);
// This method takes effects to
// every instances of this class.
// URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
// connection.setFollowRedirects(true);


// This methods only
// takes effacts to this
// instance.
// URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
connection.setInstanceFollowRedirects(true);
// Set the content type to urlencoded,
// because we will write
// some URL-encoded content to the
// connection. Settings above must be set before connect!
// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
// 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
// 进行编码
connection.setRequestProperty("Content-Type",
"application/x-www-form-urlencoded");
// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
// 要注意的是connection.getOutputStream会隐含的进行connect。
connection.connect();
DataOutputStream out = new DataOutputStream(
connection.getOutputStream());
// The URL-encoded contend
// 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
String content = "key=j0r53nmbbd78x7m1pqml06u2&type=1&toemail=jiucool@gmail.com"
+ "&activatecode=" + URLEncoder.encode("久酷博客", "utf-8");
// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
out.writeBytes(content);
out.flush();
out.close();// flush and close
BufferedReader reader = new BufferedReader(new InputStreamReader(
connection.getInputStream(), "utf-8"));// 设置编码,否则中文乱码
String line = "";
System.out.println("=============================");
System.out.println("Contents of post request");
System.out.println("=============================");
while ((line = reader.readLine()) != null) {
// line = new String(line.getBytes(), "utf-8");
System.out.println(line);
}
System.out.println("=============================");
System.out.println("Contents of post request ends");
System.out.println("=============================");
reader.close();
connection.disconnect();
}


}