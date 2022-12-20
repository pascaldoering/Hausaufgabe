package com.doering.hausaufgabe.servlets;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.doering.hausaufgabe.helper.FileHelper;

/**
 * Servlet implementation class Index
 */
@WebServlet(
		description = "StartPage", 
		urlPatterns = { 
				"/Index",
				"/index.html", 
				"/Index.html"
		})
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		File path = new File(getServletContext().getRealPath("/"));
		
		File index = new File(path,"html/Index.html");
		FileHelper fh = new FileHelper();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.getWriter().append( fh.readFile(index));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
