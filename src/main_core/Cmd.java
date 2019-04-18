package main_core;

import compare_core.StringCompare;
import mechine_learning.LearnProject;
import webspider.SpiderWebsite;

import java.util.Scanner;

import static main_core.Core.*;
import static main_core.Core.printErr;

public class Cmd {
    public static void  main(String[] args)
    {

        deal("check");
        Scanner scanner= new Scanner(System.in);
        for(int i=0;i<=99;i++)
        {
            System.out.print(">");
            deal(scanner.nextLine());
        }
    }

    public static void deal(String input) {
        String[] cmds=new String[]{"compare","compare_inGroup","compare_betweenGroup","compare_toGroup",
                "set","get","log","init","check","draw","training","spider","compare_online"};
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
                    else if (cmd.length==2)
                    {
                        printSys("Result="+compare_inGroup(cmd[1]));
                        break;
                    }
                    else
                    {
                        printErr("'compare' should with 2 or 1 parameters");
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
                        printErr("'compare_toGroup' should with 2 parameters");
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
//                case "learn_init":{
//                    if (cmd.length==1)
//                    {
//                        LearnProject.init();
//                        printSys("Finished");
//                        break;
//                    }
//                    else
//                    {
//                        printErr("'learn_init' should with no parameter");
//                        break;
//                    }
//                }
//                case "learn_add":{
//                    if (cmd.length==4)
//                    {
//                        LearnProject.addData(cmd[1],cmd[2],Boolean.parseBoolean(cmd[3]));
//                        printSys("Finished");
//                        break;
//                    }
//                    else
//                    {
//                        printErr("'learn_add' should with 3 parameters:path1,path2 and result(boolean)");
//                        break;
//                    }
//                }
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
                    if (cmd.length==3)
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
                        double similar= StringCompare.work_p(cmd[0].toLowerCase(),Scanner);
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

}
