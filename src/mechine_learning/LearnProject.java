package mechine_learning;

import file_core.ExStreamer;
import main_core.Cmd;
import main_core.Core;

import java.util.ArrayList;
import static main_core.Core.printLog;
import static main_core.Core.printSys;

public abstract class LearnProject {

    private static ArrayList<LearnData> myDatas;
    private static String record="";
    public static double edge_weight;//边权重 必选训练项
    public static double threshold;//相似度阈值 必选训练项
//    private static double pow_dis;//距离收缩参数 可选训练项
//    private static double check_threshold;//近似相似度阈值 建议训练项
//    private static double min_threshold;//字符串阈值 可选训练项


    private static double edge_weight_limit1=0;
    private static double edge_weight_limit2=1;
//    private static double pow_dis_limit1=0;
//    private static double pow_dis_limit2=10;
//    private static double check_threshold_limit1=0;
//    private static double check_threshold_limit2=1;
//    private static double min_threshold_limit1 =0;
//    private static double min_threshold_limit2 =1;
    private static double threshold_limit1=0;
    private static double threshold_limit2=1;

    //    private int pow_dis_direct=0;


    //    private int check_threshold_direct=0;

    private static final double approachIndex=2;
//    private final double stepIndex=0.01;

//    private static HashMap<Vector<Double>, Double> map;
//    static ArrayList<Double>  edge_weight_list;
//    static ArrayList<Double> pow_dis_list;
//    static ArrayList<Double> check_threshold_list;
//    static ArrayList<Double> threshold_list;


    private static final double allowDiff=0.2;//为正限制 为负放宽
    private static final double expansion=5d;
    //    private int threshold_direct=0;

    private static double edge_weight_key=0;
    private static double threshold_key=0;
//    private static double pow_dis_key;
//    private static double check_threshold_key;
//    private static double min_threshold_key;
    private static int step=3;
    private static int size=0;
    private static String[][] values=new String[999][7];
//    private final double threshold_limit1=0.5;
//    private final double threshold_limit2=0.8;
//    private final double threshold_move=0.005;
//    private int threshold_direct=0;

//
//    private final double adj_dis_limit1=1;
//    private final double adj_dis_limit2=2;
//
//    private final double adj_dis_move=0.01;
//    private int adj_dis_direct=0;


    public static void init()
    {
        step=3;
        edge_weight_limit1=0;
        edge_weight_limit2=1;
        threshold_limit1=0;
        threshold_limit2=1;
//        pow_dis_limit1=0;
//        pow_dis_limit2=2;
//        check_threshold_limit1=0.3;
//        check_threshold_limit2=0.8;
//        min_threshold_limit1 =0.1;
//        min_threshold_limit2 =0.6;
        myDatas=new ArrayList<>();
        record="";
        values=new String[999][7];
    }



//    public static String training(Network network,int time)
//    {
//        double threshold_step = (threshold_limit2 - threshold_limit1) / (step + 1);
//        double edge_weight_step = (edge_weight_limit2 - edge_weight_limit1) / (step + 1);
//        for (threshold=threshold_limit1;threshold<threshold_limit2;)
//        return null;
//    }
    public static String training(int time)
    {
        Core.trainMode();
        if (size==0) size=time;
        double minAcc=1;
        double maxAcc=0;
        String min_info="";
        String max_info="";
        double sumAcc=0;
//        double index=0;
        double edge_weight_step = (edge_weight_limit2 - edge_weight_limit1) / (step + 1);
//        double pow_dis_step = (pow_dis_limit2 - pow_dis_limit1) / (step + 1);
//        double check_threshold_step = (check_threshold_limit2 - check_threshold_limit1) / (step + 1);
//        double min_threshold_step = (min_threshold_limit2 - min_threshold_limit1) / (step + 1);
        double threshold_step = (threshold_limit2 - threshold_limit1) / (step + 1);
        int all= (int) Math.pow(step,2);
//        map=new HashMap<>();

        for (int i=1;i<=step;i++)
            for (int j=1;j<=step;j++)
//                for (int k=1;k<=step;k++)
//                    for (int l=1;l<=step;l++)
//                        for(int m=1;m<step;m++)
                        {
                            edge_weight=deal(edge_weight_limit1+i* edge_weight_step);
                            threshold=deal(threshold_limit1+j* threshold_step);
//                            pow_dis=deal(pow_dis_limit1+n* pow_dis_step);
//                            check_threshold=deal(check_threshold_limit1+k* check_threshold_step);
//                            min_threshold= deal(min_threshold_limit1 +m*min_threshold_step);
                            int now= ((i-1)*step+j);
                            printSys("Left Round:"+time+" Now run:"+now+"/"+all);
                            printSys(info());
                            double sumDiff=0;
                            for (LearnData Scanner:myDatas)
                            {
                                sumDiff+=getDiff(Scanner);
                            }
                            double accurate = deal(Math.pow(1 - sumDiff / myDatas.size() * expansion,1/approachIndex));
                            printSys(Core.df.format(accurate *100)+"%");
                            sumAcc+=accurate;
                            if (accurate >maxAcc)
                            {
                                maxAcc= accurate;
                                max_info=info();
                                edge_weight_key=edge_weight;
                                threshold_key=threshold;
//                                pow_dis_key=pow_dis;
//                                check_threshold_key=check_threshold;
//                                min_threshold_key=min_threshold;
                            }
                            if (accurate< minAcc)
                            {
                                minAcc=accurate;
                                min_info=info();
                            }
                        }
        record+="Left:"+time+"\nmaxInfo:"+max_info+":"+maxAcc+"\n"+"minInfo:"+min_info+":"+minAcc+"\nAve:"+sumAcc/ (double) all+"\n";edge_weight_limit1=edge_weight_key- edge_weight_step;
        int index=size-time;
        values[index][0]= String.valueOf(threshold_limit1);
        values[index][1]= String.valueOf(threshold_limit2);
        values[index][2]= String.valueOf(threshold_key);
        values[index][3]= String.valueOf(edge_weight_limit1);
        values[index][4]= String.valueOf(edge_weight_limit2);
        values[index][5]= String.valueOf(edge_weight_key);
        values[index][6]= String.valueOf(maxAcc);

        edge_weight_limit1=edge_weight_key- edge_weight_step;
        edge_weight_limit2=edge_weight_key+ edge_weight_step;
        threshold_limit1=threshold_key- threshold_step;
        threshold_limit2=threshold_key+ threshold_step;

//        pow_dis_limit1=pow_dis_key- pow_dis_step;
//        pow_dis_limit2=pow_dis_key+ pow_dis_step;
//        check_threshold_limit1=check_threshold_key- check_threshold_step;
//        check_threshold_limit2=check_threshold_key+ check_threshold_step;

//        min_threshold_limit1 =min_threshold_key+min_threshold_step;
//        min_threshold_limit2=min_threshold_key+min_threshold_step;
        if (time<=1)
        {
            String path= "training.xls";
            ArrayList<String> attrs=new ArrayList<>();
            attrs.add("threshold_limit1");
            attrs.add("threshold_limit2");
            attrs.add("threshold_key");
            attrs.add("edge_weight_limit1");
            attrs.add("edge_weight_limit2");
            attrs.add("edge_weight_key");
            attrs.add("maxAcc");

            new ExStreamer(path).excelOut(size, attrs, values);
            size=0;
//            printLog((infoD());
            printSys(record);
            Cmd.load();
            return record;
        }
        return training(time-1);
    }
//    public void training(double times)
//    {
//        for (int i=0;i<=times;i++) {
//            for (LearnData Scanner : myDatas) {
//
//            }
//        }
//    }

