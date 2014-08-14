package fr.wuzuf.netty.handlers.codec.tn3270.tn;

import io.netty.buffer.ByteBuf;
import fr.wuzuf.netty.handlers.codec.tn3270.HandshakeMessage;

public class ArgCommand implements HandshakeMessage {
	
	private byte command;
	private byte parameter;

	public ArgCommand(byte command, byte parameter) {
		this.command = command;
		this.parameter = parameter;
	}

	public void encode(ByteBuf buf) {
		buf.writeByte(0xff);
		buf.writeByte(this.command);
		buf.writeByte(this.parameter);
	}

}
