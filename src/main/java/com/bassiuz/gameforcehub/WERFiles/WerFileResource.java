package com.bassiuz.gameforcehub.WERFiles;

import com.bassiuz.gameforcehub.Player.Player;
import com.bassiuz.gameforcehub.Player.PlayerRepository;
import com.bassiuz.gameforcehub.tools.SortingTool;
import io.quarkus.scheduler.Scheduled;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.xml.sax.SAXException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Path("/WerFiles")
public class WerFileResource {

    private final WerFileRepository werFileRepository = new WerFileRepository();
    private final PlayerRepository playerRepository = new PlayerRepository();

    public WerFileResource() {
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
    public HashMap<Player, Integer> MostAttendance(@PathParam int year) {
        HashMap<Player, Integer> result = new HashMap<>();
        for (Player player : playerRepository.findAll())
        {
           result.put(player, werFileRepository.findByAttendingPlayer(player.getDci(), year).size());
        }
        return SortingTool.sortByValue(result);
    }

    @GET
    @Path("/MostJudged/{year}")
    @Produces("application/json")
    public HashMap<Player, Integer> MostJudged(@PathParam int year) {
        HashMap<Player, Integer> result = new HashMap<>();
        for (Player player : playerRepository.findAll())
        {
            result.put(player, werFileRepository.findByAmountJudged(player.getDci(),year).size());
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

    @GET
    @Path("/test/allEnvVariables")
    @Produces("application/json")
    public Map<String, String> allEnvVariables()
    {
        Map<String, String> map = System.getenv();
        for (Map.Entry <String, String> entry: map.entrySet()) {
            System.out.println("Variable Name:- " + entry.getKey() + " Value:- " + entry.getValue());
        }
        return map;
    }

}
