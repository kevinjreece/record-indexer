/**
 * 
 */
package server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.ServerException;
import server.facade.ServerFacade;
import shared.communication.GetSampleImage_Params;
import shared.communication.GetSampleImage_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author kevinjreece
 *
 */
public class GetSampleImageHandler implements HttpHandler {
	private Logger _logger = Logger.getLogger("recordindexer"); 
	
	private XStream _xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		GetSampleImage_Params params = (GetSampleImage_Params)_xmlStream.fromXML(exchange.getRequestBody());
		GetSampleImage_Result result = null;
		
		try {
			result = ServerFacade.getSampleImage(params);
		}
		catch (ServerException err) {
            _logger.log(Level.SEVERE, err.getMessage(), err);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;				
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		_xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}
