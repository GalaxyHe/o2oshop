package com.o2o.util;

import com.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

/**
 * @author He
 * @Date 2019/7/30
 * @Time 9:51
 * @Description TODO
 **/

public class ImageUtil {
    private static String basePath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().
            getResource("")).getPath();


    public static String generateThumbnail(InputStream thumbnailIps, String fileName,String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(fileName);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getbasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnailIps).size(200, 200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(
                    basePath + "/watermark.jpg"
            )), 0.25f).outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        return relativeAddr;
    }

    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
        //获取不重复的随机名
        String realFileName = getRandomFileName();
        System.out.println("realFileName:"+realFileName);
        //获取文件的拓展名
        String extension = getFileExtension(thumbnail.getImageName());
        System.out.println("extension:"+extension);
        //如果目标路径不存在，则自动创建
        makeDirPath(targetAddr);
        //获取文件存储的相对路径
        String relativeAddr = targetAddr + realFileName + extension;
        //获取文件要保存的路径
        File dest = new File(PathUtil.getbasePath() + relativeAddr);
        System.out.println("dest"+dest);
        //调用Thumbnails生成带水印的图片
        try {
            Thumbnails.of(thumbnail.getImage()).size(337, 640).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(
                    basePath + "/watermark.jpg"
            )), 0.25f).outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        return relativeAddr;

    }


     /**
      * @author He
      * @Description 生成随机文件名，当前年月日小时分钟秒钟+五位随机数
      * @Date
      * @Param
      * @return
      */
    public static synchronized String getRandomFileName() {
        String randomName = UUID.randomUUID().toString().substring(0,5);
        return randomName;
    }


    /**
     * @author He
     * @Description 创建文件夹
     * @Date
     * @Param
     * @return
     */
    public static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getbasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * @author He
     * @Description 获取文件拓展名
     * @Date
     * @Param String fileName
     * @return String
     */
    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * @author He
     * @Description storePath是文件的路径还是目录的路径，如果storePath是文件路径则删除
     * 如果是目录路径则删除该文件夹下的所有文件
     * @Date
     * @Param
     * @return
     */
    public static void deleteFileOrPath(String storePath){
        File fileorPath = new File(PathUtil.getbasePath() + storePath);
        if (fileorPath.exists()) {
            if (fileorPath.isDirectory()) {
                File[] files = fileorPath.listFiles();
                for (File file : Objects.requireNonNull(files)) {
                    file.delete();
                }
            }
            fileorPath.delete();
        }
    }



}
