/**
 * ViolIdlingData.java
 * cn.sh.sis.vehicle.safe.component.data.viol
 *
 * Function:怠速违反类
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0	 2013-9-30 		凤哲
 *
 * Copyright (c) 2013, Shanghai Changxing Information Technology Co., Ltd. All Rights Reserved.
 */
package cx.sh.cn.drivingbehavior.data;

import cx.sh.cn.drivingbehavior.serializable.ViolSerializable;
import cx.sh.cn.drivingbehavior.type.SignedByte;
import cx.sh.cn.drivingbehavior.type.SignedInt;
import cx.sh.cn.drivingbehavior.type.UnsignedInt;
import cx.sh.cn.drivingbehavior.type.UnsignedShort;

/**
 * ViolIdlingData
 * Function :怠速违反类
 * 
 * @author 凤哲
 * @version 1.0
 * @Date 2013-9-30 下午4:48:42
 */
public class ViolIdlingData extends ViolSerializable {

	/**
	 * 怠速违反类型ID
	 */
	private final static byte VIOL_IDLING_TYPE = 5;

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
	 * 结束时间(秒)
	 */
	private UnsignedInt endSeconds;

	/**
	 * 结束时间(毫秒)
	 */
	private UnsignedShort endMilliSec;

	/**
	 * 怠速基准值
	 */
	private SignedByte standardValue;

	/**
	 * ViolIdlingData:怠速违反构造函数
	 */
	public ViolIdlingData() {

		// 设置怠速违反ID
		super((byte) VIOL_IDLING_TYPE);
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

		// 添加结束时间(秒)
		endSeconds = new UnsignedInt();
		fieldsList.add(endSeconds);

		// 添加结束时间(毫秒)
		endMilliSec = new UnsignedShort();
		fieldsList.add(endMilliSec);

		// 添加怠速基准值
		standardValue = new SignedByte();
		fieldsList.add(standardValue);

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
	 * getEndSec:获得结束时间(秒)
	 * 
	 * @return endSeconds long
	 */
	public long getEndSec() {

		return endSeconds.getUnsignedValue();
	}

	/**
	 * setEndSec:设置结束时间(秒)
	 * 
	 * @param value
	 *            int
	 */
	public void setEndSec(int value) {

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
	 * getStandardValue:获得怠速违反基准值
	 * 
	 * @return standardValue byte
	 */
	public byte getStandardValue() {

		return standardValue.getValue();
	}

	/**
	 * setStandardValue:设置怠速违反基准值
	 * 
	 * @param value
	 *            byte
	 */
	public void setStandardValue(byte value) {

		this.standardValue.setValue(value);
	}
}
