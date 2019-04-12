package file_core;


class ExObject {
    //  private HashMap<String,String>=new HashMap();
    //private ArrayList<String> attrName;
    private String[] value;
    ExObject()
    {
      //  this.attrName=new ArrayList<>();
        this.value=new String[100];
    }
    void setAttr(int key, String val)
    {
        //boolean find=false;
        value[key]=val;
        /*
        for (int i=0;i<attrName.size();i++) {
            String Scanner=attrName.get(i);
            if (Scanner.equals(attr)) {
                value.set(i,val);
                find = true;
            }
            if (!find)
            {
                attrName.add(attr);
                value.add(val);
            }
        }
        */
    }
    public String getval(int key)
    {
        return value[key];
    }


}