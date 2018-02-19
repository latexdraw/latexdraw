package net.sf.latexdraw.resTool;

import java.util.Optional;

public class StringFinder extends ResTool {

	public StringFinder() {
		super();
	}

	public boolean existsKey(final String key) {
		return keys.containsKey(key);
	}

	public Optional<String> existsString(final String str) {
		return Optional.ofNullable(keysInvert.get(str));
	}
}
