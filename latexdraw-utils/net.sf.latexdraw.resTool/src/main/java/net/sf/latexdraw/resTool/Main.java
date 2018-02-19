package net.sf.latexdraw.resTool;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
	public static void main(final String[] args) {
		if(args.length==0) {
			throw new IllegalArgumentException("A parameter is required: the path to the folder");
		}

		final StringFinder finder = new StringFinder();
		try {
			finder.cacheResources(args[0]);
			System.out.println(finder.existsString("eps (latex) picture"));
		}catch(final IOException | URISyntaxException ex) {
			ex.printStackTrace();
		}
	}
}
