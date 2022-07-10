package application.app;

import java.io.File;

public class FileFormatException extends Exception{
    public FileFormatException(){
        super();
    }

    public FileFormatException(File file){
        super("Invalid file format" + file.getName());
    }

    public FileFormatException(File file, Throwable throwable){
        super("Invalid file format" + file.getName(), throwable);
    }
}
