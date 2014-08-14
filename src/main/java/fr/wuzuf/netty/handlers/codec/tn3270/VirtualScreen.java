package fr.wuzuf.netty.handlers.codec.tn3270;

import fr.wuzuf.netty.handlers.codec.tn3270.order.EUA;
import fr.wuzuf.netty.handlers.codec.tn3270.order.IC;
import fr.wuzuf.netty.handlers.codec.tn3270.order.OrderVisitor;
import fr.wuzuf.netty.handlers.codec.tn3270.order.PT;
import fr.wuzuf.netty.handlers.codec.tn3270.order.RA;
import fr.wuzuf.netty.handlers.codec.tn3270.order.SBA;
import fr.wuzuf.netty.handlers.codec.tn3270.order.SF;
import fr.wuzuf.netty.handlers.codec.tn3270.order.SFE;
import fr.wuzuf.netty.handlers.codec.tn3270.order.TEXT;

public class VirtualScreen implements OrderVisitor {
	private int rows, columns = 0;
	private int position = 0;
	
	private char[] screen;
	
	public VirtualScreen(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		this.screen = new char[this.rows * this.columns];
	}

	public void Clear() {
		for(int i = 0; i<this.rows*this.columns; ++i) {
			this.screen[i] = 0;
		}
		this.position = 0;
	}

	public String shortDisplay() {
		int emptyLinesCount = 0;
		boolean displayStarted = false;
		StringBuilder b = new StringBuilder();
		for(int j = 0; j < this.rows; ++j) {
			StringBuilder lb = new StringBuilder();
			for (int i = 0; i < this.columns; ++i) {
				char c = this.screen[i + j * this.columns];
				if (c != 0 && c != 0x1d) {
					lb.append(c);
				} else {
					lb.append(' ');
				}
			}
			
			// Right trim the line
			String line = lb.toString();
			int i = line.length()-1;
		    while (i >= 0 && Character.isWhitespace(line.charAt(i))) {
		        i--;
		    }
		    
		    // Handle empty lines wisely
		    if(i < 0) {
		    	if(displayStarted) {
		    		emptyLinesCount++;
		    	}
		    } else {
		    	// Display is started
		    	displayStarted = true;
		    	// Add empty lines back
		    	for(int k = 0; k < emptyLinesCount; ++k) {
		    		b.append('\n');
		    	}
		    	emptyLinesCount = 0;
		    	// Add line
		    	b.append(line.substring(0,i+1));
		    	b.append('\n');
		    }
		}
		return b.toString();
	}

	public String fullDisplay() {
		StringBuilder b = new StringBuilder();
		b.append('+');
		for (int i = 0; i < this.columns; ++i) {
			b.append('-');
		}
		b.append('+');
		b.append('\n');
		for(int j = 0; j < this.rows; ++j) {
			
			b.append('|');
			for (int i = 0; i < this.columns; ++i) {
				char c = this.screen[i + j * this.columns];
				if (c == 0) {
					b.append(' ');
				} else if (c == 0x1d) {
					b.append('`');
				} else {
					b.append(c);
				}
			}
			b.append('|');
			b.append('\n');
		}
		b.append('+');
		for (int i = 0; i < this.columns; ++i) {
			b.append('-');
		}
		b.append('+');
		return b.toString();
	}

	public void visit(EUA eua) {
		// TODO Auto-generated method stub
	}

	public void visit(IC ic) {
		// TODO Auto-generated method stub
	}

	public void visit(PT pt) {
		// TODO Auto-generated method stub
	}

	public void visit(RA ra) {
		// TODO Auto-generated method stub
	}

	public void visit(SBA sba) {
		this.position = sba.getAddr();
	}

	public void visit(SF sf) {
		this.screen[this.position] = 0x1d;
		this.position = (this.position + 1) % (this.rows * this.columns);
	}

	public void visit(SFE sfe) {
		this.screen[this.position] = 0x1d;
		this.position = (this.position + 1) % (this.rows * this.columns);
	}

	public void visit(TEXT text) {
		for(char c : text.getText().toCharArray()) {
			this.screen[this.position] = c;
			this.position = (this.position + 1) % (this.rows * this.columns);
		}
	}

}
