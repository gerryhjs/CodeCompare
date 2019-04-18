package main_core;

import compare_core.CodeCompare;
import file_core.CodeFile;
import file_core.ExStreamer;
import file_core.FolderScanner;
import graph.Diagram;
import graph.Vertex;
import graphViz.GraphVizTest;

import java.io.File;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Saika on 2019/1/12.
 */
public abstract  class Core {
    //Setting.xml
    public static DecimalFormat df = new DecimalFormat("#.00");
    public static Set<String> suffixList=new HashSet<String>(){{add("java"); }};
    public static boolean createXls=true;
    public static boolean createDiagram=true;
    public static String dictionary_path="";
    public static String outputPath =new File("").getAbsolutePath()+File.separator+"out";
    public static String dotPath="";
    //private static final String spiderPath;
    public static boolean byLines=false;
    public static boolean bySize=false;
    //parameter.xml
    public static double edge_weight =0.5;
    public static double check_threshold =0.5;
    public static double threshold=0.5;
    public static double min_threshold=0.2;
    public static  double pow_dis=0.7;
    public static  double entropyStable=0.65;
    //final parameter
    public static final double createIndex=1.6;
    public static final double BAS_DIS=2;
    public static final double ADJ_DIS =2;
    public static final double LOW_INDEX=3;
    private static final double adj_sim2=1.2;
    private static final double pow_sim2=0.3;
    public static final double entropyToIndex=0.2;
    public static final int adjustTimes=10;
    //active setting
    public static boolean printLog=true;
    public static boolean adjust=false;
    public static PrintWriter servletWriter;
    private static String[] dictionary;
//    private static final String path0="/home/hjs/KINGSTON/check/jsp-server";
//    private static final String path1="/media/hjs/KINGSTON/check/jsp-lab";

    public static String getoutputPath() {
        return outputPath;
    }

//    private static StringBuilder log=new StringBuilder();

    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");



    public static Object obj;



    //method为方法名，value为传入参数（根据自己实际情况做修改）
//    public static boolean set(String name, String value){
//        try {
//            switch (name)
//            {
//                case("threshold"):
//                {
//                   double input;
//                   try
//                   {
//                       input=Double.valueOf(value);
//                   }catch (Exception e)
//                   {
//                       return false;
//                   }
//                   if (input>1) return false;
//                   if (input<0) return false;
//                   threshold=input;
//                   return true;
//                }
//                case("edge_weight"):
//                {
//                    double input;
//                    try
//                    {
//                        input=Double.valueOf(value);
//                    }catch (Exception e)
//                    {
//                        return false;
//                    }
//                    if (input>1) return false;
//                    if (input<0) return false;
//                    edge_weight=input;
//                    return true;
//                }
//                default:
//                {
//                    return false;
//                }
//            }
//        } catch (Exception e) {
//            printErr(e.toString());
//            return false;
//        }
//    }
//    public  static String get(String name){
//        try {
//            switch (name)
//            {
//                case("threshold"):
//                {
//                    return String.valueOf(threshold);
//                }
//                case("edge_weight"):
//                {
//                    return String.valueOf(edge_weight);
//                }
//                default:
//                {
//                    return null;
//                }
//            }
//        } catch (Exception e) {
//            printErr(e.toString());
//            return null;
//        }
//    }

    public static void printLog(Object s)
    {
        s=sdf.format(new Date())+"::"+s;
        System.out.println("[info]" + s);
        if(printLog) {
            try {
                servletWriter.write("[info]" + s + "\r\n");
            }catch (Exception ignored)
            {
            }
        }
    }
    public static void printSys(Object s)
    {
        s=sdf.format(new Date())+"::"+s;
        System.out.println("[System]" + s);
        try {
            servletWriter.write("[System]" + s + "\r\n");
        }catch (Exception ignored)
        {
        }
    }

