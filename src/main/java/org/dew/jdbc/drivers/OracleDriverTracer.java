package org.dew.jdbc.drivers;

import org.dew.jdbc.*;
import org.dew.jdbc.impl.*;

import java.io.File;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.DriverPropertyInfo;
import java.sql.Driver;
import java.sql.DriverManager;

public class OracleDriverTracer implements Driver {
	private static int iCount = 0;
	private static final String sPREFIX = "jdbc:dew:";
	private static String sDefFileName = System.getProperty("user.home") + File.separator + "oracle_trace.sql";

	private static Driver m_defaultDriver;

	static {
		try {
			m_defaultDriver = (Driver) Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			DriverManager.registerDriver(new OracleDriverTracer());
		}
		catch (Exception ex) {
			System.err.println("[OracleDriverTracer] init: " + ex);
		}
	}

	public Connection connect(String sURL, Properties oInfo) throws java.sql.SQLException {
		Tracer tracer = getDefaultTracer();
		tracer.traceRem("[OracleDriverTracer.connect URL = " + sURL + ", oInfo = " + oInfo + "]");
		Connection conn = null;
		String sTag = null;
		try {
			String sRealURL = getRealURL(sURL);
			conn = m_defaultDriver.connect(sRealURL, oInfo);
			iCount++;
			sTag = getTag(sRealURL);
			String sTextRem = "[Connection " + sTag;
			sTextRem += " opened at " + getCurrentDate();
			sTextRem += " URL = " + sRealURL;
			sTextRem += ", Info = " + oInfo;
			sTextRem += ", AutoCommit = " + conn.getAutoCommit();
			sTextRem += "]";
			tracer.traceRem(sTextRem);
		} catch (SQLException ex) {
			tracer.traceException(ex);
			throw ex;
		}

		return new TConnection(conn, sTag, tracer, "oracle");
	}

	public boolean acceptsURL(String sURL) throws java.sql.SQLException {
		if (sURL == null)
			return false;
		return sURL.startsWith(sPREFIX);
	}

	public DriverPropertyInfo[] getPropertyInfo(String sURL, Properties oInfo) throws java.sql.SQLException {
		return m_defaultDriver.getPropertyInfo(getRealURL(sURL), oInfo);
	}

	public int getMajorVersion() {
		return m_defaultDriver.getMajorVersion();
	}

	public int getMinorVersion() {
		return m_defaultDriver.getMinorVersion();
	}

	public boolean jdbcCompliant() {
		return true;
	}

	private static String getRealURL(String sURL) {
		return "jdbc:oracle:" + sURL.substring(sPREFIX.length());
	}

	private String getTag(String sUrl) {
		return "C" + iCount;
	}

	private Tracer getDefaultTracer() {
		Tracer tracer = null;
		try {
			tracer = new FileTracer(sDefFileName, "-- ");
		} catch (Exception ex) {
			tracer = new NullTracer();
		}
		return tracer;
	}

	private static String getCurrentDate() {
		Calendar cal = new GregorianCalendar();
		int iYear = cal.get(java.util.Calendar.YEAR);
		int iMonth = cal.get(java.util.Calendar.MONTH) + 1;
		int iDay = cal.get(java.util.Calendar.DAY_OF_MONTH);
		int iHour = cal.get(Calendar.HOUR_OF_DAY);
		int iMinute = cal.get(Calendar.MINUTE);
		int iSecond = cal.get(Calendar.SECOND);
		String sMonth = iMonth < 10 ? "0" + iMonth : String.valueOf(iMonth);
		String sDay = iDay < 10 ? "0" + iDay : String.valueOf(iDay);
		String sHour = iHour < 10 ? "0" + iHour : String.valueOf(iHour);
		String sMinute = iMinute < 10 ? "0" + iMinute : String.valueOf(iMinute);
		String sSecond = iSecond < 10 ? "0" + iSecond : String.valueOf(iSecond);
		return iYear + "-" + sMonth + "-" + sDay + " " + sHour + ":" + sMinute + ":" + sSecond;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return m_defaultDriver.getParentLogger();
	}
}