package Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.db_Operator;

public class InsertCategoryInfo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			db_Operator myDb_Operator = new db_Operator();
			db_Operator myDb_Operator2 = new db_Operator("182.18.21.179","sqlserver","Shoes20130710","sa","server&901");
			ResultSet rs = myDb_Operator.db_Query("select Id,Introduction from ImageInfo where CategoryName = 'ÔË¶¯Æ¹ÓðÐ¬'");
			int sum = 0,count=0;
			while(rs.next()){
				long id = rs.getLong(1);
				String introduction = rs.getString(2);
				boolean male;
				boolean female;
				if (introduction.contains("ÄÐ")){
					male = true;
				}
				else {
					male = false;
				}
				if (introduction.contains("Å®")){
					female = true;
				}
				else {
					female = false;
				}
				if (male==true&&female==false){
					sum++;
					myDb_Operator2.db_Execute("delete from Binary_Category where BiImageId = "+id+" and CategoryId = 10");
				}
				else if (male==false&&female==true){
					count++;
					myDb_Operator2.db_Execute("delete from Binary_Category where BiImageId = "+id+" and CategoryId = 6");
				}
//				myDb_Operator2.db_Execute("insert into Binary_Category values("+id+",4)");
//				myDb_Operator2.db_Execute("insert into Binary_Category values("+id+",12)");
//				sum++;
			}
			System.out.println(sum);
			System.out.println(count);
//			System.out.println("ÄÐÅóÓÑ".contains("ÄÐ"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
