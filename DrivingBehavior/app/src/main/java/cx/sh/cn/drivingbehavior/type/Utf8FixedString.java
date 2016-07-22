/**
 * GbkByteString.java
 * cn.sh.sis.vehicle.safe.comm.type
 *
 * Function:GbkByteString自定义类型
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0	 2013-9-30 		陈琪
 *
 * Copyright (c) 2013, Shanghai Changxing Information Technology Co., Ltd. All Rights Reserved.
 */
package cx.sh.cn.drivingbehavior.type;

/**
 * ClassName:UnicodeByteString
 * Function :UnicodeByteString自定义类型
 * 
 * @author 陈琪
 * @version 1.0
 * @Date 2013-9-30 下午4:48:42
 */
public class Utf8FixedString {

	/**
	 * 定长
	 */
	private int fixedLength = 0;

	/**
	 * unicode字符串
	 */
	private String utf8String = "";

	/**
	 * 
	 * Creates a new instance of GbkByteString.
	 * 
	 * @param stringLength
	 */
	public Utf8FixedString(int fixedLength) {
		this.fixedLength = fixedLength;
	}

	/**
	 * getString:获取String
	 * 
	 * @return mStringBuf String
	 */
	public String getString() {

		return this.utf8String;
	}

	/**
	 * getFixedLength:获取fixedLength大小
	 * 
	 * @return fixedLength int
	 */
	public int getFixedLength() {
		return this.fixedLength;
	}

	/**
	 * setString:设置String
	 * 
	 * @param src
	 *            String
	 */
	public void setString(String src) {

		StringBuilder sb = new StringBuilder();
		int fillZeroLength = 0;

		if (src == null) {
			fillZeroLength = this.fixedLength;
		} else if (src.length() < this.fixedLength) {
			sb.append(src);
			fillZeroLength = this.fixedLength - src.length();
		} else {
			sb.append(src.substring(0, this.fixedLength));
		}

		for (int i = 0; i < fillZeroLength; i++) {
			sb.append(" ");
		}

		this.utf8String = sb.toString();
	}
}
