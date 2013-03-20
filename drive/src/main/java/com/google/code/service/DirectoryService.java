package com.google.code.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.code.service.model.FileDirectory;

public class DirectoryService {
	
	private File rootFile;
	private Boolean enumerated = new Boolean(false);
	private List<FileDirectory> fileList, dirList;

	public void setRootPath(String rootPath) {
		rootFile = new File(rootPath);
	}
	
	public String getRootPath() {
		return this.rootFile.getPath();
	}
	
	public boolean isValidRoot() {
		return rootFile.exists();
	}
	
	public List<FileDirectory> getFiles()
	{
		if (!enumerated) {
			enumerate();
		}
		return fileList;
	}
	
	public List<FileDirectory> getDirectories()
	{
		if (!enumerated) {
			enumerate();
		}
		return dirList;
	}
	
	private void enumerate()
	{
		synchronized(enumerated)
		{
			if (!this.isValidRoot()) return;
			
			fileList = new ArrayList<FileDirectory>();
			dirList = new ArrayList<FileDirectory>();

			for(File f : rootFile.listFiles())
			{
				if(f.isFile()) {
					fileList.add(new FileDirectory(f.getAbsolutePath()));
				} else if (f.isDirectory()) {
					dirList.add(new FileDirectory(f.getAbsolutePath()));
				}
			}

			enumerated = true;
		}
		
	}
	
	public static void main(String args[])
	{
		DirectoryService ds = new DirectoryService();
		ds.setRootPath("C:\\");
		for(FileDirectory fd : ds.getFiles())
		{
			System.out.println(fd.getAbsolutePath());
		}
		
		for(FileDirectory fd : ds.getDirectories())
		{
			System.out.println(fd.getAbsolutePath());
		}
	}

}
