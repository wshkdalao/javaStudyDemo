package com.ermao.learn.xml.exception;

/**
 * ClassName: PathIsNotDirectory.java
 * Author: ermao
 * Date: 2020/2/23 com.ermao.learn.xml.exception 11:54 下午
 * Description:
 */
public class PathIsNotDirectory extends RuntimeException {
    public PathIsNotDirectory(String message) {
        super(message);
    }
}
