package Servlet;


import compare_core.StringCompare;
import main_core.Core;
import mechine_learning.LearnProject;
import webspider.SpiderWebsite;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static main_core.Core.*;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
//	private DecimalFormat df = new DecimalFormat("#.00");

	Servlet()
	{
		Core.init();
	}
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

	public void deal(String input) {
		String[] cmds=new String[]{"compare","compare_inGroup","compare_betweenGroup","compare_toGroup",
				"set","get","log","init","learn","check","draw","training","spider","compare_online"};
		try
		{
			String[] cmd=input.split(" ");
			switch (cmd[0])
			{
				case "compare":{
					if (cmd.length==3)
					{
						printSys("Result="+compare(cmd[1],cmd[2]));
						break;
					}
					else
					{
						printErr("'compare' should with 2 parameters");
						break;
					}

				}
				case "compare_online":{
					if (cmd.length==2)
					{
						printSys("Result="+compare(cmd[1]));
						break;
					}
					else
					{
						printErr("'compare_online' should with 1 parameters");
						break;
					}
				}
				case "draw":{
					if (cmd.length==2)
					{
						createDiagram=true;
						check(cmd[1]);
						printSys("Finished");
						createDiagram=false;
						break;
					}
					else
					{
						printErr("'draw' should with 1 parameters");
						break;
					}
				}
				case "compare_inGroup":{
					if (cmd.length==2)
					{
						printSys("Result="+compare_inGroup(cmd[1]));
						break;
					}
					else
					{
						printErr("'compare_inGroup' should with 1 parameter");
						break;
					}
				}
				case "compare_betweenGroup":{
					if (cmd.length==3)
					{
						printSys("Result="+compare_betweenGroup(cmd[1],cmd[2]));
						break;
					}
					else
					{
						printErr("'compare_betweenGroup' should with 2 parameters");
						break;
					}
				}
				case "compare_toGroup":{
					if (cmd.length==3)
					{
						printSys("compare_toGroup="+compare_toGroup(cmd[1],cmd[2]));
						break;
					}
					else
					{
						printErr("'compare' should with 2 parameters");
						break;
					}
				}
				case "set":{
					if (cmd.length==3)
					{
						printSys("Result="+Core.set(cmd[1],cmd[2]));
						break;
					}
					else
					{
						printErr("'set' should with 2 parameters");
						break;
					}

				}
				case "get":{
					if (cmd.length==2)
					{
						printSys("Result="+Core.get(cmd[1]));
						break;
					}
					else
					{
						printErr("'get' should with 1 parameters");
						break;
					}

				}
				case "init":{
					if (cmd.length==1)
					{
						Core.init();
						printSys("Finished");
						break;
					}
					else
					{
						printErr("'init' should with no parameter");
						break;
					}
				}
				case "log": {
					if (cmd.length==2)
					{
						switch (cmd[1]) {
							case "start":
								printLog = true;
								break;
							case "stop":
								printLog = false;
								break;
							case "state":
								printSys(printLog);
								break;
							default:
								printErr("'log' should append with 'start','stop' or 'state'");
								break;
						}
						break;
					}
					else
					{
						printErr("'log' should with 1 parameter['start','stop' or 'state']");
						break;
					}

				}
				case "learn_init":{
					if (cmd.length==1)
					{
						LearnProject.init();
						printSys("Finished");
						break;
					}
					else
					{
						printErr("'learn_init' should with no parameter");
						break;
					}
				}
				case "learn_add":{
					//TODO
					if (cmd.length==4)
					{
						LearnProject.addData(cmd[1],cmd[2],Boolean.parseBoolean(cmd[3]));
						printSys("Finished");
						break;
					}
					else
					{
						printErr("'learn_add' should with 3 parameters:path1,path2 and result(boolean)");
						break;
					}
				}
				case "learn_training":{
					if (cmd.length==2)
					{
						int time;
						try
						{
							time=Integer.parseInt(cmd[1]);
						}catch (Exception e)
						{
							printErr("train time should be integer.");
							break;
						}
						LearnProject.training(time);
						printSys("Finished");
						break;
					}
					else
					{
						printErr("'learn_training' should with 1 parameter");
						break;
					}
				}
				case "check":{
					if (cmd.length==1)
					{
						printSys("Connect success.");
						break;
					}
					else
					{
						printErr("'check' should with no parameter");
						break;
					}
				}
				case "spider":
					if (cmd.length==3)
					{
						SpiderWebsite w=new SpiderWebsite();
						w.extractLinks(cmds[1],Long.valueOf(cmds[2]));
						printSys("Spider start.");
						break;
					}
					else
					{
						printErr("'spider' should with 1 parameter");
						break;
					}
				case "training":
					if (cmd.length==2)
					{
						printSys(LearnProject.training(cmds[1],Integer.valueOf(cmds[2])));
						break;
					}
					else
					{
						printErr("'training' should with 2 parameter");
						break;
					}
				default: {
					double maxSimilar=0.5;
					String guess=null;
					for (String Scanner:cmds)
					{
						double similar=StringCompare.work_p(cmd[0].toLowerCase(),Scanner);
						if (similar>maxSimilar)
						{
							maxSimilar=similar;
							guess=Scanner;
						}
					}
					printErr("unknown command '"+cmd[0]+"'");
					if (guess!=null)
					{
						printSys("Do you mean '"+guess+"'?");
					}
				}
				break;
			}
		}
		catch (Exception e)
		{
			printErr("[cmd]"+e.toString());
		}

	}


	//	@Test
//	public void test()
//	{
////		String test="input=mode:{mode};file1:{file1};file2:{file2};threshold:0.6;weight:1;\n" +
////				"smartCompare:True;sizeWeight:False;reduceIndex:Default";
//		//printLog((getValue(test,"threshold"));
//		//printLog((getValue(test,"score"));
//	}


}
