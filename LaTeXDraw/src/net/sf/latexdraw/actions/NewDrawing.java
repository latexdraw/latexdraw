package net.sf.latexdraw.actions;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.sf.latexdraw.bordel.BordelCollector;
import net.sf.latexdraw.instruments.PreferencesSetter;
import fr.eseo.malai.action.library.IOAction;

public class NewDrawing extends IOAction {
	/** The file chooser that will be used to select the location to save. */
	protected JFileChooser fileChooser;

	/** The instrument used that manage the preferences. */
	protected PreferencesSetter prefSetter;


	@Override
	protected void doActionBody() {
		if(ui.isModified()) {
			switch(SaveDrawing.showAskModificationsDialog(ui)) {
				case JOptionPane.NO_OPTION: //new
					newDrawing();
					break;
				case JOptionPane.YES_OPTION: // save + load
					File f = SaveDrawing.showDialog(fileChooser, true, ui, file);
					if(f!=null) {
						openSaveManager.save(f.getPath(), ui);
						ui.setModified(false);
						newDrawing();
					}
					break;
				case JOptionPane.CANCEL_OPTION: // nothing
					break;
				default:
					break;
			}
		}
		else newDrawing();
	}


	protected void newDrawing() {
		ui.reinit();
		try{ prefSetter.readXMLPreferences(); }
		catch(Exception exception){ BordelCollector.INSTANCE.add(exception); }
	}


	@Override
	public boolean canDo() {
		return fileChooser!=null && ui!=null && openSaveManager!=null && prefSetter!=null;
	}


	@Override
	public void flush() {
		super.flush();
		fileChooser = null;
	}


	/**
	 * @param fileChooser The file chooser that will be used to select the location to save.
	 * @since 3.0
	 */
	public void setFileChooser(final JFileChooser fileChooser) {
		this.fileChooser = fileChooser;
	}


	/**
	 * @param prefSetter The instrument used that manage the preferences.
	 * @since 3.0
	 */
	public void setPrefSetter(final PreferencesSetter prefSetter) {
		this.prefSetter = prefSetter;
	}
}
