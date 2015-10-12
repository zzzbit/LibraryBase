package Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.CommonArg;
import common.db_Operator;

public class Test {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		try {
			// System.out.println(URLEncoder.encode("?", "UTF-8"));
			// System.out.println(URLEncoder.encode("?", "ISO-8859-1"));
			// System.out.println(URLEncoder.encode("?", "GB2312"));

			// System.out.println(URLDecoder.decode("http://www.okbuy.com/REDSAND-%25E7%25BA%25A2%25E7%25A0%2582-%25E7%2594%25B7%25E5%2587%2589%25E9%259E%258B-%25E5%2587%2589%25E6%258B%2596-%25E6%259F%2594%25E8%25BD%25AF%25E8%2588%2592%25E9%2580%2582%25E7%2589%259B%25E7%259A%25AE%25E9%2587%2591%25E5%25B1%259E%25E6%2589%25A3%25E8%25AE%25BE%25E8%25AE%25A1%25E6%2597%25B6%25E5%25B0%259A%25E5%2587%2589%25E9%259E%258B-%25E9%25BB%2584%25E8%2589%25B2-CB8203-4%25E9%25BB%2584%25E6%25A3%2595/shoe-17054861.html",
			// "gbk"));

			// db_Operator mydb_Operator = new db_Operator("sqlserver",
			// "Shoes20130609", "sa", "20091743");
			// db_Operator mydb_Operator2 = new db_Operator("sqlserver",
			// "Shoes20130609", "sa", "20091743");
			// ResultSet rs = mydb_Operator.db_Query("select * from tmpBrand");
			// while (rs.next()) {
			// String tmp = rs.getString(1);
			// if (tmp.charAt(0) < 255){
			// mydb_Operator2.db_Execute("insert Brand values(2,'"+tmp+"')");
			// }
			// else {
			// mydb_Operator2.db_Execute("insert Brand values(1,'"+tmp+"')");
			// }
			// }
			// String regEx = "[\\u4e00-\\u9fa5]+";
			// String str = "字符串sf哈哈哈哈";
			// Pattern p = Pattern.compile(regEx);
			// Matcher m = p.matcher(str);
			// System.out.print("提取出来的中文有：");
			// while (m.find()) {
			// for (int i = 0; i < m.group().length(); i++){
			// System.out.println(m.group(i));
			// }
			// System.out.println(m.group(0));
			// }
			// System.out.println("郑源 - 不要在我寂寞的时候说爱我(国)".replaceAll("（.+?）",
			// "").replaceAll("\\(.+?\\)", ""));
			String digitNumStr = "11A11、22A22、33A33、44B44、55B55";
			Pattern digitNumP = Pattern.compile("(?<twoDigit>\\d{2})[A-Z]\\k<twoDigit>");
			Matcher foundDigitNum = digitNumP.matcher(digitNumStr);
			// 找到所有匹配
			while (foundDigitNum.find()) {
				String digitNumList = foundDigitNum.group("twoDigit");
				System.out.println(digitNumList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
