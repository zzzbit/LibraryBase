package common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;

public class SuperPicSegment {
	private int height;			//原图高度
	private int width;			//原图宽度
	private BufferedImage sourceImg;	//原图的图像句柄
	private int rgb;			//像素点的RGB值
	private int point[][];		//表示每次遍历处理后的结果，0表示前景（黑色），1表示背景（白色）
	private int visit[][];		//访问标志，0表示未访问，1表示已访问
	private int draw[][];		//最后的处理结果，0表示前景（黑色），1表示背景（白色）
	private double gray[][];	//保存每个点的灰度值
	private double DOOR = 15;	//阈值大小
	private int ready[][];		//采样点数组
	private int listlength = 0;	//采样点数量

	public SuperPicSegment() {

	}

	//构造函数，设置颜色相近的阈值
	public SuperPicSegment(double door) {
		DOOR = door;
	}

	//从某个背景点进行广搜遍历，找到颜色相近的点
	private int travel(int xx, int yy, double door) {
		double min = gray[xx][yy] - door;
		double max = gray[xx][yy] + door;
		int list[][] = new int[width * height][2];
		int count = -1;
		list[++count][0] = xx;
		list[count][1] = yy;
		point[xx][yy] = 1;
		for (int i = 0; i <= count; i++) {
			int x = list[i][0];
			int y = list[i][1];
			if (x - 1 >= 0) {
				if (visit[x - 1][y] == 0 && gray[x - 1][y] > min
						&& gray[x - 1][y] < max) {
					list[++count][0] = x - 1;
					list[count][1] = y;
					point[x - 1][y] = 1;
				}
				visit[x - 1][y] = 1;
			}
			if (x + 1 < width) {
				if (visit[x + 1][y] == 0 && gray[x + 1][y] > min
						&& gray[x + 1][y] < max) {
					list[++count][0] = x + 1;
					list[count][1] = y;
					point[x + 1][y] = 1;
				}
				visit[x + 1][y] = 1;
			}
			if (y - 1 >= 0) {
				if (visit[x][y - 1] == 0 && gray[x][y - 1] > min
						&& gray[x][y - 1] < max) {
					list[++count][0] = x;
					list[count][1] = y - 1;
					point[x][y - 1] = 1;
				}
				visit[x][y - 1] = 1;
			}
			if (y + 1 < height) {
				if (visit[x][y + 1] == 0 && gray[x][y + 1] > min
						&& gray[x][y + 1] < max) {
					list[++count][0] = x;
					list[count][1] = y + 1;
					point[x][y + 1] = 1;
				}
				visit[x][y + 1] = 1;
			}
		}
		return count;
	}

	//选择最大联通分量，经验算法
	private int maxArea(int xx, int yy) {
		int list[][] = new int[width * height][2];
		int count = -1;
		list[++count][0] = xx;
		list[count][1] = yy;
		for (int i = 0; i <= count; i++) {
			int x = list[i][0];
			int y = list[i][1];
			draw[x][y] = point[x][y];
			if (x - 1 >= 0) {
				if (visit[x - 1][y] == 0 && point[x - 1][y] == 0) {
					list[++count][0] = x - 1;
					list[count][1] = y;
				}
				visit[x - 1][y] = 1;
			}
			if (x + 1 < width) {
				if (visit[x + 1][y] == 0 && point[x + 1][y] == 0) {
					list[++count][0] = x + 1;
					list[count][1] = y;
				}
				visit[x + 1][y] = 1;
			}
			if (y - 1 >= 0) {
				if (visit[x][y - 1] == 0 && point[x][y - 1] == 0) {
					list[++count][0] = x;
					list[count][1] = y - 1;
				}
				visit[x][y - 1] = 1;
			}
			if (y + 1 < height) {
				if (visit[x][y + 1] == 0 && point[x][y + 1] == 0) {
					list[++count][0] = x;
					list[count][1] = y + 1;
				}
				visit[x][y + 1] = 1;
			}
		}
		return count;
	}

