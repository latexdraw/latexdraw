package net.sf.latexdraw.models.impl;

import com.sun.javafx.application.PlatformImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.ParameteriseShapeData;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IPicture;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestIPicture implements HelperTest {
	IPicture shape;
	Path path;

	@Rule public TemporaryFolder folder;

	@BeforeClass
	public static void beforeClass() {
		PlatformImpl.startup(() -> {});
	}

	@Before
	public void setUp() throws Exception {
		WaitForAsyncUtils.waitForFxEvents();
		shape = ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint());
		folder = new TemporaryFolder();
		folder.create();
		path = ParameteriseShapeData.INST.getTestPNG(folder);
		shape.setPathSource(path.toString());
	}

	@Test
	public void testPathTarget() {
		assertEquals(path.toString().replace(".png", ".eps"), shape.getPathTarget());
	}

	@Test
	public void testDelete() throws IOException {
		shape.setPathSource(path.toString());
		assertNotNull(shape.getImage());
	}

	@Test
	public void testLoadEPSAlreadyExists() throws IOException {
		shape.setPathSource(path.toString().replace(".png", ".eps"));
		assertNotNull(shape.getImage());
	}

	@Test
	public void testLoadEPSNotExists() throws IOException {
		path = Paths.get(folder.getRoot().toPath().toString(), "epsPic.eps");
		Files.copy(Paths.get("src/test/resources/epsPic.eps"), path);

		shape.setPathSource(path.toString());
		assertNotNull(shape.getImage());
	}

	@Test
	public void testLoadPDFNotExists() throws IOException {
		path = Paths.get(folder.getRoot().toPath().toString(), "pdfPic.pdf");
		Files.copy(Paths.get("src/test/resources/pdfPic.pdf"), path);

		shape.setPathSource(path.toString());
		assertNotNull(shape.getImage());
	}

	@Test
	public void testLoadPNGCreateEPS() {
		assertTrue(new File(shape.getPathTarget()).exists());
	}

	@Test
	public void testDuplicate() {
		final IPicture dup = shape.duplicate();
		assertEquals(shape.getPathSource(), dup.getPathSource());
	}

	@Test
	public void testGetHeight() {
		assertEqualsDouble(shape.getImage().getHeight(), shape.getHeight());
	}

	@Test
	public void testGetImage() {
		assertNotNull(shape.getImage());
	}

	@Test
	public void testGetWidth() {
		assertEqualsDouble(shape.getImage().getWidth(), shape.getWidth());
	}

	@Test
	public void testGetBottomLeftPoint() {
		final IPoint pt = shape.getBottomLeftPoint();
		assertEqualsDouble(shape.getPosition().getX(), pt.getX());
		assertEqualsDouble(shape.getPosition().getY() + shape.getImage().getHeight(), pt.getY());
	}

	@Test
	public void testGetBottomRightPoint() {
		final IPoint pt = shape.getBottomRightPoint();
		assertEqualsDouble(shape.getPosition().getX() + shape.getImage().getWidth(), pt.getX());
		assertEqualsDouble(shape.getPosition().getY() + shape.getImage().getHeight(), pt.getY());
	}

	@Test
	public void testGetTopLeftPoint() {
		final IPoint pt = shape.getTopLeftPoint();
		assertEqualsDouble(shape.getPosition().getX(), pt.getX());
		assertEqualsDouble(shape.getPosition().getY(), pt.getY());
	}

	@Test
	public void testGetTopRightPoint() {
		final IPoint pt = shape.getTopRightPoint();
		assertEqualsDouble(shape.getPosition().getX() + shape.getImage().getWidth(), pt.getX());
		assertEqualsDouble(shape.getPosition().getY(), pt.getY());
	}

	@Test
	public void testMirrorHorizontal() {
		final IPoint pos = ShapeFactory.INST.createPoint(shape.getPosition());
		shape.mirrorHorizontal(shape.getGravityCentre().getX());
		assertEquals(pos, shape.getPosition());
	}

	@Test
	public void testMirrorVertical() {
		final IPoint pos = ShapeFactory.INST.createPoint(shape.getPosition());
		shape.mirrorVertical(shape.getGravityCentre().getY());
		assertEquals(pos, shape.getPosition());
	}

	@Test
	public void testCopy() {
		final IPicture pic2 = ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint());
		pic2.copy(shape);
		assertEquals(shape.getPathSource(), pic2.getPathSource());
	}

	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IPicture.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}
}
