/**
 * SignedInt.java
 * cn.sh.sis.vehicle.safe.comm.type
 *
 * Function:SignedByte自定义类型
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0	 2013-9-30 		陈琪
 *
 * Copyright (c) 2013, Shanghai Changxing Information Technology Co., Ltd. All Rights Reserved.
 */
package cx.sh.cn.drivingbehavior.type;

/**
 * SignedInt
 * Function :SignedInt自定义类型
 * 
 * @author 陈琪
 * @version 1.0
 * @Date 2013-9-30 下午4:48:42
 */
public class SignedInt {

	/**
	 * 初期值
	 */
	protected int mValue = 0;

	/**
	 * 构造体
	 */
	public SignedInt() {
	}

	/**
	 * setValue:设置SignedInt
	 * 
	 * @param value
	 *            int
	 */
	public void setValue(int value) {
		this.mValue = value;
	}

	/**
	 * getValue:获取SignedInt
	 * 
	 * @return mValue int
	 */
	public int getValue() {
		return this.mValue;
	}
}
