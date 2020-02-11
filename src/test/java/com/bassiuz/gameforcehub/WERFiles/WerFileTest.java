package com.bassiuz.gameforcehub.WERFiles;

import com.bassiuz.gameforcehub.Player.PlayerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class WerFileTest {

    @Test
    void parseObjectFromXML() throws SAXException {
        String xml = WerFileTestXMLValues.simpleTournament;
        WerFile werFile = new WerFile();
        werFile.setXmlValue(xml);
        LocalDate now = LocalDate.now();
        werFile.setUploadDate(now);

        assertEquals(now, werFile.getUploadDate());
        assertEquals(xml, werFile.getXmlValue());

        werFile.parseObjectFromXML(new PlayerRepository());

        assertEquals(2, werFile.getPlayers().size());
        assertEquals(2, werFile.getPlayerList().size());

        assertEquals("1203644082", werFile.getPlayers().get(0).getDci());
        assertEquals("Bas", werFile.getPlayers().get(0).getFirstName());
        assertEquals("De Vaan", werFile.getPlayers().get(0).getLastName());

        assertEquals("19-11-14983786", werFile.getSancionId());
        //assertEquals("Magic Casual Event", werFile.getTournamentName());

        //startdate="2019-11-14"
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.NOVEMBER);
        cal.set(Calendar.YEAR, 2019);
        cal.set(Calendar.DAY_OF_MONTH, 14);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        assertEquals(cal.getTime(), werFile.getTournamentDate());
    }

    @Test
    void crashWhileParseNonXML() throws SAXException {
        String xml = "not an xml";
        WerFile werFile = new WerFile();
        werFile.setXmlValue(xml);
        Assertions.assertThrows(SAXException.class, () -> {
            werFile.parseObjectFromXML(new PlayerRepository());
        });
    }

    @Test
    void hasPlayerWithDCI() {
    }
}