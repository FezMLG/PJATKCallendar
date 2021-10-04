package pl.pijok.PJATKCallendar.utils;

public class Debug {

    private static String prefix = "[PJATKCallendar] ";

    public static void log(String a){
        System.out.println(prefix + a);
    }

    public static void log(Object a){
        System.out.println(prefix + a);
    }

}
