package nl.hypothermic.btcs;

import javax.swing.*;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * ResourceManager.java
 * @author hypothermic
 */

public final class ResourceManager {
	
	/**
	 * Extract a file from the JAR into the user's filesystem.
	 * @param origin
	 * @param destination
	 */
	public final void extract(String origin, File destination) {
		if (!destination.exists()) {
			InputStream link = (getClass().getResourceAsStream(origin));
			try {
				Files.copy(link, destination.getAbsoluteFile().toPath());
			} catch (IOException e) {
				System.err.println("BTCS: Failed to unpack resources:" + Launcher.LS);
				e.printStackTrace();
			}
			try {
				if (link != null) link.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Unzip a file within the user's filesystem.
	 * @param origin
	 * @param destination
	 */
	public final void unzip(String origin, String destination) throws Exception {
	    try {
	         ZipFile x = new ZipFile(origin);
	         if (x.isEncrypted()) {
	            throw new Exception("BTCS: Resources file should not be encrypted");
	         }
	         x.extractAll(destination);
	    } catch (ZipException e) {
	    	System.err.println("BTCS: Resources file could not be unzipped:" + Launcher.LS);
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Unzip a file within the user's filesystem to the parent folder.
	 * @param file
	 */
	public final void unzip(File file) throws Exception {
	    try {
	         ZipFile x = new ZipFile(file.getAbsolutePath());
	         if (x.isEncrypted()) {
	            throw new Exception("Resources file should not be encrypted");
	         }
	         x.extractAll(file.getAbsoluteFile().getParent());
	    } catch (ZipException e) {
	    	System.err.println("BTCS: Resources file could not be unzipped:" + Launcher.LS);
	        e.printStackTrace();
	    }
	}
}