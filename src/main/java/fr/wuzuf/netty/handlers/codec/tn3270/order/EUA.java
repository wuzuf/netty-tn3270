package fr.wuzuf.netty.handlers.codec.tn3270.order;

/**
 * Erase Unprotected to Address
 * 
 * @author gabrieldelabachelerie
 *
 */
public class EUA implements Order {
	private int addr;

	public EUA(int addr) {
		this.setAddr(addr);
	}

	public void accept(OrderVisitor v) {
		v.visit(this);
	}

	public int getAddr() {
		return addr;
	}

	public void setAddr(int addr) {
		this.addr = addr;
	}
}
