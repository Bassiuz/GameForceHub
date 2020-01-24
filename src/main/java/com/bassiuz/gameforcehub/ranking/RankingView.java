package com.bassiuz.gameforcehub.ranking;

import com.bassiuz.gameforcehub.Player.Player;
import com.bassiuz.gameforcehub.Player.PlayerRepository;
import com.bassiuz.gameforcehub.WERFiles.WerFileRepository;
import com.bassiuz.gameforcehub.tools.SortingTool;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.HashMap;
import java.util.Map;

@Path("/RankingView")
public class RankingView {

    @GET
    @Path("/MostAttendance/{year}")
    @Produces("text/html")
    public String MostAttendance(@PathParam int year) {
        HashMap<Player, Integer> result = new HashMap<>();
        for (Player player : new PlayerRepository().findAll())
        {
            result.put(player, new WerFileRepository().findByAttendingPlayer(player.getDci(), year).size());
        }
        result = SortingTool.sortByValue(result);

        String resultHtml = "";

        for(Player entry : result.keySet())
        {
            resultHtml = resultHtml + "<div><b>"+ entry.getFirstName() + " " + entry.getLastName() +"</b> - " + Integer.toString(result.get(entry)) + "</div>";
        }
        return resultHtml;
    }
}
