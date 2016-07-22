package cx.sh.cn.drivingbehavior.data;

import cx.sh.cn.drivingbehavior.serializable.ViolSerializable;
import cx.sh.cn.drivingbehavior.type.UnsignedInt;
import cx.sh.cn.drivingbehavior.type.UnsignedShort;

/**
 * Created by ZengChao on 2016/6/29.
 */
public class ViolContDrivingData extends ViolSerializable {

    /**
     * 连续驾驶违反类型ID
     */
    private final static byte VIOL_CONT_DRIVING_TYPE = 1;

    /**
     * 开始时间(秒)
     */
    private UnsignedInt startSeconds;

    /**
     * 开始时间(毫秒)
     */
    private UnsignedShort startMilliSec;

    /**
     * 结束时间(秒)
     */
    private UnsignedInt endSec;

    /**
     * 结束时间(毫秒)
     */
    private UnsignedShort endMilliSec;

    /**
     * 连续驾驶基准值
     */
    private UnsignedShort standardValue;

    /**
     * ViolContDrivingData:连续驾驶违反构造函数
     */
    public ViolContDrivingData() {

        // 设置连续驾驶违反类型ID
        super((byte) VIOL_CONT_DRIVING_TYPE);
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

        // 添加结束时间(秒)
        endSec = new UnsignedInt();
        fieldsList.add(endSec);

        // 添加结束时间(毫秒)
        endMilliSec = new UnsignedShort();
        fieldsList.add(endMilliSec);

        // 添加连续驾驶基准值
        standardValue = new UnsignedShort();
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
     * getEndSec:获得结束时间(秒)
     *
     * @return endSec long
     */
    public long getEndSec() {

        return endSec.getUnsignedValue();
    }

    /**
     * setEndSec:设置结束时间(秒)
     *
     * @param value
     *            int
     */
    public void setEndSec(int value) {

        this.endSec.setValue(value);
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
     * 设置结束时间(毫秒)
     *
     * @param value
     */
    public void setEndMilliSec(short value) {

        this.endMilliSec.setValue(value);
    }

    /**
     * 获得连续驾驶基准值
     *
     * @return 连续驾驶基准
     */
    public int getStandardValue() {

        return standardValue.getUnsignedValue();
    }

    /**
     * 设置连续驾驶基准值
     *
     * @param value
     */
    public void setStandardValue(short value) {

        this.standardValue.setValue(value);
    }

}
