/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.pst;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.data.ConfigureInjection;
import net.sf.latexdraw.data.InjectionExtension;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.parsers.pst.PSTContext;
import net.sf.latexdraw.parsers.pst.TestPSTParser;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.LangService;
import net.sf.latexdraw.util.SystemService;
import net.sf.latexdraw.view.PolymorphicConversion;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.undo.UndoCollector;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(InjectionExtension.class)
abstract class TestPSTBase<T extends IShape> implements PolymorphicConversion<T> {
	PSTViewsFactory factory;
	SystemService system;

	@ConfigureInjection
	Injector configure() {
		return new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(SystemService.class);
				bindAsEagerSingleton(LangService.class);
				bindAsEagerSingleton(PSTViewsFactory.class);
			}
		};
	}

	@BeforeEach
	public void setUp(final PSTViewsFactory fact, final SystemService system) {
		factory = fact;
		this.system = system;
	}

	@AfterEach
	public void tearDown() {
		DviPsColors.INSTANCE.clearUserColours();
		BadaboomCollector.INSTANCE.clear();
		UndoCollector.INSTANCE.clear();
	}

	@Override
	public T produceOutputShapeFrom(final T sh) {
		final String view = factory.createView(sh).orElseThrow().getCode(ShapeFactory.INST.createPoint(), IShape.PPC);
		final net.sf.latexdraw.parsers.pst.PSTLexer lexer = new net.sf.latexdraw.parsers.pst.PSTLexer(CharStreams.fromString(view));
		final net.sf.latexdraw.parsers.pst.PSTParser parser = new net.sf.latexdraw.parsers.pst.PSTParser(new CommonTokenStream(lexer));
		final TestPSTParser.ErrorPSTLatexdrawListener listener = new TestPSTParser.ErrorPSTLatexdrawListener(system);
		parser.addParseListener(listener);
		parser.pstCode(new PSTContext());

		parser.getInterpreter().clearDFA();
		lexer.getInterpreter().clearDFA();
		new ATNDeserializer().deserialize(net.sf.latexdraw.parsers.pst.PSTLexer._serializedATN.toCharArray());

		final List<IShape> shapes = listener.flatShapes();
		assertEquals(1, shapes.size());

		return (T) shapes.get(0);
	}
}
