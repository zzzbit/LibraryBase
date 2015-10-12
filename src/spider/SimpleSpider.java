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

public class SimpleSpider {
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

	public static void main(String[] args) {
		try {
			String charset = "utf-8";
			URL url = new URL(
					"http://www.360doc.com/content/10/0127/12/693664_14498352.shtml");
			URLConnection conn;
			BufferedReader bReader;
			String rLine;
			StringBuffer stringBuffer = new StringBuffer();

			// µÃµ½Ô´Âë
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
			
			String content = stringBuffer.toString();
			

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
