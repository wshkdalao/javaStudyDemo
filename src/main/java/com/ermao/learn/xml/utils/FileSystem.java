package com.ermao.learn.xml.utils;

import com.ermao.learn.xml.entity.FileAttributes;
import com.ermao.learn.xml.enumclass.FileType;
import com.ermao.learn.xml.exception.FileTypeNotMatchException;
import com.ermao.learn.xml.exception.PathIsEmptyOrNullException;
import com.ermao.learn.xml.exception.PathNotExistException;
import com.ermao.learn.xml.exception.XMLDocumentException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * ClassName: FileSystem.java
 * Author: ermao
 * Date: 2020/2/24 com.ermao.learn.xml.utils 12:03 上午
 * Description:
 */
public class FileSystem {
    /**
     * 获取当前登录系统用户主目录File
     * @return File
     */
    public static File getCurrentUserHomeFile(){
        return FileSystemView.getFileSystemView().getHomeDirectory().getAbsoluteFile();
    }

    /**
     * Java 根据文件分隔符粗略判断当前系统，并返回当前系统桌面路径
     * @return File
     */
    public static File getDesktopFile(){
        File home = getCurrentUserHomeFile();
        File desktop = new File(home,"Desktop");
        if(desktop.exists()){
            // 应该是带桌面的系统都会有"Desktop"目录（不太确定）
            return desktop;
        }else{
            // 这种情况针对linux系统
            return home;
        }
    }

    /**
     * 解析路径
     * @param path String
     * @return File
     */
    public static File parsePath(String path) throws PathIsEmptyOrNullException {
        File file;
        if(path == null || path.equals("")){
            throw new PathIsEmptyOrNullException("所传路径字符串为空或者为null！所传路径值："+path+" ！");
        }
        // 判断下符号，如果所传路径是以~开头，则解析为当前用户
        if (path.matches("^\\~.*")){
            // 获取当前登录用户home目录
            File userHome = getCurrentUserHomeFile();
            file = new File(userHome, path.replaceAll("^\\~", "")).getAbsoluteFile();
        }
        // 如果所传路径以 "./"开头
        else if(path.matches("^\\.\\/.*$")){
            file = new File("").getAbsoluteFile();
            System.out.println(file.getAbsolutePath());
        }
        // 如果所传路径以 "../"开头
        else if (path.matches("^[\\.]{2}\\/.*$")){
            file = new File("").getAbsoluteFile().getParentFile();
        }
        // 常规路径
        else{
            file = new File(path);
        }
        return file;
    }

    /**
     * 判断路径是否存在
     * @param file File
     * @return boolean
     * @throws PathNotExistException 路径不存在异常
     */
    public static boolean fileExist(File file) throws PathNotExistException {
        // 判断下路径是否存在？
        if(!file.exists()){
            throw new PathNotExistException("所传路径不存在！当前路径是："+ file.getAbsolutePath()+"！请核实文件路径！");
        }
        return true;
    }

    /**
     * 递归遍历子目录所有内容
     * @param foreachFile File 遍历目录路径
     * @return Map 此Map是一个TreeMap
     */
    public static Map<String,Object> foreachDir(File foreachFile){
        // 定义输出结果
        Map<String,Object> map = new TreeMap<String, Object>();
        File[] filesArr = foreachFile.listFiles();
        if(filesArr != null){
            // 遍历数组
            for ( File file: filesArr ) {
                // 判断文件类型
                int fileType = file.isDirectory()? FileType.DIR.getFileIndex():FileType.FILE.getFileIndex();
                // 获取文件大小
                Long fileSize = file.length();
                // 获取文件名称
                String fileName = file.getName();
                // 创建文件对象
                FileAttributes fileAttributes = new FileAttributes(fileName,fileType,fileSize);
                System.out.println(fileAttributes.toString());
                if(fileType == FileType.DIR.getFileIndex()){
                    map.put(fileName,foreachDir(file));
                }else{
                    map.put(fileName,fileAttributes);
                }
            }
        }
        return map;
    }

    /**
     * 创建目录树XML
     */
    public static void createFileXml(File outputXMLFile,Map<String,Object> tree) throws XMLDocumentException, FileTypeNotMatchException, IOException {
        // 遍历Map
        Set<String> keySet = tree.keySet();
        Iterator<String> iterator = keySet.iterator();
        // 创建文档
        Document document = DocumentHelper.createDocument();
        // 创建根节点
        Element root = document.addElement(FileType.DIR.getFileTypeEN()+"s");
        // 创建元素
        while (iterator.hasNext()){
            Element element = crateFileElement(root,tree.get(iterator.next()));
        }
        // 文档创建
        XMLParse.getInstance().writeXML(new File(outputXMLFile,"ErmaoHomeWork.xml"), document);
    }

    private static Element crateFileElement(Element elem,Object obj) throws XMLDocumentException, FileTypeNotMatchException {
        if(elem == null || obj == null){
            throw new XMLDocumentException("所传元素为null或者遍历数据为null，无法创建节点信息！");
        }
        if(obj instanceof Map){
            // 获取迭代器
            Iterator<String> iterator = ((Map<String,Object>) obj).keySet().iterator();
            while (iterator.hasNext()){
                String dirName = iterator.next();
                // 创建一个元素
                Element childElem = elem.addElement(FileType.DIR.getFileTypeEN());
                childElem.addAttribute("DIR_NAME",dirName+File.separator);
                childElem.addAttribute("FILE_TYPE", FileType.DIR.getFileTypeCN());
                crateFileElement(childElem, ((Map<String, Object>) obj).get(dirName));
            }
        }else if (obj instanceof FileAttributes) {
            // 获取文件类型
            int fileTypeIndex = ((FileAttributes) obj).getFileType();
            FileType fileType = FileType.getDir(fileTypeIndex);
            if(fileType == null){
                throw new FileTypeNotMatchException("文件类型不匹配");
            }
            // 创建子元素
            Element child = elem.addElement(fileType.getFileTypeEN());
            child.addAttribute("FILE_NAME", ((FileAttributes)obj).getFileName());
            child.addAttribute("FILE_SIZE",((FileAttributes)obj).getFileSize().toString()+" B");
            child.addAttribute("FILE_TYPE",fileType.getFileTypeCN());
        }else{
            System.out.println("哈哈："+obj.toString());
            throw new XMLDocumentException("不识别文件属性！无法创建文档！");
        }
        return elem;
    }

}
