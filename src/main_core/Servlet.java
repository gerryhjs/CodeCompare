package main_core;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static main_core.Cmd.deal;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
//	private DecimalFormat df = new DecimalFormat("#.00");

//	public Servlet()
//	{
//		Cmd.load();
//	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String input = request.getParameter("input");

		Core.setServletWriter(out);
		out.write("\r\n");
		System.out.println("Recv:"+input);
//		printSys("input="+input);
		String[] inputs=input.split(";");
		for (String Scanner:inputs)
		deal(Scanner);



		out.close();
	}



}
