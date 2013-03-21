package com.tistory.namocom.drive.resource;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class Default extends ServerResource {

	@Get
	public void representation() {
		Request request = Request.getCurrent();
		String paths[] = request.getOriginalRef().getPath().split("/");
		
		String uri;
		if (paths.length > 2) {
			uri = "/" + paths[1];
		} else {
			uri = "/";
		}
		Response.getCurrent().redirectPermanent(uri);
	}
}
