package com.bassiuz.gameforcehub.tools;

import com.bassiuz.gameforcehub.Player.Player;

import java.util.*;

public class SortingTool {
    public static HashMap<Player, Integer> sortByValue(HashMap<Player, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Player, Integer> > list =
                new LinkedList<Map.Entry<Player, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Player, Integer> >() {
            public int compare(Map.Entry<Player, Integer> o1,
                               Map.Entry<Player, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Player, Integer> temp = new LinkedHashMap<Player, Integer>();
        for (Map.Entry<Player, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

}
