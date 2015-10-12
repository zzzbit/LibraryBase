package Test;

import java.sql.ResultSet;

import common.db_Operator;

public class dbTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			db_Operator myoperator = new db_Operator("sqlserver", "Test", "sa",
					"20091743");
			ResultSet rs = myoperator.db_Query("select * from SuperSpider1_L1");
			while (rs.next()) {
				System.out.println(rs.getString(3));
			}

		} catch (Exception e) {
			System.out.println("no");
		}
	}

}