    public static void printWarn(Object s)
    {
        s=sdf.format(new Date())+"::"+s;
        System.out.println("[Warning]" + s);
        try {
            servletWriter.write("[Warning]" + s + "\r\n");
        }catch (Exception ignored)
        {
        }
    }

    public static void printErr(Object s)
    {
        s=sdf.format(new Date())+"::"+s;
        System.out.println("[ERROR]" + s);
        try {
            servletWriter.write("[ERROR]" + s + "\r\n");
        }catch (Exception ignored)
        {
        }
    }

//    public static void printRes(Object s)
//    {
//        s=sdf.format(new Date())+":"+s;
//        System.out.println(s);
//        try {
//            servletWriter.write("[Result]" + s + "\r\n");
//        }catch (Exception ignored)
//        {
//        }
//    }

//    public static void readSetting()
//    {
//        File f = new File(Core.class.getResource(File.separator).getPath());
//        String path=f.getPath()+File.separator+"setting.txt";
//        printLog("init setting@"+path);
//        FileStreamer.input(new File(path));
//        try
//        {
//
//        }
//        catch (Exception e)
//        {
//            printWarn("[init]Setting load fail@"+e.toString());
//        }
//    }
//    public static void init()
//    {
//        printLog=false;
//        createXls=false;
//        //createDiagram=false;
//        byLines=false;
//        bySize=false;
//        edge_weight =0.5;
//        threshold=0.6;
//        check_threshold =0.6;
//        adjust=false;
//        String s= FileStreamer.input(new File(dictionary_path));
//        if (s==null)
//            dictionary= null;
//        if (s != null) {
//            dictionary=s.split(",");
//        }
//    }

//    public static double compare_cd(String s) {
//        return compare(new CodeFile(new File(s)));
//    }
    public static double compare(String s) {
        Diagram d=check(s);
        if (d != null) {
            return compare(d);
        }
        else return -1;
    }
//    private String getValue(String json, String attr)
//    {
//
//        try {
//            int index1 = json.indexOf("\""+attr+"\"");
//            String newData = json.substring(index1 + attr.length()+2);
//            int index2 = newData.indexOf("\"");
//            int index3 = newData.substring(index2+1).indexOf("\"");
//            return newData.substring(index2+1, index2+index3+1);
//        }
//        catch (Exception e)
//        {
//            return null;
//        }
//    }

    public static String getOutputPath() {
        return outputPath;
    }

    public static String getDotPath() {
        return dotPath;
    }



    private static double dis(int times) {
        return BAS_DIS+ ADJ_DIS /Math.pow(times,pow_dis);
    }

//    public static double example(){
//        init();
//        int projectSize = 2;
//        String[] paths = new String[projectSize];
//        paths[0]=path0;
//        paths[1]=path1;
//        ////  printLog(();
//        ////  printLog(("Result->" + df.format(result * 100) + "%");
//        return compare(paths[0], paths[1]);
//    }


//
//    public String test_compare()
//    {
//
//        return (compare(,0,1));
//    }

    public static boolean compare(String path0,String path1,double w,double p,double ch,double th)
    {
        pow_dis=p;
        check_threshold=ch;
        edge_weight =w;
        threshold=th;
        return compare(path0,path1)>threshold;
    }

    public static double compare(String path0, String path1)//1-1
    {
        boolean tmp=createDiagram;
        //createDiagram=false;
        int projectSize = 2;
        String[] paths = new String[projectSize];
        paths[0] = path0;
        paths[1] = path1;
        Diagram[] projects = new Diagram[projectSize];
        for (int i = 0; i < projectSize; i++) {
            projects[i] = check(paths[i]);
            if (projects[i]==null)
            {
                printErr("Project '"+paths[i]+"' is null.");
                return -1;
            }
            if (projects[i].getVertexList().size()==0)
                printWarn("Project '"+paths[i]+"' is empty size code.");
        }
        createDiagram=tmp;
        return compareDiagram(projects[0],projects[1]);
    }
    public static double compare_toGroup(String path1,String path2)//1-N
    {
        double similar = 0;
        String[] paths=new File(path2).list();
        if (!new File(path1).exists())
        {
            printErr("Project '"+path2+"' is null.");
            return -1;
        }
        if (paths==null)
        {
            printErr("Group '"+path2+"' is null.");
            return -1;
        }
        if (paths.length==0)
            printWarn("Group '"+path2+"' is empty size code.");

        for(String Scanner2:paths) {
            similar = Math.max(compare(path1, Scanner2),similar);
        }
        return similar;
    }

