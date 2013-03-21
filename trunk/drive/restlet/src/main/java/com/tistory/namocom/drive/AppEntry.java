package com.tistory.namocom.drive;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.tistory.namocom.drive.resource.*;

public class AppEntry extends Application {
	
	public AppEntry() {
		super();
	}
	public AppEntry(Context parentContext) {
		super(parentContext);
	}
	
	public Restlet createInboundRoot() {
        Router router = new Router(getContext());
  
        router.attach("/hello", HelloResource.class);
        
        return router;
	}

}
