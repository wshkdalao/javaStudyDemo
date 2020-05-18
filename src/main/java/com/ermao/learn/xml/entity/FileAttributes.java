package com.ermao.learn.xml.entity;

/**
 * ClassName: FileAttributes.java
 * Author: ermao
 * Date: 2020/2/23 com.ermao.learn.xml.entity 10:18 下午
 * Description:
 */
public class FileAttributes {

    /**
     * 文件名称
     */
    protected String fileName;

    /**
     * 文件类型
     */
    protected Integer fileType;

    /**
     * 文件大小
     */
    protected Long fileSize;

    public FileAttributes() {
    }

    public FileAttributes(String fileName, Integer fileType, Long fileSize) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "FileAttributes{" +
                "fileName='" + fileName + '\'' +
                ", fileType=" + fileType +
                ", fileSize=" + fileSize +" B"+
                '}';
    }
}
