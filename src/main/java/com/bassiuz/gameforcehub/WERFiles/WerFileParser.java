package com.bassiuz.gameforcehub.WERFiles;

import com.bassiuz.gameforcehub.Player.Player;
import com.bassiuz.gameforcehub.Player.PlayerRepository;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WerFileParser {

    public static WerFile parseWerFile(WerFile werFile, PlayerRepository playerRepository) throws SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(
                    werFile.getXmlValue())));

            /// parse some values here
            for (int x = 0; x < doc.getChildNodes().getLength(); x++) {
                parseChildNodes(werFile, doc.getChildNodes().item(x), 0, playerRepository);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return werFile;
    }

    public static void parseChildNodes(WerFile werFile, Node node, int depth, PlayerRepository playerRepository) {
        String prefix = "-";
        for (int i = 0; i < depth; i++) {
            prefix += '-';
        }

        if (node.hasAttributes())
        {
            for (int j = 0; j < node.getAttributes().getLength(); j++) {
                Node attribute = node.getAttributes().item(j);
                // System.out.println(prefix + attribute.getNodeName() + " - " + attribute.getNodeValue());
            }
        }

       // System.out.println(prefix + node.getNodeName() + " - " + node.getNodeValue());

        parseSpecificNode(werFile, node, playerRepository);

        for (int x = 0; x < node.getChildNodes().getLength(); x++) {
            parseChildNodes(werFile, node.getChildNodes().item(x), depth + 1, playerRepository);
        }
    }

    public static void parseSpecificNode(WerFile werFile, Node node, PlayerRepository playerRepository)
    {
        if (node.getNodeName().equals("event"))
        {
            werFile.setFormat(node.getAttributes().getNamedItem("format").getNodeValue());
            try {
                Date startdate = new SimpleDateFormat("yyyy-MM-dd").parse(node.getAttributes().getNamedItem("startdate").getNodeValue());
                werFile.setTournamentDate(startdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (node.getAttributes().getNamedItem("headjudge") != null)
            {
                werFile.setHeadJudge(playerRepository.findByDci(node.getAttributes().getNamedItem("headjudge").getNodeValue()));
            }

        }

        if (node.getNodeName().equals("participation"))
        {
            ArrayList<Player> players = new ArrayList<Player>();
            for (int x = 0; x < node.getChildNodes().getLength(); x++) {
                if (node.getChildNodes().item(x).getNodeName().equals("person"))
                {
                    Player player = Player.findOrCreatePlayer(node.getChildNodes().item(x), playerRepository);
                    players.add(player);
                }
            }
            werFile.setPlayers(players);
        }
    }


}
