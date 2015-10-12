package hidePoetry;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.db_Operator;

public class BuildDb {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String s = null;
			String filepath;
			String regEx = "[\\u4e00-\\u9fa5|\\uF900-\\uFA2D]+";
			Pattern p = Pattern.compile(regEx);
			db_Operator myDb_Operator = new db_Operator("sqlserver", "Spider", "sa", "20091743");
			for (int i = 541; i <= 900; i++) {
				if (i < 10){
					filepath = "C:\\Users\\zhangzhizhi\\Documents\\Everyone\\#־#\\#Ȥ\\ʫ#\\qts\\qts_000"+i+".htm";
				}
				else if (i < 100) {
					filepath = "C:\\Users\\zhangzhizhi\\Documents\\Everyone\\#־#\\#Ȥ\\ʫ#\\qts\\qts_00"+i+".htm";
				}
				else {
					filepath = "C:\\Users\\zhangzhizhi\\Documents\\Everyone\\#־#\\#Ȥ\\ʫ#\\qts\\qts_0"+i+".htm";
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "gb2312"));
				while ((s = br.readLine()) != null) {
					int pos = s.indexOf("400080");
					if (pos<0){
						continue;
					}
					Matcher m = p.matcher(s);
					while (m.find()) {
						String msg = m.group(0);
						if (msg.length() != 5 && msg.length() != 7 || s.charAt(m.end()) != '#' && s.charAt(m.end()) != '#'){
							continue;
						}
						if (msg.length() == 5 && s.charAt(m.end()) == '#'){
							myDb_Operator.db_Execute("insert into Poetry values(15,'"+msg.charAt(0)+"','"+msg.charAt(1)+"','"+msg.charAt(2)+"','"+msg.charAt(3)+"','"+msg.charAt(4)+"',null,null)");
						}
						else if (msg.length() == 5 && s.charAt(m.end()) == '#'){
							myDb_Operator.db_Execute("insert into Poetry values(25,'"+msg.charAt(0)+"','"+msg.charAt(1)+"','"+msg.charAt(2)+"','"+msg.charAt(3)+"','"+msg.charAt(4)+"',null,null)");
						}
						else if (msg.length() == 7 && s.charAt(m.end()) == '#'){
							myDb_Operator.db_Execute("insert into Poetry values(17,'"+msg.charAt(0)+"','"+msg.charAt(1)+"','"+msg.charAt(2)+"','"+msg.charAt(3)+"','"+msg.charAt(4)+"','"+msg.charAt(5)+"','"+msg.charAt(6)+"')");
						}
						else if (msg.length() == 7 && s.charAt(m.end()) == '#'){
							myDb_Operator.db_Execute("insert into Poetry values(27,'"+msg.charAt(0)+"','"+msg.charAt(1)+"','"+msg.charAt(2)+"','"+msg.charAt(3)+"','"+msg.charAt(4)+"','"+msg.charAt(5)+"','"+msg.charAt(6)+"')");
						}
					}
				}
				br.close();
			}
			myDb_Operator.db_Close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
