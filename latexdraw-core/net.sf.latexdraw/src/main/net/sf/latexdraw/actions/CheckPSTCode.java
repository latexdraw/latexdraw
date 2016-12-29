package net.sf.latexdraw.actions;

import java.text.ParseException;
import java.util.stream.Collectors;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;
import net.sf.latexdraw.ui.dialog.InsertCodeDialog;
import org.malai.action.Action;
import scala.collection.JavaConversions;

public class CheckPSTCode extends Action {
	InsertCodeDialog dialog;

	public CheckPSTCode() {
		super();
	}

	@Override
	public boolean isRegisterable() {
		return false;
	}

	@Override
	protected void doActionBody() {
		PSTParser parser = new PSTParser();
		try {
			parser.parsePSTCode(dialog.getText());

			if(PSTParser.errorLogs().isEmpty()) {
				dialog.cleanErrorMessage();
			}else {
				dialog.setErrorMessage(JavaConversions.asJavaCollection(
					PSTParser.errorLogs()).stream().collect(Collectors.joining("\n")));
				PSTParser.errorLogs().clear();
			}
		}catch(ParseException e) {
			dialog.setErrorMessage(e.getMessage());
		}
	}

	@Override
	public boolean canDo() {
		return dialog!=null;
	}

	public void setDialog(final InsertCodeDialog insertDialog) {
		dialog = insertDialog;
	}
}
