package org.firstinspires.ftc.teamcode.Utilities.Files.FileUtils;

public class FileDataHandler {

    FileLogReader reader;
    FileLogWriter writer;

    public FileDataHandler(String filename){
        reader = new FileLogReader(filename);
        writer = new FileLogWriter(filename);
    }

    public void writeData(){

    }

}
