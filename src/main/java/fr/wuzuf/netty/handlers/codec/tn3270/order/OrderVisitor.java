package fr.wuzuf.netty.handlers.codec.tn3270.order;

public interface OrderVisitor {
	void visit(EUA eua);
	void visit(IC ic);
	void visit(PT pt);
	void visit(RA ra);
	void visit(SBA sba);
	void visit(SF sf);
	void visit(SFE sfe);
	void visit(TEXT text);
}
