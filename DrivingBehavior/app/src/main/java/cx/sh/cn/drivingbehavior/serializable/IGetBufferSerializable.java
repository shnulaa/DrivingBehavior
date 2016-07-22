/**
 * IGetBufferSerializable.java
 * cn.sh.sis.vehicle.safe.component.serializable
 *
 * Function:获取缓存序列化
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0	 2013-9-30 		陈琪
 *
 * Copyright (c) 2013, Shanghai Changxing Information Technology Co., Ltd. All Rights Reserved.
 */
package cx.sh.cn.drivingbehavior.serializable;

/**
 * ClassName:IGetBufferSerializable
 * Function :获取缓存序列化
 * 
 * @author 陈琪
 * @version 1.0
 * @Date 2013-9-30 下午4:48:42
 */
public interface IGetBufferSerializable {

	/**
	 * getBuffer:获取缓存字节数组
	 * 
	 * @return byte[] 字节数组
	 */
	byte[] getBuffer();

	/**
	 * getBufferLength:缓存字节数组长度
	 * 
	 * @return int 缓存字节数组长度
	 */
	int getBufferLength();
}
