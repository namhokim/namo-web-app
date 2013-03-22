package com.tistory.namocom.drive.helper;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.representation.FileRepresentation;

public class FileRepresentationHelper {
	
	private FileRepresentationHelper() 	{
	}
	
	public static FileRepresentation get(
			String resource, MediaType mediaType) throws FileNotFoundException 	{
		
		try {
			String raw_fn = FileRepresentationHelper.class.getResource(resource).getPath();
			String dec_fn = URLDecoder.decode(raw_fn, CharacterSet.UTF_8.toString());
			if(dec_fn.startsWith("/") && dec_fn.length()>2) {
				dec_fn = dec_fn.substring(1);
			}
			String path = FILE_SCHEME + raw_fn;
			return new FileRepresentation(path, mediaType);	// OK
		} catch(NullPointerException e) {
			throw new FileNotFoundException();
		} catch(UnsupportedEncodingException e) {	// impossible
			return null;
		}
	}
	
	private static final String FILE_SCHEME = "file://";

}
