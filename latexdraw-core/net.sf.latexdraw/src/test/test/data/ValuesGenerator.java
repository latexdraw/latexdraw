package test.data;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;

public interface ValuesGenerator {
	double[] doubleValues = {-0.00001, -1.34, -83.12, 0d, 0.00001, 1.34, 83.12};
	int[] intValues = {-12, -5, -1, 0, 1, 6, 13};
	float[] floatValues = {-10.23f, -0.001f, 0f, 0.00001f, 1.34f, 83.12f};

	IRectangle[] rectangles = {ShapeFactory.INST.createRectangle(),
//		ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(20d, 26d), ShapeFactory.INST.createPoint(30d, 35d)),
//		ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(-30d, -35d), ShapeFactory.INST.createPoint(20d, 26d)),
//		ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(-30d, -35d), ShapeFactory.INST.createPoint(-20d, -26d)),
		ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(0d, 0d), ShapeFactory.INST.createPoint(20d, 26d))};
}