    public static double compare(Diagram diagram)
    {
        Set<Vertex> vertices=diagram.getVertexList();
        if (vertices.size()==0) return 0;
        double sumResult=0;
        for (Vertex Scanner:vertices)
        {
           double similar=compare(Scanner.getCf());
           printLog("similar="+similar);
           sumResult+=similar;
        }
        return sumResult/vertices.size();
    }
    public static double compare(CodeFile cf)//1-N
    {
        double similar = 0;
        File[] paths=new File(outputPath).listFiles();
        printLog(Arrays.toString(paths));
        if (paths==null)
        {
            printErr("Group '"+outputPath+"' is null.");
            return -1;
        }
        if (paths.length==0)
            printWarn("Group '"+outputPath+"' is empty size code.");

        for(File Scanner:paths)
            try {
                double s=CodeCompare.compare(cf,new CodeFile(Scanner));
                printLog("tmp:"+s);
                similar = Math.max(s, similar);
            } catch (Exception e) {
                printWarn(e.toString());
            }
        return similar;
    }

    public static String compare_inGroup(String path)//N
    {
        String s= compare_betweenGroup(path,path);
        if (s==null)
        {
            printLog("Auto change to inGroup2");
            return compare_toGroup2(path,path);
        }
        else return s;
    }
    public static String compare_toGroup2(String path1,String path2)
    {
        String[] codes1=new File(path1).list();
        String[] codes2=new File(path2).list();
        ArrayList<CodeFile> codeFiles1=new ArrayList<>();
        ArrayList<CodeFile> codeFiles2=new ArrayList<>();
        if (codes1==null)
        {
            printErr(path1+":code list is null");
            return null;
        }
        if (codes2==null)
        {
            printErr(path2+":code list is null");
            return null;
        }
        String[][] exc=new String[codes1.length+1][codes2.length+1];
        for (int i=0;i<codes1.length;i++)
            exc[i+1][0]=codes1[i];
        for (int j=0;j<codes2.length;j++)
            exc[0][j+1]=codes2[j];
        for (String Scanner:codes1)
//            if (pd(new File(Scanner)))
            codeFiles1.add(new CodeFile(new File(path1+File.separator+ Scanner)));
        for (String Scanner:codes2)
            if (pd(new File(Scanner)))
            codeFiles2.add(new CodeFile(new File(path2+File.separator+ Scanner)));

        for (int i = 0; i < codeFiles1.size(); i++)
            for (int j = 0; j < codeFiles2.size(); j++)
            {
                CodeFile Scanner1=codeFiles1.get(i);
                CodeFile Scanner2=codeFiles2.get(j);
                if (Scanner1.equals(Scanner2)) continue;
                double similar= CodeCompare.compare(Scanner1,Scanner2);
                exc[i+1][j+1]= String.valueOf(similar);
            }
        if (createXls)
        {
            new  ExStreamer("compareResult.xls").excelOut(exc);
        }
        return null;
    }

