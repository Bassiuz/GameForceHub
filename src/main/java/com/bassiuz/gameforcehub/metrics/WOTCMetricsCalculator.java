package com.bassiuz.gameforcehub.metrics;

import com.bassiuz.gameforcehub.WERFiles.WerFile;
import com.bassiuz.gameforcehub.WERFiles.WerFileRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import java.util.*;

public class WOTCMetricsCalculator {
    public static String getEngagedPlayers() {
        return "Not enough input to calculate metric";
    }

    public static String getTickets() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR) - 1);
        return Integer.toString(getTicketCountFromTo(calendar.getTime(), new Date()));
    }

    public static String getEngagedPlayersMonthsAverage(int month) {
        return "Not enough input to calculate metric";
    }

    public static String getTicketsMonthsAverage(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MONTH ,calendar.get(Calendar.MONTH) - month);
        return Integer.toString(getTicketCountFromTo(calendar.getTime(), new Date())/month);
    }

    public static String getEngagedPlayersYearAverage(int month) {
        return "Not enough input to calculate metric";
    }

    public static String getTicketsYearAverage(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - month);
        return Integer.toString(getTicketCountFromTo(calendar.getTime(), new Date())/month*12);
    }

    private static int getTicketCountFromTo(Date start, Date end)
    {
        int count = 0;
        for (WerFile werFile : new WerFileRepository().findAllInRange(start, end))
        {
            count = count + werFile.getPlayers().size();
        }
        return count;
    }

}


