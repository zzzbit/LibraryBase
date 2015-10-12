package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class myTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;
		String url = null;
		try {
			long time1 = System.currentTimeMillis();
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			url = "jdbc:sqlserver://localhost:1433;DatabaseName=Spider";
//			con = DriverManager.getConnection(url, "sa", "20091743");	
			Class.forName("com.mysql.jdbc.Driver");
			url = "jdbc:mysql://localhost:3306/Spider";
			con = DriverManager.getConnection(url, "root", "root");
			con.setAutoCommit(false);
			

			int i;
			for (i = 0; i < 100000; i++) {

				
				st = con.createStatement();
				st.execute("insert into ImageInfo(Id,Name,Path,UploadTime,Introduction,Price,Url,Supplier,ClassifyFlag,InfoCheckFlag,AutoSegFlag,HandSegFlag,FeatureFlag,IndexFlag) values ("+(time1+i)+",'1369019446078.jpg','C:\\DataLib\\Spider\\1369019446078.jpg','2013/5/20',' ±öÄ¾¼òÔ¼·«²¼Ð¬ÐÝÏÐ°åÐ¬ º«°æ³±Á÷ÄÐÊ¿ÐÝÏÐÐ¬Ó¢Â×³±ÄÐÐ¬×Ó»¬°åÐ¬M2828 ½ðÅÆ¼¯½á »ÒÉ« 44Âë - ¾©¶«ÊÖ»ú°æ',89.00,'http://m.jd.com/product/1005440865.html','null',0,0,1,0,1,0)");
				if ((System.currentTimeMillis() - time1)%1000 == 0){
					con.commit();
				}
				con.commit();
				if(System.currentTimeMillis() - time1 > 20000){
					break;
				}
			}

			st.close();
			con.close();
			
			
			System.out.println(i);
			System.out.println(System.currentTimeMillis() - time1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
