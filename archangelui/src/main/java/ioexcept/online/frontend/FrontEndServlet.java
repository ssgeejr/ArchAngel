package ioexcept.online.frontend;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;

public class FrontEndServlet extends HttpServlet {

	private String message;

	public void init() throws ServletException {
		// Do required initialization
		message = "Hello World";
		System.out.println(message);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");
		HttpURLConnection conn = null;
		StringBuffer searchresult = new StringBuffer();
		try {

			
//			if(request.getParameter("manufacturer") != null) {
				
//				String key = noNulls((String)request.getParameter("fieldUno")).equals("0")?"Manufacturer":"Marketing Name";
				String car = noNulls((String)request.getParameter("car"));
				System.out.println("Value: " + car);
				System.out.println("setting URL to: http://archangelms:8080/archangel/query");
//when linking two containers, you map to the EXPORT port not the mapped port				
//				URL url = new URL("http://archangelms:9000/archangel/query");
				URL url = new URL("http://archangelms:8080/archangel/query");
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				String input = "{\"car_model\":\"" + car + "\"}";
				System.out.println(input);
				OutputStream os = conn.getOutputStream();
				os.write(input.getBytes());
				os.flush();
				try{
					System.out.println("RESPONSE CODE: " + conn.getResponseCode());
				}catch(Exception e){
					System.out.println("RESPONSE CODE NOT FOUND");
				}
				if (conn.getResponseCode() == 200) {
					BufferedReader br = new BufferedReader(new InputStreamReader(
							(conn.getInputStream())));
					String output;
					System.out.println("Server response .... \n");
					while ((output = br.readLine()) != null) {
						System.out.println(output);
						searchresult.append(output);
					}
					
				}else {
					searchresult.append("Results not found");
				}
			    try {
			    	request.setAttribute("result", searchresult.toString());
			    	getServletConfig().getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
//			    	getServletConfig().getServletContext().getRequestDispatcher("http://localhost:9000/query").forward(request,response);
	//		    	response.sendRedirect("http://localhost:9000/query"); 
	//		        getServletConfig().getServletContext().getRequestDispatcher("/result.jsp").forward(request,response);
			    } catch (Exception e) {
			    	e.printStackTrace();
			    }
//			}//end if !null
	   } catch (MalformedURLException e) {
			e.printStackTrace();
	   } catch (IOException e) {
			e.printStackTrace();
	  }finally{
		try{conn.disconnect();}catch(Exception cone){}
	  }
			
	}

	private String noNulls(String in){
		return (in == null)?"":in.trim();
	}
	
	public void destroy() {
		// do nothing.
	}

}
