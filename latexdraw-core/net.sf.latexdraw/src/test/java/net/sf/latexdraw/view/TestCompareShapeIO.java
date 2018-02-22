package net.sf.latexdraw.view;

import net.sf.latexdraw.models.interfaces.shape.IShape;

public abstract class TestCompareShapeIO<T extends IShape> {
	protected T srcShape;
	protected T outShape;

	protected abstract T produceOutputShapeFrom();
}
