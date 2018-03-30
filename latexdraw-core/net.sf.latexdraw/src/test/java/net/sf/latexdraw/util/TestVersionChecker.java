package net.sf.latexdraw.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import net.sf.latexdraw.instruments.StatusBarController;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.api.FxToolkit;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestVersionChecker {
	@Before
	public void setUp() throws Exception {
		FxToolkit.registerPrimaryStage();
	}

	@AfterClass
	public static void afterClass() throws TimeoutException {
		FxToolkit.cleanupStages();
	}

	@Test
	public void testCheckVersionNoNewVersion() throws InterruptedException {
		StatusBarController controller = mock(StatusBarController.class);
		FutureTask<Void> future = new FutureTask<>(new VersionChecker(controller), null);
		ExecutorService taskExecutor = Executors.newFixedThreadPool(1);
		taskExecutor.execute(future);
		taskExecutor.shutdown();
		taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	}
}
