/**
 * ISetBufferSerializable.java
 * cn.sh.sis.vehicle.safe.component.serializable
 *
 * Function:设置缓存序列化
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0	 2013-9-30 		陈琪
 *
 * Copyright (c) 2013, Shanghai Changxing Information Technology Co., Ltd. All Rights Reserved.
 */
package cx.sh.cn.drivingbehavior.serializable;

/**
 * ClassName:ISetBufferSerializable
 * Function :设置缓存序列化
 * 
 * @author 陈琪
 * @version 1.0
 * @Date 2013-9-30 下午4:48:42
 */
public interface ISetBufferSerializable {

	/**
	 * setBuffer:设置缓存字节数组
	 * 
	 * @param bytesBuf
	 *            字节数组
	 * 
	 * @return boolean 状态
	 */
	boolean setBuffer(byte[] bytesBuf);

	/**
	 * setBuffer:设置缓存字节数组
	 * 
	 * @param bytesBuf
	 *            字节数组
	 * @param offset
	 *            偏移地址
	 * @param length
	 *            字节数组长度
	 * 
	 * @return boolean 状态
	 */
	boolean setBuffer(byte[] bytesBuf, int offset, int length);
}
