package com.bassiuz.gameforcehub.WERFiles;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import com.bassiuz.gameforcehub.Player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WerFileRepository extends JpaRepository<WerFile, Long> {

    boolean existsWerFileByFileName(String fileName);

    @Query("select w from WerFile w inner join w.players p where p.id = :id AND YEAR(w.tournamentDate) = :year")
    List<WerFile> findByAttendingPlayer(@Param("id") Long id, @Param("year") int year);

    @Query("select w from WerFile w where headJudge.id = :id AND YEAR(w.tournamentDate) = :year")
    List<WerFile> findByAmountJudged(@Param("id") Long id, @Param("year") int year);
}