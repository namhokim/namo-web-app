package com.tistory.namocom.drive;

import java.io.File;
//import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import org.restlet.routing.Template;
//import org.slf4j.bridge.SLF4JBridgeHandler;

import com.tistory.namocom.drive.resource.*;

public class AppEntry extends Application {
	
	public static final String RELEASE_PATH;	// not end with '\'
	public final static int UPLOAD_MAX_BYTES = 52428800;	// 50MB
	private static final String RELEASE_SUB_PATH = "\\drive";
	
	static {
		// Release Directory Set
		File f = new File(".");
		String currPath = f.getAbsolutePath();
		String rootPath = currPath.substring(0, currPath.indexOf('\\')) + RELEASE_SUB_PATH;
		File d = new File(rootPath);
		if(!d.exists()) d.mkdirs();
		RELEASE_PATH = rootPath;
		f = null;
		d = null;
	}
	
	public AppEntry() {
		super();
	}
	public AppEntry(Context parentContext) {
		super(parentContext);
	}
	
	@Override
	public synchronized Restlet createInboundRoot() {
		
		// Restlet Framework basic log off
		//Application.getCurrent().getLogger().setLevel(Level.OFF);
		
		// Replacing default JDK logging with log4j
		//SLF4JBridgeHandler.install();
				
        Router router = new Router(getContext());
        router.attachDefault(Default.class);
        router.attach("/upload", Upload.class);
        router.attach("/download", Download.class, Template.MODE_STARTS_WITH);
        
        return router;
	}

}
