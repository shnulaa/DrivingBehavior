/**
 * SignedByte.java
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
 * SignedByte
 * Function :SignedByte自定义类型
 * 
 * @author 陈琪
 * @version 1.0
 * @Date 2013-9-30 下午4:48:42
 */
public class SignedByte {

	/**
	 * 初期值
	 */
	protected byte mValue = 0;

	/**
	 * 构造体
	 */
	public SignedByte() {
	}

	/**
	 * getValue:获取SignedByte
	 * 
	 * @return mValue byte
	 */
	public byte getValue() {
		return this.mValue;
	}

	/**
	 * setValue:设置SignedByte
	 * 
	 * @param value
	 *            byte
	 */
	public void setValue(byte value) {
		this.mValue = value;
	}
}
