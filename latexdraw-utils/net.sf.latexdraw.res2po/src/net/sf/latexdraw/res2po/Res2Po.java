package net.sf.latexdraw.res2po;
import java.io.*;

/**
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * @author Arnaud BLOUIN
 */
public class Res2Po  {
	public static void main(final String argc[]) {
		if(argc.length<4)
			throw new IllegalArgumentException();
		
		generatePo(new File(argc[0]), new File(argc[1]), new File(argc[2]), argc[3]);
	}

	
	public static void generatePo(final File baseDir, final File inputDir, final File outputDir, final String id) {
		if(inputDir==null || !inputDir.canRead() || outputDir==null || !outputDir.canWrite() || id==null || !outputDir.isDirectory() || !inputDir.isDirectory())
			throw new IllegalArgumentException();
		
		FileFilter filter = new PropertiesFilter();
		File[] filesBase = baseDir.listFiles(filter);
		File f, f2;
		
		for(int i=0; i<filesBase.length; i++) {
			f = new File(outputDir+File.separator+
				filesBase[i].getPath().substring(filesBase[i].getPath().lastIndexOf(File.separator), filesBase[i].getPath().lastIndexOf("."))+
				"-" + id + ".po");
			
			try {
				FileReader flB 		= new FileReader(filesBase[i]);
				BufferedReader brB 	= new BufferedReader(flB);
				FileOutputStream fos = new FileOutputStream(f);
				OutputStreamWriter osw = new OutputStreamWriter(fos, "ISO-8859-1");
				BufferedWriter bw 	= new BufferedWriter(osw);
				f2 = new File(inputDir+File.separator+filesBase[i].getName());
				
				if(f2.canRead())
				{
					FileInputStream fis = new FileInputStream(f2);
					InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
					BufferedReader brI = new BufferedReader(isr);
					generatePo(brB, brI, bw, id);
					
					brI.close();
					isr.close();
					fis.close();
				}
				else
					generateFullPo(brB, bw, id);
				
				bw.close();
				osw.close();
				fos.close();
				brB.close();
				flB.close();
				
				FileWriter fwPOT		= new FileWriter(new File(outputDir+File.separator+
											filesBase[i].getPath().substring(filesBase[i].getPath().lastIndexOf(File.separator), 
											filesBase[i].getPath().lastIndexOf(".")) + ".pot"));
				BufferedWriter bwPOT	= new BufferedWriter(fwPOT);
				flB = new FileReader(filesBase[i]);
				brB = new BufferedReader(flB);
				
				generatePot(brB, bwPOT);
				
				brB.close();
				flB.close();
				bwPOT.close();
				fwPOT.close();
			} 
			catch(Exception e) { e.printStackTrace(); }
		}
	}


