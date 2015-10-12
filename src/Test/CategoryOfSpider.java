package Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.db_Operator;

public class CategoryOfSpider {
	private String charset = "utf-8"; 				// 网站编码
	private String regDescription; 			// 获得图片描述正则式
	db_Operator myoperator2 = new db_Operator("sqlserver", "Shoes20130708", "sa",
			"20091743");
	private int man = 0;
	private int woman = 0;
	private int other = 0;

	/**
	 * 根据URL获取图片
	 * @param urlString URL地址
	 * @param filePath 保存文件路径
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
	 * 根据正则式得到想要的内容
	 * @param regString 正则式
	 * @param content 内容
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
		String result = content.substring(content.lastIndexOf(firstPart)
				+ firstPart.length(), content.lastIndexOf(lastPart));
		if (seppos != -1) {
			result = regString.substring(seppos + 1).replace("(*)", result);
		}
		return result;
	}

	/**
	 * 得到分隔符前的正则式
	 * @param regString 带参数的正则式
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
	public void startSpider() {
		db_Operator myoperator = new db_Operator("sqlserver", "Shoes20130708", "sa",
				"20091743");
		try {
			ResultSet rs=myoperator.db_Query("select Id,Url from ImageInfo where CategoryName is null");
			int num = 0;
			while(rs.next()){
				long id = rs.getLong(1);
				String url = rs.getString(2);
				readContentPage(url, id);
				if (num%100==0){
					System.out.println(num);
				}
				num++;
			}
			myoperator.db_Close();
			myoperator2.db_Close();
			System.out.println("man:"+man);
			System.out.println("woman:"+woman);
			System.out.println("other:"+other);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private String getUrlContent(String urlString) {
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
	 * 解析新的网页，提取其中含有的链接信息
	 * @param strUrl Url地址
	 * @param myOperator 图片操作对象
	 * @return true表示处理成功，false表示处理失败
	 */
	public boolean readContentPage(String strUrl,long id) {
		String categoryName = "null";
		Pattern p;
		Matcher m;

		String urlContent = getUrlContent(strUrl);
//		add2File(urlContent,"c:\\1.html");
		if (urlContent == null) {
			return false;
		}
		// 得到描述
		regDescription = "男鞋</a>&gt;";
		p = Pattern.compile(regDescription, Pattern.CASE_INSENSITIVE);
		m = p.matcher(urlContent);
		if (m.find()) {
			urlContent = urlContent.substring(m.end(), urlContent.length());
			regDescription = "'>.+?</a>&gt;";
			p = Pattern.compile(regDescription, Pattern.CASE_INSENSITIVE);
			m = p.matcher(urlContent);
			if (m.find()) {
				categoryName = getResult(regDescription, m.group(0));
//				System.out.println("男"+categoryName);
				myoperator2.db_Execute("update ImageInfo set CategoryName='男"+categoryName+"' where Id ="+id);
			}
			else {
//				System.out.println("男鞋未找到分类 "+strUrl);
				myoperator2.db_Execute("update ImageInfo set CategoryName='男鞋' where Id ="+id);
			}
		}
		else{
			regDescription = "女鞋</a>&gt;";
			p = Pattern.compile(regDescription, Pattern.CASE_INSENSITIVE);
			m = p.matcher(urlContent);
			if (m.find()) {
				urlContent = urlContent.substring(m.end(), urlContent.length());
				regDescription = "'>.+?</a>&gt;";
				p = Pattern.compile(regDescription, Pattern.CASE_INSENSITIVE);
				m = p.matcher(urlContent);
				if (m.find()) {
					categoryName = getResult(regDescription, m.group(0));
//					System.out.println("女"+categoryName);
					myoperator2.db_Execute("update ImageInfo set CategoryName='女"+categoryName+"' where Id ="+id);
				}
				else {
//					System.out.println("女鞋未找到分类 "+strUrl);
					myoperator2.db_Execute("update ImageInfo set CategoryName='女鞋' where Id ="+id);
				}
			}
			else {
//				System.out.println("非男鞋女鞋 "+strUrl);
				
				regDescription = "运动</a>&gt;";
				p = Pattern.compile(regDescription, Pattern.CASE_INSENSITIVE);
				m = p.matcher(urlContent);
				if (m.find()) {
					urlContent = urlContent.substring(m.end(), urlContent.length());
					regDescription = "'>.+?</a>&gt;";
					p = Pattern.compile(regDescription, Pattern.CASE_INSENSITIVE);
					m = p.matcher(urlContent);
					if (m.find()) {
						categoryName = getResult(regDescription, m.group(0));
//						System.out.println("女"+categoryName);
						myoperator2.db_Execute("update ImageInfo set CategoryName='运动"+categoryName+"' where Id ="+id);
					}
					else {
						myoperator2.db_Execute("update ImageInfo set CategoryName='运动鞋' where Id ="+id);
					}
				}
				else {
					other++;
				}
			}
		}
		return true;
	}
	
	private synchronized boolean add2File(String s, String pathString) {
		try {
			// 创建接收文件目录
			OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(
					pathString), charset);
			w.write(s);
			w.flush();
			w.close();
			return true;
		} catch (Exception e) {
			System.out.println("加入信息文件失败!");
			return false;
		}
	}

	public static void main(String[] args) {
		new CategoryOfSpider().startSpider();
	}
}
