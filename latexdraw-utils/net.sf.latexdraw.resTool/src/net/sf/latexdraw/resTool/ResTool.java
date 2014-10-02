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

	public static final String PATH_RES = "file:///media/data/dev/latexdraw/latexdrawGit/latexdraw-core/net.sf.latexdraw/src/resources/main/lang/en-US";
	public static final String PROP_EXT = "properties";
	
	protected final Map<String,Pair<String,Path>> keys;
	protected final Map<String,String> keysInvert;
	
	public ResTool() {
		super();
		keys 		= new HashMap<>();
		keysInvert 	= new HashMap<>();
	}
	
	
	public void cacheResources() throws IOException, URISyntaxException {
		Files.list(Paths.get(new URI(PATH_RES))).filter(f -> f.getFileName().toString().endsWith(PROP_EXT)).forEach(f -> readFile(f).ifPresent(txt ->
			Arrays.asList(txt.split("\\n")).forEach(line -> {
				final String[] tab = line.split("=");
				if(keys.containsKey(tab[0])) {
					System.err.println(">> Duplicated key: " + tab[0] + " in " + f);
					System.err.println("Find: " + keys.get(tab[0]));
				}else {
					keys.put(tab[0], new Pair<>(tab[1], f));
					keysInvert.put(tab[1], tab[0]);
				}
		})));
	}
	
	
	public static Optional<String> readFile(Path path){
		  try {
			return Optional.of(new String(Files.readAllBytes(Paths.get(path.toString())), StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
}
