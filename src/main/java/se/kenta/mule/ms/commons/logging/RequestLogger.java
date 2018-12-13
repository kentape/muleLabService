package se.kenta.mule.ms.commons.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

public class RequestLogger implements Callable{
	
	private static final Logger logger = LogManager.getLogger(RequestLogger.class);

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		System.out.println("Execute " + this.getClass().getName() + "::onCall");
		MuleMessage muleMessage = eventContext.getMessage();
		Long startTime = startTimer(muleMessage);
		doLogging(muleMessage, startTime);
		
		return muleMessage;
	}
	
	private void doLogging(MuleMessage muleMessage, Long startTime) {
		ThreadContext.put("mule_message_id", muleMessage.getUniqueId());
		ThreadContext.put("_startTime_", String.valueOf(startTime));
		String _timeMillis_ = ThreadContext.get("timeMillis");
		ThreadContext.put("_timeMillis_", _timeMillis_);
		ThreadContext.put("null", null);
		
		//ThreadContext.put("compact", "true");
		//ThreadContext.put("eventEol", "true");
		logger.debug("Execute " + this.getClass().getName() + ": JSONLayout properties=\"true\"");
		//logger.debug("Execute " + this.getClass().getName() + ": JSONLayout complete=\"false\"");
		//logger.debug("Execute " + this.getClass().getName() + ": DEBUG");
		//logger.info("Execute " + this.getClass().getName() + ": INFO");
		//logger.error("Execute " + this.getClass().getName() + ": ERROR");
		ThreadContext.clearMap();
	}
	
	private Long startTimer(MuleMessage muleMessage){
		Long start = System.currentTimeMillis();
		muleMessage.setProperty("_startTime_", start, PropertyScope.SESSION);
		return start;
	}

}
