package com.doering.hausaufgabe.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FileHelper {
	/**
	 * Hilfsmethode zum Auslesen von Dateien
	 * @param file Auszulesende Datei
	 * @return String-Inhalt der Datei
	 */
	public String readFile(File file) {
		StringBuffer sb = new StringBuffer();
		try{
			FileReader fr = new FileReader(file);
		    BufferedReader br = new BufferedReader(fr);
	
		    String zeile = br.readLine();
		    while( zeile != null ) {
		    	sb.append(zeile.trim()+"\n");
		    	zeile = br.readLine();
		    }
	
		    br.close();
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return sb.toString();
	}
	/**
	 * Hilfsmethode zum auslesen eines JSON
	 * @param rd Reader, welcher JSON öffnet
	 * @return String Inhalt des JSON
	 * @throws IOException
	 */
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
	/**
	 * Hilfsmethode zum Auslesen eines JsonArray von einer URL
	 * @param url von der das JsonArray gelesen wird
	 * @return JSONArray Objekt
	 * @throws IOException
	 * @throws JSONException
	 */
	  public JSONArray readJsonArrayFromUrl(String url) throws IOException {
		    InputStream is = new URL(url).openStream();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONArray json = new JSONArray(jsonText);
		      return json;
		    } catch(JSONException ex) {
		    	ex.printStackTrace();
		    } finally {
		      is.close();
		    }
			return null;
	  }
	  /**
	   * Hilfsmethode zum Auslesen eines JsonObjektes von einer URL
	   * @param url von der das JsonObjekt gelesen wird
	   * @return JSONObject Objekt
	   * @throws IOException
	   * @throws JSONException
	   */
	  public JSONObject readJsonObjectFromUrl(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONObject json = new JSONObject(jsonText);
		      return json;
		    } finally {
		      is.close();
		    }
	  }
	  
	  /**
	   * Hilfsmethode um alle HTML-Tags eines Strings zu ersetzen
	   * @param html der String, welcher verändert werden soll
	   * @return String ohne HTML-Tags
	   */
	  public String replaceHTML(String html) {
		  return html.replaceAll("<[^>]*>", " ");
	  }
	  /**
	   * Hilfsmethode um alle Zahlen eines Strings zu ersetzen
	   * @param html
	   * @return
	   */
	  public String replaceAllNumbers(String html) {
		  return html.replaceAll("\\d", " ");
	  }
	  
	  public String replaceAllPunctuation(String html) {
		  return html.replaceAll("[\\p{Punct}&&[^_-]]+", " ") 
				  .replaceAll("/\\?", " ")
				  .replaceAll("\n", " ")
				  .replaceAll("\\u201C", " ")
				  .replaceAll("\\u201E", " ")
				  .replaceAll("\\u2013", " ")
				  .replaceAll("\\u201A", " ");
	  }
	  
	  public String replaceAllSpaces(String html) {
		  return html.replaceAll("  ", " ");
	  }
	  
	  public Map<String, Integer> wordCountMap(String[] strings) {
		  Map<String, Integer> map = new HashMap<String, Integer> ();
		  for (String s: strings) {
		    if(s.equals("")) continue;
		    
		    if (!map.containsKey(s)) { 
		      map.put(s, 1);
		    }
		    else {
		      int count = map.get(s);
		      map.put(s, count++);
		    }
		  }
		  return map;
		}
}
