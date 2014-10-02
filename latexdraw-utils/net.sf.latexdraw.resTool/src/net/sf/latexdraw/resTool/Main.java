package net.sf.latexdraw.resTool;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

	public static void main(String[] args) {
		final StringFinder finder = new StringFinder();
		try {
			finder.cacheResources();
			System.out.println(finder.existsString("scale"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
