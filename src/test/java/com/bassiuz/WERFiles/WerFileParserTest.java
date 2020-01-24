package com.bassiuz.WERFiles;

import com.bassiuz.gameforcehub.WERFiles.WerFile;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


@QuarkusTest
public class WerFileParserTest {

  //  @Test
    public void testXMLFileNotSetIfStringIsNotXML() {
        WerFile werFile = new WerFile();
        werFile.setXmlValue("Indistinguisable Gibberish");
        Assertions.assertEquals(null,
                werFile.getXmlValue(),
                "XML Value not set, because String is not XML."
        );
    }

    @Test
    public void testXMlFileSet() {
        WerFile werFile = new WerFile();
        werFile.setXmlValue(WerFileTestXMLValues.simpleTournament);
        Assertions.assertEquals(WerFileTestXMLValues.simpleTournament,
                                werFile.getXmlValue(),
                                "XML Value Properly Set"
        );
    }

  //  @Test
    public void testIfTournamentHasPlayersAfterParsing() {
        WerFile werFile = new WerFile();
        werFile.setXmlValue(WerFileTestXMLValues.simpleTournament);
        Assertions.assertEquals(2,
                werFile.getPlayerList().size(),
                "Tournament has to include 2 players."
        );
    }
}
