package net.sf.latexdraw.res2po;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Res2Pot {
	public static void main(final String[] args) throws IOException {
		if(args.length < 2) {
			throw new IllegalArgumentException();
		}

		generatePots(new File(args[0]), new File(args[1]));
	}


	public static void generatePots(final File baseDir, final File outputDir) throws IOException {
		final FileFilter filter = new PropertiesFilter();
		final File[] filesBase = baseDir.listFiles(filter);

		for(final File aFilesBase : filesBase) {
			try(FileWriter fwPOT = new FileWriter(
				new File(outputDir + File.separator + aFilesBase.getPath().substring(aFilesBase.getPath().lastIndexOf(File.separator), aFilesBase.getPath().lastIndexOf(".")) + ".pot"));
				BufferedWriter bwPOT = new BufferedWriter(fwPOT);
				FileReader flB = new FileReader(aFilesBase);
				BufferedReader brB = new BufferedReader(flB)) {
				Res2Po.generatePot(brB, bwPOT);
			}
		}
	}
}
