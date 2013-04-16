package com.tistory.namocom.drive.resource;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.restlet.Request;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.restlet.ext.velocity.TemplateRepresentation;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tistory.namocom.drive.AppEntry;
import com.tistory.namocom.drive.helper.FileRepresentationHelper;

public class Download extends ServerResource {
	
	final Logger logger = LoggerFactory.getLogger(Download.class);
	
	@Get
	public Representation representation() {
		logger.debug("/download");
		
		Request request = Request.getCurrent();
		
		DownloadRequestor dq = new DownloadRequestor(request);
		String resFile = AppEntry.RELEASE_PATH + dq.getPath();
		File file = new File(resFile);
		String name = dq.getLastSegment();
		
		if (file.exists()) {
			if (file.isDirectory()) {
				logger.debug("download::directory {}", file.getAbsoluteFile());
				return new DirEnumerator(request, file).getRepresentation();
			}  else if (file.isFile()) {
				logger.debug("download::file {}", file.getAbsoluteFile());
				if (dq.isRemove) {
					return remove(file, name);
				} else {
					return new FileRepresentation(file, MediaType.APPLICATION_OCTET_STREAM);
				}
			} else {
				return new StringRepresentation(name + " - What is this???");
			}
		} else {
			setStatus(Status.CLIENT_ERROR_NOT_FOUND);		// 404
			return new StringRepresentation(name + " is not found.");
		}
	}

	
	private Representation remove(File file, String filename) {
		
		try {
			file.delete();
			
			// make the data model
			Map<String,Object> dm = new HashMap<String, Object>();
			dm.put("filename", filename);
			
			// get template
			FileRepresentation fr = FileRepresentationHelper.get(
					"/com/tistory/namocom/drive/template/after_delete.vm", MediaType.TEXT_HTML);
			fr.setCharacterSet(CharacterSet.UTF_8);

			// make template
			TemplateRepresentation tr = new TemplateRepresentation(fr, MediaType.TEXT_HTML);
			tr.setDataModel(dm);
			return tr;
		} catch (Exception e) {
			return new StringRepresentation(e.getMessage() + " - Error in remove");
		}
	}


	class DownloadRequestor {
		
		final Logger logger = LoggerFactory.getLogger(DownloadRequestor.class);
		private String filePath, lastSegment;
		private boolean isRemove;
		
		public DownloadRequestor(Request request) {
			Reference r = request.getResourceRef();
			/*
			 * 브라우저 주소 - http://localhost:8080/drive.restlet/app/download/test.txt
			 *  baseRefPath - /drive.restlet/app/download
			 *  basePath -    /drive.restlet/app/download/test.txt
			 */
			String baseRefPath = r.getBaseRef().getPath();
			String basePath = r.getPath();
			this.filePath = urlDecoding(
					basePath.replaceAll(baseRefPath, "").replace('/', '\\'));
			
			logger.debug("baseRefPath : {}", baseRefPath);
			logger.debug("basePath : {}", basePath);
			
			this.lastSegment = urlDecoding(request.getOriginalRef().getLastSegment());

			String query = r.getQuery();
			this.isRemove = (query != null && query.equals("action=remove"));
			logger.debug("isRemove : {}", isRemove);
		}

		public String getPath() {
			return this.filePath;
		}

		public String getLastSegment() {
			return lastSegment;
		}
		
		public boolean isRemove() {
			return isRemove;
		}
		
		public String urlDecoding(String original) {
			if(original.contains("%")) {
				try {
					return URLDecoder.decode(original, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.error(e.getMessage());
				}
			}
			return original;
		}
	}
	
	class DirEnumerator {
		
		final Logger logger = LoggerFactory.getLogger(DirEnumerator.class);
		File dir;
		Request request;
		String basePath;

		public DirEnumerator(Request request, File file) {
			this.request = request;
			this.dir = file;
			basePath = request.getResourceRef().getPath();
		}
		
		public Representation getRepresentation() {
			
			try {
				FileRepresentation fr = FileRepresentationHelper.get(
						"/com/tistory/namocom/drive/template/download.vm", MediaType.TEXT_HTML);
				fr.setCharacterSet(CharacterSet.UTF_8);
					
				TemplateRepresentation tr = new TemplateRepresentation(fr, MediaType.TEXT_HTML);
				
				// make the data
				Map<String,Object> data = new HashMap<String, Object>();
				data.put("title", "download");
				data.put("listTitle", "files:");
				
				StringBuffer sb = new StringBuffer();
				File [] fList = dir.listFiles();
				for(File aDir : fList) {
					sb.append(
						String.format("  <li><a href=\"%s/%s\">%s<a/> - <a href=\"%s/%s?action=remove\">X<a/></li>\n",
								basePath, aDir.getName(), aDir.getName(),
								basePath, aDir.getName())
						);
				}
				data.put("listData", sb.toString());
				tr.setDataModel(data);	// velocity data set
				return tr;
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				return new StringRepresentation("Error");
			}
		}
		
	}

}
