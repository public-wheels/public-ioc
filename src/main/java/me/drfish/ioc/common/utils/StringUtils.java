package me.drfish.ioc.common.utils;

/**
 * @author drfish
 * @date 18/11/2017
 **/
public class StringUtils {
    public static boolean isNotBlank(String str){
        if (str != null && str.isEmpty()) {
            return true;
        }
        return false;
    }
}
