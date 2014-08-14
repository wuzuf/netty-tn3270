package fr.wuzuf.netty.handlers.codec.tn3270;

import java.nio.charset.Charset;
import java.util.ArrayList;

import fr.wuzuf.netty.handlers.codec.tn3270.order.Order;
import fr.wuzuf.netty.handlers.codec.tn3270.order.TEXT;
import io.netty.buffer.ByteBuf;

public class DataMessage implements Message {

	private ArrayList<Order> orders;
	
	
	public DataMessage() {
		this.orders = new ArrayList<Order>();
	}

	public void encode(ByteBuf buf) {
		buf.writeBytes(new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x7d,
				(byte) 0xc1, 0x50, 0x11, (byte) 0xc1, 0x50 });
		for(Order order : this.orders) {
			if (order instanceof TEXT) {
				buf.writeBytes(Charset.forName("IBM1047").encode(((TEXT)order).getText()));
			}
		}
		buf.writeBytes(new byte[] { (byte) 0xff, (byte) 0xef });
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

}
