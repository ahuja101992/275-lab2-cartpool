package edu.sjsu.cmpe275.cartpool.util;

public class UtilFunctions {
    public final static boolean isAdmin(String email) {
        String domainName = email.split("@")[1];
        return "sjsu.edu".equals(domainName);
    }
}
