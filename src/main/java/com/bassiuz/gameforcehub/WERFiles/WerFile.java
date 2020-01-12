package com.bassiuz.gameforcehub.WERFiles;

import com.bassiuz.gameforcehub.Player.Player;
import com.bassiuz.gameforcehub.Player.PlayerRepository;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.persistence.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@NamedQuery(name = "WerFile.findAllCustom",
        query = "SELECT COUNT(w) from WerFile w")
public class WerFile {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate uploadDate;
    private String fileName;

    @Column(length = 3000000)
    private String xmlValue;

    @ManyToMany(cascade = {CascadeType.ALL})
    private List<Player> players = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.ALL})
    private Player headJudge;
    private Date tournamentDate;

    private String format;


    public WerFile() {
    }

    public WerFile(String name) {
        fileName = name;
    }


    public void parseObjectFromXML(PlayerRepository playerRepository) throws SAXException {
        WerFileParser.parseWerFile(this, playerRepository);
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setXmlValue(String xmlValue) {
        try {
            this.xmlValue = xmlValue;
        } catch (Exception e) {
            this.xmlValue = null;
        }
    }

    @Override
    public String toString() {
        return "WerFile{" +
                "id=" + id +
                '}';
    }

    public Collection<Player> getPlayerList() {
        return players;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Player getHeadJudge() {
        return headJudge;
    }

    public void setHeadJudge(Player headJudge) {
        this.headJudge = headJudge;
    }

    public Date getTournamentDate() {
        return tournamentDate;
    }

    public void setTournamentDate(Date tournamentDate) {
        this.tournamentDate = tournamentDate;
    }
}