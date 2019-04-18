package graphViz;

import main_core.Core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GraphVizTest {
    private final String graphVizPath= Core.getOutputPath();
    private final String dotPath=(System.getProperty("os.name").toLowerCase().startsWith("win")) ? Core.getDotPath():"dot";


    public void draw(String list,String name) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmssSSS");
        name+=sdf.format(new Date());
        GraphViz gViz=new GraphViz(graphVizPath,dotPath);
        gViz.setPicName(name);
        gViz.start_graph();
        /*
        带有中文的图需要加上以下设置
        graph [fontname="Microsoft Yahei"];
node [shape=plaintext, fontname="Microsoft Yahei"];
edge [shape=plaintext, fontname="Microsoft Yahei"];
         */
//        for (String Scanner:list) {
//            gViz.addln(Scanner);
//        }
        gViz.addln(list);
        gViz.end_graph();
        try {
            gViz.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        gViz.delete();
    }


}
