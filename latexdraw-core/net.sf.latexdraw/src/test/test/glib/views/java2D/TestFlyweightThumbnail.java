package test.glib.views.java2D;

import java.awt.Image;

import junit.framework.TestCase;
import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.glib.views.Java2D.impl.FlyweightThumbnail;
import net.sf.latexdraw.glib.views.Java2D.impl.LViewsFactory;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewText;
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK;

import org.junit.Before;
import org.junit.Test;
import org.malai.undo.UndoCollector;

import scala.Option;
import scala.Tuple4;
import scala.collection.mutable.Set;

public class TestFlyweightThumbnail extends TestCase {
	protected IViewText viewTxt;

	@Before
	@Override
	public void setUp() {
		FlyweightThumbnail.images().clear();
		DrawingTK.setFactory(new LShapeFactory());
		View2DTK.setFactory(new LViewsFactory());
		UndoCollector.INSTANCE.setSizeMax(0);
		viewTxt = (IViewText)View2DTK.getFactory().createView(DrawingTK.getFactory().createText(false, DrawingTK.getFactory().createPoint(), "coucou"));
	}


	@Test
	public void testNewTextNewPicture() {
		Image img = viewTxt.getImage();
		assertNotNull(img);
		assertEquals(1, FlyweightThumbnail.images().size());
		Option<Tuple4<Image,Set<IText>,String,String>> optTuple = FlyweightThumbnail.images().get(((IText)viewTxt.getShape()).getText());
		assertTrue(optTuple.isDefined());
		assertEquals(img, optTuple.get()._1());
		assertEquals(1, optTuple.get()._2().size());
		assertEquals(viewTxt.getShape(), optTuple.get()._2().last());
	}



	@Test
	public void testNewTwoSameTextsNewSinglePicture() {
		@SuppressWarnings("unused")
		IViewText v2 = (IViewText)View2DTK.getFactory().createView(DrawingTK.getFactory().createText(false, DrawingTK.getFactory().createPoint(), "coucou"));
		assertEquals(1, FlyweightThumbnail.images().size());
		Option<Tuple4<Image,Set<IText>,String,String>> optTuple = FlyweightThumbnail.images().get(((IText)viewTxt.getShape()).getText());
		assertTrue(optTuple.isDefined());
		assertEquals(2, optTuple.get()._2().size());
	}



	@Test
	public void testNewTwoSameTextsRemoveOneStillPicture() {
		IViewText v2 = (IViewText)View2DTK.getFactory().createView(DrawingTK.getFactory().createText(false, DrawingTK.getFactory().createPoint(), "coucou"));
		FlyweightThumbnail.notifyImageFlushed((IText)viewTxt.getShape(), ((IText)viewTxt.getShape()).getText());
		assertEquals(1, FlyweightThumbnail.images().size());
		Option<Tuple4<Image,Set<IText>,String,String>> optTuple = FlyweightThumbnail.images().get(((IText)v2.getShape()).getText());
		assertTrue(optTuple.isDefined());
		assertEquals(1, optTuple.get()._2().size());
		assertEquals(v2.getShape(), optTuple.get()._2().last());
	}


	@Test
	public void testNewTwoTextsRemoveOneOnePictureRemains() {
		IViewText v2 = (IViewText)View2DTK.getFactory().createView(DrawingTK.getFactory().createText(false, DrawingTK.getFactory().createPoint(), "aaa"));
		assertEquals(2, FlyweightThumbnail.images().size());
		FlyweightThumbnail.notifyImageFlushed((IText)viewTxt.getShape(), ((IText)viewTxt.getShape()).getText());
		assertEquals(1, FlyweightThumbnail.images().size());
		Option<Tuple4<Image,Set<IText>,String,String>> optTuple = FlyweightThumbnail.images().get(((IText)v2.getShape()).getText());
		assertTrue(optTuple.isDefined());
		assertEquals(1, optTuple.get()._2().size());
		assertEquals(v2.getShape(), optTuple.get()._2().last());
	}
}
