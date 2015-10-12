package remoteDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Yidaosou {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("classNotFound");
		}
		String url = "jdbc:sqlserver://182.18.21.179:1433;DatabaseName=Shoes20130710";
		Connection con;
		try {
			con = DriverManager.getConnection(url, "sa", "server&901");
		} catch (Exception e) {
			System.out.println("connectionIsFaile");
			return;
		}
		try {
			ROperator dbOperator = new ROperator();
			ResultSet rs = dbOperator.db_Query("select BiImageId,CategoryId from Binary_Category");
			Statement st = con.createStatement();
			st.executeUpdate("delete Binary_Category");
			st.close();
			long begin = System.currentTimeMillis();
			int num = 0;
			PreparedStatement pstmt=null;
			while(rs.next()){
				pstmt = con.prepareStatement("insert Binary_Category(BiImageId,CategoryId) values(?,?)");
				pstmt.setLong(1, rs.getLong(1));
				pstmt.setInt(2, rs.getInt(2));
				pstmt.executeUpdate();
				num++;
				if (num % 100 == 0){
					System.out.println(num+"/"+(System.currentTimeMillis()-begin)/1000);
				}
			}
			rs.close();
			pstmt.close();
			dbOperator.db_Close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