    public static String compare_betweenGroup(String path1, String path2)//N-N
    {
        if (!path1.endsWith("File.separator")) path1 += File.separator;
        if (!path2.endsWith("File.separator")) path2 += File.separator;
        String[] paths1 = new File(path1).list();
        String[] paths2 = new File(path2).list();
        StringBuilder result = new StringBuilder();
        if (paths1 == null) {
            printErr("Group '" + path1 + "' is null.");
            return null;
        }
        if (paths2 == null) {
            printErr("Group '" + path2 + "' is null.");
            return null;
        }
        if (path1.length() == 0)
            printWarn("Group '" + path1 + "' is empty size project.");

        String[][] exc = new String[paths1.length + 1][paths2.length + 1];
        for (int i=0;i<paths1.length;i++)
            exc[i+1][0]=paths1[i];

        for (int j=0;j<paths2.length;j++)
            exc[0][j+1]=paths2[j];

        for (int i = 0; i < paths1.length; i++)
            for (int j = 0; j < paths2.length; j++) {
//                printLog(i+" "+j);
                String Scanner1 = paths1[i];
                String Scanner2 = paths2[j];
                if (!Scanner1.equals(Scanner2)) {
                    double similar = compare(path1 + Scanner1, path2 + Scanner2);
                    exc[i+1][j+1] = String.valueOf(similar);
                    if (similar > threshold)
                        result.append(Scanner1).append("-").append(Scanner2).append(":").append(similar).append(";\n");
                }
            }

        if (createXls)
        {
            new  ExStreamer("compareResult.xls").excelOut(exc);
        }
        return result.toString();
    }


