/**
 * AsciiByteString.java
 * cn.sh.sis.vehicle.safe.comm.type
 *
 * Function:AsciiByteString自定义类型
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0	 2013-9-30 		陈琪
 *
 * Copyright (c) 2013, Shanghai Changxing Information Technology Co., Ltd. All Rights Reserved.
 */
package cx.sh.cn.drivingbehavior.type;

import java.util.Arrays;

import cx.sh.cn.drivingbehavior.utils.ConvTools;


/**
 * ClassName:AsciiByteString
 * Function :AsciiByteString自定义类型
 * 
 * @author 陈琪
 * @version 1.0
 * @Date 2013-9-30 下午4:48:42
 */
public class AsciiByteString {

	/**
	 * String长度
	 */
	private int mStringLength = 0;

	/**
	 * byte数组
	 */
	private byte[] mStringBuf = null;

	/**
	 * 
	 * Creates a new instance of AsciiByteString.
	 * 
	 * @param stringLength
	 */
	public AsciiByteString(int stringLength) {
		mStringLength = stringLength;
		mStringBuf = new byte[mStringLength];
	}

	/**
	 * getStringBuf:获取String对应的byte数组
	 * 
	 * @return mStringBuf byte[]
	 */
	public byte[] getStringBuf() {
		return mStringBuf;
	}

	/**
	 * setStringBuf:设置String对应的byte数组
	 * 
	 * @param bytesBuf
	 *            byte[]
	 * @param offset
	 *            int
	 */
	public void setStringBuf(byte[] bytesBuf, int offset) {

		Arrays.fill(mStringBuf, (byte) 0);

		if (bytesBuf.length > this.mStringLength) {
			System.arraycopy(bytesBuf, offset, mStringBuf, 0, mStringLength);
		} else {
			System.arraycopy(bytesBuf, offset, mStringBuf, 0, bytesBuf.length);
		}
	}

	/**
	 * getString:获取String
	 * 
	 * @return mStringBuf String
	 */
	public String getString() {
		return new String(mStringBuf);
	}

	/**
	 * getStringLength:获取String长度
	 * 
	 * @return mStringLength int
	 */
	public int getStringLength() {
		return mStringLength;
	}

	/**
	 * setString:设置String
	 * 
	 * @param asciiString
	 *            String
	 */
	public void setString(String asciiString) {

		byte[] bytesBuf = ConvTools.stringToBytesAscii(asciiString);
		setStringBuf(bytesBuf, 0);
	}
}