    private static double getResult(LearnData Scanner) {
//        Cmd.load();
        Core.edge_weight =edge_weight;
        Core.threshold=threshold;
//        Core.check_threshold=check_threshold;
//        Core.pow_dis=pow_dis;
//        Core.min_threshold=min_threshold;

        return Core.compare(Scanner.getPath0(), Scanner.getPath1());
    }
    private static double getDiff(LearnData Scanner)
    {
        double result = getResult(Scanner);
        boolean isSimilar = Scanner.isSimilar();
        double diff;
//        printLog(("CalcSimilar:"+deal(result)+"/"+threshold);
//        printLog(("Fact:"+isSimilar);
        if (isSimilar)
        {
            if (result >threshold+allowDiff) diff=0;
            else diff=(threshold+allowDiff)- result;
        }
        else
        {
            if (result <threshold-allowDiff) diff=0;
            else diff= result -(threshold-allowDiff);
        }
//        printLog(("Diff:"+deal(diff));
        return Math.pow(diff,approachIndex);
    }
    public static Double deal(double input)
    {
        return  input;
//        BigDecimal b = new BigDecimal(input);
//        return   b.setScale(20, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Object training(String cmd, int times) {
        String[] datas=cmd.substring(1,cmd.length()-1).split(";");
        for (String Scanner:datas)
        {
            String[] tags=Scanner.split(",");
            addData(tags[0],tags[1], Boolean.parseBoolean(tags[2]));
        }

        return training(times);
    }
//    public double getIndex()
//    {
//        long grade=1000000;
//        return edge_weight*Math.pow(grade,4)+pow_dis*Math.pow(grade,3)+check_threshold*Math.pow(grade,4)+threshold;
//    }

//    public void setMyDatas(ArrayList<LearnData> myDatas) {
//        LearnProject.myDatas = myDatas;
//    }
    public static void addData(String path0, String path1, boolean similar)
    {
        LearnProject.myDatas.add(new LearnData(path0,path1,similar));
    }

    public static String info() {
        /*
        this.edge_weight=edge_weight;
        this.adj_dis=adj_dis;
        this.pow_dis=pow_dis;
        this.threshold=threshold;
        this.check_threshold=check_threshold;
         */
        return "edge_weight:["+edge_weight_limit1+"]/"+edge_weight+"/["+edge_weight_limit2+"]"+
//                "\npow_dis:["+pow_dis_limit1+"]/"+pow_dis+"/["+pow_dis_limit2+"]"+
                "\nthreshold:["+threshold_limit1+"]/"+threshold+"/["+threshold_limit2+"]"+
//                "\ncheck_threshold:["+check_threshold_limit1+"]/"+check_threshold+"/["+check_threshold_limit2+"]" +
//        "\nmin_threshold:["+min_threshold_limit1+"]/"+min_threshold+"/["+min_threshold_limit2+
                "";
    }

//    public static String infoD() {
//        /*
//        this.edge_weight=edge_weight;
//        this.adj_dis=adj_dis;
//        this.pow_dis=pow_dis;
//        this.threshold=threshold;
//        this.check_threshold=check_threshold;
//         */
//        return "edge_weight:"+edge_weight_key+
////                "\npow_dis:"+pow_dis_key+
//                "\nthreshold:"+threshold_key+
////                "\ncheck_threshold:"+check_threshold_key+
////                "\nmin_threshold:"+min_threshold_key+
//                "";
//    }
}
