package test.gui.robot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;

import org.testfx.api.FxRobotInterface;

/**
 * TestFX does not provide all the required routines to test GUIs. This trait defines routines for
 * taking screen shots.
 */
public interface FxRobotCaptureScreenshot extends FxRobotInterface {
	static Path createScreenshotFolder(final String folderName, final boolean withDate) {
		try {
			if(withDate)
				return Files.createDirectory(Paths.get(folderName + System.currentTimeMillis()));
			return Files.createDirectory(Paths.get(folderName));
		}catch(IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	static Path createScreenshotFolder() {
		return createScreenshotFolder("screenshots-", true);
	}

	/**
	 * Takes a screenshot using a file name.
	 * @param dir
	 * @param name
	 * @param node
	 * @return The screenshot
	 */
	default File captureScreenshot(final Path dir, final String name, final Node node) {
		File captureFile = new File(dir + File.separator + name + new Date().getTime() + ".png");
		Platform.runLater(() -> {
			WritableImage img = node.snapshot(null, null);
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", captureFile);
			}catch(Exception e) {
				e.printStackTrace();
			}
		});
		return captureFile;
	}
}
