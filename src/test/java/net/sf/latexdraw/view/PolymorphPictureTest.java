package net.sf.latexdraw.view;

import java.io.File;
import java.io.IOException;
import net.sf.latexdraw.data.ParameteriseShapeData;
import net.sf.latexdraw.model.api.shape.Picture;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public interface PolymorphPictureTest extends PolymorphicConversion<Picture> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#createDiversifiedPicture")
	default void testPathPicture(final Picture sh) throws IOException {
		final File tmpFile = Files.temporaryFolder();
		sh.setPathSource(ParameteriseShapeData.INST.getTestPNG(tmpFile.toPath()).toString());
		final Picture s2 = produceOutputShapeFrom(sh);
		assertEquals(sh.getPathSource(), s2.getPathSource());
	}
}
