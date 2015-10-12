package Test;

import java.io.File;

import common.SuperPicSegment;

public class segTest {

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		File[] files;
//		String rootPath = "C:\\Test\\1.1\\segment";
//		String imagePath;
//		String binaryPath;
//		files = new File(rootPath).listFiles();
//		long time3 = System.currentTimeMillis();
//		// Java·Ö¸î
//		for (int i = 0; i < files.length; i++) {
//			imagePath = files[i].getAbsolutePath();
//			if (imagePath.charAt(imagePath.length() - 5) != 'b') {
//				int pos = imagePath.lastIndexOf('.');
//				binaryPath = imagePath.substring(0, pos) + "_b"
//						+ imagePath.substring(pos);
//				new SuperPicSegment().startSegment(imagePath, binaryPath);
//			}
//		}
//		long time4 = System.currentTimeMillis();
//		System.out.println(time4-time3);
//	}
	public static void main(String[] args) {
		File[] files;
		String srcPath = "C:\\Test\\now";
		String disPath = srcPath + "_b";
		String imageName;
		files = new File(srcPath).listFiles();
		new File(disPath).mkdir();
		long time3 = System.currentTimeMillis();
		// Java·Ö¸î
		for (int i = 0; i < files.length; i++) {
			imageName = files[i].getName();
			new SuperPicSegment().startSegment(srcPath+"\\"+imageName, disPath+"\\"+imageName);
		}
		long time4 = System.currentTimeMillis();
		System.out.println(time4-time3);
	}
}
