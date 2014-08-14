package fr.wuzuf.netty.handlers.codec.tn3270.handshake;

import fr.wuzuf.netty.handlers.codec.tn3270.HandshakeMessage;
import io.netty.buffer.ByteBuf;

public class DeviceTypeIs implements HandshakeMessage {

	private String deviceType;
	private String deviceName;

	public DeviceTypeIs(short[] deviceType, short[] deviceName) {
		// TODO Auto-generated constructor stub
	}

	public DeviceTypeIs(String deviceType, String deviceName) {
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}
	
	public void encode(ByteBuf buf) {
		buf.writeByte(0xff);
		buf.writeByte(0xfa);
		buf.writeByte(0x28);
		buf.writeByte(0x02);
		buf.writeByte(0x07);
		buf.writeBytes(this.deviceType.getBytes());
		buf.writeByte(0x01);
		buf.writeBytes(this.deviceName.getBytes());
		buf.writeByte(0xff);
		buf.writeByte(0xf0);
	}
}
