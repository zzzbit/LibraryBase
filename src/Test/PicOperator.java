package Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import common.db_Operator;

public class PicOperator {
//	private db_Operator dbcon;
	private String dbName;
	private String dbUsr;
	private String dbPwd;

	// 构造函数，连接数据库
	public PicOperator() {
		try {
			FileInputStream fis = new FileInputStream("serverconfig.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					fis, "utf-8"));
			br.readLine();
			dbName = br.readLine();
			dbUsr = br.readLine();
			dbPwd = br.readLine();
			fis.close();
			br.close();
		} catch (Exception e) {
			System.out.println("配置文件出错");
		}
		
	}

	// 实现图片的预处理，分割和提取特征
	public double[][] picPreDeal(String imagePath, String imageBinary) {
		double[][] result = new double[3][88];
		for (int i = 0; i < 3; i++){
			for (int j = 0;j < 88; j++){
				result[i][j] = 1.1;
			}
		}
		return result;
	}

	// 根据路径、信息和特征实现数据库的导入
	public boolean picImportMsg(String imagePath, String imageBinary,
			String msgString, double[][] feature) {
		String sql_str;
		String regex = "@@";
		String[] msg = { "null", "0", "null", "null" };
		String msgSplit[] = msgString.split(regex);
		String picname;
		ResultSet rs; // 记录集
		int imageId;
		int binaryId;
		try {
			picname = imagePath.substring(imagePath.lastIndexOf('\\') + 1,
					imagePath.length());
			System.arraycopy(msgSplit, 0, msg, 0, msgSplit.length);
			sql_str = "insert into ImageInfo(Name,Path,UploadTime,Introduction,"
					+ "Price,Url,Supplier,ClassifyFlag,"
					+ "InfoCheckFlag,AutoSegFlag,HandSegFlag,FeatureFlag,IndexFlag) "
					+ "values ('"
					+ picname
					+ "','"
					+ imagePath
					+ "',CONVERT(varchar(100), GETDATE(), 20),'"
					+ msg[0]
					+ "',"
					+ msg[1]
					+ ",'"
					+ msg[2]
					+ "','"
					+ msg[3]
					+ "',0,0,1,0,1,0)";
			// 插入图片信息表
			db_Operator dbcon = new db_Operator("sqlserver", dbName, dbUsr, dbPwd);
			if (!dbcon.db_Execute(sql_str)) {
				System.out.println("插入图片表：SQL语句错误");
				return false;
			}
			dbcon.db_Close();
		} catch (Exception e) {
			System.out.println("插入图片表：插入信息出错");
			return false;
		}
//		// 将二值图信息和图片信息Id插入数据库二值图 表
		try {
			db_Operator dbcon = new db_Operator("sqlserver", dbName, dbUsr, dbPwd);
			rs = dbcon.db_Query("select IDENT_CURRENT('ImageInfo')");
			rs.next();
			imageId = rs.getInt(1);
			rs.close();
			sql_str = "insert into BinaryImage(ImageId,Path) values ("
					+ imageId + ",'" + imageBinary + "')";
			if (!dbcon.db_Execute(sql_str)) {
				System.out.println("插入二值图信息表：SQL语句错误");
				dbcon.db_Execute("delete ImageInfo where Id =" + imageId);
				return false;
			}
			dbcon.db_Close();
		} catch (Exception e) {
			System.out.println("插入二值图表：插入信息出错");
			return false;
		}

		try {
			db_Operator dbcon = new db_Operator("sqlserver", dbName, dbUsr, dbPwd);
			rs = dbcon.db_Query("select IDENT_CURRENT('BinaryImage')");
			rs.next();
			binaryId = rs.getInt(1);
			rs.close();
			// 分别将特征插入数据库
			if (!dbcon
					.InsertFeature(feature[0], feature[0].length, 2, binaryId)) {
				System.out.println("插入特征值表：插入颜色特征失败");
				dbcon.db_Execute("delete FeatureData where BiImageId = "
						+ binaryId);
				return true;
			}
			if (!dbcon
					.InsertFeature(feature[1], feature[1].length, 1, binaryId)) {
				System.out.println("插入特征值表：插入形状特征失败");
				dbcon.db_Execute("delete FeatureData where BiImageId = "
						+ binaryId);
				return true;
			}
			if (!dbcon
					.InsertFeature(feature[2], feature[2].length, 3, binaryId)) {
				System.out.println("插入特征值表：插入纹理特征失败");
				dbcon.db_Execute("delete FeatureData where BiImageId = "
						+ binaryId);
				return true;
			}
			dbcon.db_Execute("update ImageInfo set FeatureFlag = 1 where Id = "
					+ binaryId);
		} catch (Exception e) {
			System.out.println("插入特征值表：插入特征错误");
			return true;
		}
		return true;
	}

	// 图片导入，返回1表示图片分割出错，返回2表示有重复图片，返回3表示该图片不是鞋类，返回4表示导入信息失败，返回0表示导入正确
	public int picImport(String imagePath, String msgString) {
		int pos = imagePath.lastIndexOf('.');
		String binaryPath = imagePath.substring(0, pos) + "_b"
				+ imagePath.substring(pos);
		double[][] feature = picPreDeal(imagePath, binaryPath);

		// 进行导入
		if (!picImportMsg(imagePath, binaryPath, msgString, feature)) {
			new File(imagePath).delete();
			new File(binaryPath).delete();
			return 4;
		}
		return 0;
	}

}
