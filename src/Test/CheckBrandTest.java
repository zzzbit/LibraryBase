package Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.ResultSet;

import common.db_Operator;

public class CheckBrandTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		db_Operator myoperator = new db_Operator("sqlserver", "Shoes20130609", "sa",
				"20091743");
		db_Operator myoperator1 = new db_Operator("sqlserver", "Shoes20130609", "sa",
				"20091743");
		db_Operator myoperator2 = new db_Operator("sqlserver", "Shoes20130609", "sa",
				"20091743");
		try {
			int sum = 0;
			ResultSet rs=myoperator.db_Query("select Id,Introduction from ImageInfo where Brand='null'");
			BufferedWriter w = new BufferedWriter(new FileWriter("introduction.txt"));
			while(rs.next()){
				long id = rs.getLong(1);
				String introduction = rs.getString(2);
//				System.out.println(id +":"+introduction);
				
				ResultSet rs1 = myoperator1.db_Query("select * from Brand");
				int now = sum;
				while (rs1.next()) {
					String tmp = rs1.getString(1);
					if(introduction.contains(tmp)){
//						System.out.println(tmp);
						sum++;
						myoperator2.db_Execute("update ImageInfo set Brand='"+tmp+"' where Id ="+id);
						break;
					}
				}
				if (now == sum)
					w.write(introduction+"\r\n");
				rs1.close();
			}
			w.flush();
			w.close();
			myoperator1.db_Close();
			rs.close();
			myoperator.db_Close();
			myoperator2.db_Close();
			System.out.println(sum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
