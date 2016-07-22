/**
 * BaseBufferSerializable.java
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

import java.util.ArrayList;
import java.util.List;

import cx.sh.cn.drivingbehavior.type.AsciiByteString;
import cx.sh.cn.drivingbehavior.type.SignedByte;
import cx.sh.cn.drivingbehavior.type.SignedInt;
import cx.sh.cn.drivingbehavior.type.SignedShort;
import cx.sh.cn.drivingbehavior.utils.ConvTools;


/**
 * ClassName:BaseBufferSerializable
 * Function :任务定时器
 * 
 * @author 陈琪
 * @version 1.0
 * @Date 2013-9-30 下午4:48:42
 */
public abstract class BaseBufferSerializable implements ISetBufferSerializable, IGetBufferSerializable {

	/**
	 * 字段列表
	 */
	protected List<Object> fieldsList = null;

	/**
	 * 缓存长度
	 */
	private int bufferLength = 0;

	/**
	 * Creates a new instance of BaseBufferSerializable.
	 */
	public BaseBufferSerializable() {

		this.fieldsList = new ArrayList<Object>();

		initFieldsList();
		initFieldsBufferLength();
	}

	/**
	 * initFieldsList:初始化字段列表
	 */
	abstract protected void initFieldsList();

	/**
	 * (non-Javadoc)
	 */
	@Override
	public byte[] getBuffer() {

		int iIdxOffset = 0;
		int iBufferLength = getBufferLength();
		byte[] bytesBuf = new byte[iBufferLength];

		// 遍历字段列表
		for (Object obj : this.fieldsList) {

			// 有符号字节
			if (obj instanceof SignedByte) {
				bytesBuf[iIdxOffset] = ((SignedByte) obj).getValue();
				iIdxOffset += 1;
			}

			// 有符号短整型
			else if (obj instanceof SignedShort) {
				short sValue = ((SignedShort) obj).getValue();
				sValue = Short.reverseBytes(sValue);
				System.arraycopy(ConvTools.shortToBytes(sValue), 0, bytesBuf, iIdxOffset, 2);
				iIdxOffset += 2;
			}

			// 有符号整型
			else if (obj instanceof SignedInt) {
				int iValue = ((SignedInt) obj).getValue();
				iValue = Integer.reverseBytes(iValue);
				System.arraycopy(ConvTools.intToBytes(iValue), 0, bytesBuf, iIdxOffset, 4);
				iIdxOffset += 4;
			}

			// ASCII字节字符串
			else if (obj instanceof AsciiByteString) {
				byte[] bytesString = ((AsciiByteString) obj).getStringBuf();
				System.arraycopy(bytesString, 0, bytesBuf, iIdxOffset, bytesString.length);
				iIdxOffset += bytesString.length;
			}
		}

		// 长度不一致
		if (iIdxOffset != iBufferLength) {
			bytesBuf = null;
		}

		return bytesBuf;
	}

	/**
	 * (non-Javadoc)
	 */
	@Override
	public boolean setBuffer(byte[] bytesBuf) {
		return setBuffer(bytesBuf, 0, bytesBuf.length);
	}

	/**
	 * (non-Javadoc)
	 */
	@Override
	public boolean setBuffer(byte[] bytesBuf, int offset, int length) {

		if (getBufferLength() == length && bytesBuf.length >= offset + length) {

			int iIdxOffset = offset;

			// 遍历字段列表
			for (Object obj : this.fieldsList) {

				// 有符号字节
				if (obj instanceof SignedByte) {
					((SignedByte) obj).setValue(bytesBuf[iIdxOffset]);
					iIdxOffset += 1;
				}

				// 有符号短整型
				else if (obj instanceof SignedShort) {
					short sValue = ConvTools.bytesToShort(bytesBuf, iIdxOffset);
					sValue = Short.reverseBytes(sValue);
					((SignedShort) obj).setValue(sValue);
					iIdxOffset += 2;
				}

				// 有符号整型
				else if (obj instanceof SignedInt) {
					int iValue = ConvTools.bytesToInt(bytesBuf, iIdxOffset);
					iValue = Integer.reverseBytes(iValue);
					((SignedInt) obj).setValue(iValue);
					iIdxOffset += 4;
				}

				// ASCII字节字符串
				else if (obj instanceof AsciiByteString) {
					((AsciiByteString) obj).setStringBuf(bytesBuf, iIdxOffset);
					iIdxOffset += ((AsciiByteString) obj).getStringLength();
				}
			}

			return true;
		}

		return false;
	}

	/**
	 * (non-Javadoc)
	 */
	@Override
	public int getBufferLength() {
		return this.bufferLength;
	}

	/**
	 * initFieldsBufferLength:(这里用一句话描述这个方法的作用)
	 */
	private void initFieldsBufferLength() {

		this.bufferLength = 0;

		// 遍历字段列表
		for (Object obj : this.fieldsList) {

			// 有符号字节
			if (obj instanceof SignedByte) {
				this.bufferLength += 1;
			}

			// 有符号短整型
			else if (obj instanceof SignedShort) {
				this.bufferLength += 2;
			}

			// 有符号整型
			else if (obj instanceof SignedInt) {
				this.bufferLength += 4;
			}

			// ASCII字节字符串
			else if (obj instanceof AsciiByteString) {
				this.bufferLength += ((AsciiByteString) obj).getStringLength();
			}
		}
	}
}
