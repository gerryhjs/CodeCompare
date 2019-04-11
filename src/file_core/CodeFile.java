package file_core;

import main_core.Core;

import java.io.File;

/**
 * Created by Saika on 2019/1/8.
 */
public class CodeFile {
    private String packageName;
    private String fileName;
    private String name;
    private String[] codes;
    private String code ;
    private int index;
    private long size;
    private long lines;
    public String getFileName()
    {
        return fileName;
    }

    public String getPackageName()
    {
        return packageName;
    }
    public void setPackageName(String packageName)
    {
        if(packageName!=null)
        this.packageName=packageName;
        else
            this.packageName="null";
    }

    public CodeFile(File file) { //    private double LOW_DOWN=1;
        index=0;
        size=0;
        lines=0;
        this.fileName=file.getName();

        try {
            this.setName(fileName.split(("\\."))[0]);
        }
        catch (Exception e)
        {
            this.setName(fileName);
        }
        String code=FileStreamer.input(file);
        this.code=removeComment(code);
        code=deal(code);
        size=code.length();
        codes= code.split("\r\n");
        lines=codes.length;
        if (codes[0].contains("package"))
        {
            this.setPackageName(codes[0].replace("package", "").replace(" ", "").replace(";", ""));
        }
        else
        {
            this.setPackageName("unknown");
        }
    }
    private String deal(String s)
    {
        s=removeComment(s);
        s=removeTab(s);

        String[] dictionary= Core.getDictionary();
        if (dictionary==null) return s;
        for (String Scanner:dictionary)
        {
            s=s.replace(Scanner,"*");
        }
        return s;

    }

    private String removeComment(String s)
    {
        s=s.replaceAll("/\\*{1,2}[\\s\\S]*?\\*/","*");
        s=s.replaceAll("//[\\s\\S]*?\\n","*");
        return s;
    }
    private String removeTab(String s)
    {
        s=s.replace("  ","*");
        s=s.replace("\t","*");
        s=s.replaceAll("^\\s*\\n","*");
        return s;
    }

    public String getCode() {
        return code;
    }
    public String[] getCodes(){return codes; }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public long getSize() {
        return size;
    }

    public long getLines() {
        return lines;
    }


//    public int getRelateIndex() {
//        return relateIndex;
//    }
//
//    public void setRelateIndex(int relateIndex) {
//        this.relateIndex = relateIndex;
//    }
//
//    public int getUseIndex() {
//        return useIndex;
//    }
//
//    public void setUseIndex(int useIndex) {
//        this.useIndex = useIndex;
//    }
//
//    public int getCreateIndex() {
//        return createIndex;
//    }
//
//    public void setCreateIndex(int createIndex) {
//        this.createIndex = createIndex;
//    }
}
