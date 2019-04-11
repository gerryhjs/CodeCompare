package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Diagram {
    private Set<Vertex> VertexList;
    private String name;
    //private ArrayList<Edge> EdgeList;

    public Diagram(String name)
    {
        this.setName(name);
        init();
    }

    private void init() {
        this.VertexList=new HashSet<>();
        //this.EdgeList=new ArrayList<>();
    }

    public void addVertex(Vertex input)
    {
        VertexList.add(input);
    }

    public Set<Vertex> getVertexList()
    {
        return VertexList;
    }

//    public void tranArray()
//    {
//        int size=VertexList.size();
//        double[][] matrix=new double[size][size];
//    }

    public Vertex getVertex(String fileName)
    {
        for (Vertex Scanner:VertexList)
            if (Scanner.getFileName().equals(fileName)) return Scanner;
        return null;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance(Vertex v1, Vertex v2, double LOW_DOWN) {
        //dijkstra
        if (v1.getDistance(v2)>0) return v1.getDistance(v2);
        HashMap<Vertex,Double> dis= new HashMap<>();
        for (Vertex Scanner:VertexList)
        {
            dis.put(Scanner,Double.MAX_VALUE);
        }
        dis.put(v1,0d);
        while(dis.keySet().size()>=1) {
            double min=Double.MAX_VALUE;
            Vertex goon=null;
            for (Vertex Scanner : dis.keySet()) {
                if (dis.get(Scanner)<min)
                {
                    min=dis.get(Scanner);
                    goon=Scanner;
                }
            }
            if (goon==null) return -1;
            if (goon==v2) return dis.get(v2);
            dis.remove(goon);
            for (Vertex Scanner :goon.getTo()) {
                double dis_now=dis.get(goon)+goon.getDistance(Scanner)+LOW_DOWN;
                if(dis.get(Scanner)<dis_now)
                    dis.put(Scanner,dis_now);
            }
        }
        return dis.get(v2);
    }
}
