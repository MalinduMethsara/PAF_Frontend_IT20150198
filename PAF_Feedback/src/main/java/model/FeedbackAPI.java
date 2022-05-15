package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FeedbackAPI
 */
@WebServlet("/FeedbackAPI")
public class FeedbackAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Feedback noteObj = new Feedback();
	
    public FeedbackAPI() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String output = noteObj.insertNotes(request.getParameter("FeedbackID"),
				        request.getParameter("UserID"),
				 		request.getParameter("UserName"),
				 		request.getParameter("Subject"),
				 		request.getParameter("Date"),
				 		request.getParameter("Comment"));
		response.getWriter().write(output); 
	}
	
	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
	
		try
		{
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ?
					scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			String[] params = queryString.split("&");
			 
			for (String param : params)
			{ 
				 String[] p = param.split("=");
				 map.put(p[0], p[1]);
			}
		 }
		catch (Exception e)
		{
		}
		return map;
		}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request);
		String output = noteObj.updateNotes(paras.get("hidItemIDSave").toString(),
				        paras.get("FeedbackID").toString(),
						paras.get("UserID").toString(),
						paras.get("UserName").toString(),
						paras.get("Subject").toString(),
						paras.get("Date").toString(),
						paras.get("Comment").toString());
		response.getWriter().write(output); 
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request);
		String output = noteObj.deleteNotes(paras.get("noticeID").toString());
		response.getWriter().write(output); 
	}

}
