package common;

/**
 * moduleInterface实现调用JNI的方法
 * @author zhangzhizhi
 *
 */
public class moduleInterface {
	static {
		System.loadLibrary("jnimoduleInterface");
	}

	/**
	 * 颜色特征提取
	 * @param imagePath 原图路径
	 * @param imageBinary 二值图路径
	 * @param result 特征值数组
	 * @param dimension 维数
	 * @return 0表示提取成功，其他表示失败
	 */
	public native int IFE_Color(String imagePath, String imageBinary,
			double[] result, int[] dimension);
	/**
	 * 
	 * @param imgNum 颜色特征的数量
	 * @param imgColorDim  颜色特征的维数
	 * @param imgColorFeat  颜色特征
	 * @param domiColorLabel 返回的颜色的类别
	 * @param domiColorNum 返回类别的数量
	 * @return 0表示成功，其他表示失败
	 */
     public native boolean ISC_DominantColor(int imgNum,int imgColorDim,double[]imgColorFeat,
    		int[]domiColorLabel,int domiColorNum);
	/**
	 * 形状特征提取
	 * @param imagePath 原图路径
	 * @param imageBinary 二值图路径
	 * @param result 特征值数组
	 * @param dimension 维数
	 * @return 0表示提取成功，其他表示失败
	 */
	public native int IFE_Shape(String imagePath, String imageBinary,
			double[] result, int[] dimension);

	/**
	 * 纹理特征提取
	 * @param imagePath 原图路径
	 * @param imageBinary 二值图路径
	 * @param result 特征值数组
	 * @param dimension 维数
	 * @return 0表示提取成功，其他表示失败
	 */
	public native int IFE_Texture(String imagePath, String imageBinary,
			double[] result, int[] dimension);

	/**
	 * 构建索引
	 * @param dbtype 数据库类型
	 * @param dbname 数据库名
	 * @param username 数据库用户名
	 * @param password 密码
	 * @param featId 要构建索引结构的特征类型在数据库中的Id
	 * @param indId 树形索引结构在数据库中对应的索引类型id
	 * @param DBFeature 要构建索引的所有数据的特征
	 * @param DBDataId 要构建索引的数据在数据库中Id，其顺序与    DBFeature一一对应
	 * @param sampleNum 数据的个数
	 * @param dim 数据的维数
	 * @param gaussianLow 高斯成分个数上限，缺省值为1
	 * @param gaussianHigh 高斯成分个数下限，缺省值为30
	 * @param maxTreeLevel 树形结构层数上限，缺省值为5
	 * @param minNode 节点中数据个数的下限，缺省值为30
	 * @param maxPercentage 子节点中数据项个数占父节点百分比的上限，缺省值为0.5
	 * @param minPercentage 子节点中数据项个数占父节点百分比的下限,缺省值为0.01
	 * @param covmin covmin值
	 * @return -1表示Run_Properly, -2表示The_Input_Para_Is_Illegal，-4表示Can_Not_Open_The_Database
	 */
	public native int IIndex_Construct(String dbtype,String dbname, String username,
			String password, int featId, int indId, int cateId,double[][] DBFeature,
			long[] DBDataId, int sampleNum, int dim, int gaussianLow,
			int gaussianHigh, int maxTreeLevel, int minNode,
			double maxPercentage, double minPercentage, double covmin);

	/**
	 * 检索前的初始化，将featureTypeId数组对应的各特征的树形索引结构读入内存；其中indid为树形索引结构在数据库中对应的索引类型id
	 * @param dbtype 数据库类型
	 * @param dbname 数据库名
	 * @param username 数据库用户名
	 * @param password 密码
	 * @param featureTypeId 要读入索引结构的各特征的Id
	 * @param featureTypeNum 特征Id的个数
	 * @param indexid 树形索引结构在数据库中对应的索引类型id
	 * @return 程序运行的结果代码，如-1表示Run_Properly, -2表示The_Input_Para_Is_Illegal，-4表示Can_Not_Open_The_Database等
	 */
	public native int IIndex_InitialIndeForest(String dbtype,String dbname, String username,
			String password, int[] featureTypeId, int featureTypeNum,
			int indexid, int[] cateId, int cateIdNum);

	/**
	 * 基于多种特征的索引结构进行检索
	 * @param userFeature 剪除背景后的图片区域的各种特征的数组，各种特征数据串接为一个double数组，串接的顺序与IIndex_InitialIndeForest函数中 featureTypeId的顺序相同
	 * @param featureTypeNum 特征种类的个数
	 * @param dim 各种特征维数
	 * @param resultPicID 保存符合条件的图片id的数组
	 * @return 若返回值大于等于0，则表示检索结果中数据的个数；若返回值小于0，则表示结果代码，如-1表示Run_Properly, -2表示The_Input_Para_Is_Illegal，-4表示Can_Not_Open_The_Database
	 */
	public native int IIndex_Search(double[] userFeature, int featureTypeNum,
			int[] dim, long[] resultPicID,int cateId);

	/**
	 * 在完成检索后，释放索引结构的内存空间
	 * @param featureTypeNum 特征种类的个数
	 * @return 程序运行的结果代码，如-1表示Run_Properly, -2表示The_Input_Para_Is_Illegal，-4表示Can_Not_Open_The_Database等
	 */
	public native int IIndex_FreeMemory(int featureTypeNum);
}
