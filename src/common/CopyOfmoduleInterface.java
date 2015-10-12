package common;


public class CopyOfmoduleInterface {
	static {
		System.loadLibrary("jnimoduleInterface");
	}
	// 0表示可以构建索引
	public static int indexflag = 0;

	public native int IFE_Color(String imagePath, String imageBinary,
			double[] result, int[] dimension);

	public native int IFE_Shape(String imagePath, String imageBinary,
			double[] result, int[] dimension);

	public native int IFE_Texture(String imagePath, String imageBinary,
			double[] result, int[] dimension);

	public native int OSA_SegImg2BinImg(String srcImgFileName,
			String dirImgFileName);

	public native int IIS_FirstSeg(String resultAddr, String imageAddr,
			int topX, int topY, int bottomX, int bottomY);

	public native double ISC_SimiComputing(double[] feature1,
			double[] feature2, int[] dim, int totalDim, int classNum,
			boolean gaussFileType, String gaussFile, String gaussFile2,
			double covMin, int histCalType);

	public native boolean ConnectDb(String dbName, String userName, String pwd);

	public native boolean GetFeature(double[] Feature, int FeatureTypeId,
			int BiImageId);

	public native boolean InsertFeature(double[] Feature, int FeatureDim,
			int FeatureTypeId, int BiImageId);

	public native boolean FreeDb();

	public native int IIndex_Construct(String dbname, String username,
			String password, int featId, int indId, double[][] DBFeature,
			int[] DBDataId, int sampleNum, int dim, int gaussianLow,
			int gaussianHigh, int maxTreeLevel, int minNode,
			double maxPercentage, double minPercentage, double covmin);

	public native int IIndex_InitialIndeForest(String dbname, String username,
			String password, int[] featureTypeId, int featureTypeNum,
			int indexid);

	public native int IIndex_Search(double[] userFeature, int featureTypeNum,
			int[] dim, int[] resultPicID);

	public native int IIndex_FreeMemory(int featureTypeNum);


}
