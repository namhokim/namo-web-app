package com.tistory.namocom.drive.resource;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.restlet.data.Status;
import org.restlet.ext.fileupload.RestletFileUpload;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.tistory.namocom.drive.AppEntry;

public class Upload extends ServerResource {

	@Post
	public Representation representation(Representation entity) throws Exception {
		
    	if (entity == null) {
    		// POST request with no entity.
    		setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
    		return new StringRepresentation("Uploaded Error");
    	}
    	
    	// 1. Create a factory for disk-based file items
    	DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(AppEntry.UPLOAD_MAX_BYTES);
        
        // 2. Create a new file upload handler based on the Restlet
        RestletFileUpload upload = new RestletFileUpload(factory);
        List<FileItem> items;
        
        // 3. Request is parsed by the handler which generates a
        // list of FileItems
        items = upload.parseRequest(getRequest());
    	
		// Process only the uploaded item called "fileToUpload" and
        // save it on disk
		boolean found = false;
		for (final Iterator<FileItem> it = items.iterator();
				it.hasNext() && !found;) {
			FileItem fi = it.next();
			 if (fi.getFieldName().equals("file")) {
				 String name = fi.getName();
                 found = true;
                 File file = new File(AppEntry.RELEASE_PATH + "\\" + name);
                 if(file.exists()) file.delete();
                 fi.write(file);
             }
		}
		
		setStatus(Status.SUCCESS_CREATED);		// 201
		return new StringRepresentation("Uploaded");
	}
	
}
