package net.sf.latexdraw.view.pst;

import java.lang.reflect.InvocationTargetException;
import net.sf.latexdraw.data.ConfigureInjection;
import net.sf.latexdraw.data.InjectionExtension;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.SystemService;
import net.sf.latexdraw.view.ViewsSynchroniserHandler;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.ViewFactory;
import net.sf.latexdraw.view.latex.VerticalPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(InjectionExtension.class)
public class TestDrawingProperties {
	Drawing drawing;
	PSTCodeGenerator gen;

	@ConfigureInjection
	Injector configureInjection() {
		return new Injector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(SystemService.class);
				bindToInstance(PSTViewsFactory.class, Mockito.mock(PSTViewsFactory.class));
				bindAsEagerSingleton(PSTCodeGenerator.class);
				bindToInstance(ViewFactory.class, Mockito.mock(ViewFactory.class));
				bindAsEagerSingleton(Canvas.class);
				bindWithCommand(Drawing.class, Canvas.class, canvas -> canvas.getDrawing());
				bindWithCommand(ViewsSynchroniserHandler.class, Canvas.class, canvas -> canvas);
			}
		};
	}

	@BeforeEach
	void setUp(final PSTCodeGenerator gen, final Drawing drawing) {
		this.drawing = drawing;
		this.gen = gen;
		drawing.addShape(ShapeFactory.INST.createCircle());
	}

	@Test
	void testLabelGetSet() {
		gen.setLabel("Foo");
		assertEquals("Foo", gen.getLabel());
	}

	@Test
	void testSetLabelNULL() {
		gen.setLabel(null);
		assertEquals("", gen.getLabel());
	}

	@Test
	void testLabelInGenCode() {
		gen.setLabel("thisisalabel");
		assertTrue(gen.getDocumentCode().contains("\\label{thisisalabel}"));
	}

	@Test
	void testCaptionGetSet() {
		gen.setCaption("Foo");
		assertEquals("Foo", gen.getCaption());
	}

	@Test
	void testSetCaptionNULL() {
		gen.setCaption(null);
		assertEquals("", gen.getCaption());
	}

	@Test
	void testCaptionInGenCode() {
		gen.setCaption("thisisacaption");
		assertTrue(gen.getDocumentCode().contains("\\caption{thisisacaption}"));
	}

	@Test
	void testCommentGetSet() {
		gen.setComment("Foo");
		assertEquals("Foo", gen.getComment());
	}

	@Test
	void testCommentGetSetWithTag() {
		gen.setComment("Foo");
		assertEquals("% Foo", gen.getCommentWithTag());
	}

	@Test
	void testCommentGetSetWithTagMultiLines() {
		gen.setComment("Foo\nbar");
		assertEquals("% Foo\n% bar", gen.getCommentWithTag());
	}

	@Test
	void testSetCommentNULL() {
		gen.setComment(null);
		assertEquals("", gen.getComment());
	}

	@Test
	void testSetCommentNULLWithTag() {
		gen.setComment(null);
		assertEquals("% ", gen.getCommentWithTag());
	}

	@Test
	void testCommentInGenCode() {
		gen.setComment("thisisacomment");
		assertTrue(gen.getDocumentCode().contains("% thisisacomment"));
	}

	@Test
	void testGetSetCenter() {
		gen.setPositionHoriCentre(true);
		assertTrue(gen.isPositionHoriCentre());
	}

	@Test
	void testCenterInGenCode() {
		gen.setPositionHoriCentre(true);
		assertTrue(gen.getDocumentCode().contains("\\begin{center}"));
	}

	@Test
	void testCenterInGenCodeButNotCommented() {
		gen.setPositionHoriCentre(true);
		assertFalse(gen.getDocumentCode().contains("% \\begin{center}"));
	}

	@Test
	void testGetSetVertTokenBottom() {
		gen.setPositionVertToken(VerticalPosition.BOTTOM);
		assertEquals(VerticalPosition.BOTTOM, gen.getPositionVertToken());
	}

	@Test
	void testGetSetVertTokenFLOATSPAGE() {
		gen.setPositionVertToken(VerticalPosition.FLOATS_PAGE);
		assertEquals(VerticalPosition.FLOATS_PAGE, gen.getPositionVertToken());
	}

	@Test
	void testGetSetVertTokenHERE() {
		gen.setPositionVertToken(VerticalPosition.HERE);
		assertEquals(VerticalPosition.HERE, gen.getPositionVertToken());
	}

	@Test
	void testGetSetVertTokenHEREHERE() {
		gen.setPositionVertToken(VerticalPosition.HERE_HERE);
		assertEquals(VerticalPosition.HERE_HERE, gen.getPositionVertToken());
	}

	@Test
	void testGetSetVertTokenNONE() {
		gen.setPositionVertToken(VerticalPosition.NONE);
		assertEquals(VerticalPosition.NONE, gen.getPositionVertToken());
	}

	@Test
	void testGetSetVertTokenTOP() {
		gen.setPositionVertToken(VerticalPosition.TOP);
		assertEquals(VerticalPosition.TOP, gen.getPositionVertToken());
	}

	@Test
	void testGetSetVertTokenBottomInGenCode() {
		gen.setPositionVertToken(VerticalPosition.BOTTOM);
		assertTrue(gen.getDocumentCode().contains("\\begin{figure}[b]"), () -> gen.getDocumentCode());
	}

	@Test
	void testGetSetVertTokenFLOATSPAGEInGenCode() {
		gen.setPositionVertToken(VerticalPosition.FLOATS_PAGE);
		assertTrue(gen.getDocumentCode().contains("\\begin{figure}[p]"), () -> gen.getDocumentCode());
	}

	@Test
	void testGetSetVertTokenHEREInGenCode() {
		gen.setPositionVertToken(VerticalPosition.HERE);
		assertTrue(gen.getDocumentCode().contains("\\begin{figure}[h]"));
	}

	@Test
	void testGetSetVertTokenHEREHEREInGenCode() {
		gen.setPositionVertToken(VerticalPosition.HERE_HERE);
		assertTrue(gen.getDocumentCode().contains("\\begin{figure}[H]"));
	}

	@Test
	void testGetSetVertTokenNONEInGenCode() {
		gen.setPositionVertToken(VerticalPosition.NONE);
		assertFalse(gen.getDocumentCode().contains("\\begin{figure}"), () -> gen.getDocumentCode());
	}

	@Test
	void testGetSetVertTokenTOPInGenCode() {
		gen.setPositionVertToken(VerticalPosition.TOP);
		assertTrue(gen.getDocumentCode().contains("\\begin{figure}[t]"));
	}
}
