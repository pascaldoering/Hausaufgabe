package com.doering.hausaufgabe.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import com.doering.hausaufgabe.helper.FileHelper;

/**
 * Servlet implementation class InternateAbfrageBeitrag
 */
@WebServlet("/InternateAbfrageBeitrag")
public class InternateAbfrageBeitrag extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InternateAbfrageBeitrag() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FileHelper fh = new FileHelper();
		String id = request.getParameter("ID");
		
//		System.out.println("AbfrageBeitrag: id: "+id);
		
		try {
			JSONArray textArr = fh.readJsonArrayFromUrl("https://www.internate.org/wp-json/wp/v2/posts?_fields[]=content&per_page=1&include="+id);
			
			if(textArr != null) {
				JSONObject text = (JSONObject) textArr.get(0);
				
				String blog = text.getJSONObject("content").getString("rendered");
				
				String[] arr = fh.replaceAllSpaces(fh.replaceAllPunctuation(fh.replaceAllNumbers(fh.replaceHTML(blog)))).split(" ");
				
				Map<String, Integer> map = fh.wordCountMap(arr);
				
				String mapJson = JSONValue.toJSONString(map);
				
				response.setCharacterEncoding("UTF-8");
				
				response.getWriter().append(mapJson);
			}
			
		} catch(JSONException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
