package net.sf.latexdraw.glib.models.impl;

import java.awt.Color;
import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.ILine;
import net.sf.latexdraw.glib.models.interfaces.ILineArcShape;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import fr.eseo.malai.mapping.ActiveArrayList;

/**
 * A Group is a group of IShape.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 02/14/2008<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
class LGroup extends LShape implements IGroup {
	/** The set of shapes. */
	protected List<IShape> shapes;


	/**
	 * Creates a group of shapes.
	 * @param uniqueID True: the model will have a unique ID.
	 */
	protected LGroup(final boolean uniqueID) {
		super(uniqueID);

		shapes = new ActiveArrayList<IShape>();
	}


	@Override
	public void addShape(final IShape sh) {
		if(sh!=null)
			shapes.add(sh);
	}


	@Override
	public void addShape(final IShape s, final int index) {
		if(s!=null && index<=shapes.size() && (index==-1 || index>=0))
			if(index==-1 || index==shapes.size())
				shapes.add(s);
			else
				shapes.add(index, s);
	}


	@Override
	public void clear() {
		shapes.clear();
	}


	@Override
	public boolean contains(final IShape sh) {
		return sh==null ? false : shapes.contains(sh);
	}



	@Override
	public void setThickness(final double thickness) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(thickness))
			for(final IShape shape : shapes)
				shape.setThickness(thickness);
	}


	/**
	 * @return The thickness of the first thicknessable shape. Otherwise NaN is returned.
	 */
	@Override
	public double getThickness() {
		double th = Double.NaN;

		for(int i=0, size=shapes.size(); i<size && Double.isNaN(th); i++)
			if(shapes.get(i).isThicknessable())
				th = shapes.get(i).getThickness();

		return th;
	}


	@Override
	public boolean isThicknessable() {
		boolean th = false;

		for(int i=0, size=shapes.size(); i<size && !th; i++)
			th = shapes.get(i).isThicknessable();

		return th;
	}


	@Override
	public Color getLineColour() {
		return isEmpty() ? null : shapes.get(0).getLineColour();
	}


	@Override
	public boolean isLineStylable() {
		boolean stylable = false;

		for(int i=0, size=shapes.size(); i<size && !stylable; i++)
			stylable = shapes.get(i).isLineStylable();

		return stylable;
	}


	@Override
	public LineStyle getLineStyle() {
		LineStyle style = null;

		for(int i=0, size=shapes.size(); i<size && style==null; i++)
			if(shapes.get(i).isLineStylable())
				style = shapes.get(i).getLineStyle();

		return style;
	}


	@Override
	public void setLineStyle(final LineStyle style) {
		if(style!=null)
			for(final IShape shape : shapes)
				if(shape.isLineStylable())
					shape.setLineStyle(style);
	}



	@Override
	public boolean isBordersMovable() {
		boolean moveable = false;

		for(int i=0, size=shapes.size(); i<size && !moveable; i++)
			moveable = shapes.get(i).isBordersMovable();

		return moveable;
	}


	@Override
	public BorderPos getBordersPosition() {
		BorderPos pos = null;

		for(int i=0, size=shapes.size(); i<size && pos==null; i++)
			if(shapes.get(i).isBordersMovable())
				pos = shapes.get(i).getBordersPosition();

		return pos;
	}



	@Override
	public void setBordersPosition(final BorderPos position) {
		if(position!=null)
			for(final IShape shape : shapes)
				if(shape.isBordersMovable())
					shape.setBordersPosition(position);
	}


	/**
	 * @return The line arc of the first shape that supports rounded corners. Otherwise
	 * NaN is returned.
	 */
	@Override
	public double getLineArc() {
		double value = Double.NaN;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && Double.isNaN(value); i++) {
			sh = shapes.get(i);
			if(sh instanceof ILineArcShape && ((ILineArcShape)sh).isRoundCorner())
				value = ((ILineArcShape)sh).getLineArc();
		}

		return value;
	}


	@Override
	public void setLineArc(final double lineArc) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(lineArc))
			for(final IShape shape : shapes)
				if(shape instanceof ILineArcShape)
					((ILineArcShape)shape).setLineArc(lineArc);
	}



	@Override
	public boolean containsRoundables() {
		boolean roundable = false;

		for(int i=0, size=shapes.size(); i<size && !roundable; i++)
			roundable = shapes.get(i) instanceof ILineArcShape;

		return roundable;
	}


	/**
	 * @return True if one of the shape of the group has rounded corners.
	 */
	@Override
	public boolean isRoundCorner() {
		boolean round = false;

		for(int i=0, size=shapes.size(); i<size && !round; i++)
			round = shapes.get(i) instanceof ILineArcShape && ((ILineArcShape)shapes.get(i)).isRoundCorner();

		return round;
	}


	@Override
	public void setRoundCorner(final boolean isCornerRound) {
		for(final IShape shape : shapes)
			if(shape instanceof ILineArcShape)
				((ILineArcShape)shape).setRoundCorner(isCornerRound);
	}


	@Override
	public void setLineColour(final Color lineColour) {
		if(lineColour!=null)
			for(final IShape shape : shapes)
				shape.setLineColour(lineColour);
	}


	@Override
	public void setDbleBordCol(final Color colour) {
		if(colour!=null)
			for(final IShape shape : shapes)
				if(shape.isDbleBorderable())
					shape.setDbleBordCol(colour);
	}


	@Override
	public Color getDbleBordCol() {
		Color color = null;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && color==null; i++) {
			sh = shapes.get(i);
			if(sh.isDbleBorderable() && sh.hasDbleBord())
				color = shapes.get(i).getDbleBordCol();
		}

		return color;
	}


	@Override
	public boolean hasDbleBord() {
		boolean dble = false;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && !dble; i++) {
			sh   = shapes.get(i);
			dble = sh.isDbleBorderable() && sh.hasDbleBord();
		}

		return dble;
	}


	@Override
	public void setHasDbleBord(final boolean dbleBorders) {
		for(final IShape shape : shapes)
			if(shape.isDbleBorderable())
				shape.setHasDbleBord(dbleBorders);
	}


	@Override
	public boolean isDbleBorderable() {
		boolean dbleable = false;

		for(int i=0, size=shapes.size(); i<size && !dbleable; i++)
			dbleable = shapes.get(i).isDbleBorderable();

		return dbleable;
	}


	@Override
	public void setDbleBordSep(final double dbleBorderSep) {
		for(final IShape shape : shapes)
			if(shape.isDbleBorderable())
				shape.setDbleBordSep(dbleBorderSep);
	}


	@Override
	public double getDbleBordSep() {
		double dbleSep = Double.NaN;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && Double.isNaN(dbleSep); i++) {
			sh = shapes.get(i);
			if(sh.isDbleBorderable() && sh.hasDbleBord())
				dbleSep = sh.getDbleBordSep();
		}

		return dbleSep;
	}


	@Override
	public boolean isShadowable() {
		boolean shadowable = false;

		for(int i=0, size=shapes.size(); i<size && !shadowable; i++)
			shadowable = shapes.get(i).isShadowable();

		return shadowable;
	}


	@Override
	public boolean hasShadow() {
		boolean shadow = false;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && !shadow; i++) {
			sh   = shapes.get(i);
			shadow = sh.isShadowable() && sh.hasShadow();
		}

		return shadow;
	}



	@Override
	public void setHasShadow(final boolean shadow) {
		for(final IShape shape : shapes)
			if(shape.isShadowable())
				shape.setHasShadow(shadow);
	}


	@Override
	public void setShadowSize(final double shadSize) {
		for(final IShape shape : shapes)
			if(shape.isShadowable())
				shape.setShadowSize(shadSize);
	}


	@Override
	public double getShadowSize() {
		double shadSize = Double.NaN;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && Double.isNaN(shadSize); i++) {
			sh = shapes.get(i);
			if(sh.isShadowable() && sh.hasShadow())
				shadSize = sh.getShadowSize();
		}

		return shadSize;
	}


	@Override
	public void setShadowAngle(final double shadAngle) {
		for(final IShape shape : shapes)
			if(shape.isShadowable())
				shape.setShadowAngle(shadAngle);
	}


	@Override
	public double getShadowAngle() {
		double shadAngle = Double.NaN;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && Double.isNaN(shadAngle); i++) {
			sh = shapes.get(i);
			if(sh.isShadowable() && sh.hasShadow())
				shadAngle = sh.getShadowAngle();
		}

		return shadAngle;
	}


	@Override
	public void setShadowCol(final Color colour) {
		if(colour!=null)
			for(final IShape shape : shapes)
				if(shape.isShadowable())
					shape.setShadowCol(colour);
	}


	@Override
	public Color getShadowCol() {
		Color color = null;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && color==null; i++) {
			sh = shapes.get(i);
			if(sh.isShadowable() && sh.hasShadow())
				color = sh.getShadowCol();
		}

		return color;
	}


	@Override
	public FillingStyle getFillingStyle() {
		FillingStyle style = null;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && style==null; i++) {
			sh = shapes.get(i);
			if(sh.isInteriorStylable())
				style = sh.getFillingStyle();
		}

		return style;
	}


	@Override
	public void setFillingStyle(final FillingStyle style) {
		if(style!=null)
			for(final IShape shape : shapes)
				if(shape.isInteriorStylable())
					shape.setFillingStyle(style);
	}


	@Override
	public boolean isFillable() {
		boolean fillable = false;

		for(int i=0, size=shapes.size(); i<size && !fillable; i++)
			fillable = shapes.get(i).isFillable();

		return fillable;
	}


	@Override
	public boolean isInteriorStylable() {
		boolean interiorStylable = false;

		for(int i=0, size=shapes.size(); i<size && !interiorStylable; i++)
			interiorStylable = shapes.get(i).isInteriorStylable();

		return interiorStylable;
	}


	@Override
	public void setFillingCol(final Color colour) {
		if(colour!=null)
			for(final IShape shape : shapes)
				if(shape.isFillable())
					shape.setFillingCol(colour);
	}


	@Override
	public Color getFillingCol() {
		Color color = null;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && color==null; i++) {
			sh = shapes.get(i);
			if(sh.isFillable() && sh.isFilled())
				color = sh.getFillingCol();
		}

		return color;
	}


	@Override
	public void setHatchingsCol(final Color colour) {
		if(colour!=null)
			for(final IShape shape : shapes)
				if(shape.isInteriorStylable())
					shape.setHatchingsCol(colour);
	}


	@Override
	public Color getHatchingsCol() {
		Color color = null;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && color==null; i++) {
			sh = shapes.get(i);
			if(sh.isInteriorStylable() && sh.getFillingStyle().isHatchings())
				color = sh.getHatchingsCol();
		}

		return color;
	}


	@Override
	public void setGradColStart(final Color colour) {
		if(colour!=null)
			for(final IShape shape : shapes)
				if(shape.isInteriorStylable())
					shape.setGradColStart(colour);
	}


	@Override
	public Color getGradColStart() {
		Color color = null;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && color==null; i++) {
			sh = shapes.get(i);
			if(sh.isInteriorStylable() && sh.getFillingStyle().isGradient())
				color = sh.getGradColStart();
		}

		return color;
	}


	@Override
	public void setGradColEnd(final Color colour) {
		if(colour!=null)
			for(final IShape shape : shapes)
				if(shape.isInteriorStylable())
					shape.setGradColEnd(colour);
	}


	@Override
	public Color getGradColEnd() {
		Color color = null;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && color==null; i++) {
			sh = shapes.get(i);
			if(sh.isInteriorStylable() && sh.getFillingStyle().isGradient())
				color = sh.getGradColEnd();
		}

		return color;
	}


	@Override
	public void setGradAngle(final double gradAngle) {
		for(final IShape shape : shapes)
			if(shape.isInteriorStylable())
				shape.setGradAngle(gradAngle);
	}


	@Override
	public double getGradAngle() {
		double gradientAngle = Double.NaN;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && Double.isNaN(gradientAngle); i++) {
			sh = shapes.get(i);
			if(sh.isInteriorStylable() && sh.getFillingStyle().isGradient())
				gradientAngle = sh.getGradAngle();
		}

		return gradientAngle;
	}


	@Override
	public void setGradMidPt(final double gradMidPoint) {
		for(final IShape shape : shapes)
			if(shape.isInteriorStylable())
				shape.setGradMidPt(gradMidPoint);
	}


	@Override
	public double getGradMidPt() {
		double gradientMidPt = Double.NaN;
		IShape sh;

		for(int i=0, size=shapes.size(); i<size && Double.isNaN(gradientMidPt); i++) {
			sh = shapes.get(i);
			if(sh.isInteriorStylable() && sh.getFillingStyle().isGradient())
				gradientMidPt = sh.getGradMidPt();
		}

		return gradientMidPt;
	}


	@Override
	public IShape getShapeAt(final int i) {
		int size = shapes.size();
		return size==0 || i<-1 || i>= size ? null : i==-1 ? shapes.get(size-1) : shapes.get(i);
	}


	@Override
	public List<IShape> getShapes() {
		return shapes;
	}


	@Override
	public boolean isEmpty() {
		return shapes.isEmpty();
	}


	@Override
	public boolean removeShape(final IShape sh) {
		return sh==null ? false : shapes.remove(sh);
	}


	@Override
	public IShape removeShape(final int i) {
		return shapes.isEmpty() || i<-1 || i>=shapes.size() ? null : i==-1 ? shapes.remove(shapes.size()-1) : shapes.remove(i);
	}


	@Override
	public int size() {
		return shapes.size();
	}


	/**
	 * Duplicates the group. Does not duplicate the shapes it contains.
	 */
	@Override
	public IGroup duplicate() {
		return duplicate(false);
	}


	@Override
	public IGroup duplicate(final boolean duplicateShapes) {
		IGroup dup = new LGroup(true);

		if(duplicateShapes)
			for(final IShape sh : shapes)
				dup.addShape(sh.duplicate());
		else
			for(final IShape sh : shapes)
				dup.addShape(sh);

		dup.update();

		return dup;
	}


	@Override
	public boolean isArrowable() {
		boolean arrowable = false;

		for(int i=0, size=shapes.size(); i<size && !arrowable; i++)
			arrowable = shapes.get(i).isArrowable();

		return arrowable;
	}


	@Override
	public IArrow getArrowAt(final int position) {
		IArrow arrow = null;

		for(int i=0, size=shapes.size(); i<size && arrow==null; i++)
			if(shapes.get(i).isArrowable())
				arrow = shapes.get(i).getArrowAt(position);

		return arrow;
	}


	@Override
	public List<IArrow> getArrows() {
		List<IArrow> shapeArrows = null;

		for(int i=0, size=shapes.size(); i<size && shapeArrows==null; i++)
			if(shapes.get(i).isArrowable())
				shapeArrows = shapes.get(i).getArrows();

		return shapeArrows;
	}


	@Override
	public ILine getArrowLine(final IArrow arrow) {
		ILine arrowLine = null;

		for(int i=0, size=shapes.size(); i<size && arrowLine==null; i++)
			if(shapes.get(i).isArrowable())
				arrowLine = shapes.get(i).getArrowLine(arrow);

		return arrowLine;
	}


	@Override
	public void setDotSizeDim(final double dotSizeDim) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setDotSizeDim(dotSizeDim);
	}

	@Override
	public void setDotSizeNum(final double dotSizeNum) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setDotSizeNum(dotSizeNum);
	}

	@Override
	public void setTBarSizeNum(final double tbarSizeNum) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setTBarSizeNum(tbarSizeNum);
	}

	@Override
	public void setTBarSizeDim(final double tbarSizeDim) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setTBarSizeDim(tbarSizeDim);
	}

	@Override
	public double getTBarSizeDim() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getTBarSizeDim();

		return Double.NaN;
	}

	@Override
	public double getTBarSizeNum() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getTBarSizeNum();

		return Double.NaN;
	}

	@Override
	public void setRBracketNum(final double rBracketNum) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setRBracketNum(rBracketNum);
	}

	@Override
	public void setBracketNum(final double bracketNum) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setBracketNum(bracketNum);
	}

	@Override
	public void setArrowLength(final double lgth) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setArrowLength(lgth);
	}

	@Override
	public void setArrowSizeDim(final double arrowSizeDim) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setArrowSizeDim(arrowSizeDim);
	}

	@Override
	public void setArrowSizeNum(final double arrowSizeNum) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setArrowSizeNum(arrowSizeNum);
	}

	@Override
	public void setArrowInset(final double inset) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setArrowInset(inset);
	}

	@Override
	public double getDotSizeDim() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getDotSizeDim();

		return Double.NaN;
	}

	@Override
	public double getDotSizeNum() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getDotSizeNum();

		return Double.NaN;
	}

	@Override
	public double getBracketNum() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getBracketNum();

		return Double.NaN;
	}

	@Override
	public double getArrowSizeNum() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getArrowSizeNum();

		return Double.NaN;
	}

	@Override
	public double getArrowSizeDim() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getArrowSizeNum();

		return Double.NaN;
	}

	@Override
	public double getArrowInset() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getArrowSizeNum();

		return Double.NaN;
	}

	@Override
	public double getArrowLength() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getArrowLength();

		return Double.NaN;
	}

	@Override
	public double getRBracketNum() {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				return sh.getRBracketNum();

		return Double.NaN;
	}


	@Override
	public boolean isShowPtsable() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void translate(final double tx, final double ty) {
		for(int i=0, size=shapes.size(); i<size; i++)
			shapes.get(i).translate(tx, ty);
	}


	@Override
	public TextPosition getTextPosition() {
		for(final IShape sh : shapes)
			if(sh instanceof IText)
				return ((IText)sh).getTextPosition();
		return null;
	}


	@Override
	public void setTextPosition(final TextPosition textPosition) {
		if(textPosition!=null)
			for(final IShape sh : shapes)
				if(sh instanceof IText)
					((IText)sh).setTextPosition(textPosition);
	}


	@Override
	public String getText() {
		for(final IShape sh : shapes)
			if(sh instanceof IText)
				return ((IText)sh).getText();

		return null;
	}


	@Override
	public void setText(final String text) {
		if(text!=null && text.length()>0)
			for(final IShape sh : shapes)
				if(sh instanceof IText)
					((IText)sh).setText(text);
	}


	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public IPoint getPosition() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setPosition(final IPoint pt) {
		// TODO Auto-generated method stub
	}


	@Override
	public void setPosition(final double x, final double y) {
		// TODO Auto-generated method stub
	}


	@Override
	public void setX(final double x) {
		// TODO Auto-generated method stub
	}


	@Override
	public void setY(final double y) {
		// TODO Auto-generated method stub
	}


	@Override
	public boolean containsTexts() {
		for(final IShape sh : shapes)
			if(sh instanceof IText)
				return true;

		return false;
	}


	@Override
	public void addToRotationAngle(final IPoint gravCentre, final double angle) {
		final IPoint gc = gravCentre==null ? gravityCentre : gravCentre;

		for(final IShape sh : shapes)
			sh.addToRotationAngle(gc, angle);
	}


	@Override
	public void setRotationAngle(final double rotationAngle) {
		for(final IShape sh : shapes)
			sh.setRotationAngle(rotationAngle);
	}


	@Override
	public void rotate(final IPoint point, final double angle) {
		for(final IShape sh : shapes)
			sh.rotate(point, angle);
	}


	@Override
	public double getRotationAngle() {
		return shapes.isEmpty() ? 0. : shapes.get(0).getRotationAngle();
	}


	@Override
	public void updateGravityCentre() {
		switch(shapes.size()) {
			case 0:
				gravityCentre.setPoint(0., 0.);
				break;
			case 1:
				gravityCentre.setPoint(shapes.get(0).getGravityCentre());
				break;
			default:
				final IPoint tl = getTopLeftPoint();
				final IPoint br = getBottomRightPoint();
				gravityCentre.setPoint((tl.getX()+br.getX())/2., (tl.getY()+br.getY())/2.);
				break;
		}
	}


	@Override
	public IPoint getBottomRightPoint() {
		double x;
		double y;

		if(shapes.isEmpty()) {
			x = Double.NaN;
			y = Double.NaN;
		} else {
			x = Double.MIN_VALUE;
			y = Double.MIN_VALUE;
			IPoint br;

			for(final IShape sh : shapes) {
				br = sh.getBottomRightPoint();
				if(br.getX()>x)
					x = br.getX();
				if(br.getY()>y)
					y = br.getY();
			}
		}

		return new LPoint(x, y);
	}


	@Override
	public IPoint getBottomLeftPoint() {
		double x;
		double y;

		if(shapes.isEmpty()) {
			x = Double.NaN;
			y = Double.NaN;
		} else {
			x = Double.MAX_VALUE;
			y = Double.MIN_VALUE;
			IPoint bl;

			for(final IShape sh : shapes) {
				bl = sh.getBottomLeftPoint();
				if(bl.getX()<x)
					x = bl.getX();
				if(bl.getY()>y)
					y = bl.getY();
			}
		}

		return new LPoint(x, y);
	}


	@Override
	public IPoint getTopLeftPoint() {
		double x;
		double y;

		if(shapes.isEmpty()) {
			x = Double.NaN;
			y = Double.NaN;
		} else {
			x = Double.MAX_VALUE;
			y = Double.MAX_VALUE;
			IPoint tl;

			for(final IShape sh : shapes) {
				tl = sh.getTopLeftPoint();
				if(tl.getX()<x)
					x = tl.getX();
				if(tl.getY()<y)
					y = tl.getY();
			}
		}

		return new LPoint(x, y);
	}


	@Override
	public IPoint getTopRightPoint() {
		double x;
		double y;

		if(shapes.isEmpty()) {
			x = Double.NaN;
			y = Double.NaN;
		} else {
			x = Double.MIN_VALUE;
			y = Double.MAX_VALUE;
			IPoint tr;

			for(final IShape sh : shapes) {
				tr = sh.getTopRightPoint();
				if(tr.getX()>x)
					x = tr.getX();
				if(tr.getY()<y)
					y = tr.getY();
			}
		}

		return new LPoint(x, y);
	}


	@Override
	public boolean hasHatchings() {
		boolean has = false;

		for(int i=0, size=shapes.size(); i<size && !has; i++)
			has = shapes.get(i).hasHatchings();

		return has;
	}


	@Override
	public boolean hasGradient() {
		boolean has = false;

		for(int i=0, size=shapes.size(); i<size && !has; i++)
			has = shapes.get(i).hasGradient();

		return has;
	}


	@Override
	public void setModified(final boolean modified) {
		for(final IShape shape : shapes)
			shape.setModified(modified);

		super.setModified(modified);
	}
	
	@Override
	public void setArrowStyle(final ArrowStyle style, final int position) {
		for(final IShape sh : shapes)
			if(sh.isArrowable())
				sh.setArrowStyle(style, position);
	}

	@Override
	public ArrowStyle getArrowStyle(final int position) {
		ArrowStyle style=null;

		for(int i=0, size=shapes.size(); i<size && style==null; i++)
			if(shapes.get(i).isArrowable())
				style = shapes.get(i).getArrowStyle(position);
			
		return style;
	}
}
