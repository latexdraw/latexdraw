package test.gui;

import static org.junit.Assert.*;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapePropertyCustomiser;

import org.junit.Before;
import org.junit.Test;

public abstract class TestShapePropGUI<T extends ShapePropertyCustomiser> extends TestLatexdrawGUI {
	Pencil pencil;
	Hand hand;
	T ins;

	
	@Override
	@Before
	public void setUp() {
		super.setUp();
		pencil	= (Pencil)guiceFactory.call(Pencil.class);
		hand	= (Hand)guiceFactory.call(Hand.class);
	}
	
	@Test public void testGetPencil() {
		assertNotNull(ins.getPencil());
	}
	
	@Test public void testGetHand() {
		assertNotNull(ins.getHand());
	}
}
