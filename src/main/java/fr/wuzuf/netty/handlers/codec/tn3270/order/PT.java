package fr.wuzuf.netty.handlers.codec.tn3270.order;

/**
 * Program Tab
 * 
 * Advances the current buffer address to the first position of the next unprotected field
 * {@link http://en.wikipedia.org/wiki/IBM_3270#Orders}
 * @author gabrieldelabachelerie
 *
 */
public class PT implements Order {


	public void accept(OrderVisitor v) {
		v.visit(this);
	}
}
