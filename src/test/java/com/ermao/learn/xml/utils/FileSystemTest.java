package com.ermao.learn.xml.utils;

import com.ermao.learn.xml.exception.FileTypeNotMatchException;
import com.ermao.learn.xml.exception.XMLDocumentException;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileSystemTest {

    @Test
    public void testGetDesktopPath() {
        System.out.println(FileSystem.getDesktopFile().getAbsolutePath());
    }

    @Test
    public void testGetCurrentUserHomeFile(){
        System.out.println(FileSystem.getCurrentUserHomeFile().getAbsolutePath());
    }

    @Test
    public void tesForeachDir() throws XMLDocumentException, FileTypeNotMatchException, IOException {
        Map<String,Object> map = FileSystem.foreachDir(new File("/Users/ermao/Development/java/learn/xml-homework"));
        File outputDir = FileSystem.getDesktopFile();
        System.out.println(outputDir.getAbsoluteFile());
        File outputXMLFile = new File(outputDir,"test.xml");
        FileSystem.createFileXml(outputXMLFile,map);
    }
}