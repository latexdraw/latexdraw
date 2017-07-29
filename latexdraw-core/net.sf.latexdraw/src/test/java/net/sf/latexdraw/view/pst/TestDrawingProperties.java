package net.sf.latexdraw.view.pst;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.view.ViewsSynchroniserHandler;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.latex.VerticalPosition;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestDrawingProperties {
	IDrawing drawing;
	PSTCodeGenerator gen;

	@Before
	public void setUp() throws Exception {
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(Canvas.class).asEagerSingleton();
				bind(PSTCodeGenerator.class).asEagerSingleton();
			}
			@Provides
			IDrawing provideDrawing(final Canvas canvas) {
				return canvas.getDrawing();
			}
			@Provides
			ViewsSynchroniserHandler provideViewsSynchroniserHandler(final Canvas canvas) {
				return canvas;
			}
		});
		gen = injector.getInstance(PSTCodeGenerator.class);
		drawing = injector.getInstance(IDrawing.class);
		drawing.addShape(ShapeFactory.INST.createCircle());
	}

	@Test
	public void testLabelGetSet() throws Exception {
		gen.setLabel("Foo");
		assertEquals("Foo", gen.getLabel());
	}

	@Test
	public void testSetLabelNULL() throws Exception {
		gen.setLabel(null);
		assertEquals("", gen.getLabel());
	}

	@Test
	public void testLabelInGenCode() throws Exception {
		gen.setLabel("thisisalabel");
		assertTrue(gen.getDocumentCode().contains("\\label{thisisalabel}"));
	}

	@Test
	public void testCaptionGetSet() throws Exception {
		gen.setCaption("Foo");
		assertEquals("Foo", gen.getCaption());
	}

	@Test
	public void testSetCaptionNULL() throws Exception {
		gen.setCaption(null);
		assertEquals("", gen.getCaption());
	}

	@Test
	public void testCaptionInGenCode() throws Exception {
		gen.setCaption("thisisacaption");
		assertTrue(gen.getDocumentCode().contains("\\caption{thisisacaption}"));
	}

	@Test
	public void testCommentGetSet() throws Exception {
		gen.setComment("Foo");
		assertEquals("% Foo", gen.getComment());
	}

	@Test
	public void testSetCommentNULL() throws Exception {
		gen.setComment(null);
		assertEquals("", gen.getComment());
	}

	@Test
	public void testCommentInGenCode() throws Exception {
		gen.setComment("thisisacomment");
		assertTrue(gen.getDocumentCode().contains("% thisisacomment"));
	}

	@Test
	public void testGetSetCenter() throws Exception {
		gen.setPositionHoriCentre(true);
		assertTrue(gen.isPositionHoriCentre());
	}

	@Test
	public void testCenterInGenCode() throws Exception {
		gen.setPositionHoriCentre(true);
		assertTrue(gen.getDocumentCode().contains("\\begin{center}"));
	}

	@Test
	public void testCenterInGenCodeButNotCommented() throws Exception {
		gen.setPositionHoriCentre(true);
		assertFalse(gen.getDocumentCode().contains("% \\begin{center}"));
	}

	@Test
	public void testGetSetVertTokenBottom() throws Exception {
		gen.setPositionVertToken(VerticalPosition.BOTTOM);
		assertEquals(VerticalPosition.BOTTOM, gen.getPositionVertToken());
	}

	@Test
	public void testGetSetVertTokenFLOATS_PAGE() throws Exception {
		gen.setPositionVertToken(VerticalPosition.FLOATS_PAGE);
		assertEquals(VerticalPosition.FLOATS_PAGE, gen.getPositionVertToken());
	}

	@Test
	public void testGetSetVertTokenHERE() throws Exception {
		gen.setPositionVertToken(VerticalPosition.HERE);
		assertEquals(VerticalPosition.HERE, gen.getPositionVertToken());
	}

	@Test
	public void testGetSetVertTokenHERE_HERE() throws Exception {
		gen.setPositionVertToken(VerticalPosition.HERE_HERE);
		assertEquals(VerticalPosition.HERE_HERE, gen.getPositionVertToken());
	}

	@Test
	public void testGetSetVertTokenNONE() throws Exception {
		gen.setPositionVertToken(VerticalPosition.NONE);
		assertEquals(VerticalPosition.NONE, gen.getPositionVertToken());
	}

	@Test
	public void testGetSetVertTokenTOP() throws Exception {
		gen.setPositionVertToken(VerticalPosition.TOP);
		assertEquals(VerticalPosition.TOP, gen.getPositionVertToken());
	}

	@Test
	public void testGetSetVertTokenBottomInGenCode() throws Exception {
		gen.setPositionVertToken(VerticalPosition.BOTTOM);
		assertTrue(gen.getDocumentCode(), gen.getDocumentCode().contains("\\begin{figure}[b]"));
	}

	@Test
	public void testGetSetVertTokenFLOATS_PAGEInGenCode() throws Exception {
		gen.setPositionVertToken(VerticalPosition.FLOATS_PAGE);
		assertTrue(gen.getDocumentCode(), gen.getDocumentCode().contains("\\begin{figure}[p]"));
	}

	@Test
	public void testGetSetVertTokenHEREInGenCode() throws Exception {
		gen.setPositionVertToken(VerticalPosition.HERE);
		assertTrue(gen.getDocumentCode().contains("\\begin{figure}[h]"));
	}

	@Test
	public void testGetSetVertTokenHERE_HEREInGenCode() throws Exception {
		gen.setPositionVertToken(VerticalPosition.HERE_HERE);
		assertTrue(gen.getDocumentCode().contains("\\begin{figure}[H]"));
	}

	@Test
	public void testGetSetVertTokenNONEInGenCode() throws Exception {
		gen.setPositionVertToken(VerticalPosition.NONE);
		assertFalse(gen.getDocumentCode(), gen.getDocumentCode().contains("\\begin{figure}"));
	}

	@Test
	public void testGetSetVertTokenTOPInGenCode() throws Exception {
		gen.setPositionVertToken(VerticalPosition.TOP);
		assertTrue(gen.getDocumentCode().contains("\\begin{figure}[t]"));
	}
}
