package br.com.vitafarma.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;

public class VitafarmaTest extends GWTTestCase {
	@Override
	public String getModuleName() {
		return ("br.com.vitafarma.web.Vitafarma");
	}

	public void testSimple() {
		GWT.create(Vitafarma.class);

		GWTTestCase.assertTrue(true);
	}

	public void testTimer() {
		// Setup an asynchronous event handler.
		Timer timer = new Timer() {
			public void run() {
				// do some validation logic

				// tell the test system the test is now done
				finishTest();
			}
		};

		// Set a delay period significantly longer than the
		// event is expected to take.
		this.delayTestFinish(500);

		// Schedule the event and return control to the test system.
		timer.schedule(100);
	}
}
