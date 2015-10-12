package fileDeal;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class DealUrl {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String s = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream("C:\\insert.txt"), "gb2312"));
			OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream("C:\\out.txt"), "gb2312");
			while ((s = br.readLine()) != null) {
				int pos = s.indexOf("insert");
				if (pos < 0){
					continue;
				}
				s = s.substring(pos);
				int pos2 = s.indexOf("','2013");
				String part3 = s.substring(pos2);
				s = s.substring(0, pos2);
				int pos1 = s.lastIndexOf(',');
				String part1 = s.substring(0, pos1+3);
				String part2 = s.substring(pos1+3).replaceAll("'", "^");
				
				
				w.write(part1+part2+part3+"\r\n");
				w.flush();
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