    private static double compareDiagram(Diagram diagram1, Diagram diagram2)
    {
        Diagram diagram=clone(diagram1);
        Set<Vertex> vertexList1=clone(diagram1.getVertexList());
        Set<Vertex> vertexList2=clone(diagram2.getVertexList());
        //筛选过滤点
        for (Vertex Scanner:diagram1.getVertexList())
            if (!checkCode(Scanner)) vertexList1.remove(Scanner);
        //匹配点相似度
        Map<Vertex,Vertex> likely=new HashMap<>();
        double sumSimilar=0;
        int index=0;
        double weightIndex=0;
         printLog("Scanning Vertex");
        String[][] values=new String[vertexList1.size()+1][4];
        for (Vertex Scanner:vertexList1)
        {
            index++;
             printLog("Looking For->"+Scanner.info() +"====="+index+"/"+vertexList1.size());
            Vertex result=null;
            Vertex like=null;
            double maxSimilar=0;
            int index2=0;
            for (Vertex Scanner2:vertexList2) {
                index2++;
                 printLog("Looking For->"+Scanner.info()+"==??==>"+Scanner2.info()+"====="+index2+"/"+vertexList2.size());
                double similar = Scanner.similar(Scanner2);

                 printLog("Similar:"+df.format(similar * 100) + "%");
                if (similar > maxSimilar) {
                    like=Scanner2;

                    if (similar> check_threshold)
                    result = Scanner2;

                    maxSimilar = similar;
                    if (maxSimilar == 1) break;
                }
            }
             if (like!=null) printLog("==>"+like.info()+":"+df.format(maxSimilar * 100) + "%");
            likely.put(Scanner, result);
            values[index - 1][0] = Scanner.info();

            values[index - 1][2] = df.format(maxSimilar * 100) + "%";

            if (result != null) {
                values[index - 1][1] = result.info();
                diagram1.getVertex(Scanner.getFileName()).setAppend("->"+result.getFileName());
            }
            else
            {
                values[index - 1][1] ="NULL";
            }


            if (byLines) weightIndex+=Scanner.getCf().getLines();
            if (bySize) weightIndex+=Scanner.getCf().getSize();
            weightIndex++;

            if (byLines) sumSimilar+=Scanner.getCf().getLines()*maxSimilar;
            if (bySize) sumSimilar+=Scanner.getCf().getSize()*maxSimilar;
            sumSimilar += maxSimilar;

                //sumSimilar+=Math.pow(max_similar,2);
        }

        if (weightIndex<=0) weightIndex=1;
        double similar1=sumSimilar/weightIndex;
        double sumSimilar2=0;
        double maxSimilar2;
        index=0;
        weightIndex=0;

//        //如果数据库来源图是爬虫获取回0 截断匹配
//        if (diagram2.getName()==null) return similar1;
        //匹配边相似度
         printLog("Scanning Edge");
        for (Vertex Scanner:vertexList1)
        {
            index++;
             printLog("Looking For->"+Scanner.info() +"====="+(index+vertexList1.size())+"/"+vertexList1.size()*2);
            Vertex like=likely.get(Scanner);
            if (like!=null)
            {
                 printLog("relate->"+like.info());
                if ((Scanner.getTo().size()>0)||(like.getTo().size()>0))
                {
                    Set<Vertex> relates=Scanner.getTo();
                    double sum=0;
                    for (Vertex Scanner2:relates)
                    {
                        try {
                             printLog("Relation1 ->"+Scanner2.info());
                            double distance1 = diagram1.getDistance(Scanner,Scanner2,LOW_INDEX);

                            Vertex like2 = likely.get(Scanner2);
                            if (like2!=null)
                                 printLog("Relation2 ->"+like2.info());

                            double distance2=diagram2.getDistance(like,like2,LOW_INDEX);

                             printLog("dis1:"+distance1+" dis2:"+distance2);

                            double similar;
                            if (distance1==distance2) similar=1;
                            else if (distance2 != -1) {
                                double diff=Math.abs(distance1-distance2);
                                double sumDis=distance1+distance2;
                                similar=1-diff/sumDis;
                                similar=similar*Math.pow(Scanner.similar(Scanner2),pow_sim2)*adj_sim2;
                                if (similar>1) similar=1;
                                //// printLog("S="+similar);
                            }
                            else similar = 0;

                            diagram.getVertex(Scanner.getFileName()).relate( diagram.getVertex(Scanner2.getFileName()),similar);

                            sum+=similar;//*(Scanner.similar(like));//+((Vertex)Scanner2).similar(like2)
                        }catch (Exception e)
                        {
                             printLog(e.toString());
                        }
                    }
//                double edge_MaxS=1d;

                    if (relates.size()>0) maxSimilar2=sum/relates.size();
                    else maxSimilar2=0;
//                    if (max_similar>edge_MaxS)
//                        max_similar=edge_MaxS;
                    // printLog(max_similar2);
                    values[index - 1][3] = df.format(maxSimilar2 * 100) + "%";
                    if (byLines)  weightIndex+=Scanner.getCf().getLines();
                    if (bySize)  weightIndex+=Scanner.getCf().getSize();
                    weightIndex++;

                    if (byLines) sumSimilar2+=Scanner.getCf().getLines()*maxSimilar2;
                    if (bySize) sumSimilar2+=Scanner.getCf().getSize()*maxSimilar2;
                    sumSimilar2+=maxSimilar2;
                     printLog("Similar:"+maxSimilar2);
                }
                else {
                    values[index - 1][3] = "NaN";
                     printLog("relate-NaN");
                    Set<Vertex> relates=Scanner.getTo();
                    for (Vertex Scanner2:relates)
                    {
                        diagram.getVertex(Scanner.getFileName()).relate( diagram.getVertex(Scanner2.getFileName()),-1);
                    }
                }
            }
            else
            {
                Set<Vertex> relates=Scanner.getTo();
                for (Vertex Scanner2:relates)
                {
                    diagram.getVertex(Scanner.getFileName()).relate( diagram.getVertex(Scanner2.getFileName()),-2);
                }
            }
        }

        double similar2;
        if (weightIndex<=0) weightIndex=1;
            similar2=sumSimilar2/(weightIndex);
        if (similar2>1) similar2=1;

        double adjust_edge=Math.tan(0.5*edge_weight*Math.PI);
        double result=(similar1+similar2* adjust_edge)/(1+ adjust_edge);
//        values[v1.size()][2]= String.valueOf(similar1);
//        values[v1.size()][3]= String.valueOf(similar2);
//        values[v1.size()][4]= String.valueOf(result);
        if (createXls) {
            ArrayList<String> attrs=new ArrayList<>();
             printLog("Similar1:"+similar1+" Similar2:"+similar2);
            attrs.add("origin");
            attrs.add("similar_target");
            attrs.add("vertex_Similar");
            attrs.add("edge_Similar");
            //  attrs.add("Similar");
            String path= "[" + diagram1.getName() + "]-[" + diagram2.getName() + "]"+"S" + df.format(result) +".xls";
            printSys(path);
            new ExStreamer(path).excelOut(vertexList1.size(), attrs, values);
        }
        if (createDiagram)
        {
            draw(diagram);
        }
        return result;
    }

