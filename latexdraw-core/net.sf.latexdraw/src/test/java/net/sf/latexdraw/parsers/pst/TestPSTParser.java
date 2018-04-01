package net.sf.latexdraw.parsers.pst;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.junit.After;
import org.junit.Before;
import org.malai.command.CommandsRegistry;
import org.malai.undo.UndoCollector;

import static org.junit.Assert.fail;

public abstract class TestPSTParser {
	PSTLatexdrawListener listener;

	@Before
	public void setUp() throws Exception {
		DviPsColors.INSTANCE.clearUserColours();

		listener = new ErrorPSTLatexdrawListener();
		listener.log.addHandler(new Handler() {
			@Override
			public void publish(final LogRecord record) {
				fail(record.getMessage());
			}

			@Override
			public void flush() {
			}

			@Override
			public void close() throws SecurityException {
			}
		});
	}

	@After
	public void tearDown() {
		CommandsRegistry.INSTANCE.clear();
		CommandsRegistry.INSTANCE.removeAllHandlers();
		BadaboomCollector.INSTANCE.clear();
		UndoCollector.INSTANCE.clear();
	}

	<T extends IShape> T getShapeAt(final int i) {
		return (T) listener.getShapes().get(i);
	}

	void parser(final String code) {
		final net.sf.latexdraw.parsers.pst.PSTParser parser =
			new net.sf.latexdraw.parsers.pst.PSTParser(new CommonTokenStream(new net.sf.latexdraw.parsers.pst.PSTLexer(CharStreams.fromString(code))));
		parser.addParseListener(listener);
		final ErrorListener errList = new ErrorListener();
		parser.addErrorListener(errList);
		parser.pstCode(new PSTContext());
//		// Trying to flush the parser
		parser.removeParseListener(listener);
		parser.removeErrorListener(errList);
		new ATNDeserializer().deserialize(net.sf.latexdraw.parsers.pst.PSTLexer._serializedATN.toCharArray());
	}

	public static class ErrorListener extends BaseErrorListener {
		@Override
		public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol, final int line, final int charPositionInLine, final
		String msg, final RecognitionException e) {
			fail(msg);
		}
	}

	public static class ErrorPSTLatexdrawListener extends PSTLatexdrawListener {
		@Override
		public void enterUnknowncmds(final net.sf.latexdraw.parsers.pst.PSTParser.UnknowncmdsContext ctx) {
			super.enterUnknowncmds(ctx);
			fail(ctx.LATEXCMD().getText());
		}

		@Override
		public void enterUnknownParamSetting(final net.sf.latexdraw.parsers.pst.PSTParser.UnknownParamSettingContext ctx) {
			super.enterUnknownParamSetting(ctx);
			fail(ctx.name.getText());
		}
	}
}
