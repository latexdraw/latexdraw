package test.gui;

import static org.mockito.Mockito.mock;
import net.sf.latexdraw.glib.views.jfx.Canvas;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.inject.AbstractModule;

@RunWith(MockitoJUnitRunner.class)
class ShapePropModule extends AbstractModule {
	Pencil pencil;
	Hand hand;

	@Override
	protected void configure() {
		pencil 	= mock(Pencil.class);
		hand	= mock(Hand.class);
		bind(Pencil.class).toInstance(pencil);
		bind(Hand.class).toInstance(hand);
		bind(Canvas.class).asEagerSingleton();
	}
}
