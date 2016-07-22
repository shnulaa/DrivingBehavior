/**
 * ViolOverSpeedData.java
 * cn.sh.sis.vehicle.safe.component.data.viol
 *
 * Function:超速违反类
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
 * ViolOverSpeedData
 * Function :超速违反类
 * 
 * @author 凤哲
 * @version 1.0
 * @Date 2013-9-30 下午4:48:42
 */
public class ViolOverSpeedData extends ViolSerializable {

	/**
	 * 超速违反类型ID
	 */
	private final static byte VIOL_OVERSPEED_TYPE = 3;

	/**
	 * 开始时间(秒)
	 */
	private UnsignedInt startSeconds;

	/**
	 * 开始时间(毫秒)
	 */
	private UnsignedShort startMilliSec;

	/**
	 * 开始时纬度
	 */
	private SignedInt startLatitude;

	/**
	 * 开始时经度
	 */
	private SignedInt startLongitude;

	/**
	 * 最高转速
	 */
	private UnsignedShort maxRotationalSpeed;

	/**
	 * 最高速度
	 */
	private UnsignedShort maxSpeed;

	/**
	 * 结束时间(秒)
	 */
	private UnsignedInt endSeconds;

	/**
	 * 结束时间(毫秒)
	 */
	private UnsignedShort endMilliSec;

	/**
	 * 结束时纬度
	 */
	private SignedInt endLatitude;

	/**
	 * 结束时经度
	 */
	private SignedInt endLongitude;

	/**
	 * 车速基准值
	 */
	private UnsignedShort speedReferenceValue;

	/**
	 * ViolOverSpeedData:超速违反构造函数
	 */
	public ViolOverSpeedData() {

		// 设置超速违反ID
		super((byte) VIOL_OVERSPEED_TYPE);
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

		// 添加开始纬度
		startLatitude = new SignedInt();
		fieldsList.add(startLatitude);

		// 添加开始经度
		startLongitude = new SignedInt();
		fieldsList.add(startLongitude);

		// 添加最高转速
		maxRotationalSpeed = new UnsignedShort();
		fieldsList.add(maxRotationalSpeed);

		// 添加最高车速
		maxSpeed = new UnsignedShort();
		fieldsList.add(maxSpeed);

		// 添加结束时间(秒)
		endSeconds = new UnsignedInt();
		fieldsList.add(endSeconds);

		// 添加结束时间(毫秒)
		endMilliSec = new UnsignedShort();
		fieldsList.add(endMilliSec);

		// 添加结束纬度
		endLatitude = new SignedInt();
		fieldsList.add(endLatitude);

		// 添加结束经度
		endLongitude = new SignedInt();
		fieldsList.add(endLongitude);

		// 添加速度基准值
		speedReferenceValue = new UnsignedShort();
		fieldsList.add(speedReferenceValue);

	}

	/**
	 * getStartSeconds:获得开始时间(秒)
	 * 
	 * @return startSeconds long
	 */
	public long getStartSeconds() {

		return startSeconds.getUnsignedValue();
	}

	/**
	 * setStartSeconds:设置开始时间(秒)
	 * 
	 * @param value
	 *            int
	 */
	public void setStartSeconds(int value) {

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
	 * getStartLatitude:获得开始纬度
	 * 
	 * @return startLatitude int
	 */
	public int getStartLatitude() {

		return startLatitude.getValue();
	}

	/**
	 * setStartLatitude:设置开始纬度
	 * 
	 * @param value
	 *            int
	 */
	public void setStartLatitude(int value) {

		this.startLatitude.setValue(value);
	}

	/**
	 * getStartLongitude:获得开始经度
	 * 
	 * @return startLongitude int
	 */
	public int getStartLongitude() {

		return startLongitude.getValue();
	}

	/**
	 * setStartLongitude:设置开始经度
	 * 
	 * @param value
	 *            int
	 */
	public void setStartLongitude(int value) {

		this.startLongitude.setValue(value);
	}

	/**
	 * getMaxRotationalSpeed:获得最高转速
	 * 
	 * @return maxRotationalSpeed int
	 */
	public int getMaxRotationalSpeed() {

		return maxRotationalSpeed.getUnsignedValue();
	}

	/**
	 * setMaxRotationalSpeed:设置最高转速
	 * 
	 * @param value
	 *            short
	 */
	public void setMaxRotationalSpeed(short value) {

		this.maxRotationalSpeed.setValue(value);
	}

	/**
	 * getMaxSpeed:获得最高车速
	 * 
	 * @return maxSpeed int
	 */
	public int getMaxSpeed() {

		return maxSpeed.getUnsignedValue();
	}

	/**
	 * setMaxSpeed:设置最高车速
	 * 
	 * @param value
	 *            short
	 */
	public void setMaxSpeed(short value) {

		this.maxSpeed.setValue(value);
	}

	/**
	 * getEndSec:获得结束时间(秒)
	 * 
	 * @return endSeconds long
	 */
	public long getEndSec() {

		return endSeconds.getUnsignedValue();
	}

	/**
	 * setEndSeconds:设置结束时间(秒)
	 * 
	 * @param value
	 *            int
	 */
	public void setEndSeconds(int value) {

		this.endSeconds.setValue(value);
	}

	/**
	 * getEndMilliSec:获得结束时间(毫秒)
	 * 
	 * @return endMilliSec int
	 */
	public int getEndMilliSec() {

		return endMilliSec.getUnsignedValue();
	}

	/**
	 * setEndMilliSec:设置结束时间(毫秒)
	 * 
	 * @param value
	 *            short
	 */
	public void setEndMilliSec(short value) {

		this.endMilliSec.setValue(value);
	}

	/**
	 * getEndLatitude:获得结束纬度
	 * 
	 * @return endLatitude int
	 */
	public int getEndLatitude() {

		return endLatitude.getValue();
	}

	/**
	 * setEndLatitude:设置结束纬度
	 * 
	 * @param value
	 *            int
	 */
	public void setEndLatitude(int value) {

		this.endLatitude.setValue(value);
	}

	/**
	 * getEndLongitude:获得结束经度
	 * 
	 * @return endLongitude int
	 */
	public int getEndLongitude() {

		return endLongitude.getValue();
	}

	/**
	 * setEndLongitude:设置结束经度
	 * 
	 * @param value
	 *            int
	 */
	public void setEndLongitude(int value) {

		this.endLongitude.setValue(value);
	}

	/**
	 * getSpeedReferenceValue:获得超速基准值
	 * 
	 * @return speedReferenceValue int
	 */
	public int getSpeedReferenceValue() {

		return speedReferenceValue.getUnsignedValue();
	}

	/**
	 * setSpeedReferenceValue:设置超速基准值
	 * 
	 * @param value
	 *            short
	 */
	public void setSpeedReferenceValue(short value) {

		this.speedReferenceValue.setValue(value);
	}

}
