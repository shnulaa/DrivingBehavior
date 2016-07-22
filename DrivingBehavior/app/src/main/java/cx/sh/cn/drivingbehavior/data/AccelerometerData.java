package cx.sh.cn.drivingbehavior.data;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by ZengChao on 2016/7/21.
 */
public class AccelerometerData {
    private float valueX;

    private float valueY;

    private float valueZ;

    private Queue<Float> valueZList = new LinkedList<Float>();


    private final int MAX_SIZE = 12;

    public float getValueX() {
        return valueX;
    }

    public void setValueX(float valueX) {
        this.valueX = valueX;
    }

    public float getValueY() {
        return valueY;
    }

    public void setValueY(float valueY) {
        this.valueY = valueY;
    }

    public float getValueZ() {
        return valueZ;
    }

    public void setValueZ(float valueZ) {
        this.valueZ = valueZ;
    }

    public Queue<Float> getValueZList() {
        return valueZList;
    }

    public Queue<Float> addToValueZList(float valueZ) {
        if (valueZList.size() == MAX_SIZE) {
            valueZList.poll();
            valueZList.offer(valueZ);
        } else {
            valueZList.offer(valueZ);
        }
        return valueZList;
    }

}
