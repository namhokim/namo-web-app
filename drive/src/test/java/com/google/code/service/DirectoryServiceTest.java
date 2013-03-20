package com.google.code.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DirectoryServiceTest {
	
	private DirectoryService ds;

	@Before
	public void setUp() throws Exception {
		ds = new DirectoryService();
	}

	@Test
	public void createTest() {
		assertNotNull(ds);
	}
	
	@Test
	public void setRootPathTest() {
		ds.setRootPath("C:\\");
		assertEquals("C:\\", ds.getRootPath());
		
		ds.setRootPath("C:/");
		assertEquals("C:\\", ds.getRootPath());
		
		ds.setRootPath("D:/");
		assertEquals("D:\\", ds.getRootPath());
	}
	
	@Test
	public void isValieRootTest() {
		String path = "C:/";
		ds.setRootPath(path);
		assertTrue(ds.isValidRoot());
		
		String path2 = "CC:/";
		ds.setRootPath(path2);
		assertFalse(ds.isValidRoot());
	}

}
