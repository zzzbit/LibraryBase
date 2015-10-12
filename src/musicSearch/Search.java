package musicSearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 配置文件 第1行：任务名称 第2行：起始网址行数n(int)
 * 第3行-第n+2行：起始网址，例：www.sort=&page=(*)|1|3，(*)为通配符，从1到3 第n+3行：级别数m(int)
 * 第n+4行-第m+n+3行：例：02jpg_@@og:image" content=".+?"@@01Intro@@property="og:title
 * " content=".+?"。
 * （接上）第一位为0表示单个匹配，为1为循环匹配，第二位为0表示下一层的Url，为1表示单纯的信息，为2表示文件下载，为3表示分页。
 * （接上）若为文件下载在第3位开始为后缀名至'_'结束，奇数的@@为匹配类型，偶数的@@为匹配规则，
 * （接上）匹配规则中，'|'前为正则式，其中有且只有一个".+?"
 * ，其他字符串的匹配用"[\s\S]+?"，表示需要提取的信息，'|'为拼接表达式，其中(*)为提取的信息
 * 
 * @author zhangzhizhi
 * 
 */
public class Search {
	private String charset = "utf-8"; // 网站编码
	private boolean charsetFlag = false; // 已获得网站编码标志
	/**
	 * 得到Url地址的内容
	 * 
	 * @param urlString
	 *            URL地址
	 * @return 该页面的内容
	 */
	private String getUrlContent(String urlString) {
		if (!charsetFlag) {
			getCharset(urlString);
			charsetFlag = true;
		}
		try {
			URL url = new URL(urlString);
			URLConnection conn;
			BufferedReader bReader;
			String rLine;
			StringBuffer stringBuffer = new StringBuffer();

			// 得到源码
			conn = url.openConnection();
			conn.setDoOutput(true);

			bReader = new BufferedReader(new InputStreamReader(
					url.openStream(), charset));
			while ((rLine = bReader.readLine()) != null) {
				stringBuffer.append(rLine + "\r\n");
			}

			if (bReader != null) {
				bReader.close();
			}
			return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("得到Url页面内容出错");
			return null;
		}
	}

	/**
	 * 根据URL获取图片
	 * 
	 * @param urlString
	 *            URL地址
	 * @param filePath
	 *            保存文件路径
	 * @return true表示成功，false表示失败
	 */
	public boolean GetPicture(String urlString, String filePath) {
		try {
			URL url = new URL(urlString);
			URLConnection conn = (URLConnection) url.openConnection();
			InputStream is = conn.getInputStream();
			File file = new File(filePath);
			FileOutputStream out = new FileOutputStream(file);
			int i = 0;
			while ((i = is.read()) != -1) {
				out.write(i);
			}
			is.close();
			out.close();
			return true;
		} catch (Exception e) {
			System.out.println("GetPicture failed!");
			return false;
		}
	}

	/**
	 * 得到网页编码
	 * 
	 * @param urlString
	 *            URL地址
	 * @return true表示成功，false表示失败
	 */
	private boolean getCharset(String urlString) {
		try {
			URL url = new URL(urlString);
			URLConnection conn;
			conn = url.openConnection();
			conn.setDoOutput(true);
			BufferedReader bReader;
			String rLine;
			bReader = new BufferedReader(
					new InputStreamReader(url.openStream()));
			while ((rLine = bReader.readLine()) != null) {
				String tmp = rLine.toLowerCase();
				if (tmp.contains("gb2312")) {
					charset = "gb2312";
				} else if (tmp.contains("utf-8")) {
					charset = "utf-8";
				} else if (tmp.contains("gbk")) {
					charset = "gbk";
				} else {
					continue;
				}
				break;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 根据正则式得到想要的内容
	 * 
	 * @param regString
	 *            正则式
	 * @param content
	 *            内容
	 * @return 匹配的内容
	 */
	private String getResult(String regString, String content) {
		int endpos = regString.indexOf(".+?"); // 需要替换的内容
		int seppos = regString.indexOf('|'); // 分离正则式和替换表达式
		String firstPart = regString.substring(0, endpos); // 找寻".+?"之前的字符串
		int startpos = firstPart.lastIndexOf("+?");
		if (startpos != -1) { // 如果存在"+?"，截取"+?"和".+?"之间的字符串
			firstPart = firstPart.substring(startpos + 2);
		}
		String lastPart;
		if (seppos != -1) {
			lastPart = regString.substring(endpos + 3, seppos);
		} else {
			lastPart = regString.substring(endpos + 3);
		}
		int stoppos = lastPart.indexOf("[\\s\\S]+?");
		if (stoppos != -1) {
			lastPart = lastPart.substring(0, stoppos);
		}
		String result = content.substring(content.lastIndexOf(firstPart)
				+ firstPart.length(), content.lastIndexOf(lastPart));
		if (seppos != -1) {
			result = regString.substring(seppos + 1).replace("(*)", result);
		}
		return result;
	}

	/**
	 * 得到分隔符前的正则式
	 * 
	 * @param regString
	 *            带参数的正则式
	 * @return 正则式
	 */
	private String getReg(String regString) {
		int last;
		if (regString.indexOf('|') != -1) {
			last = regString.indexOf('|');
		} else {
			last = regString.length();
		}
		return regString.substring(0, last);
	}

	/**
	 * 爬虫程序入口，由用户提供的域名站点开始，对所有链接页面进行抓取
	 */
	public boolean searchIrc(String filePath) {
		String dir = filePath.substring(0,filePath.lastIndexOf('.'));
		String name = filePath.substring(filePath.lastIndexOf('\\')+1, filePath.lastIndexOf('.'));
		String url = "";
		try {
			url = "http://www.cnlyric.com/search.php?k="+URLEncoder.encode(name.replaceAll("（.+?）", "").replaceAll("\\(.+?\\)", ""), "GB2312")+"&t=s";
		} catch (UnsupportedEncodingException e) {
			return false;
		}
		String reg = "<a href=\".+?\" class=\"ld\"|http://www.cnlyric.com/(*)";
		String urlContent = getUrlContent(url);
		Pattern p = Pattern.compile(getReg(reg));
		Matcher m = p.matcher(urlContent);
		if (m.find()){
			if (!GetPicture(getResult(reg, m.group(0)), dir+".lrc")){
				return false;
			}
			else {
				return true;
			}
		}
		else {
			return false;
		}
	}

	public static void main(String[] args) {
		new Search().searchIrc("C:\\Users\\zhangzhizhi\\Music\\BEYOND - 光辉岁月.mp3");
	}
}
