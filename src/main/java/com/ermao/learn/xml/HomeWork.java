package com.ermao.learn.xml;

import com.ermao.learn.xml.utils.FileSystem;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

/**
 * ClassName: HomeWork.java
 * Author: ermao
 * Date: 2020/2/23 com.ermao.learn.xml 11:56 下午
 * Description:
 */
public class HomeWork {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loop1:
        while(true){
            // 第一步：获取用户想要遍历的目录
            System.out.println("请输入需要查询的目录：");
            // 定义用户获取读取的目录
            String path = scanner.next();
            // 用户所输入遍历
            File checkDirPath = checkInputForeachPath(path,scanner);

            // 第二步：获取用户想要输出到指定文件的XML目录
            // 获取XML输出路径
            System.out.println("是否指定读取目录后XML输出目录？");
            System.out.println("如果不指定，将默认输出至桌面，请在控制台输入：【1】即可");
            System.out.println("如果需要指定路径则，输入你指定的输出路径：");
            // 用户输输出XML目录路径
            String userInputPath = scanner.next();
            // 定义XML文件输出路径
            File outputXMLPath = checkXMLOutputPath(userInputPath, scanner);

            // 第三步：向用户展示录入信息
            System.out.println("确认当前读取目录信息：");
            System.out.println(String.format("读取目录为：%s", checkDirPath.getAbsolutePath()));
            System.out.println(String.format("输出XML目录为：%s", outputXMLPath.getAbsolutePath()));

            // 第四步：遍历目录
            Map<String,Object> tree = FileSystem.foreachDir(checkDirPath);

            // 第五步：输出信息至XML文件
            try {
                FileSystem.createFileXml(outputXMLPath, tree);
            } catch (Exception e) {
                System.out.println("创建输出文件失败，原因："+e.getMessage());
                break ;
            }

            // 是否退出系统
            while(true){
                System.out.println("是否继续输出目录信息？");
                System.out.println("1：继续查询；2：退出系统");
                int inputNum = scanner.nextInt();
                if(inputNum == 1){
                    break;
                }else{
                    break loop1;
                }
            }
        }
    }

    /**
     * 检查用户的遍历目录是否有效
     * @param path String 用户输入遍历目录字符串
     * @param scanner Scanner
     * @return File
     */
    private static File checkInputForeachPath(String path,Scanner scanner){
        // 定义输出File对象
        File file;
        while (true){
            // 解析用户输入查询目录
            try {
                file = FileSystem.parsePath(path);
                // 判断用户输入路径是否真实存在
                FileSystem.fileExist(file);
                break;
            } catch (Exception e) {
                System.out.println("遍历目录错误："+e.getMessage());
                System.out.println("请重新输入遍历目录！");
                path = scanner.next();
                continue;
            }
        }
        return file;
    }

    private static File checkXMLOutputPath(String path,Scanner scanner){
        File file;
        while (true){
            // 判断用户输入的XML输出路径是否有效
            try {
                // 解析用户输入
                if(path.equals("1")){
                    // 获取用户桌面的路径
                    file = FileSystem.getDesktopFile();
                }else{
                    // 解析用户输入的XML输出路径
                    file = FileSystem.parsePath(path);
                    // 判断用户输入路径是否真实存在
                    FileSystem.fileExist(file);
                }
                break;
            }catch (Exception e){
                // 输出信息后
                System.out.println(e.getMessage());
                System.out.println("请重新输入XML输出路径；");
                path = scanner.next();
                continue;
            }
        }
        return file;
    }
}
