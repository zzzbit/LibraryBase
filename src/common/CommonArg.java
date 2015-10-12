package common;

/**
 * Common类实现公共变量的存储
 * @author zhangzhizhi
 *
 */
public class CommonArg {
	// 唯一标记
	private static long index;
	// 接收数据库图片路径
	private static String dataLibPath = "E:\\DataLib";
	// 存储从客户端接收到的图像
	private static String receivePath = "\\image";
	// 高斯文件1的路径
	private static String gaussFile1 = "file\\shapeMMP2.0_0_new.txt";
	// 高斯文件的路径2
	private static String gaussfile2 = "file\\shapeMMP2.0_1_new.txt";
	// 是否可以建立索引，true表示可以，false表示不行
	private static boolean indexFlag = true;
	// 是否可以爬虫，true表示可以，false表示不行
	private static boolean spiderFlag = true;
	// 强制爬虫停止标志
	private static boolean spiderStopFlag = false;
	// 数据库名
	private static String dbType = "sqlserver";
	// 数据库名
	private static String dbName = "Shoes20130708";
	// 数据库名
	private static String dbNameManage = "Shoes20130708";
	// 数据库用户名
	private static String username = "sa";
	// 数据库密码
	private static String password = "20091743";

	/**
	 * @return the dataLibPath
	 */
	public static String getDataLibPath() {
		return dataLibPath;
	}

	/**
	 * @param dataLibPath
	 *            the dataLibPath to set
	 */
	public static void setDataLibPath(String dataLibPath) {
		CommonArg.dataLibPath = dataLibPath;
	}

	/**
	 * @return the index
	 */
	public synchronized static long getIndex() {
		index++;
		return index;
	}
	/**
	 * @return the receivePath
	 */
	public static String getReceivePath() {
		return receivePath;
	}

	/**
	 * @param receivePath the receivePath to set
	 */
	public static void setReceivePath(String receivePath) {
		CommonArg.receivePath = receivePath;
	}

	/**
	 * @return the gaussFile1
	 */
	public static String getGaussFile1() {
		return gaussFile1;
	}

	/**
	 * @param gaussFile1 the gaussFile1 to set
	 */
	public static void setGaussFile1(String gaussFile1) {
		CommonArg.gaussFile1 = gaussFile1;
	}

	/**
	 * @return the gaussfile2
	 */
	public static String getGaussfile2() {
		return gaussfile2;
	}

	/**
	 * @param gaussfile2 the gaussfile2 to set
	 */
	public static void setGaussfile2(String gaussfile2) {
		CommonArg.gaussfile2 = gaussfile2;
	}

	/**
	 * @return the dbNameSearch
	 */
	public static String getDbName() {
		return dbName;
	}

	/**
	 * @param dbNameSearch the dbNameSearch to set
	 */
	public static void setDbName(String dbName) {
		CommonArg.dbName = dbName;
	}

	/**
	 * @return the dbNameManage
	 */
	public static String getDbNameManage() {
		return dbNameManage;
	}

	/**
	 * @param dbNameManage the dbNameManage to set
	 */
	public static void setDbNameManage(String dbNameManage) {
		CommonArg.dbNameManage = dbNameManage;
	}
	/**
	 * @param index
	 *            the index to set
	 */
	public static void setIndex(long index) {
		CommonArg.index = index;
	}

	/**
	 * @return the indexFlag
	 */
	public static boolean isIndexFlag() {
		return indexFlag;
	}

	/**
	 * @param indexFlag
	 *            the indexFlag to set
	 */
	public static void setIndexFlag(boolean indexFlag) {
		CommonArg.indexFlag = indexFlag;
	}

	/**
	 * @return the spiderFlag
	 */
	public static boolean isSpiderFlag() {
		return spiderFlag;
	}

	/**
	 * @param spiderFlag
	 *            the spiderFlag to set
	 */
	public static void setSpiderFlag(boolean spiderFlag) {
		CommonArg.spiderFlag = spiderFlag;
	}

	/**
	 * @return the spiderStopFlag
	 */
	public static boolean isSpiderStopFlag() {
		return spiderStopFlag;
	}

	/**
	 * @param spiderStopFlag
	 *            the spiderStopFlag to set
	 */
	public static void setSpiderStopFlag(boolean spiderStopFlag) {
		CommonArg.spiderStopFlag = spiderStopFlag;
	}

	/**
	 * @return the username
	 */
	public static String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public static void setUsername(String username) {
		CommonArg.username = username;
	}

	/**
	 * @return the password
	 */
	public static String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public static void setPassword(String password) {
		CommonArg.password = password;
	}

	/**
	 * @return the dbType
	 */
	public static String getDbType() {
		return dbType;
	}

	/**
	 * @param dbType
	 *            the dbType to set
	 */
	public static void setDbType(String dbType) {
		CommonArg.dbType = dbType;
	}

}
