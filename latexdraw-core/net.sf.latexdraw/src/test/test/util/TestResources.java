package test.util;

import java.net.URL;

import javax.swing.ImageIcon;

import junit.framework.TestCase;

import org.junit.Test;

public class TestResources extends TestCase {
	@Test
	public void testRessources() {
		assertNotNull(loadImageIcon("/res/LaTeXDrawIcon.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/LaTeXDrawSmall.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/emblem-important.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/help-browser.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/comment.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/preferences-desktop-theme.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/preferences-system.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/New.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/hatch/hatch.none.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/hatch/hatch.vert.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/hatch/hatch.horiz.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/hatch/hatch.cross.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.arc.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.arc.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.arc.r.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.arc.r.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.arrow.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.arrow.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.barEnd.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.barEnd.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.barIn.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.barIn.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.bracket.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.bracket.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.bracket.r.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.bracket.r.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.circle.end.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.circle.end.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.circle.in.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.circle.in.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.dbleArrow.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.dbleArrow.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.disk.end.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.disk.end.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.disk.in.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.disk.in.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.none.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.none.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.rarrow.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.rarrow.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.rdbleArrow.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.rdbleArrow.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.roundIn.left.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/arrowStyles/line.roundIn.right.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/dotStyles/dot.asterisk.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/dotStyles/dot.none.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/dotStyles/dot.bar.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/dotStyles/dot.o.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/dotStyles/dot.cross.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/dotStyles/dot.diamond.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/dotStyles/dot.diamondF.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/dotStyles/dot.ocross.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/dotStyles/dot.oplus.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/dotStyles/dot.pentagon.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/dotStyles/dot.pentagonF.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/dotStyles/dot.plus.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/dotStyles/dot.square.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/dotStyles/dot.squareF.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/dotStyles/dot.triangle.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/dotStyles/dot.triangleF.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/lineStyles/lineStyle.none.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/lineStyles/lineStyle.dashed.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/lineStyles/lineStyle.dotted.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/doubleBoundary/double.boundary.middle.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/doubleBoundary/double.boundary.into.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/doubleBoundary/double.boundary.out.png"));//$NON-NLS-1$

		assertNotNull(loadImageIcon("/res/grid.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/Copy.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/Paste.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/Cut.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/Draw.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/text.png"));		//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/polygon.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/rotation.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/InFrontSelect.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/behindSelect.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/background.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/foreground.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/Undo.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/Redo.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/Magnify.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/wedge.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/Arc.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/document-open.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/document-save.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/document-save-as.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/circle.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/del.png"));		//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/dot.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/ellipse.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/empty.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/rectangle.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/rhombus.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/select.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/square.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/triangle.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/Print.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/Object.png"));	//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/AutoBorders.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/go-next.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/join.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/separate.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/bezierCurve.png"));//$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/chord.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/CopySel.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/go-previous.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/joinedLines.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/emblem-unreadable.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/stop.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/image-x-generic.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/thickness.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/hatch/gradient.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/displayGrid.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/rotation180.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/rotation90.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/rotation270.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/mirrorH.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/mirrorV.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/axes.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/texEditor.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/delimitor.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/freehand/curve.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/freehand/line.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/BezierCurves/closeCurve.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/BezierCurves/closeLine.png")); //$NON-NLS-1$
		assertNotNull(loadImageIcon("/res/closedBezier.png")); //$NON-NLS-1$
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

