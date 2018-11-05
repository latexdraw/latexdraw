package net.sf.latexdraw.view.jfx;

import java.util.Arrays;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.FreeHandStyle;
import net.sf.latexdraw.model.api.shape.Freehand;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class TestViewFreeHand extends TestViewBorderedShape<ViewFreeHand, Freehand, Path> {
	@BeforeAll
	public static void beforeClass() {
		try {
			Platform.startup(() -> {
			});
		}catch(final IllegalStateException ex) {
			// Ok
		}
	}

	List<PathElement> before;

	@BeforeEach
	void setUpFreeHand() {
		before = duplicatePath(border.getElements());
	}

	@Override
	protected Freehand createModel() {
		return ShapeFactory.INST.createFreeHand(Arrays.asList(
			ShapeFactory.INST.createPoint(9, 23),
			ShapeFactory.INST.createPoint(21, 11),
			ShapeFactory.INST.createPoint(45, 3),
			ShapeFactory.INST.createPoint(87, 125),
			ShapeFactory.INST.createPoint(187, 25),
			ShapeFactory.INST.createPoint(287, 425),
			ShapeFactory.INST.createPoint(387, 325),
			ShapeFactory.INST.createPoint(19, 233),
			ShapeFactory.INST.createPoint(121, 114),
			ShapeFactory.INST.createPoint(445, 33)));
	}

	@Test
	void testChangeInterval() {
		model.setInterval(model.getInterval() * 2);
		assertNotEquals(border.getElements(), before);
	}

	@Test
	void testChangeType() {
		model.setType(model.getType() == FreeHandStyle.CURVES ? FreeHandStyle.LINES : FreeHandStyle.CURVES);
		assertNotEquals(border.getElements(), before);
	}

	@Test
	void testChangeOpen() {
		model.setOpened(!model.isOpened());
		assertNotEquals(border.getElements(), before);
	}

	@Override
	@Test
	void testShadowPositionSameThanBorder() {
		assertEquals(border.getElements(), view.getShadow().orElseThrow().getElements());
	}

	@Override
	@Test
	public void testOnTranslateX() {
		model.translate(11d, 0d);
		assertPathSameButNotEqual(before, border.getElements());
	}

	@Override
	@Test
	public void testOnTranslateY() {
		model.translate(0d, 11d);
		assertPathSameButNotEqual(before, border.getElements());
	}
}
