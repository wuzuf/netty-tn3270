package fr.wuzuf.netty.handlers.codec.tn3270;

import fr.wuzuf.netty.handlers.codec.tn3270.handshake.DeviceTypeIs;
import fr.wuzuf.netty.handlers.codec.tn3270.handshake.FunctionsIs;
import fr.wuzuf.netty.handlers.codec.tn3270.handshake.FunctionsRequest;
import fr.wuzuf.netty.handlers.codec.tn3270.handshake.SendDeviceType;
import fr.wuzuf.netty.handlers.codec.tn3270.tn.ArgCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Handles a client-side channel.
 */
@Sharable
public class HandshakeMessageHandler extends
		SimpleChannelInboundHandler<Message> {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg)
			throws Exception {
		if (msg instanceof HandshakeMessage) {
			if (msg instanceof ArgCommand) {
				ByteBuf buf = ctx.alloc().buffer();
				buf.writeByte(0xff);
				buf.writeByte(0xfb);
				buf.writeByte(0x28);
				ctx.writeAndFlush(buf);
			} else if (msg instanceof SendDeviceType) {
				ctx.writeAndFlush(new DeviceTypeIs("IBM-3278-2-E", "09123456"));
			} else if (msg instanceof DeviceTypeIs) {
				ctx.writeAndFlush(new FunctionsRequest(new byte[] {}));
			} else if (msg instanceof FunctionsRequest) {
				ctx.writeAndFlush(new FunctionsIs(new byte[] {}));
			}
		} else {
			// Handshake completed, remove myself from the pipeline
			ctx.fireChannelRead(msg);
			ctx.pipeline().remove(this);
		}
	}
}
