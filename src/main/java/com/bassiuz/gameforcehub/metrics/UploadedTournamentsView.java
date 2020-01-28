
package com.bassiuz.gameforcehub.metrics;

import com.bassiuz.gameforcehub.Player.Player;
import com.bassiuz.gameforcehub.Player.PlayerRepository;
import com.bassiuz.gameforcehub.WERFiles.WerFile;
import com.bassiuz.gameforcehub.WERFiles.WerFileRepository;
import com.bassiuz.gameforcehub.tools.SortingTool;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.lang.reflect.Array;
import java.util.*;

@Path("/Metrics/UploadedTournaments")
public class UploadedTournamentsView {

    @GET
    @Produces("text/html")
    public String uploadedTournaments() {

        HashMap<String, ArrayList<WerFile>> werFileListByWeek = new HashMap<>();

        ArrayList<WerFile> werfiles = new WerFileRepository().findAll();

        Collections.sort(werfiles, new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                return ((WerFile)o1).getTournamentDate().compareTo(((WerFile)o2).getTournamentDate());
            }
        });

        for (WerFile werFile : werfiles)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(werFile.getTournamentDate());
            String weekAndYear = cal.get(Calendar.YEAR) + " - " + cal.get(Calendar.WEEK_OF_YEAR);

            ArrayList<WerFile> week = werFileListByWeek.get(weekAndYear);
            if (week == null)
            {
                week = new ArrayList<>();
                werFileListByWeek.put(weekAndYear, week);
            }
            week.add(werFile);
        }

        String resultHtml = "";

        for(String week : werFileListByWeek.keySet())
        {
            resultHtml = resultHtml + "<h1>Week: " + week + "</h1>";

            resultHtml = resultHtml + "<table style=\"width:100%\">\n";
            resultHtml = resultHtml + "<tr><h1><th>Tournament Name</th><th>File Name</th><th>Tournament Date</th><th>Sanction ID</th></h1></tr>";
            resultHtml = resultHtml + "<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>";

            for (WerFile werFile : werFileListByWeek.get(week))
            {
                resultHtml = resultHtml + "<tr><th>" + werFile.getTournamentName() + "</th><th>" + werFile.getFileName() + "</th><th>" + werFile.getTournamentDate() + "</th><th>" + werFile.getSancionId() + "</th></tr>";
            }
            resultHtml = resultHtml + "</table>";
        }
        return resultHtml;
    }
}
