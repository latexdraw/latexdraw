package net.sf.latexdraw.parser.pst;

import java.io.IOException;
import java.nio.file.Path;
import net.sf.latexdraw.data.ParameteriseShapeData;
import net.sf.latexdraw.model.api.shape.Picture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class TestParsingIncludeGraphics extends TestPSTParser {
	@Test
	public void testImageKONoExtension() {
		listener.log.removeHandler(parserLogHandler);
		parser("\\includegraphics{foo/bar}");
		assertThat(parsedShapes).isEmpty();
	}

	@Test
	public void testImageKODoesNotExist() {
		listener.log.removeHandler(parserLogHandler);
		parser("\\includegraphics{foo/bar.png}");
		assertThat(parsedShapes).isEmpty();
	}

	@Test
	public void testImageOK(@TempDir final Path dir) throws IOException {
		final Path path = ParameteriseShapeData.INST.getTestPNG(dir);
		parser("\\includegraphics{" + path.toString() + "}");
		assertThat(parsedShapes.size()).isEqualTo(1);
		assertThat(parsedShapes.get(0)).isInstanceOf(Picture.class);
	}
}
