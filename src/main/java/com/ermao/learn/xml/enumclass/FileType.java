package com.ermao.learn.xml.enumclass;

/**
 * EnumName: FileType.java
 * Author: ermao
 * Date: 2020/2/27 com.ermao.learn.xml.enumclass 12:44 上午
 * Description:
 */
public enum FileType {
    DIR("Dir","文件夹",1),
    FILE("File","文件",2);

    /**
     * 文件类型说明（英文）
     */
    private String fileTypeEN;

    /**
     * 文件类型说明（中文）
     */
    private String fileTypeCN;

    /**
     * 索引
     */
    private int fileIndex;

    /**
     * 构造器
     * @param fileTypeEN String
     * @param fileTypeCN String
     */
    FileType(String fileTypeEN, String fileTypeCN, int fileIndex) {
        this.fileTypeEN = fileTypeEN;
        this.fileTypeCN = fileTypeCN;
        this.fileIndex = fileIndex;
    }

    public static FileType getDir(int fileIndex){
        for (FileType fileType: FileType.values() ) {
            if(fileType.getFileIndex() == fileIndex){
                return fileType;
            }
        }
        return null;
    }

    public String getFileTypeEN() {
        return fileTypeEN;
    }

    public String getFileTypeCN() {
        return fileTypeCN;
    }

    public int getFileIndex() {
        return fileIndex;
    }
}
