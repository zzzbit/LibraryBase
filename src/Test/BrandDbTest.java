package Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import common.db_Operator;

public class BrandDbTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		db_Operator myoperator = new db_Operator("sqlserver", "Spider", "sa",
				"20091743");
		try {
			String s=null;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream("brand_access.txt")));
			while ((s = br.readLine()) != null) {
				myoperator.db_Execute("insert into Brand values('"+s+"')");
			}
			br.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
