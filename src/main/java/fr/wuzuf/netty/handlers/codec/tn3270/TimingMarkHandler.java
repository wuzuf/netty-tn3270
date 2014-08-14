package fr.wuzuf.netty.handlers.codec.tn3270;

import fr.wuzuf.netty.handlers.codec.tn3270.tn.ArgCommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TimingMarkHandler extends
		SimpleChannelInboundHandler<Object> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (msg instanceof ArgCommand) {
			ctx.writeAndFlush(new ArgCommand((byte) 0xfb, (byte) 0x06));
		} else {
			ctx.fireChannelRead(msg);
		}

	}

}