	private static void generatePo(final BufferedReader brB, final BufferedReader brI, final BufferedWriter bw, final String id) {
		if(brB==null || brI==null || bw==null)
			return ;
		
		try {
			bw.write("msgid \"\"\n");
			bw.write("msgstr \"\"\n");
			bw.write("\"Project-Id-Version: "+ id + "\\n\"\n");
			bw.write("\"Content-Type: text/plain; charset=ISO-8859-1\\n\"\n\n");
			
			String line1 = brB.readLine(), line2 = brI.readLine();
		
			while(line1!=null && line2!=null) {
				if(line1.contains("=")) {
					String[] str1 = line1.split("=", 2);
					String[] str2 = line2.split("=", 2);
					
					if(!str1[0].equals(str2[0]))
						throw new IOException("Pas bonne ligne :" + line1 + " " + line2);
					
					str1[1] = replaceASCIIAccent(str1[1]);//FIXME must use the new conversion process.
					str2[1] = replaceASCIIAccent(str2[1]);
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
			
			if((line1!=null && line1.contains("=")) || (line2!=null && line2.contains("=")))
				throw new IOException("Fichier pas égaux !" + line1 + " " + line2);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	
	
	public static String replaceASCIIAccent(final String str) {
		if(str==null)
			return "";
		
		String newStr = str.replace("\\u00C0", "À");
		newStr = newStr.replace("\\u00C1", "Á");
		newStr = newStr.replace("\\u00C2", "Â");
		newStr = newStr.replace("\\u00C3", "Ã");
		newStr = newStr.replace("\\u00C4", "Ä");
		newStr = newStr.replace("\\u00C5", "Å");
		newStr = newStr.replace("\\u00C6", "Æ");
		newStr = newStr.replace("\\u00C7", "Ç");
		newStr = newStr.replace("\\u00C8", "È");
		newStr = newStr.replace("\\u00C9", "É");
		newStr = newStr.replace("\\u00CA", "Ê");
		newStr = newStr.replace("\\u00CB", "Ë");
		newStr = newStr.replace("\\u00CC", "Ì");
		newStr = newStr.replace("\\u00CD", "Í");
		newStr = newStr.replace("\\u00CE", "Î");
		newStr = newStr.replace("\\u00CF", "Ï");
		newStr = newStr.replace("\\u00D0", "Ð");
		newStr = newStr.replace("\\u00D1", "Ñ");
		newStr = newStr.replace("\\u00D2", "Ò");
		newStr = newStr.replace("\\u00D3", "Ó");
		newStr = newStr.replace("\\u00D4", "Ô");
		newStr = newStr.replace("\\u00D5", "Õ");
		newStr = newStr.replace("\\u00D6", "Ö");
		newStr = newStr.replace("\\u00D7", "×");
		newStr = newStr.replace("\\u00D8", "Ø");
		newStr = newStr.replace("\\u00D9", "Ù");
		newStr = newStr.replace("\\u00DA", "Ú");
		newStr = newStr.replace("\\u00DB", "Û");
		newStr = newStr.replace("\\u00DC", "Ü");
		newStr = newStr.replace("\\u00DD", "Ý");
		newStr = newStr.replace("\\u00DE", "Þ");
		newStr = newStr.replace("\\u00DF", "ß");
		newStr = newStr.replace("\\u00E0", "à");
		newStr = newStr.replace("\\u00E1", "á");
		newStr = newStr.replace("\\u00E2", "â");
		newStr = newStr.replace("\\u00E3", "ã");
		newStr = newStr.replace("\\u00E4", "ä");
		newStr = newStr.replace("\\u00E5", "å");
		newStr = newStr.replace("\\u00E6", "æ");
		newStr = newStr.replace("\\u00E7", "ç");
		newStr = newStr.replace("\\u00E8", "è");
		newStr = newStr.replace("\\u00E9", "é");
		newStr = newStr.replace("\\u00EA", "ê");
		newStr = newStr.replace("\\u00EB", "ë");
		newStr = newStr.replace("\\u00EC", "ì");
		newStr = newStr.replace("\\u00ED", "í");
		newStr = newStr.replace("\\u00EE", "î");
		newStr = newStr.replace("\\u00EF", "ï");
		newStr = newStr.replace("\\u00F0", "ð");
		newStr = newStr.replace("\\u00F1", "ñ");
		newStr = newStr.replace("\\u00F2", "ò");
		newStr = newStr.replace("\\u00F3", "ó");
		newStr = newStr.replace("\\u00F4", "ô");
		newStr = newStr.replace("\\u00F5", "õ");
		newStr = newStr.replace("\\u00F6", "ö");
		newStr = newStr.replace("\\u00F7", "÷");
		newStr = newStr.replace("\\u00F8", "ø");
		newStr = newStr.replace("\\u00F9", "ù");
		newStr = newStr.replace("\\u00FA", "ú");
		newStr = newStr.replace("\\u00FB", "û");
		newStr = newStr.replace("\\u00FC", "ü");
		newStr = newStr.replace("\\u00FD", "ý");
		newStr = newStr.replace("\\u00FE", "þ");
		newStr = newStr.replace("\\u00FF", "ÿ");
		newStr = newStr.replace("\\u00B0", "°");
		
		return newStr;
	}
	
	
	
	
	protected static void generatePot(final BufferedReader br, final BufferedWriter bw) {
		if(br==null || bw==null)
			return ;
		
		try {
			bw.write("msgid \"\"\n");
			bw.write("msgstr \"\"\n");
			bw.write("\"Project-Id-Version: PACKAGE VERSION\\n\"\n");
			bw.write("\"Content-Type: text/plain; charset=CHARSET\\n\"\n\n");
			
			String line = br.readLine();
			
			while(line!=null) {
				if(line.contains("=")) {
					String[] str = line.split("=", 2);
		
					str[1] = replaceASCIIAccent(str[1]);
					str[1] = str[1].replace("\\", "");
					
					bw.write("#: " + str[0].replace(".", ":") + "\n");
					bw.write("msgid \"" + str[1] + "\"\n");
					bw.write("msgstr \"\"\n");
					bw.write('\n');
				}
				
				line = br.readLine();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	

	private static void generateFullPo(final BufferedReader br, final BufferedWriter bw, final String id) {
		if(br==null || bw==null || id==null)
			return ;
		
		try {
			bw.write("msgid \"\"\n");
			bw.write("msgstr \"\"\n");
			bw.write("\"Project-Id-Version: "+ id + "\\n\"\n");
			bw.write("\"Content-Type: text/plain; charset=ISO-8859-1\\n\"\n\n");
			
			String line = br.readLine();
			
			while(line!=null) {
				if(line.contains("=")) {
					String[] str = line.split("=", 2);
		
					str[1] = str[1].replace("\\u00E", "é");
					str[1] = str[1].replace("\\", "");
					
					bw.write("#: " + str[0].replace(".", ":") + "\n");
					bw.write("msgid \"" + str[1] + "\"\n");
					bw.write("msgstr \"" + str[1] + "\"\n");
					bw.write('\n');
				}
				
				line = br.readLine();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
