package net.sf.latexdraw.binChecker;

import java.io.IOException;

public class BinChecker {
	private StringBuilder logLatex = new StringBuilder();
	
	private StringBuilder logDviPs = new StringBuilder();
	
	private StringBuilder logPs2pdf = new StringBuilder();
	
	private StringBuilder logPdfcrop = new StringBuilder();
	
	public static void main(String[] args) {
		BinChecker checker = new BinChecker();
		checker.check();
	}
	
	
	private void check() {
		boolean ok = supportLatexCmd();
		displayInfo(logLatex.toString(), ok, "Command latex supported: ");
		ok = supportDviPsCmd();
		displayInfo(logDviPs.toString(), ok, "Command dvips supported: ");
		ok = supportPs2PdfCmd();
		displayInfo(logPs2pdf.toString(), ok || logPs2pdf.toString().contains("Usage: ps2pdfwr [options...]"), "Command ps2pdf supported: ");
		ok = supportPdfcropCmd();
		displayInfo(logPdfcrop.toString(), ok, "Command pdfcrop supported: ");
	}
	
	
	
	private void displayInfo(final String errorLog, final boolean support, final String msg) {
		System.out.println(msg + support);
		if(!support)
			System.out.println("Log: " + errorLog);
	}
	
	
	
	private boolean supportPdfcropCmd() {
		return execute(new String[]{"pdfcrop", "--version"}, logPdfcrop);
	}
	
	private boolean supportPs2PdfCmd() {
		return execute(new String[]{"ps2pdf"}, logPs2pdf);
	}
	
	private boolean supportDviPsCmd() {
		return execute(new String[]{"dvips", "--version"}, logDviPs);
	}
	
	private boolean supportLatexCmd() {
		return execute(new String[]{"latex", "--version"}, logLatex);
	}
	
	
	private boolean execute(final String[] cmd, final StringBuilder log) {
		try {
			final Process process = Runtime.getRuntime().exec(cmd);
			final StreamExecReader errReader = new StreamExecReader(process.getErrorStream());
			final StreamExecReader outReader = new StreamExecReader(process.getInputStream());

			errReader.start();
			outReader.start();

			int exitValue = process.waitFor();
			log.append(outReader.getLog());
			log.append(errReader.getLog());
			
			return exitValue==0;
			
		}catch(final IOException ex) {
			log.append(ex.getMessage());
		}catch(final InterruptedException ex) {
			log.append(ex.getMessage());
		}

		return false;
	}
}
