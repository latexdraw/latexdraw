package net.sf.latexdraw.res2po;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Res2Po {
	public static void main(final String argc[]) {
		if(argc.length < 4) {
			throw new IllegalArgumentException();
		}

		generatePo(new File(argc[0]), new File(argc[1]), new File(argc[2]), argc[3]);
	}


	public static void generatePo(final File baseDir, final File inputDir, final File outputDir, final String id) {
		if(inputDir == null || !inputDir.canRead() || outputDir == null || !outputDir.canWrite() || id == null || !outputDir.isDirectory() || !inputDir.isDirectory()) {
			throw new IllegalArgumentException();
		}

		final FileFilter filter = new PropertiesFilter();
		final File[] filesBase = baseDir.listFiles(filter);
		File f, f2;

		if(filesBase == null) {
			return;
		}

		for(final File aFilesBase : filesBase) {
			f = new File(outputDir +
				File.separator + aFilesBase.getPath().substring(aFilesBase.getPath().lastIndexOf(File.separator),
				aFilesBase.getPath().lastIndexOf(".")) + "-" + id + ".po");

			try {
				FileReader flB = new FileReader(aFilesBase);
				BufferedReader brB = new BufferedReader(flB);
				final FileOutputStream fos = new FileOutputStream(f);
				final OutputStreamWriter osw = new OutputStreamWriter(fos);
				final BufferedWriter bw = new BufferedWriter(osw);
				f2 = new File(inputDir + File.separator + aFilesBase.getName());

				if(f2.canRead()) {
					final FileInputStream fis = new FileInputStream(f2);
					final InputStreamReader isr = new InputStreamReader(fis);
					final BufferedReader brI = new BufferedReader(isr);
					generatePo(brB, brI, bw, id);

					brI.close();
					isr.close();
					fis.close();
				}else {
					generateFullPo(brB, bw, id);
				}

				bw.close();
				osw.close();
				fos.close();
				brB.close();
				flB.close();

				final FileWriter fwPOT = new FileWriter(new File(outputDir + File.separator +
						aFilesBase.getPath().substring(aFilesBase.getPath().lastIndexOf(File.separator), aFilesBase.getPath().lastIndexOf(".")) + ".pot"));
				final BufferedWriter bwPOT = new BufferedWriter(fwPOT);
				flB = new FileReader(aFilesBase);
				brB = new BufferedReader(flB);

				generatePot(brB, bwPOT);

				brB.close();
				flB.close();
				bwPOT.close();
				fwPOT.close();
			}catch(final Exception e) {
				e.printStackTrace();
			}
		}
	}


	private static void generatePo(final BufferedReader brB, final BufferedReader brI, final BufferedWriter bw, final String id) {
		if(brB == null || brI == null || bw == null) {
			return;
		}

		try {
			bw.write("msgid \"\"\n");
			bw.write("msgstr \"\"\n");
			bw.write("\"Project-Id-Version: " + id + "\\n\"\n");
			bw.write("\"Content-Type: text/plain; charset=utf-8\\n\"\n\n");

			String line1 = brB.readLine(), line2 = brI.readLine();

			while(line1 != null && line2 != null) {
				if(line1.contains("=")) {
					final String[] str1 = line1.split("=", 2);
					final String[] str2 = line2.split("=", 2);

					if(!str1[0].equals(str2[0])) {
						throw new IOException("Pas bonne ligne :" + line1 + " " + line2);
					}

					str1[1] = str1[1].replace("\\", "");
					str2[1] = str2[1].replace("\\", "");

					bw.write("#: " + str1[0].replace(".", ":") + "\n");
					bw.write("msgid \"" + str1[1] + "\"\n");
					bw.write("msgstr \"" + str2[1] + "\"\n");
					bw.write('\n');
				}

				line1 = brB.readLine();
				line2 = brI.readLine();
			}

			if((line1 != null && line1.contains("=")) || (line2 != null && line2.contains("="))) {
				throw new IOException("Fichier pas égaux !" + line1 + " " + line2);
			}
		}catch(final IOException e) {
			e.printStackTrace();
		}
	}


	protected static void generatePot(final BufferedReader br, final BufferedWriter bw) {
		if(br == null || bw == null) {
			return;
		}

		try {
			bw.write("msgid \"\"\n");
			bw.write("msgstr \"\"\n");
			bw.write("\"Project-Id-Version: PACKAGE VERSION\\n\"\n");
			bw.write("\"Content-Type: text/plain; charset=utf-8\\n\"\n\n");

			String line = br.readLine();

			while(line != null) {
				if(line.contains("=")) {
					final String[] str = line.split("=", 2);

					str[1] = str[1].replace("\\", "");

					bw.write("#: " + str[0].replace(".", ":") + "\n");
					bw.write("msgid \"" + str[1] + "\"\n");
					bw.write("msgstr \"\"\n");
					bw.write('\n');
				}

				line = br.readLine();
			}
		}catch(final IOException e) {
			e.printStackTrace();
		}
	}


	private static void generateFullPo(final BufferedReader br, final BufferedWriter bw, final String id) {
		if(br == null || bw == null || id == null) {
			return;
		}

		try {
			bw.write("msgid \"\"\n");
			bw.write("msgstr \"\"\n");
			bw.write("\"Project-Id-Version: " + id + "\\n\"\n");
			bw.write("\"Content-Type: text/plain; charset=utf-8\\n\"\n\n");

			String line = br.readLine();

			while(line != null) {
				if(line.contains("=")) {
					final String[] str = line.split("=", 2);

					str[1] = str[1].replace("\\u00E", "é");
					str[1] = str[1].replace("\\", "");

					bw.write("#: " + str[0].replace(".", ":") + "\n");
					bw.write("msgid \"" + str[1] + "\"\n");
					bw.write("msgstr \"" + str[1] + "\"\n");
					bw.write('\n');
				}

				line = br.readLine();
			}
		}catch(final IOException e) {
			e.printStackTrace();
		}
	}
}
