package org.dew.jdbc;

import java.util.*;

public 
class TMap extends HashMap<Integer,Object> 
{
	private static final long serialVersionUID = 1L;
	
	protected String sDBMS;
	
	public TMap(String sDBMS) {
		this.sDBMS = sDBMS; 
	}
	
	public String transSQL(String sSQL) {
		int iParam = 0;
		StringBuffer result = new StringBuffer(sSQL.length());
		char c;
		boolean bInAString = false;
		for (int i = 0; i < sSQL.length(); i++) {
			c = sSQL.charAt(i);
			if (c == '\'') bInAString = !bInAString;
			if (c == '?' && !bInAString) {
				iParam++;
				String sValue = getString(this.get(new Integer(iParam)));
				if (sValue != null) {
					result.append(sValue);
				} else {
					result.append("NULL");
				}
			} else {
				result.append(c);
			}
		}
		return result.toString();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("{");
		List<Integer> listKeys = new ArrayList<Integer>();
		Iterator<Integer> itKeys = this.keySet().iterator();
		while (itKeys.hasNext()) {
			listKeys.add(itKeys.next());
		}
		Collections.sort(listKeys);
		int iSize = listKeys.size();
		for (int i = 0; i < iSize; i++) {
			Object oKey = listKeys.get(i);
			String sValue = getString(this.get(oKey));
			if (i < iSize - 1)
				sb.append(oKey + "=" + sValue + ", ");
			else
				sb.append(oKey + "=" + sValue);
		}
		sb.append('}');
		return sb.toString();
	}
	
	private String getString(Object obj) {
		if (obj == null) return null;
		if (obj instanceof String) {
			return doubleQuotes(obj.toString());
		} else if (obj instanceof Date) {
			return dateTimeToSQL((Date) obj);
		}
		return obj.toString();
	}
	
	private static String doubleQuotes(String text) {
		StringBuffer result = new StringBuffer(text.length());
		char c;
		for (int i = 0; i < text.length(); i++) {
			c = text.charAt(i);
			if (c == '\'') result.append('\'');
			result.append(c);
		}
		return "'" + result.toString() + "'";
	}
	
	protected String dateTimeToSQL(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int iYear    = cal.get(java.util.Calendar.YEAR);
		int iMonth   = cal.get(java.util.Calendar.MONTH) + 1;
		int iDay     = cal.get(java.util.Calendar.DAY_OF_MONTH);
		int iHour    = cal.get(Calendar.HOUR_OF_DAY);
		int iMinute  = cal.get(Calendar.MINUTE);
		int iSecond  = cal.get(Calendar.SECOND);
		String sMonth  = iMonth  < 10 ? "0" + iMonth  : String.valueOf(iMonth);
		String sDay    = iDay    < 10 ? "0" + iDay    : String.valueOf(iDay);
		String sHour   = iHour   < 10 ? "0" + iHour   : String.valueOf(iHour);
		String sMinute = iMinute < 10 ? "0" + iMinute : String.valueOf(iMinute);
		String sSecond = iSecond < 10 ? "0" + iSecond : String.valueOf(iSecond);
		if(sDBMS != null && sDBMS.equalsIgnoreCase("oracle")) {
			return "TO_DATE('" + iYear + sMonth + sDay + " " + sHour + sMinute + sSecond + "','YYYYMMDD HH24MISS')";
		}
		return "'" + iYear + "-" + sMonth + "-" + sDay + " " + sHour + ":" + sMinute + ":" + sSecond + "'";
	}
}