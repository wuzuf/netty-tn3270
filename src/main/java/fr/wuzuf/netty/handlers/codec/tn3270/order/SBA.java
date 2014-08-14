package fr.wuzuf.netty.handlers.codec.tn3270.order;

public class SBA implements Order {

	private int addr;

	public SBA(int addr) {
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
