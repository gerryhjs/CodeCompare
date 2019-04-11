package compare_core;

import file_core.CodeFile;
import main_core.Core;

import java.util.HashMap;
import java.util.Set;

public class Vertex {
    private CodeFile cf;
    private HashMap<Vertex,Double> Edges=new HashMap<>();
    private HashMap<Vertex,Double> from=new HashMap<>();
//    private double  entropy;
    public boolean refactor =false;
//    public boolean similar;
    private  String packageName;
    private String fileName;
    private String append="";

    public Set<Vertex> getFrom() {
        return from.keySet();
    }

//    public Vertex()
//    {
//        this.setCf(null);
//    }


    public Vertex(CodeFile cf)
    {
        this.setCf(cf);
        this.packageName=cf.getPackageName();
        this.fileName=cf.getFileName();
    }
//    public Vertex(String packageName,String fileName)
//    {
//        this.packageName=packageName;
//        this.fileName=fileName;
//    }


    public CodeFile getCf() {
        return cf;
    }
    public String info()
    {
        return "["+ getPackageName()+"]"+getFileName()+append;
        //+"[valid lines:"+cf.getLines()+";valid size:"+cf.getSize()+"]"
        //+"<"+Core.df.format(getEntropy()*100)+"%>"
    }

    public String getPackageName() {
        return packageName;
    }
    public String getFileName() {
        return fileName;
    }
    public void setCf(CodeFile cf) {
        this.cf = cf;
    }

    public void relate(Vertex another, double value)
    {
        Edges.put(another,value);
        another.relateFrom(this,value);
    }

    private void relateFrom(Vertex vertex,double value) {
        from.put(vertex,value);
    }

    public Set<Vertex> getTo()
    {
        return Edges.keySet();
    }
    public double  getDistance(Vertex another)
    {
        Object distance=Edges.get(another);
        if (distance!=null) return (double) distance;
        //return Double.MAX_VALUE;
        return -1;
    }

    public double similar(Vertex another)
    {
        return CodeCompare.compare(this.getCf(),another.getCf());
    }

//    public double getEntropy() {
//        double outPackageWeight=0;
//        double myPackageWeight=0;
//        for (Vertex Scanner:getFrom())
//        {
//            if(Scanner.getPackageName().equals(this.getPackageName()))
//                myPackageWeight+= from.get(Scanner);
//            else
//                outPackageWeight+= from.get(Scanner);
//        }
//        for (Vertex Scanner:getTo())
//        {
//            if(Scanner.getPackageName().equals(this.getPackageName()))
//                myPackageWeight+= Core.entropyToIndex*Edges.get(Scanner);
//            else
//                outPackageWeight+= Core.entropyToIndex*Edges.get(Scanner);
//        }
//        return myPackageWeight/outPackageWeight;
//    }


    public boolean needRefactor() {
        //TODO
//        if(this.getFrom().size()+this.getTo().size()==0) return  false;
//        if(bestBelong().equals(this.getPackageName())) return false;
        return !bestBelong().equals(getPackageName());
    }
    public boolean notRelate()
    {
        return this.getFrom().size()+this.getTo().size()==0;
    }
    public String bestBelong()
    {
        if(this.getFrom().size()+this.getTo().size()==0) return  "useless";
        HashMap<String,Double> belongValue=new HashMap<>();
        for (Vertex Scanner:getFrom())
        {
            double value;
            try {
                value = belongValue.get(Scanner.getPackageName());
            }catch (Exception e)
            {
                value=0;
            }
            value+=1d+1d/from.get(Scanner);
            if (Scanner.getPackageName().equals(getPackageName())) value+=value* Core.entropyStable;
            belongValue.put(Scanner.getPackageName(),value);
        }
        for (Vertex Scanner:getTo())
        {
            double value;
            try {
                value = belongValue.get(Scanner.getPackageName());
            }catch (Exception e)
            {
                value=0;
            }
            value+=1d+1d/Edges.get(Scanner)* Core.entropyToIndex;
            if (Scanner.getPackageName().equals(getPackageName())) value+=value* Core.entropyStable* Core.entropyToIndex;
            belongValue.put(Scanner.getPackageName(),value);
        }
        double max=-1;
        String result=null;
        for (String Scanner:belongValue.keySet())
        {
            if (max<belongValue.get(Scanner))
            {
                result=Scanner;
                max=belongValue.get(Scanner);
            }
        }
//        this.getCf().setPackageName(result);
        return result;
    }

    public void setPackageName(String packageName) {
        this.packageName=packageName;
    }


    public void setAppend(String append) {
        this.append=append;
    }
}
