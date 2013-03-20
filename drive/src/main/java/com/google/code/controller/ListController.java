package com.google.code.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class ListController {
	private static final Logger logger = LoggerFactory.getLogger(ListController.class);
	
	@RequestMapping(value = {"/list", "/list/{file}"}, method = RequestMethod.GET)
	public String list(@PathVariable String file) {
		
		if(file!=null) logger.info("List. The request file is {}.", file);
		else logger.info("List Root.");
		
		return "list";
	}
	
}
