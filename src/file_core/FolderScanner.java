package file_core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main_core.Core.printErr;

public abstract class FolderScanner {
//    private static int depth=1;
    //private ArrayList<File> finds;
    private static ArrayList<String> finds=new ArrayList<>();
    private static ArrayList<CodeFile> codeFiles=new ArrayList<>();

    public static void init() {
        finds=new ArrayList<>();
        codeFiles=new ArrayList<>();
    }

    //    private static String pathName="";
    public ArrayList<String> getFinds()
    {
        return finds;
    }
//    public ArrayList<CodeFile> codeFiles()
//    {
//        return codeFiles;
//    }
//    private boolean removeComment=true;
//    private boolean removeSpace=true;
    private static Set<String> suffixList;
//    private static String[] dictionary;
//    public FolderScanner(String pathName,int depth) throws IOException {
//        finds=new ArrayList<>();
//        codeFiles=new ArrayList<>();
//        this.pathName=pathName;
//        this.find(pathName,depth);
//    }
    public void reset()
    {
        finds=new ArrayList<>();
        codeFiles=new ArrayList<>();
    }

    /**
     * 文件遍历
     * 通过递归对代码工程内的子文件进行处理，主要作用是提供代码数据源给图量化模块，处理文件夹内的代码文件集合
     * 遍历之前先进行预处理筛选→对文件夹下的文件进行扫描
     * →筛选代码文件→通过代码文件流将代码读入
     * @param pathName 扫描的文件夹地址
     */
    public static void find(String pathName){
        File dirFile = new File(pathName);
        if (!dirFile.exists()) {
            return;
        }
        if (!dirFile.isDirectory()) {
            return;
        }
        String[] fileList = dirFile.list();
        assert fileList != null;
        for (String string : fileList) {
            File file = new File(dirFile.getPath(), string);
            if (file.isDirectory()) {
                try {
                    find(file.getCanonicalPath());
                } catch (Exception e) {
                    printErr(e.toString());
                }
            } else {
                if (pd(file)) {
                    for (String suffix : suffixList) {
                        if (!finds.contains(file.getName().replace(suffix, ""))) {
                            finds.add(file.getName().replace(suffix, ""));
                            CodeFile cf;
                            cf = new CodeFile(file);
                            codeFiles.add(cf);
                        }
                    }
                }
            }
        }
    }

    public static boolean pd(File file)
    {
        if (suffixList==null)
        {
            suffixList=new HashSet<>();
            suffixList.add("java");
        }
        for (String Scanner:suffixList)
        {
            if (file.getName().endsWith("."+Scanner)) return true;
        }
       // if (file.getName().endsWith(".c")) return true;
        //if (file.getName().endsWith(".cpp")) return true;
        return false;
    }
    public static boolean contain(String txt,String key)
    {
        Pattern pattern = Pattern.compile(key);
        Matcher matcher = pattern.matcher(txt);
        return  matcher.matches();
    }

    public static ArrayList<CodeFile> getCodeFiles() {
        return codeFiles;
    }

//    public void disableComment() {
//        removeComment=false;
//    }
//    public void disableSpace() {
//        removeSpace=false;
//    }

    public static void setSuffixList(Set<String> suffixList) {
        FolderScanner.suffixList = suffixList;
    }
}
