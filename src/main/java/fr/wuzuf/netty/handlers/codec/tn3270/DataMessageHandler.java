package fr.wuzuf.netty.handlers.codec.tn3270;

import fr.wuzuf.netty.handlers.codec.tn3270.command.WCC;
import fr.wuzuf.netty.handlers.codec.tn3270.order.EUA;
import fr.wuzuf.netty.handlers.codec.tn3270.order.IC;
import fr.wuzuf.netty.handlers.codec.tn3270.order.Order;
import fr.wuzuf.netty.handlers.codec.tn3270.order.OrderVisitor;
import fr.wuzuf.netty.handlers.codec.tn3270.order.PT;
import fr.wuzuf.netty.handlers.codec.tn3270.order.RA;
import fr.wuzuf.netty.handlers.codec.tn3270.order.SBA;
import fr.wuzuf.netty.handlers.codec.tn3270.order.SF;
import fr.wuzuf.netty.handlers.codec.tn3270.order.SFE;
import fr.wuzuf.netty.handlers.codec.tn3270.order.TEXT;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

public class DataMessageHandler extends MessageToMessageCodec<DataMessage, String>
		implements OrderVisitor {

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg,
			List<Object> out) throws Exception {
		DataMessage message = new DataMessage();
		message.getOrders().add(new TEXT(msg));
		out.add(message);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, DataMessage msg, List<Object> out)
			throws Exception {
		for(Order order : msg.getOrders()) {
			order.accept(this);
		}
	}

	public void visit(EUA eua) {
		// TODO Auto-generated method stub

	}

	public void visit(IC ic) {
		// TODO Auto-generated method stub

	}

	public void visit(PT pt) {
		// TODO Auto-generated method stub

	}

	public void visit(RA ra) {
		// TODO Auto-generated method stub

	}

	public void visit(SBA sba) {
		// TODO Auto-generated method stub

	}

	public void visit(SF sf) {
		// TODO Auto-generated method stub

	}

	public void visit(SFE sfe) {
		// TODO Auto-generated method stub

	}

	public void visit(TEXT text) {
		System.err.println(text.getText());
	}

	public void visit(WCC wcc) {
		// TODO Auto-generated method stub

	}

}
