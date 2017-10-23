package net.sf.latexdraw.util;

import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@RunWith(Theories.class)
public class TestOperatingSystem {
	@Theory
	public void testGetPS2EPSBinPath(final OperatingSystem os) {
		assertThat(os.getPS2EPSBinPath(), not(isEmptyOrNullString()));
	}

	@Theory
	public void testGetDvipsBinPath(final OperatingSystem os) {
		assertThat(os.getDvipsBinPath(), not(isEmptyOrNullString()));
	}

	@Theory
	public void testGetLatexBinPath(final OperatingSystem os) {
		assertThat(os.getLatexBinPath(), not(isEmptyOrNullString()));
	}

	@Theory
	public void testGetPs2pdfBinPath(final OperatingSystem os) {
		assertThat(os.getPs2pdfBinPath(), not(isEmptyOrNullString()));
	}

	@Theory
	public void testGetPdfcropBinPath(final OperatingSystem os) {
		assertThat(os.getPdfcropBinPath(), not(isEmptyOrNullString()));
	}
}
