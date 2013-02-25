package net.sf.latexdraw.res2po;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

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
public class Po2Res {
	public static void main(final String[] args) {
		if(args.length==0)
			throw new IllegalArgumentException("You must specify the POs path.");
		
		convertPos(args[0]);
	}
	
	
	public static String convertTranslation(final String str) {
		if(str==null)
			return null;

		char[] chars 	 = str.toCharArray();
		StringBuffer buf = new StringBuffer();
		String hex;
		
		for(char c : chars) {
			if (c >= 0x0020 && c <= 0x007e)
				buf.append(c);
			else {
				buf.append("\\u");
				hex = Integer.toHexString(c & 0xFFFF);
				for(int j=0; j<4-hex.length(); j++) // Prepend zeros because unicode requires 4 digits
					buf.append("0");
					buf.append(hex.toLowerCase()); // standard unicode format.
				}
		}
		
		if(buf.length()>0 && buf.charAt(0)==' ')
			buf.insert(0, "\\");
		
		return buf.toString();
	}
	
	
	
	public static void convertPo(final BufferedReader br, final BufferedWriter bw) {
		try {
			String line = br.readLine();
			
			while(line!=null && !line.startsWith("#: "))
				line = br.readLine();
			
			if(line==null)
				return ;
			
			String name;
			String translation;
			String model="";
			
			do {
				name = line.substring(3);
				name = name.replace(":", ".");
				
				do { 
					line = br.readLine();
					
					if(line!=null && line.length()>0 && !line.startsWith("#"))
						model += line.substring(line.indexOf('"')+1, line.lastIndexOf('"'));
				}
				while(line!=null && !line.startsWith("msgstr"));
				
				if(line==null)
					throw new IllegalArgumentException();
				
				translation = line.substring(8, line.length()-1);
				line = br.readLine();
				
				while(line!=null && line.length()>0 && !line.startsWith("#:"))
				{ 
					translation += line.substring(1, line.length()-1);
					line = br.readLine();
				}
				
				translation = convertTranslation(translation);
				
				if(translation!=null && translation.length()==0)
					translation = model; // If there is no translation, we take the model.
				
				model = "";
				bw.write(name+"="+translation+"\n");
				
				if(translation==null || translation.length()==0) {
					System.err.println("Bad translation: " + name);
					System.exit(-1);
				}
				
				System.out.println(name + "=" + translation);
				
				while(line!=null && (line.length()==0 || line.startsWith("#~")))
					line = br.readLine();
			}
			while(line!=null);
		}
		catch(IOException e) { e.printStackTrace(); }
	}
	
	
	
	public static void convertPos(final String path) {
		if(path==null)
			return ;
		
		File dir = new File(path);
		
		if(!dir.canRead() || !dir.isDirectory())
			return ;
		
		FileFilter filter = new PoFilter();
		File[] files = dir.listFiles(filter);
		
		for(int i=0; i<files.length; i++)
			try
			{
				File newdir;
				FileInputStream fis   = new FileInputStream(files[i]);
				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				BufferedReader br 	  = new BufferedReader(isr);
				String fPath 		  = files[i].getPath();
				String locate;
				String name;
				
				fPath  = fPath.substring(0, fPath.lastIndexOf('.'));
				name   = fPath.substring(fPath.lastIndexOf('/')+1);
				name   = name.substring(0, name.lastIndexOf("-"));
				name   = name.replace("-", "_");
				name  += ".properties";
				locate = fPath.substring(fPath.lastIndexOf('-')+1);
				fPath  = fPath.substring(0, fPath.lastIndexOf('/')) + File.separator + locate;
				newdir = new File(fPath);
				
				if(!newdir.exists())
					newdir.mkdir();
				
				String charset = null;
				String line = br.readLine();
				String str = "\"Content-Type: text/plain; charset=";
				
				while(line!=null && charset==null)
					if(line.startsWith(str))
						charset = line.substring(str.length(), line.indexOf("\\n\""));
					else
						line = br.readLine();
				
				br.close();
				isr.close();
				fis.close();
				fis = new FileInputStream(files[i]);
				isr = new InputStreamReader(fis, charset==null ? "UTF-8" : charset);
				br  = new BufferedReader(isr);
				
				name = name.replaceAll("dialogframes", "dialogFrames");
				name = name.replaceAll("latexdrawframe", "LaTeXDrawFrame");
				
				FileWriter fw     = new FileWriter(newdir+File.separator+name);
				BufferedWriter bw = new BufferedWriter(fw);
				
				convertPo(br, bw);
				
				bw.close();
				fw.close();
				br.close();
				isr.close();
				fis.close();
			}
			catch(Exception e) { e.printStackTrace(); }
	}
}
