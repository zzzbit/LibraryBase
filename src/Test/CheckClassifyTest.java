package Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.ResultSet;

import common.db_Operator;

public class CheckClassifyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		db_Operator myoperator = new db_Operator("sqlserver", "Shoes20130708", "sa",
				"20091743");
		db_Operator myoperator2 = new db_Operator("sqlserver", "Shoes20130708", "sa",
				"20091743");
		try {
			int sum = 0;
			ResultSet rs=myoperator.db_Query("select Id,Url from ImageInfo");
			while(rs.next()){
				long id = rs.getLong(1);
				String url = rs.getString(2);
				
				int now = sum;
//						myoperator2.db_Execute("update ImageInfo set CategoryName='"+tmp+"' where Id ="+id);

			}
			myoperator.db_Close();
			myoperator2.db_Close();
			System.out.println(sum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
