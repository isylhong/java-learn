package com.autumn.learn.java8.io;

import org.junit.Test;

import java.io.File;
import java.util.Scanner;

public class FileTest {
    @Test
    public void testChangeFileName(){
        Scanner scn = new Scanner(System.in);
        System.out.print("请输入文件目录和前缀：");
        String[] command = scn.nextLine().trim().split("\\s+");
        changFileName(command[0], command[1]);
    }

    public void changFileName(String dir, String separator) {
        File nowDir = new File(dir);      //创建File对象
        if (!nowDir.isDirectory()) {
            System.out.println(dir + "不是有效的目录名！");
            System.exit(0);
        }
        File[] subDir = nowDir.listFiles();       //目录下的所有文件及目录
        for (int i = 0; i < subDir.length; i++) {

            File srcFile = subDir[i];
            if (srcFile.isDirectory()) {
                System.out.println(srcFile.getPath());
                changFileName(srcFile.getPath(),separator);
                continue;
            }

            String srcName = srcFile.getName();
            int beginIndex = srcName.indexOf(separator);

            String destPath = nowDir + File.separator + srcName.substring(beginIndex + 1);
            File destFile = new File(destPath);

            if (!srcFile.renameTo(destFile)) {
                System.out.println(srcFile.getName() + "\t重命名失败！");
            }

            System.out.println("文件重命名成功");
        }
    }

}
