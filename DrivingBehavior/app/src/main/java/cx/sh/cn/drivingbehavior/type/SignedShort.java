/**
 * SignedShort.java
 * cn.sh.sis.vehicle.safe.comm.type
 *
 * Function:SignedShort自定义类型
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0	 2013-9-30 		陈琪
 *
 * Copyright (c) 2013, Shanghai Changxing Information Technology Co., Ltd. All Rights Reserved.
 */
package cx.sh.cn.drivingbehavior.type;

/**
 * SignedShort
 * Function :SignedShort自定义类型
 * 
 * @author 陈琪
 * @version 1.0
 * @Date 2013-9-30 下午4:48:42
 */
public class SignedShort {

	/**
	 * 初期值
	 */
	protected short mValue = 0;

	/**
	 * 
	 * Creates a new instance of SignedShort.
	 * 
	 */
	public SignedShort() {
	}

	/**
	 * getValue:获取SignedShort
	 * 
	 * @return mValue short
	 */
	public short getValue() {
		return this.mValue;
	}

	/**
	 * setValue:设置SignedShort
	 * 
	 * @param value
	 *            short
	 */
	public void setValue(short value) {
		this.mValue = value;
	}
}
