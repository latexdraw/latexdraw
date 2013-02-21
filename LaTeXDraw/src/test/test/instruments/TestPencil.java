package test.instruments;

import java.awt.Color;
import java.awt.event.MouseEvent;

import net.sf.latexdraw.actions.shape.AddShape;
import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.*;
import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle;
import net.sf.latexdraw.glib.ui.LCanvas;
import net.sf.latexdraw.glib.ui.LMagneticGrid;
import net.sf.latexdraw.instruments.EditionChoice;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.TextSetter;

import org.junit.Before;
import org.junit.Test;
import org.malai.instrument.Link;
import org.malai.swing.instrument.library.WidgetZoomer;
import org.malai.swing.interaction.SwingEventManager;
import org.malai.swing.widget.MLayeredPane;

public class TestPencil extends TestInstrument<Pencil> {
	protected LCanvas canvas;

	@Override
	@Before
	public void setUp() {
		DrawingTK.setFactory(new LShapeFactory());
		IDrawing drawing = DrawingTK.getFactory().createDrawing();
		canvas 		= new LCanvas(drawing);
		instrument 	= new Pencil(drawing, new WidgetZoomer(canvas, true, true, null, "", null, "", true), new LMagneticGrid(100, 100, canvas),
				new TextSetter(new MLayeredPane(false, false)));
		instrument.addEventable(canvas);
	}


	@Test
	public void testExceptionConstructor() {//FIXME
//		try {
//			new Pencil(null, new Zoomer(new LCanvas()));
//			fail();
//		}catch(IllegalArgumentException ex) { /* ok */ }
//		try {
//			new Pencil(null, null);
//			fail();
//		}catch(IllegalArgumentException ex) { /* ok */ }
//		try {
//			new Pencil(new LDrawing(), null);
//			fail();
//		}catch(IllegalArgumentException ex) { /* ok */ }
	}


	@Test
	public void testLinkDnD2AddShape_PressEventCreatesRectangle() {
		instrument.setActivated(true);
		double x = 100., y = 300.;
		changePencilShapeAttributes();
		instrument.setCurrentChoice(EditionChoice.RECT);
		((SwingEventManager)canvas.getEventManager()).mousePressed(new MouseEvent(canvas, 0, 100, 0, (int)x, (int)y, 0, 0, 0, false, 0));
		Link<?,?,?> link = getLink("DnD2AddShape");

		assertTrue(link.getAction() instanceof AddShape);
		assertTrue(((AddShape)link.getAction()).shape().get() instanceof IRectangle);

		IRectangle rec = (IRectangle) ((AddShape)link.getAction()).shape().get();
		checkShape(rec);
		assertEquals(rec.getTopLeftPoint().getX(), x);
		assertEquals(rec.getTopLeftPoint().getY(), y);
		assertTrue(rec.getWidth()>0);
		assertTrue(rec.getHeight()>0);
	}



	@Test
	public void testLinkDnD2AddShape_PressEventCreatesEllipse() {
		instrument.setActivated(true);
		double x = 100., y = 300.;
		changePencilShapeAttributes();
		instrument.setCurrentChoice(EditionChoice.ELLIPSE);
		((SwingEventManager)canvas.getEventManager()).mousePressed(new MouseEvent(canvas, 0, 100, 0, (int)x, (int)y, 0, 0, 0, false, 0));
		Link<?,?,?> link = getLink("DnD2AddShape");

		assertTrue(link.getAction() instanceof AddShape);
		assertTrue(((AddShape)link.getAction()).shape().get() instanceof IEllipse);

		IEllipse ell = (IEllipse) ((AddShape)link.getAction()).shape().get();
		checkShape(ell);
		assertEquals(ell.getTopLeftPoint().getX(), x);
		assertEquals(ell.getTopLeftPoint().getY(), y);
		assertTrue(ell.getWidth()>0);
		assertTrue(ell.getHeight()>0);
	}



