package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import server.facade.ServerFacade;
import server.handler.*;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * @author kevinjreece
 */
public class IndexerServer {

	
	private static final int SERVER_PORT_NUMBER = 8080;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	private static Logger _logger;
	
	static {
		try {
			initLog();
		}
		catch (IOException e) {
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	private static void initLog() throws IOException {
		
		Level logLevel = Level.FINE;
		
		_logger = Logger.getLogger("recordindexer"); 
		_logger.setLevel(logLevel);
		_logger.setUseParentHandlers(false);
		
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		_logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		_logger.addHandler(fileHandler);
	}

	
	private HttpServer server;
	
	private IndexerServer() {
		return;
	}
	
	private void run() {
		
		_logger.info("Initializing Model");
		
		try {
			ServerFacade.initialize();		
		}
		catch (ServerException e) {
			_logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		
		_logger.info("Initializing HTTP Server");
		
		try {
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
											MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			_logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}

		server.setExecutor(null); // use the default executor
		
		server.createContext("/ValidateUser", ValidateUserHandler);
		server.createContext("/GetProjects", GetProjectsHandler);
		server.createContext("/UpdateContact", GetSampleImageHandler);
		server.createContext("/DownloadBatch", DownloadBatchHandler);
		server.createContext("/SubmitBatch", SubmitBatchHandler);
		server.createContext("/GetFields", GetFieldsHandler);
		server.createContext("/Search", SearchHandler);
		server.createContext("/DownloadFile", DownloadFileHandler);
		
		_logger.info("Starting HTTP Server");

		server.start();
	}

	private HttpHandler ValidateUserHandler = new ValidateUserHandler();
	private HttpHandler GetProjectsHandler = new GetProjectsHandler();
	private HttpHandler GetSampleImageHandler = new GetSampleImageHandler();
	private HttpHandler DownloadBatchHandler = new DownloadBatchHandler();
	private HttpHandler SubmitBatchHandler = new SubmitBatchHandler();
	private HttpHandler GetFieldsHandler = new GetFieldsHandler();
	private HttpHandler SearchHandler = new SearchHandler();
	private HttpHandler DownloadFileHandler = new DownloadFileHandler();
	
	
	public static void main(String[] args) {
		new IndexerServer().run();
	}

	
	
	
	
	
	
}
