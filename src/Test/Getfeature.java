package Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.ResultSet;

import common.db_Operator;

public class Getfeature {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			int count = 1;
			db_Operator myDb_Operator = new db_Operator("sqlserver", "Shoes20130708", "sa", "20091743");
			ResultSet rs = myDb_Operator.db_Query("select FeatureData,BiImageId from FeatureData where FeatureTypeId =3 and BiImageId in(select Id from ImageInfo where InfoCheckFlag = 1)");
			int num = 0;
			while (rs.next()) {
				byte[] b = rs.getBytes(1);
				long DBDataId = rs.getLong(2);
				long l;
				BufferedWriter w = new BufferedWriter(new FileWriter("C:\\Feature\\Texture\\"+(count++)+".txt"));
				w.write(DBDataId+" 256\r\n");
				for (int i = 0; i < b.length;) {
					l = b[i++];
					l &= 0xff;
					l |= ((long) b[i++] << 8);
					l &= 0xffffl;
					l |= ((long) b[i++] << 16);
					l &= 0xffffffl;
					l |= ((long) b[i++] << 24);
					l &= 0xffffffffl;
					l |= ((long) b[i++] << 32);
					l &= 0xffffffffffl;
					l |= ((long) b[i++] << 40);
					l &= 0xffffffffffffl;
					l |= ((long) b[i++] << 48);
					l &= 0xffffffffffffffl;
					l |= ((long) b[i++] << 56);
					l &= 0xffffffffffffffffl;
					double tmp = Double
							.longBitsToDouble(l);
					w.write(tmp+"\r\n");
				}
				w.flush();
				w.close();
				num++;
				if (num % 1000 == 0){
					System.out.println(num);
				}
			}
			rs.close();
		} catch (Exception err) {
			System.out.println("¶ÁÈ¡ÌØÕ÷³ö´í");
		}
	}

}
