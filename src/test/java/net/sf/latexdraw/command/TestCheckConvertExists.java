package net.sf.latexdraw.command;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import net.sf.latexdraw.util.SystemService;
import net.sf.latexdraw.util.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.malai.command.Command;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class TestCheckConvertExists {
	CheckConvertExists cmd;
	Label label;
	Hyperlink link;

	@BeforeEach
	void setUp() {
		label = new Label();
		link = new Hyperlink();
		label.setVisible(false);
	}

	@Test
	void testLabelKO() {
		cmd = new CheckConvertExists(null, link, new SystemService());
		assertFalse(cmd.canDo());
	}

	@Test
	void testLinkKO() {
		cmd = new CheckConvertExists(label, null, new SystemService());
		assertFalse(cmd.canDo());
	}

	@Test
	void testExecuteNoConvert() {
		final SystemService mockSys = Mockito.mock(SystemService.class);
		cmd = new CheckConvertExists(label, link, mockSys);
		Mockito.when(mockSys.execute(Mockito.any(), Mockito.any())).thenReturn(new Tuple<>(false, ""));
		cmd.doIt();
		assertTrue(link.isVisible());
		assertFalse(link.getText().isEmpty());
		assertFalse(label.getText().isEmpty());
	}

	@Nested
	class WithDataOK {
		@BeforeEach
		void setUp() {
			cmd = new CheckConvertExists(label, link, new SystemService());
		}

		@Test
		void testRegristrationPolicy() {
			assertEquals(Command.RegistrationPolicy.NONE, cmd.getRegistrationPolicy());
		}

		@Test
		void testExecuteOK() {
			cmd.doIt();
			assertTrue(link.getText().isEmpty());
			assertTrue(label.getText().isEmpty());
		}
	}
}
