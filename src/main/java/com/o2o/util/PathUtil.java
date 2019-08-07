package com.o2o.util;

/**
 * @author He
 * @Date 2019/7/30
 * @Time 9:47
 * @Description TODO
 **/

public class PathUtil {
    private static String separator = System.getProperty("file.separator");

    public static String getbasePath(){
        String os = System.getProperty("os.name");
        String basePath;
        if (os.toLowerCase().startsWith("win")) {
            basePath = "C:/Users/12089/IdeaProjects/o2oshop/target";
        } else {
            basePath = "/usr/local";
        }


        basePath = basePath.replace("/", separator);
        return basePath;
    }

    public static String getShopImagePath(long shopId) {
        String shopImagePathBuilder = "/upload/images/item/shop/" +
                shopId +
                "/";
        return shopImagePathBuilder.replace("/",
                separator);
    }




}
