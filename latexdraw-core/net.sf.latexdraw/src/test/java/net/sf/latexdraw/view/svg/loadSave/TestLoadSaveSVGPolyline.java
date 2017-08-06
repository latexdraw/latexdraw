package net.sf.latexdraw.view.svg.loadSave;

import java.util.Arrays;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import org.junit.Before;

public class TestLoadSaveSVGPolyline extends TestLoadSaveSVGModifiablePointsShape<IPolyline> {
	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createPolyline(Arrays.asList(ShapeFactory.INST.createPoint(10, 20),
			ShapeFactory.INST.createPoint(30,50),
			ShapeFactory.INST.createPoint(60, 78),
			ShapeFactory.INST.createPoint(-60, -10)));
	}
}
