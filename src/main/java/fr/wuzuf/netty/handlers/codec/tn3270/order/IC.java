package fr.wuzuf.netty.handlers.codec.tn3270.order;

/**
 * Insert Cursor
 * 
 * @see http://en.wikipedia.org/wiki/IBM_3270#Orders
 * @author gabrieldelabachelerie
 *
 */
public class IC implements Order {

	public void accept(OrderVisitor v) {
		v.visit(this);
	}

}
