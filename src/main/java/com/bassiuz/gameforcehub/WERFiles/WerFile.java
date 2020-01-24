package com.bassiuz.gameforcehub.WERFiles;

import com.bassiuz.gameforcehub.Player.Player;
import com.bassiuz.gameforcehub.Player.PlayerRepository;
import org.xml.sax.SAXException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class WerFile {

    private LocalDate uploadDate;
    private String fileName;

    private String xmlValue;

    private List<Player> players = new ArrayList<>();

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

    public boolean hasPlayerWithDCI(String dci) {
        return players.stream().filter(player -> player.getDci().equals(dci)).findFirst().isPresent();
    }
}