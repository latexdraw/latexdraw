package net.sf.latexdraw.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

public class TestOperatingSystem {
	@ParameterizedTest
	@EnumSource(OperatingSystem.class)
	void testGetPS2EPSBinPath(final OperatingSystem os) {
		assertThat(os.getPS2EPSBinPath()).isNotEmpty();
	}

	@ParameterizedTest
	@EnumSource(OperatingSystem.class)
	void testGetDvipsBinPath(final OperatingSystem os) {
		assertThat(os.getDvipsBinPath()).isNotEmpty();
	}

	@ParameterizedTest
	@EnumSource(OperatingSystem.class)
	void testGetLatexBinPath(final OperatingSystem os) {
		assertThat(os.getLatexBinPath()).isNotEmpty();
	}

	@ParameterizedTest
	@EnumSource(OperatingSystem.class)
	void testGetPs2pdfBinPath(final OperatingSystem os) {
		assertThat(os.getPs2pdfBinPath()).isNotEmpty();
	}

	@ParameterizedTest
	@EnumSource(OperatingSystem.class)
	void testGetPdfcropBinPath(final OperatingSystem os) {
		assertThat(os.getPdfcropBinPath()).isNotEmpty();
	}
}
