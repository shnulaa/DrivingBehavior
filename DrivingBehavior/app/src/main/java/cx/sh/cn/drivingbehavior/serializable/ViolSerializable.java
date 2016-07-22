/**
 * ViolSerializable.java
 * cn.sh.sis.vehicle.safe.component.serializable
 *
 * Function:任务定时器
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0	 2013-9-30 		陈琪
 *
 * Copyright (c) 2013, Shanghai Changxing Information Technology Co., Ltd. All Rights Reserved.
 */
package cx.sh.cn.drivingbehavior.serializable;


import cx.sh.cn.drivingbehavior.type.UnsignedByte;

/**
 * ClassName:ViolSerializable
 * Function :违反序列化
 * 
 * @author 陈琪
 * @version 1.0
 * @Date 2013-9-30 下午4:48:42
 */
public class ViolSerializable extends BaseBufferSerializable {

	/**
	 * 违反类型
	 */
	private UnsignedByte violType;

	/**
	 * Creates a new instance of ViolSerializable.
	 * 
	 * @param violType
	 *            违反类型
	 */
	protected ViolSerializable(byte violType) {
		this.violType.setValue(violType);
	}

	/**
	 * (non-Javadoc)
	 */
	@Override
	protected void initFieldsList() {

		// 违反类型
		this.violType = new UnsignedByte();
		super.fieldsList.add(violType);
	}
}
