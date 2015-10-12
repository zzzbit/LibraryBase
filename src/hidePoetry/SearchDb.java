package hidePoetry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.script.ScriptEngineManager;

import common.db_Operator;

public class SearchDb {

	/**
	 * @param args
	 */
	public static String cts(String input,String exp) {
		String[] result = new String[input.length()];
		String resultString = "";
		db_Operator myDb_Operator = new db_Operator("sqlserver", "Spider","sa", "20091743");
		int num[][] = new int[4][input.length()];
		int[] sign = {15,25,17,27};
		Random rand = new Random();
		ResultSet rs;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < input.length(); j++) {
				num[i][j] = 0;
			}
		}
		for (int i = 0; i < input.length(); i++) {
			try {
				rs = myDb_Operator
						.db_Query("select count(*) from Poetry where Sign = 15 and R"+getValue(i, exp)+" = '"+ input.charAt(i) + "'");
				if (rs.next()) {
					num[0][i] = rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				rs = myDb_Operator
						.db_Query("select count(*) from Poetry where Sign = 25 and R"+getValue(i, exp)+" = '"+ input.charAt(i) + "'");
				if (rs.next()) {
					num[1][i] = rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				rs = myDb_Operator
						.db_Query("select count(*) from Poetry where Sign = 17 and R"+getValue(i, exp)+" = '"+ input.charAt(i) + "'");
				if (rs.next()) {
					num[2][i] = rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				rs = myDb_Operator
						.db_Query("select count(*) from Poetry where Sign = 27 and R"+getValue(i, exp)+" = '"+ input.charAt(i) + "'");
				if (rs.next()) {
					num[3][i] = rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//		for (int i = 0; i < input.length(); i++) {
//			for (int j = 0; j < 4; j++) {
//				System.out.print(num[j][i] + " ");
//			}
//			System.out.println();
//		}
		int flag = 1;
		for (int i = 0; i < input.length(); i++) {
			if (i % 2 == 0) {
				if (num[2][i] == 0) {
					flag = 2;
					break;
				}
			} else {
				if (num[3][i] == 0) {
					flag = 2;
					break;
				}
			}
		}
		//律诗
		if (flag == 1) {
			for (int i = 0; i < input.length(); i++) {
				if (i % 2 == 0) {
					try {
						int row = rand.nextInt(num[2][i]);
						int count = 0;
						rs = myDb_Operator
								.db_Query("select R1,R2,R3,R4,R5,R6,R7 from Poetry where Sign = 17 and R"+getValue(i, exp)+" = '"+ input.charAt(i) + "'");
						while (rs.next()) {
							
							if (row == count) {
								result[i] = rs.getString(1)+rs.getString(2)+rs.getString(3)+rs.getString(4)+rs.getString(5)+rs.getString(6)+rs.getString(7);
								break;
							}
							count++;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					try {
						int row = rand.nextInt(num[3][i]);
						int count = 0;
						rs = myDb_Operator
								.db_Query("select R1,R2,R3,R4,R5,R6,R7 from Poetry where Sign = 27 and R"+getValue(i, exp)+" = '"+ input.charAt(i) + "'");
						while (rs.next()) {
							
							if (row == count) {
								result[i] = rs.getString(1)+rs.getString(2)+rs.getString(3)+rs.getString(4)+rs.getString(5)+rs.getString(6)+rs.getString(7);
								break;
							}
							count++;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		//非律诗
		else {
			for (int i = 0; i < input.length(); i++) {
				if (i % 2 == 0) {
					if (num[0][i] == 0) {
						flag = 3;
						break;
					}
				} else {
					if (num[1][i] == 0) {
						flag = 3;
						break;
					}
				}
			}
			//绝句
			if (flag == 2) {
				for (int i = 0; i < input.length(); i++) {
					if (i % 2 == 0) {
						try {
							int row = rand.nextInt(num[0][i]);
							int count = 0;
							rs = myDb_Operator
									.db_Query("select R1,R2,R3,R4,R5,R6,R7 from Poetry where Sign = 15 and R"+getValue(i, exp)+" = '"
											+ input.charAt(i) + "'");
							while (rs.next()) {
								
								if (row == count) {
									result[i] = rs.getString(1)+rs.getString(2)+rs.getString(3)+rs.getString(4)+rs.getString(5)+rs.getString(6)+rs.getString(7);
									break;
								}
								count++;
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					} else {
						try {
							int row = rand.nextInt(num[1][i]);
							int count = 0;
							rs = myDb_Operator
									.db_Query("select R1,R2,R3,R4,R5,R6,R7 from Poetry where Sign = 25 and R1 = '"
											+ input.charAt(i) + "'");
							while (rs.next()) {
								
								if (row == count) {
									result[i] = rs.getString(1)+rs.getString(2)+rs.getString(3)+rs.getString(4)+rs.getString(5)+rs.getString(6)+rs.getString(7);
									break;
								}
								count++;
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
			// 绝句
			else {
				for (int i = 0; i < input.length(); i++) {
					for (int j = 0; j < 4; j++){
						if (num[j][i] != 0){
							try {
								int row = rand.nextInt(num[j][i]);
								int count = 0;
								rs = myDb_Operator
										.db_Query("select R1,R2,R3,R4,R5,R6,R7 from Poetry where Sign = "+sign[j]+" and R"+getValue(i, exp)+" = '"
												+ input.charAt(i) + "'");
								while (rs.next()) {
									
									if (row == count) {
										result[i] = rs.getString(1)+rs.getString(2)+rs.getString(3)+rs.getString(4)+rs.getString(5)+rs.getString(6)+rs.getString(7);
										break;
									}
									count++;
								}
								break;
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		myDb_Operator.db_Close();
		for (int i = 0; i < result.length - 1; i++){
			resultString += result[i]+"\n";
		}
		if (result.length - 1 >= 0){
			resultString += result[result.length - 1];
		}
		return resultString.replaceAll("null", "");
	}

	public static int getValue(int i,String exp){
		ScriptEngineManager sem = new ScriptEngineManager();
		int value = 0;
		try {
			exp = exp.replaceAll("i", i+"");
			Object result = sem.getEngineByName("javascript").eval(exp);
			value = (int)Double.parseDouble(result.toString());
		} catch (Exception e) {
		}
		return value%7+1;
	}
	public static void main(String[] args) {
		System.out.println(cts("我要好好学习天天向上笑口常开", "i"));
	}
}
