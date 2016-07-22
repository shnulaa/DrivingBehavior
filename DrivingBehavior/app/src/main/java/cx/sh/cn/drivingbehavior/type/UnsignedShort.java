/**
 * UnsignedShort.java
 * cn.sh.sis.vehicle.safe.comm.type
 *
 * Function:UnsignedShort自定义类型
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0	 2013-9-30 		陈琪
 *
 * Copyright (c) 2013, Shanghai Changxing Information Technology Co., Ltd. All Rights Reserved.
 */
package cx.sh.cn.drivingbehavior.type;

/**
 * UnsignedShort
 * Function :UnsignedShort自定义类型
 * 
 * @author 陈琪
 * @version 1.0
 * @Date 2013-9-30 下午4:48:42
 */
public class UnsignedShort extends SignedShort {

	/**
	 * 
	 * Creates a new instance of UnsignedShort.
	 * 
	 */
	public UnsignedShort() {
	}

	/**
	 * getUnsignedValue:获取UnsignedShort
	 * 
	 * @return mValue int
	 */
	public int getUnsignedValue() {
		return (int) mValue & 0xffff;
	}

}
