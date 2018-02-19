package net.sf.latexdraw.res2po;
import java.io.*;

public class PropertiesFilter implements FileFilter  {
	@Override
	public boolean accept(final File pathname) {
		return pathname != null && pathname.getPath().endsWith(".properties");
	}
}
