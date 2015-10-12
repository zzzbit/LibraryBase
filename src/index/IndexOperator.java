package index;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.ResultSet;

import common.CommonArg;
import common.db_Operator;
import common.moduleInterface;

/**
 * IndexOperator��ʵ������Ĺ���
 * @author zhangzhizhi
 *
 */
public class IndexOperator {
	/**
	 * ��������
	 * 
	 * @return �ɹ�����0�����򷵻ش������
	 */
	public int Index_Construct() {
		moduleInterface jniinterface = new moduleInterface();
		db_Operator dbOperator = new db_Operator();
		dbOperator.DeleteIndexNodeData();
		dbOperator.DeleteIndexLeafNode_BiImage();
		try {
			for (int i = 1; i <= 13; i++){
				long end = System.currentTimeMillis();
				ResultSet rs = dbOperator.db_Query("select count(*) from Binary_Category where CategoryId = "+i);
				try {
					rs.next();
					int num = rs.getInt(1);
					double[][] Shaperesult = new double[num][64];
					long[] ShapeId = new long[num];
					double[][] Textureresult = new double[num][256];
					long[] TextureId = new long[num];
					if (!(dbOperator.GetFeature(Shaperesult, ShapeId, 64, 1,i))) {
						dbOperator.close();
						return 1;
					}
					jniinterface.IIndex_Construct(CommonArg.getDbType(),
							CommonArg.getDbNameManage(), CommonArg.getUsername(),
							CommonArg.getPassword(), 1, 1, i,Shaperesult,
							ShapeId, num, 64, 2, 20, 5, 50,
							0.5, 0.01, 0.000025);
					if (!(dbOperator
							.GetFeature(Textureresult, TextureId, 256, 3,i))){
								dbOperator.close();
								return 1;
							}
					jniinterface.IIndex_Construct(CommonArg.getDbType(),
							CommonArg.getDbNameManage(), CommonArg.getUsername(),
							CommonArg.getPassword(), 3, 1,i, Textureresult, TextureId,
							num, 256, 2, 20, 5, 50, 0.5, 0.01,
							0.000025);
					
					
				} catch (Exception e) {
					System.out.println("error");
				}
				long end2 = System.currentTimeMillis();
				System.out.println("时间：" + (end2 - end));
			}
			return 0;
		} catch (Exception e) {
			dbOperator.close();
			return -7;
		}
	}
	public static void main(String[] args) {
		new IndexOperator().Index_Construct();
	}
	}
