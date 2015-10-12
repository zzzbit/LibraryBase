package remoteDB;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ROperator {
	private Connection con = null;
	private Statement st = null;
	private String url = null;
	private String name = "sa";
	private String password = "20091743";

	public ROperator(){
		String dbtype = "sqlserver";
		String dbName = "Shoes20130708";
		if (dbtype.equals("sqlserver")) {
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (ClassNotFoundException e) {
				System.out.println("classNotFound");
			}
			url = "jdbc:sqlserver://localhost:1433;DatabaseName=" + dbName;
		} else if (dbtype.equals("mysql")) {
			try {
				Class.forName("org.gjt.mm.mysql.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("classNotFound");
			}
			url = "jdbc:mysql://localhost:3306/" + dbName;
		} else {
			System.out.println("wrong db type");
		}
		try {
			con = DriverManager.getConnection(url, name, password);
			st = con.createStatement();

		} catch (Exception e) {
			System.out.println("connectionIsFaile");
		}

	}
	// 连接数据库
	public ROperator(String dbtype, String dbName, String usrName, String pwd) {
		name = usrName;
		password = pwd;
		if (dbtype.equals("sqlserver")) {
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (ClassNotFoundException e) {
				System.out.println("classNotFound");
			}
			url = "jdbc:sqlserver://localhost:1433;DatabaseName=" + dbName;
		} else if (dbtype.equals("mysql")) {
			try {
				Class.forName("org.gjt.mm.mysql.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("classNotFound");
			}
			url = "jdbc:mysql://localhost:3306/" + dbName;
		} else {
			System.out.println("wrong db type");
		}
		try {
			con = DriverManager.getConnection(url, name, password);
			st = con.createStatement();

		} catch (Exception e) {
			System.out.println("connectionIsFaile");
		}

	}
	// 连接数据库
		public ROperator(String ipAddress, String dbtype, String dbName, String usrName, String pwd) {
			name = usrName;
			password = pwd;
			if (dbtype.equals("sqlserver")) {
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				} catch (ClassNotFoundException e) {
					System.out.println("classNotFound");
				}
				url = "jdbc:sqlserver://"+ipAddress+":1433;DatabaseName=" + dbName;
			} else if (dbtype.equals("mysql")) {
				try {
					Class.forName("org.gjt.mm.mysql.Driver");
				} catch (ClassNotFoundException e) {
					System.out.println("classNotFound");
				}
				url = "jdbc:mysql://"+ipAddress+":3306/" + dbName;
			} else {
				System.out.println("wrong db type");
			}
			try {
				con = DriverManager.getConnection(url, name, password);
				st = con.createStatement();

			} catch (Exception e) {
				System.out.println("connectionIsFaile");
			}

		}
	// 执行sql语句
	public boolean db_Execute(String sql) {
		try {

			st.executeUpdate(sql);
			return true;
		} catch (Exception err) {
			System.out.println("插入、修改或删除出错，请检查sql语句");
			return false;

		}
	}

	// 查询
	public ResultSet db_Query(String sql) {
		try {
			return st.executeQuery(sql);
		} catch (Exception err) {
			System.out.println("查询出错，请检查sql语句");
			return null;
		}

	}

	// 关闭
	public void db_Close() {
		try {
			if (st != null)
				st.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			System.out.println("数据库关闭失败");
		}

	}

}
