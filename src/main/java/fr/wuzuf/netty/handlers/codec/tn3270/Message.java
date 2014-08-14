package fr.wuzuf.netty.handlers.codec.tn3270;

import io.netty.buffer.ByteBuf;

public interface Message {
	void encode(ByteBuf buf);

}
