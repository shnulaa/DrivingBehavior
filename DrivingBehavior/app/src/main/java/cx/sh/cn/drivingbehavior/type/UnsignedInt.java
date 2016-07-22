/**
 * UnsignedInt.java
 * cn.sh.sis.vehicle.safe.comm.type
 *
 * Function:UnsignedInt自定义类型
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0	 2013-9-30 		陈琪
 *
 * Copyright (c) 2013, Shanghai Changxing Information Technology Co., Ltd. All Rights Reserved.
 */
package cx.sh.cn.drivingbehavior.type;

/**
 * UnsignedInt
 * Function :UnsignedInt自定义类型
 * 
 * @author 陈琪
 * @version 1.0
 * @Date 2013-9-30 下午4:48:42
 */
public class UnsignedInt extends SignedInt {

	/**
	 * 
	 * Creates a new instance of UnsignedInt.
	 * 
	 */
	public UnsignedInt() {
	}

	/**
	 * getUnsignedValue:获取UnsignedInt
	 * 
	 * @return mValue
	 */
	public long getUnsignedValue() {
		return mValue & 0xffffffffL;
	}
}
