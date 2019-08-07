package net.sf.latexdraw.view.svg;

import java.lang.reflect.InvocationTargetException;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.data.ConfigureInjection;
import net.sf.latexdraw.data.InjectionExtension;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.parser.svg.SVGAttributes;
import net.sf.latexdraw.parser.svg.SVGDefsElement;
import net.sf.latexdraw.parser.svg.SVGDocument;
import net.sf.latexdraw.parser.svg.SVGElement;
import net.sf.latexdraw.parser.svg.SVGSVGElement;
import net.sf.latexdraw.service.LaTeXDataService;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.view.PolymorphicConversion;
import net.sf.latexdraw.view.ViewsSynchroniserHandler;
import net.sf.latexdraw.view.jfx.ViewFactory;
import net.sf.latexdraw.view.latex.DviPsColors;
import net.sf.latexdraw.view.latex.LaTeXGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import io.github.interacto.command.CommandsRegistry;
import io.github.interacto.undo.UndoCollector;
import org.mockito.Mockito;

@ExtendWith(InjectionExtension.class)
abstract class TestSVGBase<T extends Shape> implements PolymorphicConversion<T> {
	SVGDocument doc;
	SVGShapesFactory factory;

	@ConfigureInjection
	Injector createInjector() {
		return new Injector() {
			@Override
			protected void configure() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
				bindToInstance(LaTeXDataService.class, Mockito.mock(LaTeXDataService.class));
				bindToInstance(Drawing.class, Mockito.mock(Drawing.class));
				bindToInstance(ViewsSynchroniserHandler.class, Mockito.mock(ViewsSynchroniserHandler.class));
				bindToInstance(LaTeXGenerator.class, Mockito.mock(LaTeXGenerator.class));
				bindAsEagerSingleton(ViewFactory.class);
				bindAsEagerSingleton(SVGShapesFactory.class);
			}
		};
	}

	@BeforeEach
	void setUp(final SVGShapesFactory svgfac) {
		factory = svgfac;
		doc = new SVGDocument();
		final SVGSVGElement root = doc.getFirstChild();
		root.setAttribute("xmlns:" + LNamespace.LATEXDRAW_NAMESPACE, LNamespace.LATEXDRAW_NAMESPACE_URI);
		root.appendChild(new SVGDefsElement(doc));
		root.setAttribute(SVGAttributes.SVG_VERSION, "1.1");
		root.setAttribute(SVGAttributes.SVG_BASE_PROFILE, "full");
	}

	@AfterEach
	public void tearDown() {
		DviPsColors.INSTANCE.clearUserColours();
		CommandsRegistry.INSTANCE.clear();
		CommandsRegistry.INSTANCE.removeAllHandlers();
		BadaboomCollector.INSTANCE.clear();
		UndoCollector.INSTANCE.clear();
	}

	@Override
	public T produceOutputShapeFrom(final T sh) {
		final SVGElement elt = factory.createSVGElement(sh, doc);
		doc.getFirstChild().appendChild(elt);
		return (T) factory.createShape(elt);
	}
}
