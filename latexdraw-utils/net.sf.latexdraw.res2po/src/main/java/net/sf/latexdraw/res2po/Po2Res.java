package net.sf.latexdraw.res2po;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Po2Res {
	public static void main(final String[] args) {
		if(args.length == 0) {
			throw new IllegalArgumentException("You must specify the POs path.");
		}

		convertPos(args[0]);
	}


	public static String convertTranslation(final String str) {
		if(!str.isEmpty() && str.charAt(0)==' ') {
			return "\\" + str;
		}
		return str;
	}


	public static void convertPo(final BufferedReader br, final BufferedWriter bw) {
		try {
			String line = br.readLine();

			while(line != null && !line.startsWith("#: ")) {
				line = br.readLine();
			}

			if(line == null) {
				return;
			}

			String name;
			StringBuilder translation;
			StringBuilder model = new StringBuilder();

			do {
				name = line.substring(3);
				name = name.replace(":", ".");

				do {
					line = br.readLine();

					if(line != null && !line.isEmpty() && !line.startsWith("#")) {
						model.append(line.substring(line.indexOf('"') + 1, line.lastIndexOf('"')));
					}
				}while(line != null && !line.startsWith("msgstr"));

				if(line == null) {
					throw new IllegalArgumentException();
				}

				translation = new StringBuilder(line.substring(8, line.length() - 1));
				line = br.readLine();

				while(line != null && !line.isEmpty() && !line.startsWith("#:")) {
					translation.append(line.substring(1, line.length() - 1));
					line = br.readLine();
				}

				translation = new StringBuilder(convertTranslation(translation.toString()));

				if(translation.length() == 0) {
					translation = new StringBuilder(model.toString()); // If there is no translation, we take the model.
				}

				model = new StringBuilder();
				bw.write(name + "=" + translation + "\n");

				if(translation.length() == 0) {
					System.err.println("Bad translation: " + name);
					System.exit(-1);
				}

				System.out.println(name + "=" + translation);

				while(line != null && (line.isEmpty() || line.startsWith("#~"))) {
					line = br.readLine();
				}
			}while(line != null);
		}catch(final IOException ex) {
			ex.printStackTrace();
		}
	}


	public static void convertPos(final String path) {
		if(path == null) {
			return;
		}

		final File dir = new File(path);

		if(!dir.canRead() || !dir.isDirectory()) {
			return;
		}

		final FileFilter filter = new PoFilter();
		final File[] files = dir.listFiles(filter);

		if(files == null) {
			return;
		}

		for(final File file : files) {
			try {
				final File newdir;
				FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				String fPath = file.getPath();
				final String locate;
				String name;

				fPath = fPath.substring(0, fPath.lastIndexOf('.'));
				name = fPath.substring(fPath.lastIndexOf('/') + 1);
				name = name.substring(0, name.lastIndexOf("-"));
				name = name.replace("-", "_");
				name += ".properties";
				locate = fPath.substring(fPath.lastIndexOf('-') + 1);
				fPath = fPath.substring(0, fPath.lastIndexOf('/')) + File.separator + locate;
				newdir = new File(fPath);

				if(!newdir.exists()) {
					newdir.mkdir();
				}

				String charset = null;
				String line = br.readLine();
				final String str = "\"Content-Type: text/plain; charset=";

				while(line != null && charset == null) {
					if(line.startsWith(str)) {
						charset = line.substring(str.length(), line.indexOf("\\n\""));
					}else {
						line = br.readLine();
					}
				}

				br.close();
				isr.close();
				fis.close();
				fis = new FileInputStream(file);
				isr = new InputStreamReader(fis, charset == null ? "UTF-8" : charset);
				br = new BufferedReader(isr);

				name = name.replaceAll("dialogframes", "dialogFrames");
				name = name.replaceAll("latexdrawframe", "LaTeXDrawFrame");

				final FileWriter fw = new FileWriter(newdir + File.separator + name);
				final BufferedWriter bw = new BufferedWriter(fw);

				convertPo(br, bw);

				bw.close();
				fw.close();
				br.close();
				isr.close();
				fis.close();
			}catch(final Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
