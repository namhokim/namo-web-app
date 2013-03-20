package com.google.code.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.domain.UploadItem;

@Controller(value = "/upload")
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Inject private FileSystemResource fsResource;
	
	@RequestMapping(method = RequestMethod.GET)
	public String uploadForm(Locale locale, Model model) {
		logger.debug("Upload Form. The client locale is {}.", locale);
		return "upload";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String uploadData(@ModelAttribute UploadItem uploadItem, BindingResult result) {
		//logger.debug("Upload Data. The client locale is {}.", locale);
		if (result.hasErrors())
	    {
	      for(ObjectError error : result.getAllErrors())
	      {
	        System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
	      }
	      return "upload/uploadForm";
	    }

		if(!uploadItem.getFileData().isEmpty()){
			String filename = uploadItem.getFileData().getOriginalFilename();
			String imgExt = filename.substring(filename.lastIndexOf(".")+1, filename.length());

			//upload 가능한 파일 타입 지정
			if(imgExt.equalsIgnoreCase("JPG") || imgExt.equalsIgnoreCase("JPEG") || imgExt.equalsIgnoreCase("GIF")){
				byte[] bytes = uploadItem.getFileData().getBytes();
				try{
	  			     File lOutFile = new File(fsResource.getPath()+"_"+filename);
				     FileOutputStream lFileOutputStream = new FileOutputStream(lOutFile);
				     lFileOutputStream.write(bytes);
				     lFileOutputStream.close();
				}catch(IOException ie){
					//Exception 처리
					System.err.println("File writing error! ");
				}
				System.err.println("File upload success! ");
			}else{
				System.err.println("File type error! ");
			}
		}

	    // Some type of file processing...
	    System.err.println("-------------------------------------------");
	    System.err.println("Test upload: " + uploadItem.getName());
	    System.err.println("Test upload: " + uploadItem.getFileData().getOriginalFilename());
	    System.err.println("-------------------------------------------");

	    //return "redirect:/index.jsp";
		return "redirect:list";
	}

}
