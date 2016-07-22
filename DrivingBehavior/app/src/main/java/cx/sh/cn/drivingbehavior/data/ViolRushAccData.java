/**
 * ViolRushAccData.java
 * cn.sh.sis.vehicle.safe.component.data.viol
 *
 * Function:急加速违反类
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0	 2013-9-30 		凤哲
 *
 * Copyright (c) 2013, Shanghai Changxing Information Technology Co., Ltd. All Rights Reserved.
 */
package cx.sh.cn.drivingbehavior.data;

import cx.sh.cn.drivingbehavior.serializable.ViolSerializable;
import cx.sh.cn.drivingbehavior.type.SignedInt;
import cx.sh.cn.drivingbehavior.type.UnsignedInt;
import cx.sh.cn.drivingbehavior.type.UnsignedShort;

/**
 * ViolRushAccData
 * Function :急加速违反类
 * 
 * @author 凤哲
 * @version 1.0
 * @Date 2013-9-30 下午4:48:42
 */
public class ViolRushAccData extends ViolSerializable {

	/**
	 * 急加速违反ID
	 */
	private static byte VIOL_RUSHACC_TYPE = 9;

	/**
	 * 开始时间(秒)
	 */
	private UnsignedInt startSeconds;

	/**
	 * 开始时间(毫秒)
	 */
	private UnsignedShort startMilliSec;

	/**
	 * 纬度
	 */
	private SignedInt latitude;

	/**
	 * 经度
	 */
	private SignedInt longitude;

	/**
	 * 最大加速度
	 */
	private UnsignedShort maxAcceleration;

	/**
	 * ViolRushAccData:急加速违反构造函数
	 */
	public ViolRushAccData() {

		// 设置急加速违反ID
		super((byte) VIOL_RUSHACC_TYPE);
	}

	/**
	 * initFieldsList:初始化字段列表
	 */
	@Override
	protected void initFieldsList() {

		super.initFieldsList();

		// 添加开始时间(秒)
		startSeconds = new UnsignedInt();
		fieldsList.add(startSeconds);

		// 添加开始时间(毫秒)
		startMilliSec = new UnsignedShort();
		fieldsList.add(startMilliSec);

		// 添加纬度
		latitude = new SignedInt();
		fieldsList.add(latitude);

		// 添加经度
		longitude = new SignedInt();
		fieldsList.add(longitude);

		// 添加最大加速度
		maxAcceleration = new UnsignedShort();
		fieldsList.add(maxAcceleration);

	}

	/**
	 * getStartSec:获得开始时间(秒)
	 * 
	 * @return startSeconds long
	 */
	public long getStartSec() {

		return startSeconds.getUnsignedValue();
	}

	/**
	 * setStartSec:设置开始时间(秒)
	 * 
	 * @param value
	 *            int
	 */
	public void setStartSec(int value) {

		this.startSeconds.setValue(value);
	}

	/**
	 * getStartMilliSec:获得开始时间(毫秒)
	 * 
	 * @return startMilliSec int
	 */
	public int getStartMilliSec() {

		return startMilliSec.getUnsignedValue();
	}

	/**
	 * setStartMilliSec:设置开始时间(毫秒)
	 * 
	 * @param value
	 *            short
	 */
	public void setStartMilliSec(short value) {

		this.startMilliSec.setValue(value);
	}

	/**
	 * getLatitude:获得纬度
	 * 
	 * @return latitude int
	 */
	public int getLatitude() {

		return latitude.getValue();
	}

	/**
	 * setLatitude:设置纬度
	 * 
	 * @param value
	 *            int
	 */
	public void setLatitude(int value) {

		this.latitude.setValue(value);
	}

	/**
	 * getLongitude:获得经度
	 * 
	 * @return longitude int
	 */
	public int getLongitude() {

		return longitude.getValue();
	}

	/**
	 * setLongitude:设置经度
	 * 
	 * @param value
	 *            int
	 */
	public void setLongitude(int value) {

		this.longitude.setValue(value);
	}

	/**
	 * getMaxAcceleration:获得最大加速度
	 * 
	 * @return maxAcceleration int
	 */
	public int getMaxAcceleration() {

		return maxAcceleration.getUnsignedValue();
	}

	/**
	 * setMaxAcceleration:设置最大加速度
	 * 
	 * @param value
	 *            short
	 */
	public void setMaxAcceleration(short value) {

		this.maxAcceleration.setValue(value);
	}
}
