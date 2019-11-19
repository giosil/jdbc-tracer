package org.dew.jdbc.impl;

import org.dew.jdbc.Tracer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class FileTracer implements Tracer {

	protected PrintWriter printWriter;
	protected FileOutputStream fileOutputStream;
	protected String sCommentTag = "-- ";

	public FileTracer(String sFileName) throws Exception {
		fileOutputStream = new FileOutputStream(sFileName, true);
		printWriter = new PrintWriter(fileOutputStream);
	}

	public FileTracer(String sFileName, String sCommentTag) throws Exception {
		this(sFileName);
		this.sCommentTag = sCommentTag;
	}
	
	@Override
	public void traceRem(String sText) {
		if (sCommentTag == null)  return;
		printWriter.println(sCommentTag + sText);
		printWriter.flush();
	}
	
	@Override
	public void trace(String sText) {
		printWriter.println(sText);
		printWriter.flush();
	}
	
	@Override
	public void traceException(Throwable throwable) {
		String sMessage = null;
		if (sCommentTag != null) {
			sMessage = sCommentTag + "Exception: ";
			sMessage += throwable.getMessage();
		} else {
			sMessage = "[Exception: ";
			sMessage += throwable.getMessage() + "]";
		}
		printWriter.println(sMessage);
		printWriter.flush();
	}

	public void finalize() {
		try {
			printWriter.close();
			fileOutputStream.close();
		} 
		catch (IOException ex) {
		}
	}
}