package test.glib.views.java2D;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.sf.latexdraw.glib.views.Java2D.impl.FlyweightThumbnail;
import net.sf.latexdraw.glib.views.Java2D.impl.LViewsFactory;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK;

import org.junit.Before;
import org.junit.Test;

import test.glib.views.TestAbstractView;


public abstract class TestLShapeView extends TestAbstractView<IViewShape> {
	public Graphics2D g;

	@Before
	public void setUp() {
		FlyweightThumbnail.images().clear();
		FlyweightThumbnail.setThread(false);
		View2DTK.setFactory(new LViewsFactory());

		BufferedImage bufferImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
		g = bufferImage.createGraphics();
		bufferImage.flush();
	}



	@Test
	public abstract void testContains1();


	@Test
	public abstract void testContains2();


	@Test
	public abstract void testUpdateDblePathOutside();


	@Test
	public abstract void testUpdateDblePathInside();


	@Test
	public abstract void testUpdateDblePathMiddle();


	@Test
	public abstract void testUpdateGeneralPathInside();


	@Test
	public abstract void testUpdateGeneralPathMiddle();


	@Test
	public abstract void testUpdateGeneralPathOutside();


	@Test
	public abstract void testIntersects();


	@Test
	public void testPaint() {
		view.paint(g, null);
	}



	@Test
	public void testPaintBorders() {
		view.paintBorders(g);
	}



	@Test
	public void testPaintShadow() {
		view.paintShadow(g);
	}


	@Test
	public void testPaintShowPointsLines() {
		view.paintShowPointsLines(g);
	}


	@Test
	public void testPaintShowPointsDots() {
		view.paintShowPointsDots(g);
	}


	@Test
	public void testPaintFilling() {
		view.paintFilling(g);
	}
}
