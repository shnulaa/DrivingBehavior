/**
 * ObjectValuePair.java
 * cn.sh.sis.vehicle.safe.comm.type
 *
 * Function:ObjectValuePair自定义类型
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0	 2013-9-30 		陈琪
 *
 * Copyright (c) 2013, Shanghai Changxing Information Technology Co., Ltd. All Rights Reserved.
 */
package cx.sh.cn.drivingbehavior.type;

/**
 * ClassName:ObjectValuePair
 * Function :ObjectValuePair自定义类型
 * 
 * @author 陈琪
 * @version 1.0
 * @Date 2013-9-30 下午4:48:42
 */
public class ObjectValuePair {

	/**
	 * 对象
	 */
	private Object obj = null;

	/**
	 * 长度
	 */
	private int length = 0;

	/**
	 * 
	 * Creates a new instance of ObjectValuePair.
	 * 
	 * @param obj
	 * @param length
	 */
	public ObjectValuePair(Object obj, int length) {
		this.obj = obj;
		this.length = length;
	}

	/**
	 * getObj:取得对象
	 * 
	 * @return obj Object
	 */
	public Object getObj() {
		return this.obj;
	}

	/**
	 * getLength:取得长度
	 * 
	 * @return length int
	 */
	public int getLength() {
		return this.length;
	}
}
