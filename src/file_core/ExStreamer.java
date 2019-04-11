package file_core;


import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import main_core.Core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ExStreamer {

    //TODO 修改自定义比较结果输出
    private ArrayList<String> attr=new ArrayList<>();
    private String path= Core.getOutputPath();
    public ExStreamer(String filename)
    {
        if (path.endsWith(File.separator))
            this.path=path+filename;
        else
            this.path=path+File.separator+filename;
    }

//    public ExStreamer(String path) {
//        this.path=path;
//    }

//    public void addAttr(String newAttr)
//    {
//        attr.add(newAttr);
//    }
//    public void excelOut(List<ExObject> list)
//    {
//        WritableWorkbook book = null;
//        try
//        {
//            book = Workbook.createWorkbook(new File(path));
//            WritableSheet sheet = book.createSheet("sheet1", 0);
//            // Label label = new Label(0, 2, "test")
//            for (int j=0;j<attr.size();j++)
//            {
//                Label l=new Label(j,0,attr.get(j));
//                sheet.addCell(l);
//            }
//            for (int i = 0; i < list.size(); i++) {
//                ExObject exObject = list.get(i);
//                for (int j=0;j<attr.size();j++)
//                {
//                    Label l=new Label(j,i+1,exObject.getval(j));
//                    sheet.addCell(l);
//                }
//
//            }
//            book.write();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                book.close();
//            } catch (WriteException | IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    public List<ExObject> excelIn(String db, String tableName, Boolean replace) {
//        List<ExObject> list = new ArrayList<>();
//        Workbook workbook = null;
//        try {
//            workbook = Workbook.getWorkbook(new File(path));
//            Sheet sheet = workbook.getSheet(0);
//            for (int i = 0; i < sheet.getRows(); i++) {
//                ExObject exObject = new ExObject();
//               // Cell cell0 = sheet.getCell(0, i);
//                for(int j=0;j<=100;j++)
//                {
//                    try {
////                        String txt = sheet.getCell(j, i).getContents();
//                        // printLog((i + "_" + j + ":" + txt);
//                        //if (txt.length()==0) continue;
//                    }
//                    catch (Exception ignored)
//                    {
//
//                    }
//                }
//
//                list.add(exObject);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            assert workbook != null;
//            workbook.close();
//        }
//        return list;
//    }


//    public  void main_core(String args[])
//    {
//        test();
//    }
//    public  void test()
//    {
//        excelIn("bigdata","user",true);
//    }

    private void setAttrs(ArrayList<String> attrs) {
        this.attr = attrs;
    }

    public void excelOut(int size,ArrayList<String> attrs,String[][] attrsVal)
    {
//        String[] labels= null;
//        String[] attrsVal=null;
        this.setAttrs(attrs);
        ArrayList<ExObject> obs=new ArrayList<>();
        for (int n=0;n<size;n++)
        {
            ExObject exObject=new ExObject();
            for(int i=0;i<attrs.size();i++)
                exObject.setAttr(i,attrsVal[n][i]);

            obs.add(exObject);
        }

        WritableWorkbook book = null;
        File f;
        try
        {
            f=new File(path);
            book = Workbook.createWorkbook(f);
            WritableSheet sheet = book.createSheet("sheet1", 0);
            // Label label = new Label(0, 2, "test")
            for (int j=0;j<attr.size();j++)
            {
                Label l=new Label(j,0,attr.get(j));
                sheet.addCell(l);
            }

            for (int i = 0; i < obs.size(); i++) {
                ExObject exObject = obs.get(i);
                for (int j=0;j<attr.size();j++)
                {
                    Label l=new Label(j,i+1,exObject.getval(j));
                    sheet.addCell(l);
                }
            }
            book.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert book != null;
                book.close();
            } catch (WriteException | IOException e) {
                e.printStackTrace();
            }
        }
    }

}
/*

    */





//package Saika_Output;
//
//
//
//import DatabaseManage.DatabaseConnection;
//import jxl.Cell;
//import jxl.Sheet;
//import jxl.Workbook;
//import jxl.write.Label;
//import jxl.write.WritableSheet;
//import jxl.write.WritableWorkbook;
//import jxl.write.WriteException;
//import org.junit.test;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class ExStreamer {
//    private  ArrayList<String> attr=new ArrayList<>();
//    private  String path="D:/book.xls";
//
//    public ExStreamer(String path) {
//        this.path=path;
//    }
//
//    public void addAttr(String newAttr)
//    {
//        attr.add(newAttr);
//    }
//    public void setAttrs(ArrayList<String> attr)
//    {
//        this.attr=attr;
//    }
//    public void excelOut(String list)
//    {
//
//    }
//    public void excelOut(List<ExObject> list)
//    {
//        WritableWorkbook book = null;
//        try
//        {
//            book = Workbook.createWorkbook(new File(path));
//            WritableSheet sheet = book.createSheet("sheet1", 0);
//            // Label label = new Label(0, 2, "test")
//            for (int j=0;j<attr.size();j++)
//            {
//                Label l=new Label(j,0,attr.get(j));
//                sheet.addCell(l);
//            }
//            for (int i = 0; i < list.size(); i++) {
//                ExObject exObject = list.get(i);
//                for (int j=0;j<attr.size();j++)
//                {
//                    Label l=new Label(j,i+1,exObject.getval(j));
//                    sheet.addCell(l);
//                }
//
//            }
//            // д��Ŀ��·��
//            book.write();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                book.close();
//            } catch (WriteException | IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public List<ExObject> excelIn(String db, String tableName, Boolean replace) {
//        List<ExObject> list = new ArrayList<>();
//        Workbook workbook = null;
//        try {
//            workbook = Workbook.getWorkbook(new File(path));
//            Sheet sheet = workbook.getSheet(0);
//            for (int i = 1; i < sheet.getRows(); i++) {
//                ExObject exObject = new ExObject();
//                Cell cell0 = sheet.getCell(0, i);
//                //attr=new ArrayList<>();
//                ArrayList<String> s=new ArrayList<>();
//                for(int j=0;j<=100;j++)
//                {
//                    try {
//                        String txt = sheet.getCell(j, i).getContents();
//                        s.add(txt);
//                        // printLog((i + "_" + j + ":" + txt);
//                        //if (txt.length()==0) continue;
//                    }
//                    catch (Exception ignored)
//                    { }
//                }
//                DatabaseConnection dc=new DatabaseConnection(db);
//                if (!replace)
//                    dc.insert(tableName,attr,s);
//                else
//                    dc.insertWithReplace(tableName,attr,s);
//                
//                // ȡ�õ�Ԫ���ֵ,�����õ�������
//                //sheet.getCell(2, i).getContents()
//
//                list.add(exObject);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            workbook.close();
//        }
//        return list;
//    }
//
//
//
//    @test
//    public  void test()
//    {
//
////        attr.add("����A");
////        attr.add("����B");
////        attr.add("����C");
////        attr.add("����D");
////
////        ExObject o1=new ExObject();
////        ExObject o2=new ExObject();
////        o1.setAttr(0,"aaa");
////        o1.setAttr(1,"baa");
////
////        o2.setAttr(0,"caa");
////        o2.setAttr(1,"daa");
////        o2.setAttr(2,"caa");
////        o2.setAttr(3,"daa");
////
////        ArrayList<ExObject> os=new ArrayList<>();
////        os.add(o1);
////        os.add(o2);
//     //   output("book.xls",os);
//        // input("book.xls","contract");
//    }
//}
//
//
