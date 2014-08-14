package fr.wuzuf.netty.handlers.codec.tn3270.handshake;

import fr.wuzuf.netty.handlers.codec.tn3270.HandshakeMessage;
import io.netty.buffer.ByteBuf;

public class FunctionsRequest implements HandshakeMessage {

	private byte[] functionsList;

	public FunctionsRequest(byte[] functionsList) {
		this.functionsList = functionsList;
	}

	public FunctionsRequest(short[] functionsList) {
	}

	public void encode(ByteBuf buf) {
		buf.writeByte(0xff);
		buf.writeByte(0xfa);
		buf.writeByte(0x28);
		buf.writeByte(0x03);
		buf.writeByte(0x07);
		buf.writeBytes(this.functionsList);
		buf.writeByte(0xff);
		buf.writeByte(0xf0);
	}
}