    private static Diagram clone(Diagram diagram1) {
        Diagram diagram=new Diagram(diagram1.getName());
        for (Vertex Scanner:diagram1.getVertexList())
            diagram.addVertex(clone(Scanner));
        return  diagram;
    }

    private static Vertex clone(Vertex vertex) {
        return new Vertex(vertex.getCf());
    }

    private static Set<Vertex> clone(Set<Vertex> vertexList) {
        Set<Vertex> clone=new HashSet<>();
        clone.addAll(vertexList);
        return clone;
    }

    private static boolean checkCode(Vertex scanner) {
        if (scanner.getCf().getSize()<=10) return false;
        if (scanner.getCf().getLines()<=1) return false;
        return true;
    }

    public static boolean refactor(Diagram diagram)
    {
        boolean result=true;
        for (Vertex Scanner:diagram.getVertexList())
        {
            String bestBelong=Scanner.bestBelong();
            if ((bestBelong!=null)&&(!bestBelong.equals(Scanner.getPackageName()))) {
                printLog(Scanner.info() + "->" + bestBelong);
                Scanner.setPackageName(bestBelong);
                Scanner.refactor =true;
                result=false;
            }
        }
         printLog("Finish Refactor");
        return result;
    }
    public static void draw(Diagram diagram)
    {
        StringBuilder Edges= new StringBuilder();
        ArrayList<String> packageNames=new ArrayList<>();
        ArrayList<ArrayList<String>> list=new ArrayList<>();
        ArrayList<String> edges=new ArrayList<>();
        for (Vertex Scanner:diagram.getVertexList()) {
            int index = packageNames.indexOf(Scanner.getPackageName());
            if (index >= 0) {
                list.get(index).add(Scanner.getFileName());
            } else {
                packageNames.add(Scanner.getPackageName());
                ArrayList<String> newPackageCodes = new ArrayList<>();
                newPackageCodes.add(Scanner.getFileName());
                list.add(newPackageCodes);
            }
            for (Vertex Scanner2:diagram.getVertexList())
            {
                if(Scanner.getDistance(Scanner2)>0)
                {
                    boolean create=isCreate(Scanner.getCf(),Scanner2.getCf());
                    double weight=Scanner.getDistance(Scanner2);
                    StringBuilder s= new StringBuilder();
                    String style="color=black,";
                    if (!create)
                        style+="style=dashed,";
                    if ((!Scanner.getPackageName().equals(Scanner2.getPackageName()))&&(create))
                        style+="style=bold,";
                    if (weight>1)
                    s.append("\"").append(Scanner.getFileName()).append("\"").append("->").append("\"").append(Scanner2.getFileName()).append("\"").append(" ").append("[").append(style).append("label=\"").append(df.format(weight)).append("\"]\n");
                    else
                        s.append("\"").append(Scanner.getFileName()).append("\"").append("->").append("\"").append(Scanner2.getFileName()).append("\"").append(" ").append("[").append(style).append("label=\"").append(df.format(weight*100)).append("%\"]\n");
                    edges.add(s.toString());
                }
            }
        }
        for(int i=0;i<packageNames.size();i++)
        {
            String packageName= packageNames.get(i);
            Edges.append("subgraph \"cluster_").append(packageName).append("\"{").append("\n");
            Edges.append("label=\"").append(packageName).append("\";").append("\n");
            for (String Scanner:list.get(i))
            {
                Vertex vertex1=diagram.getVertex(Scanner);
                if (vertex1.refactor)
                {
                    Edges.append("\"").append(Scanner).append("\"").append("[color=blue]").append(";\n");
                }
                else if(vertex1.notRelate())
                {
                    Edges.append("\"").append(Scanner).append("\"").append("[color=yellow]").append(";\n");
                }
                else if (vertex1.needRefactor()) {
                    Edges.append("\"").append(Scanner).append("\"").append("[color=red]").append(";\n");
                }
                else
                    Edges.append("\"").append(Scanner).append("\"").append(";\n");
            }
            Edges.append("}").append("\n");
        }
        for(int i=0;i<edges.size();i++)
        {
            String Scanner=edges.get(i);
            Edges.append(Scanner);
        }
        GraphVizTest gvt=new GraphVizTest();
        gvt.draw(Edges.toString(),"Diagram_"+diagram.getName());

    }