	//分割程序入口
	public boolean startSegment(String imagePath, String binaryPath) {
		long time1 = System.currentTimeMillis();
		int i, j;
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(imagePath);
			sourceImg = javax.imageio.ImageIO.read(fileInputStream);

			width = sourceImg.getWidth();
			height = sourceImg.getHeight();
			BufferedImage newImh = new BufferedImage(width, height, 1);
			point = new int[width][height];
			visit = new int[width][height];
			draw = new int[width][height];
			gray = new double[width][height];
			for (i = 0; i < width; i++) {
				for (j = 0; j < height; j++) {
					rgb = sourceImg.getRGB(i, j);
					gray[i][j] = ((rgb >> 16) & 0xff) * 0.3
							+ ((rgb >> 8) & 0xff) * 0.59 + (rgb & 0xff) * 0.11;
				}
			}
			// 全为前景
			for (i = 0; i < width; i++) {
				for (j = 0; j < height; j++) {
					point[i][j] = 0;
					draw[i][j] = 1;
					visit[i][j] = 0;
				}
			}

			ready = new int[5000][2];
			for (i = 0; i < width / 3; i++) {
				ready[listlength++][0] = i * 3;
				ready[listlength - 1][1] = 0;
				ready[listlength++][0] = i * 3;
				ready[listlength - 1][1] = height - 1;
			}
			for (i = 1; i < height / 3; i++) {
				ready[listlength++][0] = 0;
				ready[listlength - 1][1] = i * 3;
				ready[listlength++][0] = width - 1;
				ready[listlength - 1][1] = i * 3;
			}

			int x = 0, y = 0, flag, sum = 0;
			long time2 = System.currentTimeMillis();
			while (true) {
				flag = 0;
				for (i = 0; i < listlength; i++) {
					if (point[ready[i][0]][ready[i][1]] == 0) {
						x = ready[i][0];
						y = ready[i][1];
						flag = 1;
						break;
					}
				}
				if (flag == 0) {
					break;
				}
				for (i = 0; i < width; i++) {
					for (j = 0; j < height; j++) {
						visit[i][j] = point[i][j];
					}
				}
				sum += travel(x, y, DOOR);
			}
			long time3 = System.currentTimeMillis();

			// ////////////////////////显示结果
			int num = maxArea(width / 2, height / 2);
			if (num * 1. / (height * width) < 0.1) {
				for (i = 0; i < width; i++) {
					for (j = 0; j < height; j++) {
						draw[i][j] = 1;
					}
				}
				num = maxArea(width / 2, height / 4);
				if (num * 1. / (height * width) < 0.1) {
					for (i = 0; i < width; i++) {
						for (j = 0; j < height; j++) {
							draw[i][j] = 1;
						}
					}
					num = maxArea(width / 2, (height * 3) / 4);
					if (num * 1. / (height * width) < 0.1) {
						for (i = 0; i < width; i++) {
							for (j = 0; j < height; j++) {
								draw[i][j] = 1;
							}
						}
						num = maxArea(width / 4, height / 2);
						if (num * 1. / (height * width) < 0.1) {
							for (i = 0; i < width; i++) {
								for (j = 0; j < height; j++) {
									draw[i][j] = 1;
								}
							}
							num = maxArea(width * 3 / 4, height * 3 / 4);
							if (num * 1. / (height * width) < 0.1) {
								for (i = 0; i < width; i++) {
									for (j = 0; j < height; j++) {
										draw[i][j] = 0;
									}
								}
							}
						}
					}
				}
			}
			for (i = 0; i < width; i++) {
				for (j = 0; j < height; j++) {
					if (draw[i][j] == 1) {
						newImh.setRGB(i, j, 0xFFFFFF);
					} else {
						newImh.setRGB(i, j, 0x0);
					}
				}
			}
			ImageIO.write(newImh, "JPEG", new File(binaryPath));
			long time4 = System.currentTimeMillis();
			// System.out.println("赋初值、灰度值："+(time2-time1));
			// System.out.println("对于每个点进行广搜："+(time3-time2));
			// System.out.println("最大联通分量，写入文件："+(time4-time3));
			// System.out.println(sum);
			fileInputStream.close();
		} catch (Exception e) {
			System.out.println("分割原图失败");
		}
		return true;
	}

	public static void main(String[] args) {
		new SuperPicSegment().startSegment("C:\\1.jpg", "C:\\1_b.jpg");
	}
}
