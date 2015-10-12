import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 配置文件
 * 第1行：任务名称
 * 第2行：起始网址行数n(int)
 * 第3行-第n+2行：起始网址，例：www.sort=&page=(*)|1|3，(*)为通配符，从1到3
 * 第n+3行：级别数m(int)
 * 第n+4行-第m+n+3行：例：02jpg_@@og:image" content=".+?"@@01Intro@@property="og:title" content=".+?"。
 * （接上）第一位为0表示单个匹配，为1为循环匹配，第二位为0表示下一层的Url，为1表示单纯的信息，为2表示文件下载，为3表示分页。
 * （接上）若为文件下载在第3位开始为后缀名至'_'结束，奇数的@@为匹配类型，偶数的@@为匹配规则，
 * （接上）匹配规则中，'|'前为正则式，其中有且只有一个".+?"，其他字符串的匹配用"[\s\S]+?"，表示需要提取的信息，'|'为拼接表达式，其中(*)为提取的信息
 * @author zhangzhizhi
 *
 */
public class FileSpider {
	private ArrayList<String> WaitingList = new ArrayList<String>(); // 存储未处理URL
	private int totalLevel = 2;
	private String[] rule;
	private String taskSign;
	private boolean[] addFileFlag;
	private static long index = 0;
	/**
	 * @return the index
	 */
	public static synchronized long getIndex() {
		index++;
		return index;
	}

	private String charset = "utf-8"; // 网站编码
	private boolean charsetFlag = false; // 已获得网站编码标志
	private int runningNum = 1; // 爬虫正在运行的线程数
	private int intThreadNum = 10; // 线程数
	private String fileDirectory = "myspider"; // 保存文件夹路径
	private long begin; // 爬虫开始的时间
	private String file = "now.txt";

