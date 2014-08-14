package fr.wuzuf.netty.handlers.codec.tn3270.order;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;

public class TEXT implements Order {
	
	private String text;

	public TEXT(ByteBuf slice) {
		this.text = slice.toString(Charset.forName("IBM1047"));
	}


	public TEXT(String msg) {
		this.text = msg;
	}


	public void accept(OrderVisitor v) {
		v.visit(this);
	}

	public String getText() {
		return text;
	}
}
