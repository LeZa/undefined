package com.webService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.1.3-hudson-390-
 * Generated source version: 2.0
 * <p>
 * An example of how this class may be used:
 * 
 * <pre>
 * QueryMapServerImplService service = new QueryMapServerImplService();
 * IQueryMapServer portType = service.getQueryMapServerImplPort();
 * portType.sendAlert(...);
 * </pre>
 * 
 * </p>
 * 
 */
@WebServiceClient(name = "QueryMapServerImplService", targetNamespace = "http://impl.queryMap.cxf.control.ws.com/", wsdlLocation = "http://127.0.0.1/mapWeb/cxf/queryMap?wsdl")
public class QueryMapServerImplService extends Service {

	private final static URL QUERYMAPSERVERIMPLSERVICE_WSDL_LOCATION;
	private final static Logger logger = Logger
			.getLogger(com.webService.QueryMapServerImplService.class.getName());

	static {
		URL url = null;
		try {
			URL baseUrl;
			baseUrl = com.webService.QueryMapServerImplService.class
					.getResource(".");
			url = new URL(baseUrl,
					"http://127.0.0.1/mapWeb/cxf/queryMap?wsdl");
		} catch (MalformedURLException e) {
			logger
					.warning("Failed to create URL for the wsdl Location: 'http://127.0.0.1/mapWeb/cxf/queryMap?wsdl', retrying as a local file");
			logger.warning(e.getMessage());
		}
		QUERYMAPSERVERIMPLSERVICE_WSDL_LOCATION = url;
	}

	public QueryMapServerImplService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public QueryMapServerImplService() {
		super(QUERYMAPSERVERIMPLSERVICE_WSDL_LOCATION, new QName(
				"http://impl.queryMap.cxf.control.ws.com/",
				"QueryMapServerImplService"));
	}

	/**
	 * 
	 * @return returns IQueryMapServer
	 */
	@WebEndpoint(name = "QueryMapServerImplPort")
	public IQueryMapServer getQueryMapServerImplPort() {
		return super.getPort(new QName(
				"http://impl.queryMap.cxf.control.ws.com/",
				"QueryMapServerImplPort"), IQueryMapServer.class);
	}

}
