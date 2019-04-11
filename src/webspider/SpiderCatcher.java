package webspider;
import file_core.FileStreamer;
import main_core.Core;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static main_core.Core.printLog;

public class SpiderCatcher {  
     	public String s;
		private final int MAXSIZE=1000;
     	//public ArrayList<String> list=new ArrayList<String>();
    	public SpiderCatcher(String s){      
    		this.s=s;
    	}
    	public void work(ArrayList<String> keys)
    	{
    		boolean find =false;
		StringBuilder html=new StringBuilder();
			if (keys.size()==0) find=true;
		try {
			URL url;
			url = new URL(s);

			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(1000 * 10);
			BufferedReader bufr;
			bufr = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
			String line;

			long i = 0;
			while ((line = bufr.readLine()) != null) {
				printLog(line);
				String code=deal(line);
				if (code.length()>0) html.append(code).append("\r\n");
//				FileStreamer.output(Core.getOutputPath() + File.separator + "text.txt",line+ "\r\n",true);
				i++;
				if (i >= MAXSIZE) return;
				//Matcher m = p.matcher(line);
				if (!find){
					for (String Scanner : keys) {
						if (line.contains(Scanner)) {
							{
								printLog("Fing reg in:" + url);
								FileStreamer.output(Core.getOutputPath() + File.separator + "web.txt", Scanner + "@"+select(line,Scanner) + "→" + url + "\r\n", true);
								keys = new ArrayList<>();
								find=true;
								//break;
							}
						}
					}
				}
			}
//			if (find)
			{
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
				FileStreamer.output(Core.getSpiderPath() + File.separator + sdf.format(new Date())+".txt",html.toString()+ "\r\n",true);
			}
//
		} catch (Exception e) {
			// TODO Auto-generated catch block
			printLog("[Catcher Error]==:"+ e.toString());
		}
		}

	private String deal(String html) {
//    		StringBuilder code=new StringBuilder();
    		html=html.replaceAll("<[^>]*>","");
			html=removeTab(html);
    		return html;
	}

	private String removeTab(String s)
	{
		s=s.replace("  ","");
		s=s.replace("\t","");
		s=s.replaceAll("^\\s*\\n","");
		return s;
	}
//		public boolean pd(String txt)
//    	{
//    		return true;
//    	}

		public String select(String pattern,String key)
		{
			try
			{
			String txt=pattern.substring(pattern.indexOf(key)-5, pattern.indexOf(key)+5+key.length());
			txt=txt.replaceAll(key, "["+key+"]");
			txt="..."+txt+"...";
			return txt;
			}
			catch(Exception e)
			{
				try
				{
				String txt=pattern.substring(pattern.indexOf(key), pattern.indexOf(key)+8+key.length());
				txt=txt.replaceAll(key, "["+key+"]");
				txt=txt+"...";
				return txt;
				}
				catch(Exception e2)
				{
					try
					{
					String txt=pattern.substring(pattern.indexOf(key)-8, pattern.indexOf(key)+key.length());
					txt=txt.replaceAll(key, "["+key+"]");
					txt="..."+txt;
					return txt;
					}
					catch(Exception e3)
					{
						String txt=pattern;
						txt=txt.replaceAll(key, "["+key+"]");
						return txt;
					}
				}
			}

		}

}  