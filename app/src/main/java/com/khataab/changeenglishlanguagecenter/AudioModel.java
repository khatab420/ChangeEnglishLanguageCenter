package com.khataab.changeenglishlanguagecenter;

public class AudioModel {
    private String downloadedURl;
    private String fileName;
    private String typeName;
    private String bookName;
    private String unit;

    private boolean isPlaying;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public AudioModel(String FileName, String TypeName){
        this.fileName=FileName;
        this.typeName=TypeName;
        isPlaying=false;
    }

    public AudioModel(String downloadedURl, String fileName, String typeName, String unit,String bookName) {
        this.downloadedURl = downloadedURl;
        this.fileName = fileName;
        this.typeName = typeName;
        this.bookName = bookName;
        this.unit=unit;
        isPlaying=false;
    }

    public String getDownloadedURl() {
        return downloadedURl;
    }

    public void setDownloadedURl(String downloadedURl) {
        this.downloadedURl = downloadedURl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
