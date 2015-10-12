package remoteDB;

import java.sql.ResultSet;

import common.db_Operator;

public class MlmrMainserver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		db_Operator myDb_Operator = new db_Operator("10.108.12.2","sqlserver","Shoes20130609","sa","server&901");
		ResultSet rs = myDb_Operator.db_Query("select Username from Login");
		try {
			rs.next();
			System.out.println(rs.getString(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
