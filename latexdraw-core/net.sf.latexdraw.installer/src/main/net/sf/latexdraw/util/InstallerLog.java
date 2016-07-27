/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2016 Arnaud BLOUIN
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.
 */
package net.sf.latexdraw.util;

import java.io.IOException;
import java.util.logging.*;

/**
 * The logger.
 * Created by Arnaud Blouin on 27/07/16.
 */
public final class InstallerLog {
	private static Logger logger = null;
	private static final String LOGGER_NAME = "INSTALLERLOGGER";
	private static FileHandler fileTxt;
	private static SimpleFormatter formatterTxt;

	public static Logger getLogger() {
		if(logger==null) {
			setup();
		}
		return logger;
	}

	private static void setup() {
		logger = Logger.getLogger(LOGGER_NAME);
		logger.setLevel(Level.ALL);

		try {
			fileTxt = new FileHandler("logInstall.txt");
			formatterTxt = new SimpleFormatter();
			fileTxt.setFormatter(formatterTxt);

			// suppress the logging output to the console
			Logger rootLogger = Logger.getLogger("");
			Handler[] handlers = rootLogger.getHandlers();
			if(handlers.length>0 && handlers[0] instanceof ConsoleHandler) {
				rootLogger.removeHandler(handlers[0]);
			}

			logger.addHandler(fileTxt);
		}catch(final IOException ex) {
			ex.printStackTrace();
		}
	}

	private InstallerLog() {
		super();
	}
}
