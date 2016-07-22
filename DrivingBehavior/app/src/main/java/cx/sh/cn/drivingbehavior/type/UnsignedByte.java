/**
 * UnsignedByte.java
 * cn.sh.sis.vehicle.safe.comm.type
 *
 * Function:UnsignedByte自定义类型
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0	 2013-9-30 		陈琪
 *
 * Copyright (c) 2013, Shanghai Changxing Information Technology Co., Ltd. All Rights Reserved.
 */
package cx.sh.cn.drivingbehavior.type;

/**
 * UnsignedByte
 * Function :UnsignedByte自定义类型
 * 
 * @author 陈琪
 * @version 1.0
 * @Date 2013-9-30 下午4:48:42
 */
public class UnsignedByte extends SignedByte {

	/**
	 * 
	 * Creates a new instance of UnsignedByte.
	 * 
	 */
	public UnsignedByte() {
	}

	/**
	 * getUnsignedValue:获取UnsignedByte
	 * 
	 * @return mValue short
	 */
	public short getUnsignedValue() {
		return (short) (mValue & 0xff);
	}

}
