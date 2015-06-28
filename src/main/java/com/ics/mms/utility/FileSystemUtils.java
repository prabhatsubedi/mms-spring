package com.ics.mms.utility;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class FileSystemUtils extends UtilsBase{

	/*
	 * return true if path already existed
	 * return false if path is not existed
	 */
	public boolean createDirectoryIfNotExisted(String path){

		boolean result = false;
		
		File theDir = new File(path);

		// if the directory does not exist, create it
		if (!theDir.exists())
		{
		    logger.info("creating directory: " + path);
		    try {
				FileUtils.forceMkdir(theDir);
				result = false;
			} catch (IOException e) {
				logger.error("error", e);
			}
		}else{
			result = true;
		}
		
		return result;
	}
	
	public boolean deleteFile(String path){
		File file = new File(path);
		
		boolean result = false;
		
		try {
			FileUtils.forceDelete(file);
			result = true;
		} catch (IOException e) {
			logger.error("IOException", e);
		}
		
		return result;
	}
}
