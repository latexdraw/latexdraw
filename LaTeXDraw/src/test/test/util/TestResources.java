package test.util;

import java.net.URL;

import javax.swing.ImageIcon;

import junit.framework.TestCase;

import org.junit.Test;

public class TestResources extends TestCase {
	@Test
	public void testRessources() {
		assertNotNull(loadImageIcon("main/res/LaTeXDrawIcon.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/LaTeXDrawSmall.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/emblem-important.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/help-browser.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/comment.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/preferences-desktop-theme.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/preferences-system.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/New.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/hatch/hatch.none.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/hatch/hatch.vert.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/hatch/hatch.horiz.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/hatch/hatch.cross.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.arc.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.arc.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.arc.r.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.arc.r.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.arrow.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.arrow.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.barEnd.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.barEnd.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.barIn.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.barIn.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.bracket.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.bracket.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.bracket.r.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.bracket.r.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.circle.end.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.circle.end.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.circle.in.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.circle.in.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.dbleArrow.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.dbleArrow.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.disk.end.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.disk.end.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.disk.in.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.disk.in.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.none.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.none.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.rarrow.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.rarrow.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.rdbleArrow.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.rdbleArrow.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.roundIn.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/arrowStyles/line.roundIn.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/dotStyles/dot.asterisk.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/dotStyles/dot.none.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/dotStyles/dot.bar.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/dotStyles/dot.o.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/dotStyles/dot.cross.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/dotStyles/dot.diamond.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/dotStyles/dot.diamondF.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/dotStyles/dot.ocross.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/dotStyles/dot.oplus.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/dotStyles/dot.pentagon.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/dotStyles/dot.pentagonF.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/dotStyles/dot.plus.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/dotStyles/dot.square.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/dotStyles/dot.squareF.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/dotStyles/dot.triangle.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/dotStyles/dot.triangleF.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/lineStyles/lineStyle.none.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/lineStyles/lineStyle.dashed.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/lineStyles/lineStyle.dotted.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/doubleBoundary/double.boundary.middle.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/doubleBoundary/double.boundary.into.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/doubleBoundary/double.boundary.out.png"));//$NON-NLS-1$

		assertNotNull(loadImageIcon("main/res/grid.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/Copy.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/Paste.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/Cut.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/Draw.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/text.png"));		//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/polygon.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/rotation.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/InFrontSelect.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/behindSelect.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/background.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/foreground.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/Undo.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/Redo.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/Magnify.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/wedge.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/Arc.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/document-open.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/document-save.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/document-save-as.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/circle.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/del.png"));		//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/dot.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/ellipse.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/empty.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/rectangle.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/rhombus.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/select.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/square.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/triangle.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/Print.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/Object.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/AutoBorders.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/go-next.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/join.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/separate.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/bezierCurve.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/chord.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/CopySel.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/go-previous.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/joinedLines.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/emblem-unreadable.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/stop.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/image-x-generic.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/thickness.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/hatch/gradient.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/displayGrid.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/rotation180.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/rotation90.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/rotation270.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/mirrorH.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/mirrorV.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/axes.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/texEditor.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/delimitor.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/freehand/curve.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/freehand/line.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/BezierCurves/closeCurve.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/BezierCurves/closeLine.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("main/res/closedBezier.png")); //$NON-NLS-1$
	}


	public ImageIcon loadImageIcon(String path) {
		URL url;

		try
		{
			url = getClass().getResource(path);

			if(url==null)
				return null;

			return new ImageIcon(url);

		}catch(Exception e) { return null; }
	}
}

