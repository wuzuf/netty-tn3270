package fr.wuzuf.netty.handlers.codec.tn3270.order;

public class SF implements Order {

	public SF(int s) {
		// TODO Auto-generated constructor stub
	}


	public void accept(OrderVisitor v) {
		v.visit(this);
	}
}
