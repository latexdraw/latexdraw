package net.sf.latexdraw.parsers;

import java.util.Objects;


/**
 * Defines an abstract parser that parses code.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 11/21/08<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public abstract class CodeParser implements Parser {
	/** The token for the end of the code. */
	public static final int EOC = -1;

	/**
	 * Defines a core level of the code parser that can be share with others
	 * code parser.
	 * @author Arnaud Blouin
	 */
	private static class Code {
		/** The code to parser. */
		protected String code;

		/** The current position of the character to read. */
		protected int position;

		/** The current line number. */
		protected int linePosition;

		/**
		 * Creates and initialises a core code.
		 * @param c The code to parse.
		 * @throws IllegalArgumentException If the given code is null.
		 */
		public Code(final String c) {
			super();
			code 			= Objects.requireNonNull(c);
			position 		= 0;
			linePosition 	= 1;
		}
	}


	/** The core level of the parser that contains the code to parse,...<br>
	 * It can be shared with others code parsers. */
	protected Code codeCore;


	/**
	 * Creates and initialises the code parser.
	 * @param code The code to parse.
	 * @throws IllegalArgumentException If the given code is null.
	 */
	public CodeParser(final String code) {
		super();

		if(code==null)
			throw new IllegalArgumentException();

		codeCore = new Code(code);
	}



	/**
	 * Reinitialises the parser.
	 * @since 2.0.2
	 */
	public void initialise() {
		codeCore.position = 0;
		codeCore.linePosition = 1;
	}



	/**
	 * @return the code.
	 * @since 2.0.2
	 */
	public String getCode() {
		return codeCore.code;
	}



	/**
	 * @param code the code to set. It re-initialises the parser.
	 * @since 2.0.2
	 */
	public void setCode(final String code) {
		if(code!=null) {
			this.codeCore.code = code;
			initialise();
		}
	}



    /**
     * Reads the next char and returns it.
     * @return the next read character or EOC if the end of the path is reached.
     * @since 2.0.2
     */
    public int nextChar() {
    	codeCore.position++;

    	return getChar();
    }



    /**
     * The character at the given position.
     * @param pos The position of the wanted character.
     * @return The character or EOC.
     * @since 2.0.3
     */
    public int getCharAt(final int pos) {
    	if(pos>=codeCore.code.length() || pos<0)
    		return EOC;

    	return codeCore.code.charAt(pos);
    }


    /**
     * @return the current character or EOC if the end of the path is reached.
     * @since 2.0.2
     */
    public int getChar() {
    	if(codeCore.position>=codeCore.code.length())
    		return EOC;

    	return codeCore.code.charAt(codeCore.position);
    }


    /**
     * @return True if the end of the code is reached.
     * @since 2.0.2
     */
    public boolean isEOC() {
    	return getChar()==EOC;
    }



	/**
	 * @return the position.
	 * @since 2.0.2
	 */
	public int getPosition() {
		return codeCore.position;
	}



	/**
	 * @return the line position.
	 * @since 2.0.2
	 */
	public int getLinePosition() {
		return codeCore.linePosition;
	}



	/**
	 * Skips the comment.
	 * @return The read comment.
	 * @since 2.0.2
	 */
	public abstract String skipComment();



	/**
	 * Skips the useless characters.
	 * @since 2.0.2
	 */
	public abstract void skipWSP();



	/**
	 * Skips both comments and ignorable characters.
	 * @since 2.0.2
	 */
	public void skipWSPComments() {
		int pos;

		do {
			pos = codeCore.position;
			skipWSP();
			skipComment();
		}
		while(pos!=codeCore.position && !isEOC());
	}



	/**
	 * @return True if the current position points to a comment token.
	 * @since 2.0.3
	 */
	public abstract boolean isComment();



	/**
	 * @return True if the current character is a whitespace/ignorable character.
	 * @since 2.0.2
	 */
	public abstract boolean isWSP();


	/**
	 * @return True if the current character is EOL.
	 * For the EOL CR+LF, the next character (LF) is read.
	 * @since 2.0.2
	 */
	public boolean isEOL() {
		final int c = getChar();
		boolean eol;

		if(c=='\r') {
			eol = true;

			if(nextChar()!='\n')
				codeCore.position--;
		}
		else
			eol = c=='\n';

		return eol;
	}



	/**
	 * @param position the position to set.
	 * @since 2.0.2
	 */
	public void setPosition(final int position) {
		if(position>=0)
			codeCore.position = position;
	}


	/**
	 * Increments the line position.
	 */
	public void incLinePosition() {
		codeCore.linePosition++;
	}


	/**
	 * @param linePosition the line position to set. Must be greater than 0.
	 * @since 2.0.2
	 */
	public void setLinePosition(final int linePosition) {
		if(linePosition>=1)
			codeCore.linePosition = linePosition;
	}



	/**
	 * @return the code core.
	 * @since 2.0.2
	 */
	public Code getCodeCore() {
		return codeCore;
	}



	/**
	 * @param codeCore the code core to set.
	 * @since 2.0.2
	 */
	public void setCodeCore(final Code codeCore) {
		this.codeCore = codeCore;
	}
}
