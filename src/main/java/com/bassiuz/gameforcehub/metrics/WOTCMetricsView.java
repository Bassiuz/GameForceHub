package com.bassiuz.gameforcehub.metrics;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/Metrics/WOTCMetrics")
public class WOTCMetricsView {

    @GET
    @Produces("text/html")
    public String uploadedTournaments() {
        String resultHtml = "";
        resultHtml = resultHtml + "<div><b>Current Engaged Players: </b>" + WOTCMetricsCalculator.getEngagedPlayers() + "</div>";
        resultHtml = resultHtml + "<div><b>Average Per Month in last 3 months: </b>" + WOTCMetricsCalculator.getEngagedPlayersMonthsAverage(3) + "</div>";
        resultHtml = resultHtml + "<div><b>Year Metric according to average: </b>" + WOTCMetricsCalculator.getEngagedPlayersYearAverage(3) + "</div>";

        resultHtml = resultHtml + "<br/><br/>";

        resultHtml = resultHtml + "<div><b>Tickets: </b>" + WOTCMetricsCalculator.getTickets() + "</div>";
        resultHtml = resultHtml + "<div><b>Average Per Month in last 3 months: </b>" + WOTCMetricsCalculator.getTicketsMonthsAverage(3) + "</div>";
        resultHtml = resultHtml + "<div><b>Year Metric according to average: </b>" + WOTCMetricsCalculator.getTicketsYearAverage(3) + "</div>";

        return resultHtml;
    }
}
