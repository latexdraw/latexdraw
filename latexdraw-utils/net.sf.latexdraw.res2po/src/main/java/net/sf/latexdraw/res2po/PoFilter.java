package net.sf.latexdraw.res2po;

import java.io.File;
import java.io.FileFilter;

public class PoFilter implements FileFilter {
	@Override
	public boolean accept(final File pathname) {
		return pathname != null && pathname.getPath().endsWith(".po");
	}
}
