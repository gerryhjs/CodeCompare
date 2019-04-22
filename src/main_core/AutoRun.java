package main_core;


public class AutoRun {
    public static void main(String[] args)
    {
        Cmd.load();
        Cmd.load("work.xml");
        Core.AutoRun();
    }
}
