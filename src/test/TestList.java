package test;

import compare_core.CodeCompare;
import compare_core.StringCompare;
import file_core.CodeFile;
import file_core.FileStreamer;
import graphViz.GraphVizTest;
import main_core.Core;
import main_core.Servlet;
import mechine_learning.LearnProject;
import org.junit.Test;
import webspider.SpiderWebsite;

import java.io.File;
import java.util.Scanner;

import static main_core.Core.printLog;
import static main_core.Core.printSys;

public class TestList {
    @Test
    public void stringTest()
    {
        printSys(StringCompare.work("ABCDEFGH","AFBDCEHG"));
    }
    @Test
    public void singleTest()
    {
        Core.init();
        CodeFile cf1=new CodeFile(new File("/media/hjs/KINGSTON/check/1.java"));
        CodeFile cf2=new CodeFile(new File("/media/hjs/KINGSTON/check/2.java"));
        printSys(CodeCompare.compare(cf1,cf2));
    }
    @Test
    public void draw_test()
    {
        Core.init();
        Core.createDiagram=true;
        Core.adjust=false;
        Core.check("/home/hjs/androidlearn-master/");
        Core.adjust=true;
        Core.check("/home/hjs/androidlearn-master/");

        //        Core.check("/media/hjs/KINGSTON/big_data");
    }
    @Test
    public void Factory_test()
    {
        Core.init();
        Core.createXls=true;
        Core.createDiagram=true;
        printLog(Core.compare("/home/hjs/androidlearnT2/","/home/hjs/androidlearn-master/"));
    }
    @Test
    public void singleCompare_test()
    {
        Core.init();
        Core.createXls=true;
        Core.createDiagram=true;
        printSys(Core.compare("/media/hjs/KINGSTON/check/1.java"));
    }
    @Test
    public void Learn_test()
    {
        String path0="/media/hjs/KINGSTON/check/jsp-server";
        String path1="/media/hjs/KINGSTON/check/jsp-lab";
//        String path2="/media/hjs/KINGSTON/check/saikaDL24";
        String path3="/media/hjs/KINGSTON/check/Predict";
        LearnProject.init();
        LearnProject.addData(path0,path1,true);
        LearnProject.addData(path3,path1,false);
//        LearnProject.addData(path2,path3,false);
//        LearnProject.addData(path3,path2,true);
        LearnProject.training(5);
    }
    @Test
    public void create_test()
    {
        File f = new File(this.getClass().getResource(File.separator).getPath());
        String path=f.getPath();
        System.out.println(path);
        FileStreamer.output(path+File.separator+"setting.txt","1",true);
    }

    @Test
    public void set_test()
    {
        Core.set("path0","/home/hjs/code_compare/src/dictionary");
    }

    @Test
    public void spider_test()
    {
        String SeedUrl="https://blog.csdn.net/effective_coder/article/details/8736718";
        SpiderWebsite w=new SpiderWebsite();
		w.addKey("算法");
        w.extractLinks(SeedUrl,1000);
    }
    @Test
    public void graphviz_test()
    {
        GraphVizTest graphVizTest=new GraphVizTest();
        String s="A -> B [color=red]\n" +
                " B -> C \n" +
                " B -> D \n" +
                " C -> E [color=red,style=bold]\n" +
                " B -> E \n" +
                " C -> A [color=red,style=bold]\n" +
                " D -> B \n" +
                " A -> C [style=dashed]\n" +
                " P -> A [style=dashed]\n" +
                " C -> G [style=dashed]\n" +
                " G -> A [style=dashed]\n" +
                " E -> G [style=dashed]\n" +
                " B -> G";
        graphVizTest.draw(s,"test");
    }

    @Test
    public void clientTest()
    {
            Servlet s=new Servlet();
            s.deal("check");

            Scanner scanner= new Scanner(System.in);
            for(int i=0;i<=99;i++)
            {
                s.deal(scanner.nextLine());
            }
    }






/*
 String s="A -> B \n" +
                " B -> C \n" +
                " B -> D \n" +
                " C -> E \n" +
                " B -> E \n" +
                " C -> A \n" +
                " D -> B \n";

String s="A -> C \n" +
                " P -> A \n" +
                " C -> G \n" +
                " C -> E \n" +
                " G -> A \n" +
                " C -> A \n" +
                " E -> G \n" +
                " B -> G";

	A -> B
 B -> C
 B -> D
 C -> E
 B -> E
 C -> A
 D -> B

 */
}

