package fr.wuzuf.netty.handlers.codec.tn3270;

import fr.wuzuf.netty.handlers.codec.tn3270.order.EUA;
import fr.wuzuf.netty.handlers.codec.tn3270.order.SBA;
import fr.wuzuf.netty.handlers.codec.tn3270.order.SF;
import fr.wuzuf.netty.handlers.codec.tn3270.order.TEXT;
import junit.framework.TestCase;

public class VirtualScreenTest extends TestCase {
	
	private static String emptyScreen = 
		      "+--------------------------------------------------------------------------------+\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
		    + "|                                                                                |\n"
	        + "+--------------------------------------------------------------------------------+";
	
	private static String builtScreen =
			  "+--------------------------------------------------------------------------------+\n"
            + "|`                                                                              `|\n"
            + "|>                                                                              `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "+--------------------------------------------------------------------------------+";
	
	private static String oneLineScreen =
			  "+--------------------------------------------------------------------------------+\n"
            + "|`                                                                              `|\n"
            + "|LINE1                                                                          `|\n"
            + "|>                                                                              `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "+--------------------------------------------------------------------------------+";
	
	private static String twoLinesScreen =
			  "+--------------------------------------------------------------------------------+\n"
            + "|`                                                                              `|\n"
            + "|LINE1                                                                          `|\n"
            + "|LINE2                                                                          `|\n"
            + "|>                                                                              `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "+--------------------------------------------------------------------------------+";
	
	private static String threeLinesScreen =
			  "+--------------------------------------------------------------------------------+\n"
            + "|`                                                                              `|\n"
            + "|LINE1                                                                          `|\n"
            + "|LINE2                                                                          `|\n"
            + "|LINE3                                                                          `|\n"
            + "|>                                                                              `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "+--------------------------------------------------------------------------------+";
	
	private static String splitLinesScreen =
			  "+--------------------------------------------------------------------------------+\n"
            + "|`                                                                              `|\n"
            + "|LINE1                                                                          `|\n"
            + "|                                                                               `|\n"
            + "|LINE3                                                                          `|\n"
            + "|>                                                                              `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "|                                                                               `|\n"
            + "+--------------------------------------------------------------------------------+";

    public void testEmptyScreen()
    {
        VirtualScreen screen = new VirtualScreen(24, 80);
        screen.Clear();
        assertEquals("", screen.shortDisplay());
        assertEquals(emptyScreen, screen.fullDisplay());
    }
    
    public void testWriteText() {
    	VirtualScreen screen = new VirtualScreen(24, 80);
    	buildScreen(screen);
    	screen.visit(new SBA(80));
    	screen.visit(new TEXT("LINE1"));
    	screen.visit(new SBA(160));
    	screen.visit(new TEXT(">"));
        assertEquals("LINE1\n>\n", screen.shortDisplay());
        assertEquals(oneLineScreen, screen.fullDisplay());
    }
    
    public void testSplitLines() {
    	VirtualScreen screen = new VirtualScreen(24, 80);
    	buildScreen(screen);
    	screen.visit(new SBA(80));
    	screen.visit(new TEXT("LINE1"));
    	screen.visit(new SBA(240));
    	screen.visit(new TEXT("LINE3"));
    	screen.visit(new SBA(320));
    	screen.visit(new TEXT(">"));
        assertEquals("LINE1\n\nLINE3\n>\n", screen.shortDisplay());
        assertEquals(splitLinesScreen, screen.fullDisplay());
    }
    
    public void testBuildScreen() {
    	VirtualScreen screen = new VirtualScreen(24, 80);
    	buildScreen(screen);
        assertEquals(">\n", screen.shortDisplay());
        assertEquals(builtScreen, screen.fullDisplay());
    }
    
    public void testSoftClearScreen() {
    	VirtualScreen screen = new VirtualScreen(24, 80);
    	buildScreen(screen);
    	// Write three lines
    	screen.visit(new SBA(80));
    	screen.visit(new TEXT("LINE1"));
    	screen.visit(new SBA(160));
    	screen.visit(new TEXT("LINE2"));
    	screen.visit(new SBA(240));
    	screen.visit(new TEXT("LINE3"));
    	screen.visit(new SBA(320));
    	screen.visit(new TEXT(">"));
        assertEquals(threeLinesScreen, screen.fullDisplay());
        // Write above the same lines
    	screen.visit(new SBA(80));
    	screen.visit(new TEXT("LINE1 LONGER"));
    	assertEquals("LINE1 LONGER\nLINE2\nLINE3\n>\n", screen.shortDisplay());
        // Write again using EUA to clear unused fields
    	screen.visit(new SBA(80));
    	screen.visit(new TEXT("LINE1"));
    	screen.visit(new EUA(160));
    	screen.visit(new TEXT("LINE2"));
    	screen.visit(new EUA(80));
        assertEquals(twoLinesScreen, screen.fullDisplay());
    }
    
    public void testShortDisplaySpaces() {
    	VirtualScreen screen = new VirtualScreen(24, 80);
    	buildScreen(screen);
    	screen.visit(new SBA(80));
    	screen.visit(new TEXT("  LINE1"));
    	assertEquals("  LINE1\n", screen.shortDisplay());
    	screen.visit(new SBA(160));
    	screen.visit(new TEXT("  LINE2"));
    	screen.visit(new SBA(242));
    	screen.visit(new TEXT("LINE3"));
    	assertEquals("  LINE1\n  LINE2\n  LINE3\n", screen.shortDisplay());
    }

	private void buildScreen(VirtualScreen screen) {
		screen.visit(new SBA(0));
    	screen.visit(new SF(0));
    	for(int i = 0; i < 24; ++i) {
    		screen.visit(new SBA(80 * (i+1) - 1));
    		screen.visit(new SF(0));
    	}
    	screen.visit(new SBA(80));
    	screen.visit(new TEXT(">"));
	}
}
