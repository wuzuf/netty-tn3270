package fr.wuzuf.netty.handlers.codec.tn3270.order;

public class SFE implements Order {

	public SFE(short s) {
		// TODO Auto-generated constructor stub
	}


	public void accept(OrderVisitor v) {
		v.visit(this);
	}
}
