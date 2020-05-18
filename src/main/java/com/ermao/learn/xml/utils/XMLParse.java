package com.ermao.learn.xml.utils;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * ClassName: XMLParse.java
 * Author: ermao
 * Date: 2020/2/23 com.ermao.learn.xml 10:16 下午
 * Description:
 */
public class XMLParse {
    private static XMLParse xmlParseInstance;

    /**
     * 所传File对象
     */
    private File file;

    /**
     * DOM4J XML阅读器
     */
    private SAXReader saxReader;

    /**
     * DOM4J XML写入工具
     */
    private XMLWriter xmlWriter;

    private XMLParse() {

    }

    /**
     * 初始化一些必要的参数
     */
    private static void init(){
        xmlParseInstance.file = null;
    }

    /**
     * 获取实例信息
     * @return XMLParse
     */
    public static XMLParse getInstance() {
        if(xmlParseInstance == null){
            xmlParseInstance = new XMLParse();
        }
        // 初始化实例
        init();
        return xmlParseInstance;
    }


    public void readXML(){

    }

    public void writeXML(File outputFile, Document document) throws IOException {
        // 设置文档编码
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding(StandardCharsets.UTF_8.name());
        // 创建输出流
        OutputStream out = new FileOutputStream(outputFile);
        XMLWriter writer = new XMLWriter(out,format);
        writer.write(document);
        writer.close();
    }
}
