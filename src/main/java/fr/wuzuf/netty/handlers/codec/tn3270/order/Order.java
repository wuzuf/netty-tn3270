package fr.wuzuf.netty.handlers.codec.tn3270.order;

public interface Order {
	void accept(OrderVisitor v);
}
