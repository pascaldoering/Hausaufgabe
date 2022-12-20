package com.doering.hausaufgabe.servlets;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.doering.hausaufgabe.helper.FileHelper;

import com.google.gson.Gson;

/**
 * Servlet implementation class InternateAbfrage
 */
@WebServlet("/InternateAbfrage")
public class InternateAbfrage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InternateAbfrage() {
		super();
		 initRunnable();
	}
	
	private ArrayList<Integer> ids = new ArrayList<Integer>();
	
	private Timer timer = new Timer();
	
	private boolean isRunning = false;

	private void loadIDs() throws JSONException, IOException {
		System.out.println("loadIDs aufgerufen");
		FileHelper fh = new FileHelper();
		
		JSONArray json = fh.readJsonArrayFromUrl("https://www.internate.org/wp-json/wp/v2/posts?_fields[]=id&per_page=100");
		
		for(Object ob : json) {			
			if(ob instanceof JSONObject && ((int)(Math.random()*100)) % 2 == 0) { 	//Dieser Teil wurde zum testen des Nachladens hinzugefuegt um zufaellig die IDs der Liste hinzuzufuegen
			//if(ob instanceof JSONObject) {		//Mit dieser Zeile werden alle IDs geladen
				JSONObject jsonOb = (JSONObject) ob;
				String id = ((Integer) jsonOb.get("id")).toString();

				if(!ids.contains(Integer.parseInt(id))) {
					ids.add(Integer.parseInt(id));
					System.out.println("id: "+id+" added");
				}
			}
		}
	}
	
	public void initRunnable() {
		int begin = 10;
		int timeinterval = 30000;
		if(!isRunning) {
			isRunning = true;
			timer.scheduleAtFixedRate(new TimerTask() {
				  @Override
				  public void run() {
					  try {
						loadIDs();
					} catch (JSONException | IOException ex) {
						ex.printStackTrace();
					}
				  }
			},begin, timeinterval);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, JSONException {
		System.out.println("doGet aufgerufen mit ids: "+ids);
		
		String result = new Gson().toJson(ids);
		
		response.setContentType("aplication/json");
		response.getWriter().append(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
