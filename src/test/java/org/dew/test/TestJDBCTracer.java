package org.dew.test;

import org.dew.jdbc.Tracer;

import org.dew.jdbc.impl.SystemOutTracer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestJDBCTracer extends TestCase {
	
	public TestJDBCTracer(String testName) {
		super(testName);
	}
	
	public static Test suite() {
		return new TestSuite(TestJDBCTracer.class);
	}
	
	public void testApp() {
		Tracer tracer = new SystemOutTracer();
		tracer.traceRem("Remark");
		tracer.trace("Message");
	}
	
}
