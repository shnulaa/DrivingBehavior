/**
 * ConvTools.java
 * cn.sh.sis.vehicle.safe.comm
 *
 * Function:转换工具
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0	 2013-9-30 		陈琪
 *
 * Copyright (c) 2013, Shanghai Changxing Information Technology Co., Ltd. All Rights Reserved.
 */
package cx.sh.cn.drivingbehavior.utils;

/**
 * ClassName:ConvTools
 * Function :转换工具
 * 
 * @author 陈琪
 * @version 1.0
 * @Date 2013-9-30 下午4:48:42
 */
public class ConvTools {

	/**
	 * shortToBytes:short型转成byte数组
	 * 
	 * @param value
	 *            short值
	 * 
	 * @return 转换后byte数组
	 */
	public static byte[] shortToBytes(short value) {
		byte[] b = new byte[2];
		b[1] = (byte) (value & 0xff);
		b[0] = (byte) ((value >> 8) & 0xff);
		return b;
	}

	/**
	 * bytesToShort:byte数组转成short
	 * 
	 * @param b
	 *            byte数组
	 * @param offset
	 *            起始位置
	 * 
	 * @return 转换后short
	 */
	public static short bytesToShort(final byte[] b, int offset) {
		return (short) (b[1 + offset] & 0xff | (b[0 + offset] & 0xff) << 8);
	}

	/**
	 * intToBytes:int转成byte数组
	 * 
	 * @param value
	 *            int值
	 * 
	 * @return 转换后byte数组
	 */
	public static byte[] intToBytes(int value) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (value >>> (24 - i * 8));
		}
		return b;
	}

	/**
	 * bytesToInt:byte数组转int
	 * 
	 * @param b
	 *            byte数组
	 * @param offset
	 *            起始位置
	 * 
	 * @return 转换后int
	 */
	public static int bytesToInt(final byte[] b, int offset) {
		int temp = 0;
		int res = 0;
		for (int i = 0; i < 4; i++) {
			res <<= 8;
			temp = b[i + offset] & 0xff;
			res |= temp;
		}
		return res;
	}

	/**
	 * stringToBytesAscii:String转byte数组
	 * 
	 * @param value
	 *            String值
	 * 
	 * @return 转换后byte数组
	 */
	public static byte[] stringToBytesAscii(final String value) {
		char[] buffer = value.toCharArray();
		byte[] b = new byte[buffer.length];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) buffer[i];
		}
		return b;
	}

	/**
	 * byteArrayPrintToString:byte数组打印成String
	 * 
	 * @param byteArray
	 *            byte数组
	 * 
	 * @return 转换后String
	 */
	public static String byteArrayPrintToString(final byte[] byteArray) {

		if (byteArray == null || byteArray.length == 0) {
			return null;
		}

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char ret[] = new char[byteArray.length * 2];
		for (int i = 0, j = 0; i < byteArray.length; i++) {
			ret[j++] = hexDigits[byteArray[i] >>> 4 & 0xf];
			ret[j++] = hexDigits[byteArray[i] & 0xf];
		}

		return new String(ret);
	}
}
