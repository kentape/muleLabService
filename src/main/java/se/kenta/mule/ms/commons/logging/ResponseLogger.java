package se.kenta.mule.ms.commons.logging;

import java.nio.charset.StandardCharsets;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

public class ResponseLogger implements Callable{
	
	private static final Logger logger = LogManager.getLogger(ResponseLogger.class);

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		MuleMessage muleMessage = eventContext.getMessage();
		Long longs[] = getExecutionTime(muleMessage);
		doLogging(muleMessage, longs);
		return muleMessage;
	}
	
	private void doLogging(MuleMessage muleMessage, Long[] longs) throws Exception {
		ThreadContext.put("endTime", String.valueOf(longs[0]));
		ThreadContext.put("responseTime", String.valueOf(longs[1]));
		Set<String> keys = ThreadContext.getContext().keySet();
		for(String key:keys){
			System.out.println(key);
		}
		logger.info(muleMessage.getPayloadAsString(StandardCharsets.UTF_8.name()));
		ThreadContext.clearMap();
	}
	
	private Long[] getExecutionTime(MuleMessage muleMessage){
		Long startTime = muleMessage.getProperty("_startTime_", PropertyScope.SESSION);
		System.out.println("Starttime from own property: " + startTime);
		long endTime = System.currentTimeMillis();
		System.out.println("Endtime: " + endTime);
		long executionTime = endTime - startTime;
		Long[] longs = new Long[2];
		longs[0] = endTime;
		longs[1] = executionTime;
		return longs;
	}
}
