package Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;

import common.db_Operator;

public class CopyFileById {

	public static void copy(String sourceFile, String targetFile) {
		try {
			BufferedInputStream inBuff = null;
			BufferedOutputStream outBuff = null;
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
//			db_Operator myDb_Operator = new db_Operator();
//			File[] files;
//			String rootPath = "C:\\Test\\Cluster\\Shape";
//			String filePath;
//			files = new File(rootPath).listFiles();
//			for (int i = 0; i < files.length; i++) {
//				filePath = files[i].getAbsolutePath();
//				String s = null;
//				BufferedReader br = new BufferedReader(new InputStreamReader(
//						new FileInputStream(filePath), "utf-8"));
//				br.readLine();
//				String ids = br.readLine();
//				br.close();
//				String[] id = ids.split(" ");
////				System.out.println(id.length);
//				String tmpPath = filePath.substring(0,filePath.lastIndexOf('.'));
//				new File(tmpPath).mkdir();
//				for (int j = 0; j < id.length; j++){
//					try {
//						ResultSet rs = myDb_Operator.db_Query("select Path from ImageInfo where Id ="+id[j]);
//						rs.next();
//						String disPath = rs.getString(1);
//						new File(filePath).mkdir();
//						copy(disPath, tmpPath+"\\"+id[j]+".jpg");
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//				}
//			}
			
			db_Operator myDb_Operator = new db_Operator();
			File[] files;
			String rootPath = "C:\\Test\\Cluster\\Texture";
			String filePath;
			files = new File(rootPath).listFiles();
			for (int i = 0; i < files.length; i++) {
				filePath = files[i].getAbsolutePath();
				String s = null;
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(filePath), "utf-8"));
				br.readLine();
				String ids = br.readLine();
				br.close();
				String[] id = ids.split(" ");
//				System.out.println(id.length);
				String tmpPath = filePath.substring(0,filePath.lastIndexOf('.'));
				new File(tmpPath).mkdir();
				for (int j = 0; j < id.length; j++){
					try {
						ResultSet rs = myDb_Operator.db_Query("select Path from ImageInfo where Id ="+id[j]);
						rs.next();
						String disPath = rs.getString(1);
						copy(disPath, tmpPath+"\\"+id[j]+".jpg");
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
