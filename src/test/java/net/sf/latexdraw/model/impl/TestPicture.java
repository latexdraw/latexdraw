package net.sf.latexdraw.model.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.sf.latexdraw.data.ParameteriseShapeData;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.Picture;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.PositionShape;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.TempDirectory;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(TempDirectory.class)
public class TestPicture {
	Picture shape;
	Path path;
	Path folder;

	@BeforeEach
	public void setUp(@TempDirectory.TempDir final Path dir) throws IOException {
		WaitForAsyncUtils.waitForFxEvents();
		shape = ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint());
		folder = dir;
		path = ParameteriseShapeData.INST.getTestPNG(folder);
		shape.setPathSource(path.toString());
	}

	@Test
	public void testPathTarget() {
		assertEquals(path.toString().replace(".png", ".eps"), shape.getPathTarget());
	}

	@Test
	public void testDelete() {
		shape.setPathSource(path.toString());
		assertNotNull(shape.getImage());
	}

	@Test
	public void testLoadEPSAlreadyExists() {
		shape.setPathSource(path.toString().replace(".png", ".eps"));
		assertNotNull(shape.getImage());
	}

	@Test
	public void testLoadEPSNotExists() throws IOException {
		path = Paths.get(folder.toString(), "epsPic.eps");
		Files.copy(Paths.get("src/test/resources/epsPic.eps"), path);

		shape.setPathSource(path.toString());
		assertNotNull(shape.getImage());
	}

	@Test
	public void testLoadPDFNotExists() throws IOException {
		path = Paths.get(folder.toString(), "pdfPic.pdf");
		Files.copy(Paths.get("src/test/resources/pdfPic.pdf"), path);

		shape.setPathSource(path.toString());
		assertNotNull(shape.getImage());
	}

	@Test
	public void testLoadPNGCreateEPS() {
		assertTrue(new File(shape.getPathTarget()).exists());
	}

	@Test
	public void testMirroH() {
		shape.setPosition(10d, 20d);
		final Point gc = shape.getGravityCentre();
		shape.mirrorHorizontal(5d);
		assertEquals(gc.horizontalSymmetry(5d), shape.getGravityCentre());
	}

	@Test
	public void testMirroV() {
		shape.setPosition(10d, 20d);
		final Point gc = shape.getGravityCentre();
		shape.mirrorVertical(5d);
		assertEquals(gc.verticalSymmetry(5d), shape.getGravityCentre());
	}

	@Test
	public void testGetFullBottomRightPoint() {
		shape.setPosition(11d, -20d);
		assertEquals(shape.getBottomRightPoint(), shape.getFullBottomRightPoint());
	}

	@Test
	public void testGetFullTopLeftPoint() {
		shape.setPosition(11d, -20d);
		assertEquals(shape.getTopLeftPoint(), shape.getFullTopLeftPoint());
	}

	@Test
	public void testDuplicate() {
		final Picture dup = shape.duplicate();
		assertEquals(shape.getPathSource(), dup.getPathSource());
	}

	@Test
	public void testIsColourable() {
		assertFalse(shape.isColourable());
	}

	@Test
	public void testGetHeight() {
		assertEquals(shape.getImage().getHeight(), shape.getHeight(), 0.001);
	}

	@Test
	public void testGetImage() {
		assertNotNull(shape.getImage());
	}

	@Test
	public void testGetWidth() {
		assertEquals(shape.getImage().getWidth(), shape.getWidth(), 0.001);
	}

	@Test
	public void testGetBottomLeftPoint() {
		final Point pt = shape.getBottomLeftPoint();
		assertEquals(shape.getPosition().getX(), pt.getX(), 0.001);
		assertEquals(shape.getPosition().getY() + shape.getImage().getHeight(), pt.getY(), 0.001);
	}

	@Test
	public void testGetBottomRightPoint() {
		final Point pt = shape.getBottomRightPoint();
		assertEquals(shape.getPosition().getX() + shape.getImage().getWidth(), pt.getX(), 0.001);
		assertEquals(shape.getPosition().getY() + shape.getImage().getHeight(), pt.getY(), 0.001);
	}

	@Test
	public void testGetTopLeftPoint() {
		final Point pt = shape.getTopLeftPoint();
		assertEquals(shape.getPosition().getX(), pt.getX(), 0.001);
		assertEquals(shape.getPosition().getY(), pt.getY(), 0.001);
	}

	@Test
	public void testGetTopRightPoint() {
		final Point pt = shape.getTopRightPoint();
		assertEquals(shape.getPosition().getX() + shape.getImage().getWidth(), pt.getX(), 0.001);
		assertEquals(shape.getPosition().getY(), pt.getY(), 0.001);
	}

	@Test
	public void testMirrorHorizontal() {
		final Point pos = ShapeFactory.INST.createPoint(shape.getPosition());
		shape.mirrorHorizontal(shape.getGravityCentre().getX());
		assertEquals(pos, shape.getPosition());
	}

	@Test
	public void testMirrorVertical() {
		final Point pos = ShapeFactory.INST.createPoint(shape.getPosition());
		shape.mirrorVertical(shape.getGravityCentre().getY());
		assertEquals(pos, shape.getPosition());
	}

	@Test
	public void testCopy() {
		final Picture pic2 = ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint());
		pic2.copy(shape);
		assertEquals(shape.getPathSource(), pic2.getPathSource());
	}

	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(Rectangle.class));
		assertFalse(shape.isTypeOf(Circle.class));
		assertTrue(shape.isTypeOf(Shape.class));
		assertTrue(shape.isTypeOf(PositionShape.class));
		assertTrue(shape.isTypeOf(Picture.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}
}