	@Test
	public void testLinkDnD2AddShape_PressEventCreatesCircle() {
		instrument.setActivated(true);
		double x = 100., y = 300.;
		changePencilShapeAttributes();
		instrument.setCurrentChoice(EditionChoice.CIRCLE);
		((SwingEventManager)canvas.getEventManager()).mousePressed(new MouseEvent(canvas, 0, 100, 0, (int)x, (int)y, 0, 0, 0, false, 0));
		Link<?,?,?> link = getLink("DnD2AddShape");

		assertTrue(link.getAction() instanceof AddShape);
		assertTrue(((AddShape)link.getAction()).shape().get() instanceof ICircle);

		ICircle circle = (ICircle) ((AddShape)link.getAction()).shape().get();
		checkShape(circle);
		assertEquals(circle.getGravityCentre().getX(), x);
		assertEquals(circle.getGravityCentre().getY(), y);
		assertTrue(circle.getWidth()>0);
		assertTrue(circle.getHeight()>0);
	}



	@Test
	public void testLinkDnD2AddShape_PressEventCreatesSquare() {
		instrument.setActivated(true);
		double x = 100., y = 300.;
		changePencilShapeAttributes();
		instrument.setCurrentChoice(EditionChoice.SQUARE);
		((SwingEventManager)canvas.getEventManager()).mousePressed(new MouseEvent(canvas, 0, 100, 0, (int)x, (int)y, 0, 0, 0, false, 0));
		Link<?,?,?> link = getLink("DnD2AddShape");

		assertTrue(link.getAction() instanceof AddShape);
		assertTrue(((AddShape)link.getAction()).shape().get() instanceof ISquare);

		ISquare square = (ISquare) ((AddShape)link.getAction()).shape().get();
		checkShape(square);
		assertEquals(square.getGravityCentre().getX(), x);
		assertEquals(square.getGravityCentre().getY(), y);
		assertTrue(square.getWidth()>0);
		assertTrue(square.getHeight()>0);
	}



	public void changePencilShapeAttributes() {
		instrument.setActivated(true);
		instrument.getGroupParams().setLineColour(Color.BLUE);
		instrument.getGroupParams().setThickness(13.);
		instrument.getGroupParams().setBordersPosition(BorderPos.OUT);
		instrument.getGroupParams().setDotStyle(DotStyle.BAR);
		instrument.getGroupParams().setRadius(24.);
		instrument.getGroupParams().setHasDbleBord(true);
		instrument.getGroupParams().setDbleBordCol(Color.CYAN);
		instrument.getGroupParams().setHasShadow(true);
		instrument.getGroupParams().setFillingCol(Color.MAGENTA);
		instrument.getGroupParams().setFillingStyle(FillingStyle.GRAD);
		instrument.getGroupParams().setGradColEnd(Color.RED);
		instrument.getGroupParams().setGradColStart(Color.YELLOW);
		instrument.getGroupParams().setHatchingsCol(Color.GRAY);
		instrument.getGroupParams().setLineStyle(LineStyle.DOTTED);
		instrument.getGroupParams().setShadowCol(Color.GREEN);
	}



	public void checkShape(@SuppressWarnings("unused") final IShape shape) {//FIXME
//		assertEquals(instrument.getLineColor(), shape.getLineColour());
//		assertEquals(instrument.getThickness(), shape.getThickness());
//		if(shape.isBordersMovable())
//			assertEquals(instrument.getBorderPosition(), shape.getBordersPosition());
//		if(shape instanceof IDot) {
//			assertEquals(instrument.getDotStyle(), ((IDot)shape).getDotStyle());
//			assertEquals(instrument.getDotSize(), ((IDot)shape).getRadius()*2.);
//		}
//		if(shape.isDbleBorderable()) {
//			assertEquals(instrument.isDoubleBorder(), shape.hasDbleBord());
//			assertEquals(instrument.getDoubleBorderColor(), shape.getDbleBordCol());
//		}
//		if(shape.isFillable()) {
//			assertEquals(instrument.getFillingColor(), shape.getFillingCol());
//
//			if(shape.isInteriorStylable()) {
//				assertEquals(instrument.getFillingStyle(), shape.getFillingStyle());
//				assertEquals(instrument.getGradEndColor(), shape.getGradColEnd());
//				assertEquals(instrument.getGradStartColor(), shape.getGradColStart());
//				assertEquals(instrument.getHatchingsColor(), shape.getHatchingsCol());
//			}
//		}
//
//		if(shape.isLineStylable())
//			assertEquals(instrument.getLineStyle(), shape.getLineStyle());
//		if(shape.isShadowable()) {
//			assertEquals(instrument.getShadowColor(), shape.getShadowCol());
//			assertEquals(instrument.isShadow(), shape.hasShadow());
//		}
	}
}

