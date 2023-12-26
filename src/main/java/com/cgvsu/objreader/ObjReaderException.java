//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.cgvsu.objreader;

public class ObjReaderException extends RuntimeException {
    public ObjReaderException(String errorMessage, int lineIndex) {
        super("Error parsing .obj file on line: " + lineIndex + ". " + errorMessage);
    }

    public ObjReaderException(String errorMessage) {
        super("Error parsing .obj file: " + errorMessage);
    }
}
