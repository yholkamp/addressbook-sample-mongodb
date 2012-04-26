package org.axonframework.sample.ws;

import junit.framework.Assert;
import org.eclipse.jetty.server.Server;

public class AddressbookWS {

	private static AddressbookWS instance;

	private Server server;

	private boolean useSSL = false;

	private AddressbookWS() {

		this.useSSL = useSSL;
		server = new Server();
		Connector connector = new SocketConnector();
		connector.setPort(9090);
		server.setConnectors(new Connector[] { connector });

		WebAppContext webapp = new WebAppContext();
		webapp.setConfigurationClasses(new String[] {
				"org.mortbay.jetty.webapp.WebInfConfiguration", //
				// we need to load or own jetty-env, not in the one in the
				// varb-ws.war
				// overrides the default
				// org.mortbay.jetty.plus.webapp.EnvConfiguration class
				"nl.enovation.varb.ws.OtherJettyEnvConfiguration",
				//
				"org.mortbay.jetty.plus.webapp.Configuration", // web.xml
				"org.mortbay.jetty.webapp.JettyWebXmlConfiguration",// jettyWeb
		});
		webapp.setContextPath("/varb-ws");
		webapp.setWar("file:../varb-ws/target/varb-ws.war");
		webapp.setExtractWAR(false);
		server.setHandler(webapp);
		server.setStopAtShutdown(true);

		try {
			server.start();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public static AddressbookWS getInstance() {
		return getInstance(false);
	}

	public static AddressbookWS getInstance(boolean useSSL) {
		if (instance == null) {
			instance = new AddressbookWS();
		}
		return instance;
	}

	public boolean isRunning() {
		return server.isRunning();
	}

	public String getBaseURL() {
		return (useSSL ? "https" : "http") + "://localhost:9090" + "/axon-addressbook-ui/";
	}
}