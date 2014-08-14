package fr.wuzuf.netty.handlers.codec.tn3270.handshake;

import fr.wuzuf.netty.handlers.codec.tn3270.HandshakeMessage;
import io.netty.buffer.ByteBuf;

public class FunctionsIs implements HandshakeMessage{

	private byte[] functionsList;

	public FunctionsIs(byte[] functionsList) {
		this.functionsList = functionsList;
	}
	public FunctionsIs(short[] functionsList) {
		// TODO Auto-generated constructor stub
	}

	public void encode(ByteBuf buf) {
		buf.writeByte(0xff);
		buf.writeByte(0xfa);
		buf.writeByte(0x28);
		buf.writeByte(0x03);
		buf.writeByte(0x04);
		buf.writeBytes(this.functionsList);
		buf.writeByte(0xff);
		buf.writeByte(0xf0);
	}
}
