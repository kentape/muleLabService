package se.kenta.mule.ms.commons.util;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

public class SleepUtil implements Callable{

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		MuleMessage muleMessage = eventContext.getMessage();
		Long random = (long)(Math.random() * 5000);
		System.out.println("_RANDOM_: " + random);
		Thread.sleep(random);
		return muleMessage;
	}
	
	

}
