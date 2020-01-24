package com.bassiuz.gameforcehub.Player;

import com.bassiuz.gameforcehub.tools.LocalRepository;

import java.util.ArrayList;
import java.util.Optional;

public class PlayerRepository{

    private static LocalRepository<Player> players = new LocalRepository<Player>();

    public Player findByDci(String Dci)
    {
        Optional<Player> optionalPlayer = players.stream().filter(player -> player.getDci().equals(Dci)).findFirst();

        if (optionalPlayer.isEmpty())
        {
            return null;
        }

        return optionalPlayer.get();
    }

    public Player save(Player player)
    {
        return players.save(player, findByDci(player.getDci()));
    }

    public ArrayList<Player> findAll()
    {
        return players;
    }

    private boolean has(Player player)
    {
        return findByDci(player.getDci()) != null;
    }
}
