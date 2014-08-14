package fr.wuzuf.netty.handlers.codec.tn3270;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;

import junit.framework.TestCase;

public class ParserTest extends TestCase {
	public void testBasicScreenDecoding() {
		final ArrayList<Object> tokens = new ArrayList<Object>();
		Parser parser = new Parser(new ParserListener() {
			public void emit(Object obj) {
				tokens.add(obj);
			}
		});
		ByteBuf buffer = Unpooled.wrappedBuffer(new byte[]{0, 0, 0, 0, 0, (byte)0xf5, (byte)0xc3, 0x11, (byte)0xc1, 0x50});
		parser.parse(buffer);
		assertEquals(4, tokens.size());
	}
}
