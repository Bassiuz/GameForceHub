package com.bassiuz.gameforcehub.Player;

import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {

    Player findByDci(String Dci);
}