	// 读取配置文件
		private boolean readConfigFile() {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(file)));
				// 起始页地址
				taskSign = br.readLine();
				int M = Integer.parseInt(br.readLine());
				for (int i = 0; i < M; i++) {
					// 加入List中
					String startPage = br.readLine();
					if (startPage.contains("(*)")){
						int min = Integer.parseInt(startPage.substring(startPage.indexOf('|')+1, startPage.lastIndexOf('|')));
						int max = Integer.parseInt(startPage.substring(startPage.lastIndexOf('|')+1));
						for (int j = min; j <= max; j++){
							String tmp =  startPage.substring(0,startPage.indexOf('|'));
							tmp = tmp.replace("(*)",""+j);
							WaitingList.add(tmp);
						}
					}
					else {
						WaitingList.add(startPage);
					}
				}
				// 保存图片文件夹地址
				totalLevel = Integer.parseInt(br.readLine());
				rule = new String[totalLevel];
				addFileFlag = new boolean[totalLevel];
				for (int i = 0; i < totalLevel; i++){
					addFileFlag[i] = true;
					rule[i] = br.readLine();
				}
				br.close();
				return true;
			} catch (Exception e) {
				System.out.println("读取配置文件失败");
				return false;
			}
		}
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
			conn.setConnectTimeout(2000);
			conn.setReadTimeout(2000);
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
				if (tmp.contains("gb2312")){
					charset = "gb2312";
				}
				else if (tmp.contains("utf-8")){
					charset = "utf-8";
				}
				else if (tmp.contains("gbk")){
					charset = "gbk";
				}
				else {
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
	 * 从等待队列里取出一个
	 * 
	 * @return 取出来的URL地址
	 */
	private synchronized String getWaitingUrl() {
		if (WaitingList.isEmpty()) {
			return null;
		}
		String tmpAUrl = WaitingList.get(0);
		WaitingList.remove(0);
		return tmpAUrl;
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
		if (stoppos != -1){
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
	public void startSpider() {
		// 记录开始时间
		begin = System.currentTimeMillis();
		readConfigFile();

		setIndex(System.currentTimeMillis());
		System.setProperty("proxySet", "true");
		System.setProperty("http.proxyHost", "10.108.12.6");
		System.setProperty("http.proxyPort", "901");
		// 多线程调用处理程序
		for (int i = 0; i < intThreadNum; i++) {
			new Thread(new ProcessWaitingList()).start();
		}

		// 判断主线程终止条件
		while (true) {
			if (WaitingList.isEmpty()
					&& runningNum == 1) {
				System.out.println("Time:"
						+ (System.currentTimeMillis() - begin) / 1000 + "s");
				System.out.println("Finished!");
				break;
			}
		}
	}

	// 将信息写入txt文件
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

	private void dealFun(String url, int level, long formerId) {
		if (level >= totalLevel) {
			return;
		}
		String urlContent = getUrlContent(url);
		if (addFileFlag[level]){
			System.out.println(charset);
			add2File(url+urlContent, taskSign+"_"+level+".html");
			addFileFlag[level] = false;
		}
		
		String regex = "@@";
		String msgSplit[] = rule[level].split(regex);
		int num = msgSplit.length;

		long id = getIndex();
		int index = 0;

		String result[] = new String[num / 2];
		for (int i = 0; i < num / 2; i++) {
			result[i] = "";
		}
		for (int i = 0; i < num; i += 2) {
			String realReg = getReg(msgSplit[i + 1]);
			String tmpStr = urlContent;
			Pattern p = Pattern.compile(realReg);
			Matcher m = p.matcher(tmpStr);
			boolean blnp = m.find();
			if (msgSplit[i].charAt(0) == '0') {
				if (blnp) {
					String urlResutl = getResult(msgSplit[i + 1], m.group(0));
					if (msgSplit[i].charAt(1) == '0') {
						dealFun(urlResutl, level + 1, id);
						result[i / 2] = urlResutl;
					} else if (msgSplit[i].charAt(1) == '1') {
						result[i / 2] = urlResutl;
					} else if (msgSplit[i].charAt(1) == '2') {
						int pos = urlResutl.lastIndexOf('.');
						String postfix;
						if (pos < 0 || urlResutl.length() - pos > 5){
							postfix = "."+ msgSplit[i].substring(2,msgSplit[i].indexOf('_'));
						}
						else {
							postfix = urlResutl.substring(pos);
						}
						String filePath = fileDirectory
								+ "\\"
								+ id
								+ "_"
								+ (index++)
								+ postfix;
						GetPicture(urlResutl, filePath);
						result[i / 2] = filePath;
					}
					 else if (msgSplit[i].charAt(1) == '3') {
						 	dealFun(urlResutl, level, id);
							result[i / 2] = urlResutl;
						}
				}
			} else {
				while (blnp) {
					String urlResutl = getResult(msgSplit[i + 1], m.group(0));
					if (msgSplit[i].charAt(1) == '0') {
						dealFun(urlResutl, level + 1, id);
						result[i / 2] += urlResutl + "\r\n";
					} else if (msgSplit[i].charAt(1) == '1') {
						result[i / 2] += urlResutl + "\r\n";
					} else if (msgSplit[i].charAt(1) == '2') {
						int pos = urlResutl.lastIndexOf('.');
						String postfix;
						if (pos < 0 || urlResutl.length() - pos > 5){
							postfix = "."+ msgSplit[i].substring(2,msgSplit[i].indexOf('_'));
						}
						else {
							postfix = urlResutl.substring(pos);
						}
						String filePath = fileDirectory
								+ "\\"
								+ id
								+ "_"
								+ (index++)
								+ postfix;
						GetPicture(urlResutl, filePath);
						result[i / 2] += filePath + "\r\n";
					}
					tmpStr = tmpStr.substring(m.end(), tmpStr.length());
					m = p.matcher(tmpStr);
					blnp = m.find();
				}
			}
		}
	}

	/**
	 * @param index the index to set
	 */
	public static synchronized void setIndex(long index) {
		FileSpider.index = index;
	}

	/**
	 * 处理等待队列的线程
	 * 
	 * @author zhangzhizhi
	 * 
	 */
	class ProcessWaitingList implements Runnable {
		public void run() {
			runningNum++;
			while (true) {
				if (WaitingList.isEmpty()) {
					break;
				}
				String url = getWaitingUrl();
				dealFun(url, 0, 0);
			}
			runningNum--;
		}
	}

	public static void main(String[] args) {
		new FileSpider().startSpider();
	}
}
