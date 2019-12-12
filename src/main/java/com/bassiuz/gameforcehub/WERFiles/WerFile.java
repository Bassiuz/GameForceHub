package com.bassiuz.gameforcehub.WERFiles;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class WerFile {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate uploadDate;
    private String fileName;
    private String xmlValue;


    public WerFile() {
    }

    public WerFile(String name) {
        fileName = name;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getXmlValue() {
        return xmlValue;
    }

    public void setXmlValue(String xmlValue) {
        this.xmlValue = xmlValue;
    }

    @Override
    public String toString() {
        return "WerFile{" +
                "id=" + id +
                '}';
    }
}