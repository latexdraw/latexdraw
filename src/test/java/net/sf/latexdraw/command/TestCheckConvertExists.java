package net.sf.latexdraw.command;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import net.sf.latexdraw.util.SystemUtils;
import net.sf.latexdraw.util.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.github.interacto.command.Command;
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
	SystemUtils system;

	@BeforeEach
	void setUp() {
		system = SystemUtils.getInstance();
		label = new Label();
		link = new Hyperlink();
		label.setVisible(false);
	}

	@AfterEach
	void tearDown() {
		SystemUtils.setSingleton(system);
	}

	@Test
	void testExecuteNoConvert() {
		final SystemUtils mockSys = Mockito.mock(SystemUtils.class);
		Mockito.when(mockSys.execute(Mockito.any(), Mockito.any())).thenReturn(new Tuple<>(false, ""));
		SystemUtils.setSingleton(mockSys);
		cmd = new CheckConvertExists(label, link);
		cmd.doIt();
		assertTrue(link.isVisible());
		assertFalse(link.getText().isEmpty());
		assertFalse(label.getText().isEmpty());
	}

	@Nested
	class TestWithDataOK {
		@BeforeEach
		void setUp() {
			cmd = new CheckConvertExists(label, link);
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
