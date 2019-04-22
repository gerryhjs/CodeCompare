package main_core;

import compare_core.StringCompare;
import file_core.FileStreamer;
import mechine_learning.LearnProject;
import webspider.SpiderWebsite;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

import static main_core.Core.*;

public class Cmd {
    private static final String settingPath=new File("").getAbsolutePath()+File.separator+"setting.xml";
    private static final String parameterPath=new File("").getAbsolutePath()+File.separator+"parameter.xml";
    public static void  main(String[] args)
    {
        load();
        deal("hello");
        Scanner scanner= new Scanner(System.in);
        for(int i=0;i<=999;i++)
        {
            System.out.print(">");
            deal(scanner.nextLine());
        }
    }

    public static void deal(String input) {
        printSys("run>>"+input);
        String[] cmds=new String[]{"compare","compare_inGroup","compare_betweenGroup","compare_toGroup",
                "log","init","hello","check","training","spider","compare_online",
                "quickCompare","quickGroup","quickCheck"};
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
                case "check":{
                    if (cmd.length==2)
                    {
//                        createDiagram=true;
                        check(cmd[1]);
                        printSys("Finished");
//                        createDiagram=false;
                        break;
                    }
                    else
                    {
                        printErr("'check' should with 1 parameters");
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
//                        printSys("Result="+Core.set(cmd[1],cmd[2]));
                        printSys("set is no longger support at deal");
                        break;
                    }
                    else
                    {
                        printSys("set is no longger support at deal");
//                        printErr("'set' should with 2 parameters");
                        break;
                    }

                }
                case "get":{
                    if (cmd.length==2)
                    {
//                        printSys("Result="+Core.get(cmd[1]));
                        printSys("get is no longger support at deal");
                        break;
                    }
                    else
                    {
                        printSys("get is no longger support at deal");
//                        printErr("'get' should with 1 parameters");
                        break;
                    }

                }
                case "init":{
                    if (cmd.length==1)
                    {
//                        Core.init();
                        printWarn("Init is no longer use in deal.");
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
                case "hello":{
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
                case "spider": {
                    if (cmd.length == 3) {
                        SpiderWebsite w = new SpiderWebsite();
                        w.extractLinks(cmds[1], Long.valueOf(cmds[2]));
                        printSys("Spider start.");
                        break;
                    } else {
                        printErr("'spider' should with 1 parameter");
                        break;
                    }
                }
                case "training": {
                    if (cmd.length == 3) {
                        printSys(LearnProject.training(cmds[1], Integer.valueOf(cmds[2])));
                        break;
                    } else {
                        printErr("'training' should with 2 parameter");
                        break;
                    }
                }
                case "quickCompare":{
                    String path1=selectFolder();
                    String path2=selectFolder();
                    printSys(Core.compare(path1,path2));
                    break;
                }
                case "quickGroup":{
                    String path1=selectFolder();
                    printSys(Core.compare_inGroup(path1));
                    break;
                }
                case "quickCheck":{
                    String path1=selectFolder();
                    check(path1);
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

    public static boolean set(String attr,String val)
    {
        try {
            if (attr.contains("/"))
            {
//                printLog("set empty:"+attr.replace("/",""));
                return true;
            }
            switch (attr) {
                case "df": {
                    df = new DecimalFormat(val);
                    return true;
                }
                case "suffixList": {
//                    if (!val.equals(suffixList.toString()))
//                        printWarn("Sorry.No authority edit suffixList:"+suffixList.toString());
//                    else
//                        return true;
                    suffixList= new HashSet<>();
                    try {
                        String[] s = val.substring(1, val.length() - 1).split(",");
                        suffixList.addAll(Arrays.asList(s));
                    }catch (Exception e)
                    {
                        return false;
                    }
                    return true;
                }
                case "createXls":{
                    createXls=Boolean.parseBoolean(val);
                    return true;
                }
                case "createDiagram":{
                    createDiagram=Boolean.parseBoolean(val);
                    return true;
                }
                case "model": {
                    switch (val.toLowerCase()) {
                        case "line":
                            byLines = true;
                            break;
                        case "size":
                            bySize = true;
                            break;
                        case "normal":
                            return true;
                        default:
                            printWarn("Wrong attr for model.");
                            return true;
                    }
                }
                case "dictionary":{
                    if (new File(val).isFile())
                        dictionary_path = val;
                    else
                        printWarn("Dictionary not exist!");
                    return true;
                }
                case "outputPath":{
                    if (new File(val).exists())
                        outputPath=val;
                    else
                        printWarn("No output path.Project path instead.");
                    return true;
                }
                case "dotPath":
                {
                    if (new File(val).exists())
                        dotPath=val;
                    else
                        printWarn("No dot-Path set.");
                    return true;
                }
                case "threshold":{
                    threshold= Double.parseDouble(val);
                    return true;
                }
                case "check":{
                    check_threshold= Double.parseDouble(val);
                    return true;
                }
                case "min":{
                    min_threshold = Double.parseDouble(val);
                    return true;
                }
                case "weight":{
                    edge_weight=Double.parseDouble(val);
                    return true;
                }
                case "pow":{
                    pow_dis=Double.parseDouble(val);
                    return true;
                }
                case "entropy":{
                    entropyStable=Double.parseDouble(val);
                    return true;
                }
                case "mode":{
                    mode=val;
                    return true;
                }
                case "path1":{
                    path1=val;
                    return true;
                }
                case "path2":{
                    path2=val;
                    return true;
                }
                default: {
                    printErr("Set invalid parameter:" + attr);
                    return false;
                }
            }
        }
        catch (Exception e)
        {
            printErr("Set Failed Error:"+e.toString());
            return false;
        }
    }
//    public static String get(String attr)
//    {
//        try {
//            switch (attr) {
//
//            }
//            return null;
//        }catch (Exception e)
//        {
//            printErr("get Failed:"+e.toString());
//            return null;
//        }
//    }
    public static void load()
    {
        if (load(settingPath)&& load(parameterPath))
            printSys("Auto Load xml Success.");
        else {
            printErr("Auto load xml Failed!");
//            Core.init();
        }
    }

    static boolean load(String path) {
        if (!new File(path).exists())
        {
            printWarn("Cannot file xml! Use default instead."+path);
            FileStreamer.output(settingPath,"<xml></xml>",false);
            return false;
        }
        String s=FileStreamer.input(new File(path));
        String[]vals;
        if (s != null) {
            vals = s.split("\n");
        }
        else
        {
            printWarn("Fail split val.");
            return false;
        }
        for (String Scanner:vals)
        {
            try {
            int index=Scanner.indexOf("<");
            int index2=Scanner.indexOf(">");
            int index3=Scanner.indexOf("</");
                String attr = Scanner.substring(index+1, index2);
                String val = Scanner.substring(index2+1, index3);
//            printLog("Set:"+attr+":"+val);
            if (!set(attr,val))
                printWarn("Set fail@"+attr+":"+val);
            }catch (Exception ignored)
            {

            }
        }
        return true;
    }


    private String selectFile()
    {
        return  select(false);
    }
    private static String selectFolder()
    {
        return select(true);
    }
    private static String select(boolean onlyFolder)
    {
        JFileChooser fc = new JFileChooser();
        if (onlyFolder)
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//只能选择目录
        String path;
        File f;
        int flag = 0;
        try{
            flag=fc.showOpenDialog(null);
        }
        catch(HeadlessException head){
            printErr("Open File Dialog ERROR!");
        }
        if(flag==JFileChooser.APPROVE_OPTION){
            //获得该文件
            f=fc.getSelectedFile();
            path=f.getPath();
            return path;
        }
        return null;
    }

}
