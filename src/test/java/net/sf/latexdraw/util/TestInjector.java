package net.sf.latexdraw.util;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Handler;
import net.sf.latexdraw.HelperTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TestInjector implements HelperTest {
	Injector injector;
	@Mock Handler handler;

	@BeforeEach
	public void setUp() {
		I.cpt = 0;
		Injector.LOGGER.addHandler(handler);
	}

	@AfterEach
	public void tearDown() {
		Injector.LOGGER.removeHandler(handler);
		if(injector != null) {
			injector.clear();
		}
	}

	@Test
	public void testConstructor() {
		final Injector injector = new Injector() {
			@Override
			protected void configure() {
			}
		};
		injector.initialise();
	}

	@Test
	public void testCreateRegisteredObject() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(D.class);
			}
		};
		injector.initialise();

		assertNotNull(injector.getInstance(D.class));
		Mockito.verify(handler, Mockito.never()).publish(Mockito.any());
	}

	@Test
	public void testFailClassWithNoDefaultConst() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(K.class);
			}
		};
		injector.initialise();
		assertNull(injector.getInstance(K.class));
		Mockito.verify(handler, Mockito.atLeastOnce()).publish(Mockito.any());
	}

	@Test
	public void testFailOnNullClass() {
		injector = new Injector() {
			@Override
			protected void configure() {
			}
		};
		injector.initialise();
		assertNull(injector.getInstance(null));
		Mockito.verify(handler, Mockito.never()).publish(Mockito.any());
	}

	@Test
	public void testSingletonRegisteredObject() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(D.class);
			}
		};
		injector.initialise();
		assertSame(injector.getInstance(D.class), injector.getInstance(D.class));
		Mockito.verify(handler, Mockito.never()).publish(Mockito.any());
	}

	@Test
	public void testFailOnNonConfigParam() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(X.class);
			}
		};
		injector.initialise();
		assertNotNull(injector.getInstance(X.class));
		assertNull(injector.getInstance(X.class).b);
	}

	@Test
	public void testInjectOnDeclaredField() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(D.class);
				bindAsEagerSingleton(C.class);
			}
		};
		injector.initialise();
		assertNotNull(injector.getInstance(C.class));
		assertNotNull(injector.getInstance(D.class));
		assertSame(injector.getInstance(D.class), injector.getInstance(C.class).d);
		Mockito.verify(handler, Mockito.never()).publish(Mockito.any());
	}

	@Test
	public void testInjectOnInheritedField() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(D.class);
				bindAsEagerSingleton(E.class);
			}
		};
		injector.initialise();
		assertNotNull(injector.getInstance(D.class));
		assertNotNull(injector.getInstance(E.class));
		assertSame(injector.getInstance(D.class), injector.getInstance(E.class).d);
		Mockito.verify(handler, Mockito.never()).publish(Mockito.any());
	}

	@Test
	public void testDoNotCreateSingletonSeveralTimes() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(I.class);
			}
		};
		injector.initialise();
		injector.getInstance(I.class);
		injector.getInstance(I.class);
		assertEquals(1, I.cpt);
		Mockito.verify(handler, Mockito.never()).publish(Mockito.any());
	}

	@Test
	public void testFailBindingOnOutClassAlreadyReg() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(C.class);
				bindAsEagerSingleton(E.class);
				bindWithCommand(C.class, E.class, obj -> obj);
			}
		};
		injector.initialise();
		assertNotNull(injector.getInstance(C.class));
		assertNotNull(injector.getInstance(E.class));
		Mockito.verify(handler, Mockito.atLeastOnce()).publish(Mockito.any());
	}

	@Test
	public void testFailBindingOnSrcClassNotReg() {
		injector = new Injector() {
			@Override
			protected void configure() {
				bindWithCommand(C.class, E.class, obj -> obj);
			}
		};
		injector.initialise();
		Mockito.verify(handler, Mockito.atLeastOnce()).publish(Mockito.any());
	}

	@Test
	public void testGetInstanceNotNull() {
		injector = new Injector() {
			@Override
			protected void configure() {
			}
		};
		injector.initialise();
		assertNotNull(injector.getInstances());
	}

	@Test
	public void testGetInstanceOK() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(D.class);
			}
		};
		injector.initialise();
		final D obj = injector.getInstance(D.class);
		assertEquals(obj, injector.getInstances().iterator().next());
	}

	@Test
	public void testSupplierGetInstance() {
		final D d = new D();
		injector = new Injector() {
			@Override
			protected void configure() {
				bindToSupplier(D.class, () -> d);
			}
		};
		injector.initialise();
		final D obj = injector.getInstance(D.class);
		assertEquals(d, obj);
	}

	@Test
	public void testSupplierInjection() {
		final B b = new B();
		injector = new Injector() {
			@Override
			protected void configure() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
				bindToSupplier(B.class, () -> b);
				bindAsEagerSingleton(X.class);
			}
		};
		injector.initialise();
		final X obj = injector.getInstance(X.class);
		assertEquals(b, obj.b);
	}

	@Test
	void testInjectOneParameter() {
		injector = new Injector() {
			@Override
			protected void configure() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
				bindAsEagerSingleton(B.class);
				bindAsEagerSingleton(X.class);
			}
		};

		injector.initialise();
		final X x = injector.getInstance(X.class);
		assertNotNull(x);
		assertNotNull(x.b);
	}

	@Test
	void testInjectParameters() {
		final F f = new F() {
		};

		injector = new Injector() {
			@Override
			protected void configure() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
				bindAsEagerSingleton(D.class);
				bindAsEagerSingleton(E.class);
				bindToInstance(F.class, f);
				bindAsEagerSingleton(Z.class);
			}
		};

		injector.initialise();
		final Z z = injector.getInstance(Z.class);
		assertNotNull(z);
		assertNotNull(z.d);
		assertEquals(injector.getInstance(D.class), z.d);
		assertNotNull(z.e);
		assertEquals(injector.getInstance(E.class), z.e);
		assertNotNull(z.f);
		assertEquals(injector.getInstance(F.class), z.f);
	}

	@Test
	void testNoConstructorToInject() {
		injector = new Injector() {
			@Override
			protected void configure() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
				bindAsEagerSingleton(B.class);
				assertThrows(NoSuchMethodException.class, () -> bindAsEagerSingleton(J.class));
			}
		};

		injector.initialise();
	}

	static class J {
		B b;

		J(final B b) {
			super();
			this.b = b;
		}
	}

	static class X {
		final B b;

		@Inject
		X(final B b) {
			super();
			this.b = b;
		}
	}

	static class B {
	}

	static class Z {
		final D d;
		final E e;
		final F f;

		@Inject
		Z(final D d, final E e, final F f) {
			super();
			this.d = d;
			this.e = e;
			this.f = f;
		}
	}

	static class C {
		final D d;

		@Inject
		C(final D d) {
			super();
			this.d = d;
		}
	}

	static class D {
	}

	static class E extends C {
		@Inject
		E(final D d) {
			super(d);
		}
	}

	interface F {
	}

	static class G implements F {

	}

	static class I {
		static int cpt = 0;

		I() {
			super();
			cpt++;
		}
	}

	static final class K {
		private K() {
			super();
		}
	}
}
