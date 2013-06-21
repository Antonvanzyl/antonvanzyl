package runnable;

import org.apache.log4j.PropertyConfigurator;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class JettyRun {

	private static Server server;

	public static Server getServer() {
		return server;
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		// set default logging for JEtty
		PropertyConfigurator.configure("src/test/resources/log4j.properties");

		System.setProperty("DEBUG", "true");
		System.setProperty("jboss.jvmRoute", "BudgetService_Local");

		server = new Server();

		// create and configure the servlet
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/antonvanzyl");
		webapp.setWar("src/main/webapp");
		webapp.setClassLoader(Thread.currentThread().getContextClassLoader());
		webapp.setLogUrlOnStart(true);
		server.addHandler(webapp);

		// create simple connector
		SocketConnector simpleConnector = new SocketConnector();
		simpleConnector.setName("simpleconnector");
		simpleConnector.setHost("0.0.0.0");
		simpleConnector.setPort(19080);

		server.setConnectors(new Connector[] { simpleConnector });

		try {
			server.start();
			System.out.println("http://localhost:"+simpleConnector.getPort()+webapp.getContextPath()); 
			server.join();
		} catch (Exception ex) {
			try {
				server.stop();
			} catch (Throwable ex2) {
				throw new RuntimeException(ex2);
			}
			throw new RuntimeException(ex);
		}
	}
}
