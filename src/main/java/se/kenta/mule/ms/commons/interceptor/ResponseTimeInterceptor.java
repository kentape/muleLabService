package se.kenta.mule.ms.commons.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.interceptor.AbstractEnvelopeInterceptor;
import org.mule.management.stats.ProcessingTime;

public class ResponseTimeInterceptor extends AbstractEnvelopeInterceptor{
	
	// Option 2 from: https://dzone.com/articles/mule-calculate-total-processing-time-of-any-flow-i
	/*
	 Logmessage:
	 {
	  "timeMillis" : 1539543248032,
	  "thread" : "[mulelabservice].HTTP_Listener_Configuration.worker.01",
	  "level" : "INFO",
	  "loggerName" : "se.kenta.mule.ms.commons.interceptor.ResponseTimeInterceptor",
	  "message" : "**************************************************************************************************\r\n | Flow: mulelabserviceFlow | Procesing Time:10 ms\r\n**************************************************************************************************",
	  "endOfBatch" : false,
	  "loggerFqcn" : "org.apache.logging.log4j.spi.AbstractLogger",
	  "contextMap" : [ ]
	}
	 
	 */

	private static final Logger logger = LogManager.getLogger(ResponseTimeInterceptor.class);

	@Override
	public MuleEvent before(MuleEvent event) throws MuleException {
		return event;
	}

	@Override
	public MuleEvent after(MuleEvent event) throws MuleException {
		return event;
	}
	
	@Override
	public MuleEvent last(MuleEvent event, ProcessingTime time, long startTime, boolean exceptionWasThrown) throws MuleException { 
		try {
			long endTime = System.currentTimeMillis();
			logger.info( "**************************************************************************************************"
			+ System.getProperty("line.separator")
			+ " | Flow: " + event.getFlowConstruct().getName()
			+ " | Procesing Time:" + (endTime - startTime)
			+ " ms" + System.getProperty("line.separator")
			+ "**************************************************************************************************");
			return event;
		} catch (Exception e) {
			e.printStackTrace();
		} return event;
	}
}
