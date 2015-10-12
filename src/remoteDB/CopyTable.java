package remoteDB;

public class CopyTable {

	/**
	 * @param args
	 */
	String localDbName;
	String localPassword;
	String ipAddress;
	String disPassword;
	String disDbName;
	public CopyTable(String localDbName,String localPassword,String ipAddress,String disPassword,String disDbName){
		this.localDbName = localDbName;
		this.localPassword = localPassword;
		this.ipAddress = ipAddress;
		this.disPassword = disPassword;
		this.disDbName = disDbName;
	}
	public void CopyWholeTable(String tableName){
		ROperator rOperator = new ROperator("sqlserver", localDbName, "sa", localPassword);
		String disDataTableName = "OPENDATASOURCE('SQLOLEDB','Data Source="+ipAddress+";User ID=sa;Password="+disPassword+"')."+disDbName+".dbo."+tableName;
		rOperator.db_Execute("delete "+disDataTableName);
		String sql = "insert "+disDataTableName+"(BiImageId,CategoryId) select BiImageId,CategoryId FROM "+localDbName+".dbo."+tableName;
		rOperator.db_Execute(sql);
	}
	public static void main(String[] args) {
		CopyTable copyTable = new CopyTable("Shoes20130708", "20091743","182.18.21.179" , "server&901", "Shoes20130710");
		copyTable.CopyWholeTable("Binary_Category");
	}

}
