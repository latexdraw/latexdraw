package net.sf.latexdraw.resTool;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.util.Pair;

public abstract class ResTool {
	public static final String PROP_EXT = "properties";

	public static Optional<String> readFile(final Path path) {
		try {
			return Optional.of(new String(Files.readAllBytes(Paths.get(path.toString())), StandardCharsets.UTF_8));
		}catch(final IOException ex) {
			ex.printStackTrace();
			return Optional.empty();
		}
	}
	protected final Map<String, Pair<String, Path>> keys;
	protected final Map<String, String> keysInvert;


	public ResTool() {
		super();
		keys = new HashMap<>();
		keysInvert = new HashMap<>();
	}

	public void cacheResources(final String path) throws IOException, URISyntaxException {
		Files.list(Paths.get(new URI(path))).filter(f -> f.getFileName().toString().endsWith(PROP_EXT)).
			forEach(f -> readFile(f).ifPresent(txt -> Arrays.asList(txt.split("\\n")).forEach(line -> {
			final String[] tab = line.split("=");
			if(keys.containsKey(tab[0])) {
				System.err.println(">> Duplicated key: " + tab[0] + " in " + f);
				System.err.println("Find: " + keys.get(tab[0]));
			}else {
				if(tab.length > 1) {
					keys.put(tab[0], new Pair<>(tab[1], f));
					keysInvert.put(tab[1], tab[0]);
				}
			}
		})));
	}
}