    public static Diagram check(String path) {
        String[] temp;
        if  (System.getProperty("os.name").toLowerCase().startsWith("win"))
            temp=path.split("\\\\");
            else
            temp=path.split("/");
        String name=temp[temp.length-1];
        Diagram m=new Diagram(name);
        FolderScanner.init();
        if (suffixList.size()==0) {
            printErr("SuffixList is null!");
            return null;
        }
        try {
            FolderScanner.find(path);
        } catch (Exception e) {
            printErr(e.toString());
        }
        for (CodeFile Scanner:FolderScanner.getCodeFiles()) {
            Vertex v=new Vertex(Scanner);
            m.addVertex(v);
            Scanner.setIndex(0);
        }
        for (CodeFile Scanner:FolderScanner.getCodeFiles()) {
            for (CodeFile Scanner2:FolderScanner.getCodeFiles()) {
                int times= appearTime(Scanner, Scanner2);
                if (times>0) {
                    double weight=dis(times);//距离权重 1-1.25
                    if(isCreate(Scanner,Scanner2)) weight=weight*createIndex;
                    m.getVertex(Scanner.getFileName()).relate(m.getVertex(Scanner2.getFileName()),weight);
                }
            }
        }
        if (adjust)
        {
            int times=0;
            while(!refactor(m)){
                if (times++>adjustTimes) break;
            }
        }
        if (createDiagram)
            draw(m);
        return m;
    }

