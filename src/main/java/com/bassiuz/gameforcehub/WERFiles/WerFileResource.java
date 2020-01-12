package com.bassiuz.gameforcehub.WERFiles;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.bassiuz.gameforcehub.Player.Player;
import com.bassiuz.gameforcehub.Player.PlayerRepository;
import com.bassiuz.gameforcehub.tools.SortingTool;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import org.springframework.web.bind.annotation.RequestBody;
import org.xml.sax.SAXException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


@Path("/WerFiles")
public class WerFileResource {

    private final WerFileRepository werFileRepository;
    private final PlayerRepository playerRepository;

    public WerFileResource(WerFileRepository werFileRepository, PlayerRepository playerRepository) {
        this.werFileRepository = werFileRepository;
        this.playerRepository = playerRepository;
    }

    @GET
    @Produces("application/json")
    public Iterable<WerFile> findAll() {
        for (WerFile werFile : werFileRepository.findAll())
        {
            try {
                werFile.parseObjectFromXML(playerRepository);
                werFileRepository.save(werFile);
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }
        return werFileRepository.findAll();
    }

    @GET
    @Path("/MostAttendance/{year}")
    @Produces("application/json")
    public HashMap<String, Integer> MostAttendance(@PathParam int year) {
        HashMap<String, Integer> result = new HashMap<>();
        for (Player player : playerRepository.findAll())
        {
           result.put(player.getFirstName() + " " + player.getLastName(), werFileRepository.findByAttendingPlayer(player.getId(), year).size());
        }
        return SortingTool.sortByValue(result);
    }

    @GET
    @Path("/MostJudged/{year}")
    @Produces("application/json")
    public HashMap<String, Integer> MostJudged(@PathParam int year) {
        HashMap<String, Integer> result = new HashMap<>();
        for (Player player : playerRepository.findAll())
        {
            result.put(player.getFirstName() + " " + player.getLastName(), werFileRepository.findByAmountJudged(player.getId(),year).size());
        }
        return SortingTool.sortByValue(result);
    }

    @POST
    @Path("/name/{name}")
    @Produces("application/json")
    public WerFile create(@PathParam String name) {
        return werFileRepository.save(new WerFile(name));
    }

    @POST
    @Path("/postList")
    @Produces("application/json")
    public ArrayList<String> postList(@RequestBody ArrayList<String> list) {

        ArrayList<String> results = new ArrayList<>();

        for (String fileName : list)
            if(!werFileRepository.existsWerFileByFileName(fileName))
                results.add(fileName);

        return results;
    }

    @POST
    @Path("/postFile")
    @Produces("application/json")
    public WerFile postList(@RequestBody WerFile file) {
        file.setUploadDate(LocalDate.now());
        return werFileRepository.save(file);
    }


}
