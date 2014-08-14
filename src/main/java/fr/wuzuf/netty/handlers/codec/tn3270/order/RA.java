package fr.wuzuf.netty.handlers.codec.tn3270.order;

public class RA implements Order {

	public RA(int getAddr, short s) {
		// TODO Auto-generated constructor stub
	}

	public void accept(OrderVisitor v) {
		v.visit(this);
	}

}
