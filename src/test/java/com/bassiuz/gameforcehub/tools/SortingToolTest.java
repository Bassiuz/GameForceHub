package com.bassiuz.gameforcehub.tools;

import com.bassiuz.gameforcehub.Player.Player;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SortingToolTest {

    @Test
    void sortByValue() {
        HashMap<Player, Integer> hashmap = new HashMap<>();

        Player bas = new Player();
        bas.setFirstName("bas");
        Player frits = new Player();
        frits.setFirstName("frits");

        Player stefan = new Player();
        stefan.setFirstName("Stefan Motherfucking Beerens");
        hashmap.put(bas, 2);
        hashmap.put(stefan, 23123);
        hashmap.put(frits, 1);

        hashmap = SortingTool.sortByValue(hashmap);

        assertEquals(stefan, hashmap.keySet().toArray()[0]);
        assertEquals(bas, hashmap.keySet().toArray()[1]);
        assertEquals(frits, hashmap.keySet().toArray()[2]);
    }
}