    private static int appearTime(CodeFile Scanner, CodeFile Scanner2)
    {
        if (Scanner.getName().equals(Scanner2.getName())) return 0;
        String target="[^a-zA-Z0-9]"+Scanner2.getName()+"[^a-zA-Z0-9]";
        if (Scanner.getName().equals(Scanner2.getName()))
            target=Scanner2.getName();
        return appearNumber(Scanner.getCode(),target);
    }
    private static boolean isCreate(CodeFile Scanner, CodeFile Scanner2)
    {
        try {
            String target = "new" + Scanner2.getName() + "[^a-zA-Z0-9]";
            return appearNumber(Scanner.getCode().replace(" ", ""), target) >= 1;
        }catch (Exception e)
        {
            return false;
        }
    }


//    public static void draw(String path) {
//        StringBuilder Edges= new StringBuilder();
//        ArrayList<String> edges=new ArrayList<>();
//        ArrayList<ArrayList<String>> Vertex=new ArrayList<>();
//        ArrayList<String> packageNames=new ArrayList<>();
//
//        FolderScanner.init();
//        try {
//            FolderScanner.find(path,1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        for (CodeFile Scanner:FolderScanner.getCodeFiles()) {
//            Scanner.setIndex(0);
//        }
//        for (CodeFile Scanner:FolderScanner.getCodeFiles()) {
//            int index=packageNames.indexOf(Scanner.getPackageName());
//            if (index>=0)
//            {
//                Vertex.get(index).add(Scanner.getFileName());
//            }
//            else
//            {
//                packageNames.add(Scanner.getPackageName());
//                ArrayList<String> newPackageCodes=new ArrayList<>();
//                newPackageCodes.add(Scanner.getFileName());
//                Vertex.add(newPackageCodes);
//            }
//
//            for (CodeFile Scanner2:FolderScanner.getCodeFiles()) {
//                int times= appearTime(Scanner, Scanner2);
//                if (times>0) {
//                    boolean create=isCreate(Scanner,Scanner2);
//                    double weight=dis(times);//权重 1-1.25
//                    if(create) weight=weight*createIndex;
//
//                    StringBuilder s= new StringBuilder();
//                    String style="color=black,";
//                    if (!create)
//                        style+="style=dashed,";
//                    if ((!Scanner.getPackageName().equals(Scanner2.getPackageName()))&&(create))
//                    {
//                        style+="style=bold,";
//                    }
//                    s.append("\"").append(Scanner.getFileName()).append("\"").append("->").append("\"").append(Scanner2.getFileName()).append("\"").append(" ").append("[").append(style).append("label=\"").append(df.format(weight)).append("\"]\n");
//                    edges.add(s.toString());
//                }
//            }
//
//        }
//
//
//
//        for(int i=0;i<packageNames.size();i++)
//        {
//            String packageName= packageNames.get(i);
//            Edges.append("subgraph cluster_").append(packageName).append("{").append("\n");
//            Edges.append("label=\"").append(packageName).append("\";").append("\n");
//            for (String Scanner:Vertex.get(i))
//            {
//                Edges.append("\"").append(Scanner).append("\"").append(";\n");
//            }
//            Edges.append("}").append("\n");
//        }
//        for(String Scanner:edges)
//        {
//            Edges.append(Scanner);
//        }
//        String[] temp=path.split("/");
//        String name=temp[temp.length-1];
//        GraphVizTest gvt=new GraphVizTest();
//        gvt.draw(Edges.toString(),"Diagram_"+name);
////        GraphVizTest gvt2=new GraphVizTest();
////        gvt2.draw(Edges2,"relation_"+i);
//
//    }



    //    String type="";
//                if ((code.indexOf("new " + Scanner2.getName() + "[^a-zA-Z0-9]") > 0)) {
//                    Scanner.create(Scanner2);
//                    type="CREATE";
//                   //times*=5;
//                }
//                else if ((code.indexOf("[^a-zA-Z0-9]"+Scanner2.getName()  + ".") > 0)
//                        ||(!Scanner2.getFileName().equals(Scanner.getFileName()))) {
//                    type="use";
//                    Scanner.use(Scanner2);
//                    //times*=2;
//                }
//                else if ((times > 0))
//                {
//                    type="relate";
//                    Scanner.relate(Scanner2);
//                }



//    private static String selectFolder()
//    {
//        JFileChooser fileChooser = new JFileChooser("");
//        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        int returnVal = fileChooser.showOpenDialog(fileChooser);
//        if(returnVal == JFileChooser.APPROVE_OPTION){
//            return  fileChooser.getSelectedFile().getAbsolutePath();
//        }
//        return null;
//    }

    private static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }

    public static String[] getDictionary() {

        // printLog(s);
        return dictionary;
    }

    public static void setServletWriter(PrintWriter servletWriter) {
        Core.servletWriter = servletWriter;
    }

//    public static ArrayList<String> getSuffixList() {
//        return suffixList;
//    }

//
//    public void setByLines(boolean byLines) {
//        Core.byLines = byLines;
//    }
//
//    public void setBySize(boolean bySize) {
//        Core.bySize = bySize;
//    }
//
//    public void setSuffixList(ArrayList<String> suffixList)
//    {
//        Core.suffixList =suffixList;
//    }
//    public  void setCreateDiagram(boolean createDiagram) {
//        Core.createDiagram = createDiagram;
//    }
//
    public static boolean pd(File file) {
        for (String Scanner : suffixList) {
            if (file.getName().endsWith("." + Scanner)) return true;
        }
        // if (file.getName().endsWith(".c")) return true;
        //if (file.getName().endsWith(".cpp")) return true;
        return false;
    }


}
