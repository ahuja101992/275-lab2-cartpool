package edu.sjsu.cmpe275.cartpool.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilFunctions {
    public final static boolean isAdmin(String email) {
        String domainName = email.split("@")[1];
        return "sjsu.edu".equals(domainName);
    }

    public final static boolean validateZipcode(String zip) {
        String regex = "^[0-9]{5}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(zip);
        return matcher.matches();
    }

}
