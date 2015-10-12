package spider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Test.PicOperator;

import common.CommonArg;

public class Spider {
	private ArrayList<String> WaitingList = new ArrayList<String>(); // 存储未处理URL
	private String charset; 				// 网站编码
	private boolean charsetFlag = false; 	// 已获得网站编码标志
	private boolean getting; 				// 得到内容页面URL线程是否正在运行标志，true表示正在运行，flase表示已经运行结束
	private int succeedNum = 0; 			// 获取成功的图片数量
	private int failedNum = 0; 				// 获取失败的图片数量
	private int sameNum = 0; 				// 重复的图片数量
	private int runningNum = 1; 			// 爬虫正在运行的线程数
	private int count = 0;					// 已加入的页面Url总数
	private String strHomePage; 			// 主页地址
	private int intThreadNum; 				// 线程数
	private String fileDirectory; 			// 保存文件夹路径
	private boolean AgentFlag = false; 		// 代理标志
	private String IpAddress; 				// 代理IP
	private String Port; 					// 代理端口
	private boolean TimeFlag = false; 		// 限制最大时间标志
	private long MaxTime; 					// 限制最长时间，单位毫秒，此为1分钟
	private boolean MaxUrlFlag = false; 	// 最大页面数标志
	private int MaxUrl; 					// 最大Url数
	private String regDescription; 			// 获得图片描述正则式
	private String regPrice; 				// 获取价格正则式
	private String regPicUrl; 				// 获取图片网址正则式
	private String regPicPageUrl; 			// 获取图片页面网址正则式
	private String regNextPageUrl; 			// 下一页的网页网址
	private String regContentPageUrl; 		// 内容网页网址正则式
	private long begin;						// 爬虫开始的时间
	/**
	 * 读取配置文件
	 * @return
	 */
	private boolean readConfigFile() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream("file\\spiderConfig.txt")));
			// 起始页地址
			strHomePage = br.readLine();
			// 保存图片文件夹地址
			fileDirectory = br.readLine();
			new File(fileDirectory).mkdir();
			// 内容网页网址正则式
			regContentPageUrl = br.readLine();
			// 下一页的网页网址
			regNextPageUrl = br.readLine();
			// 图片页面网址正则式
			regPicPageUrl = br.readLine();
			// 图片网址正则式
			regPicUrl = br.readLine();
			// 价格正则式
			regPrice = br.readLine();
			// 描述正则式
			regDescription = br.readLine();
			// 设置最大线程数
			try {
				intThreadNum = Integer.parseInt(br.readLine());
			} catch (Exception e) {
				intThreadNum = 10;
			}
			// 设置最大URL数标志
			try {
				MaxUrl = Integer.parseInt(br.readLine());
				MaxUrlFlag = true;
			} catch (Exception e) {
				MaxUrlFlag = false;
			}
			// 设置最长时间标志
			try {
				MaxTime = Integer.parseInt(br.readLine()) * 6000;
				TimeFlag = true;
			} catch (Exception e) {
				TimeFlag = false;
			}
			// 设置代理标志
			try {
				IpAddress = br.readLine();
				Port = br.readLine();
				AgentFlag = true;
			} catch (Exception e) {
				AgentFlag = false;
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
	 * @param urlString URL地址
	 * @return 该页面的内容
	 */
	private String getUrlContent(String urlString) {
		if (!charsetFlag) {
			if (!getCharset(urlString)) {
				charset = "utf-8";
			}
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
	 * 得到网页编码
	 * @param urlString URL地址
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
				Matcher m = Pattern.compile("charset.+?\".+?\"").matcher(rLine);
				if (m.find()) {
					charset = m.group(0).substring(
							m.group(0).indexOf('\"') + 1,
							m.group(0).length() - 1);
					return false;
				}
			}
			return false;
		} catch (Exception e) {
			System.out.println("得到编码出错");
			return false;
		}
	}

	/**
	 * 从等待队列里取出一个
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
		int stoppos = lastPart.indexOf("[\\s\\S]+?");
		if (stoppos != -1){
			lastPart = lastPart.substring(0, stoppos);
		}
		String result = content.substring(content.indexOf(firstPart)
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
		// 记录开始时间
		begin = System.currentTimeMillis();
		CommonArg.setIndex(begin);
		// 读取配置文件
		if (!readConfigFile()) {
			return;
		}
		// 设置代理
		if (AgentFlag) {
			System.setProperty("proxySet", "true");
			System.setProperty("http.proxyHost", IpAddress);
			System.setProperty("http.proxyPort", Port);
		}

		// 得到所有内容页面的url
		getting = true;
		new Thread(new GetWaitingList()).start();

		// 多线程调用处理程序
		for (int i = 0; i < intThreadNum; i++) {
			new Thread(new ProcessWaitingList()).start();
		}

		// 判断主线程终止条件
		while (true) {
			if (CommonArg.isSpiderStopFlag() ||WaitingList.isEmpty() && runningNum == 1) {
				System.out.println("Succeed:" + succeedNum);
				System.out.println("Failed:" + failedNum);
				System.out.println("Same:" + sameNum);
				System.out.println("Time:"
						+ (System.currentTimeMillis() - begin) / 1000 + "s");
				System.out.println("Finished!");
				CommonArg.setSpiderFlag(false);
				break;
			}
			if (CommonArg.isSpiderStopFlag() || (TimeFlag && System.currentTimeMillis() - begin > MaxTime
					) ) {
				WaitingList.clear();
				TimeFlag = false;
			}
		}
	}

	/**
	 * 解析新的网页，提取其中含有的链接信息，返回下一页的网址
	 * @param strUrl 一级Url地址
	 * @return 下一页的一级Url地址
	 */
	public synchronized String readNextPage(String strUrl) {
		boolean pageFlag = false;
		Pattern p;
		Matcher m;

		// 得到内容片网页的URL
		String urlContent = getUrlContent(strUrl);
		String tmpStr = urlContent;

		p = Pattern
				.compile(getReg(regContentPageUrl), Pattern.CASE_INSENSITIVE);
		m = p.matcher(tmpStr);
		boolean blnp = m.find();
		while (blnp == true) {
			String url = getResult(regContentPageUrl, m.group(0));
			if (MaxUrlFlag && count >= MaxUrl) {
				return null;
			}
			WaitingList.add(url);
			count++;
			tmpStr = tmpStr.substring(m.end(), tmpStr.length());
			m = p.matcher(tmpStr);
			blnp = m.find();
			pageFlag = true;
		}

		if (!pageFlag) {
			return null;
		}
		// 再处理下一页的情况
		p = Pattern.compile(getReg(regNextPageUrl), Pattern.CASE_INSENSITIVE);
		m = p.matcher(urlContent);
		if (m.find()) {
			String url = getResult(regNextPageUrl, m.group(0));
			return url;
		}
		return null;
	}

	/**
	 * 解析新的网页，提取其中含有的链接信息
	 * @param strUrl Url地址
	 * @param myOperator 图片操作对象
	 * @return true表示处理成功，false表示处理失败
	 */
	public boolean readContentPage(String strUrl, PicOperator myOperator) {
		String imageURLString = "null";
		String descriptionString = "null";
		String priceString = "null";
		String tmpStr;
		Pattern p;
		Matcher m;

		String urlContent = getUrlContent(strUrl);
		if (urlContent == null) {
			return false;
		}
		// 得到描述
		p = Pattern.compile(regDescription, Pattern.CASE_INSENSITIVE);
		m = p.matcher(urlContent);
		if (m.find()) {
			descriptionString = getResult(regDescription, m.group(0));
		}

		// 得到价格
		p = Pattern.compile(regPrice, Pattern.CASE_INSENSITIVE);
		m = p.matcher(urlContent);
		if (m.find()) {
			priceString = getResult(regPrice, m.group(0));
		}

		String picName;
		if (regPicPageUrl.equals("true")) {
			tmpStr = urlContent;
		} else {
			p = Pattern
					.compile(getReg(regPicPageUrl), Pattern.CASE_INSENSITIVE);
			m = p.matcher(urlContent);
			if (m.find()) {
				String url = getResult(regPicPageUrl, m.group(0));
				tmpStr = getUrlContent(url);
			} else {
				System.out.println("图片网页页面Url错误");
				return false;
			}
		}
		// 得到图片URL
		p = Pattern.compile(getReg(regPicUrl), Pattern.CASE_INSENSITIVE);
		m = p.matcher(tmpStr);
		boolean blnp = m.find();
		while (blnp == true) {
			imageURLString = getResult(regPicUrl, m.group(0));
			long timestamp = CommonArg.getIndex();
			picName = timestamp
					+ imageURLString.substring(imageURLString.lastIndexOf('.'));
			// 如果得到图片失败
			if (!GetPicture(imageURLString, fileDirectory + picName)) {
				failedNum++;
			}
			// 如果得到图片成功
			else {
				String imagePath = fileDirectory + picName;
				String message = descriptionString + "@@" + priceString + "@@"
						+ strUrl;
				if (succeedNum == 200){
					System.out.println((System.currentTimeMillis()-begin)+":"+succeedNum);
				}
				succeedNum++;
			}
			tmpStr = tmpStr.substring(m.end());
			m = p.matcher(tmpStr);
			blnp = m.find();
		}
		return true;
	}

	/**
	 * 处理等待队列的线程
	 * @author zhangzhizhi
	 *
	 */
	class ProcessWaitingList implements Runnable {
		public void run() {
			runningNum++;
			PicOperator myOperator = new PicOperator();
			while (true) {
				if (WaitingList.isEmpty()) {
					if (!getting || CommonArg.isSpiderStopFlag()) {
						break;
					} else {
						try {
							Thread.sleep(200);
							continue;
						} catch (InterruptedException e) {
						}
					}
				}
				String url = getWaitingUrl();
				if (url == null) {
					continue;
				}
				readContentPage(url, myOperator);
			}
			runningNum--;
		}
	}


	/**
	 * 获取页面URL线程
	 * @author zhangzhizhi
	 *
	 */
	class GetWaitingList implements Runnable {
		public void run() {
			String nextUrlString = strHomePage;
			while (true) {
				nextUrlString = readNextPage(nextUrlString);
				if (nextUrlString == null || CommonArg.isSpiderStopFlag()) {
					break;
				}
				if (WaitingList.size() > 100) {
					try {
						Thread.sleep(1000);
						continue;
					} catch (InterruptedException e) {
					}
				}
			}
			getting = false;
		}
	}
	public static void main(String[] args) {
		new Spider().startSpider();
	}
}
