package cx.sh.cn.drivingbehavior.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by ZengChao on 2016/7/1.
 */
public class GyroscopeData {
    private float angleX;

    private float angleY;

    private float angleZ;

    private Queue<Float> angleZList = new LinkedList<Float>();

    private float firstAngleZ;

    private float lastAngleZ;

    private final int MAX_SIZE = 30;

    public float getAngleX() {
        return angleX;
    }

    public void setAngleX(float angleX) {
        this.angleX = angleX;
    }

    public float getAngleY() {
        return angleY;
    }

    public void setAngleY(float angleY) {
        this.angleY = angleY;
    }

    public float getAngleZ() {
        return angleZ;
    }

    public void setAngleZ(float angleZ) {
        this.angleZ = angleZ;
    }

    public Queue<Float> getAngleZList() {
        return angleZList;
    }

    public Queue<Float> addToAngleZList(float angleZ) {
        if (angleZList.size() == MAX_SIZE) {
            angleZList.poll();
            angleZList.offer(angleZ);
            setLastAngleZ(angleZ);
        } else {
            angleZList.offer(angleZ);
            setLastAngleZ(Float.MAX_VALUE);
        }
        setFirstAngleZ(angleZList.element());
        return angleZList;
    }


    public float getFirstAngleZ() {
        return firstAngleZ;
    }

    public void setFirstAngleZ(float firstAngleZ) {
        this.firstAngleZ = firstAngleZ;
    }

    public float getLastAngleZ() {
        return lastAngleZ;
    }

    public void setLastAngleZ(float lastAngleZ) {
        this.lastAngleZ = lastAngleZ;
    }

    public int getMAX_SIZE() {
        return MAX_SIZE;
    }

}
