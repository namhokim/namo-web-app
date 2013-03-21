package com.tistory.namocom.drive;

import java.io.File;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.tistory.namocom.drive.resource.*;

public class AppEntry extends Application {
	
	public static final String RELEASE_PATH;
	public final static int UPLOAD_MAX_BYTES = 52428800;	// 50MB
	private static final String RELEASE_SUB_PATH = "\\drive\\";
	
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
	
	public Restlet createInboundRoot() {
        Router router = new Router(getContext());
  
        router.attach("/upload", Upload.class);
        router.attach("/download", Download.class);
        router.attachDefault(Default.class);
        
        return router;
	}

}
