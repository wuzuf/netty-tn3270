package fr.wuzuf.netty.handlers.codec.tn3270;

import java.util.List;

import fr.wuzuf.netty.handlers.codec.tn3270.command.EOM;
import fr.wuzuf.netty.handlers.codec.tn3270.command.SOM;
import fr.wuzuf.netty.handlers.codec.tn3270.order.Order;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

public class Codec extends ByteToMessageCodec<Message> implements
		ParserListener {

	private Parser parser;
	private List<Object> out;
	private DataMessage message;

	public Codec() {
		this.parser = new Parser(this);
		
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			final List<Object> out) throws Exception {
		this.out = out;
		this.parser.parse(in.readSlice(in.readableBytes()));
	}

	public void emit(Object obj) {
		if(obj instanceof SOM) {
			// New data message starting
			this.message = new DataMessage();
		} else if(obj instanceof EOM) {
			// Data message completely decoded
			out.add(message);
			this.message = null;
		} else if (this.message != null) {
			// Currently decoding a data message
			if (obj instanceof Order) {
				this.message.getOrders().add((Order)obj);
			}
		} else {
			// Handshake messages
			out.add(obj);
		}
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg,
			ByteBuf out) throws Exception {
		msg.encode(out);
	}
}
