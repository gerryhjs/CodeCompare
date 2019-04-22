package webspider;

import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Vector;

import static main_core.Core.printLog;

public class SpiderWebsite {


    public static Vector<String> svecOutUrl = new Vector<>();
    public static Vector<String> svecBadUrl = new Vector<>();
    public static Vector<String> svecAUrl = new Vector<>();
    public static int DEEP = 8;
    public static boolean bl;
    public static Parser parser;
    public static ArrayList<String> getHtml= new ArrayList<>();
    public static String hostName = "";
    public static int breaker;
    public static int breakTime=100;
    public static int limitPerPage=200;
    public static ArrayList<String> keys=new ArrayList<>();
    public void extractLinks(String url,long maxStep)  {
        if (maxStep<=0) return;
        SpiderCatcher c;//=new SpiderCatcher(url);

    	breaker=0;

        String str1;
        URL wwwurl;
        Vector<String> vecUrl = new Vector<>();
        try {
            parser = new Parser(url);
         //   parser.setEncoding("GBK");
            bl = true;
        } catch (Exception e) {
            bl = false;
            printLog(String.valueOf(e));
        }

       // String filterStr = "A";
        String filterStr = "a";
        try
        {
        TagNameFilter filter = new TagNameFilter(filterStr);
        NodeList links = parser.extractAllNodesThatMatch(filter);
        for (long i = 0; i < links.size(); i++) {
            if (bl) {
            	 if (breaker>=breakTime) 
                 {
                 	  printLog("Break out!");
                 	  break;
                 }
                LinkTag LinkTag = (LinkTag) links.elementAt((int) i);
                str1 = LinkTag.getLink();
                printLog(i+":"+str1);
                if (getHtml.contains(str1)) 
                	{
                	printLog("Repeat Website!");
                	  if(!url.equals(hostName))
                      {   
                   	  breaker++;
                 	   printLog(url+" BreakTime:"+breaker+"/"+breakTime);
                       if (breaker>=breakTime) 
                       {
                       	    printLog("Break out!");
                       	   	break;
                       }
                      }
                      continue;
                	}
                if (!pd(str1))
            	{
            	printLog("Unrelated Website!");
            	  if(!url.equals(hostName))
                  {   
               	   breaker++;
             	   printLog(url+" BreakTime:"+breaker+"/"+breakTime);
                   if (breaker>=breakTime) 
                   {
                   	    printLog("Break out!");
                     	break;
                   }
                
                  }
            	   continue;
            	}
                getHtml.add(str1);
                c=new SpiderCatcher(str1);
                 c.work(keys);
                printLog("Finish Catcher");
                if (str1.equals(""))
                    continue;
                if (!svecAUrl.contains(str1)) 
                {
                    try {
                        wwwurl = new URL(str1);
                        URLConnection con = wwwurl.openConnection();
                        con.setConnectTimeout(1000);
                        con.getInputStream();
                    } catch (SocketTimeoutException e) {
                		printLog("[Bad Connect]:"+ e.toString());
                        svecBadUrl.add(str1);
                        if(!url.equals(hostName))
                        {   
                     	   breaker++;
                            printLog(url+" BreakTime:"+breaker+"/"+breakTime);
                         if (breaker>=breakTime) 
                         {
                             printLog("Break out!");
                           	break;
                         }
                     
                        }
                        continue;
                    } catch (Exception e) {
                        printLog("[URL Error]:"+ e.toString());
                    	  if(!url.equals(hostName))
                          {   
                       	   breaker++;
                              printLog(url+" BreakTime:"+breaker+"/"+breakTime);
                           if (breaker>=breakTime) 
                           {
                               printLog("Break out!");
                         	break;
                           }
                       
                          }
                    	    continue;
               
                    }
                    //if (GetHostName(str1).equals(hostName))
                    if(url.length()>0)
                    {
                        svecAUrl.add(str1);
                        vecUrl.add(str1);
                    } 
                    else {
                        svecOutUrl.add(str1);
                    }
                }
            }
        }
        }
        catch (Exception e) {
        	  if(!url.equals(hostName))
              {   
           	   breaker++;
                  printLog(url+" BreakTime:"+breaker+"/"+breakTime);
               if (breaker>=limitPerPage) 
               {
                   printLog("Ready to Break!");
               }
           
              }
        }

        String strNew;
        int b = 1;
        if (b <= DEEP) {

            for (int i = 0; i < vecUrl.size(); i++) {
            	if ((i>=breakTime) && (!url.equals(hostName))) 
            	{
            		printLog("Break next");
            		break;
            	}
                strNew = vecUrl.get(i);
                extractLinks(strNew,maxStep-1);
                //Website w=new Website();
                //w.extractLinks(strNew);
            }
        }

    }
    public void addKey(String key)
    {
        keys.add(key);
    }
    public boolean pd(String url)
    {
        return url.length() != 0;
    }
//    public static String GetHostName(String host) {
//        URL aurl;
//        String ss = " ";
//        try {
//            aurl = new URL(host);
//            ss = aurl.getHost();
//            ss = ss.substring(ss.length() - 10);
//        } catch (Exception e) {
//           // e.printStackTrace();
//        	printWarn(String.valueOf(e));
//        }
//        return ss;
//    }
    


}