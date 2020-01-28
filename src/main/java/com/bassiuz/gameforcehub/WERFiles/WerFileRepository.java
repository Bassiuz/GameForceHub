package com.bassiuz.gameforcehub.WERFiles;

import com.bassiuz.gameforcehub.tools.LocalRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WerFileRepository{

    private static LocalRepository<WerFile> werFiles = new LocalRepository<>();

    public boolean existsWerFileByFileName(String fileName)
    {
        Optional<WerFile> werFileOptional = werFiles.stream().filter(werFile -> werFile.getFileName().equals(fileName)).findFirst();
        return werFileOptional.isPresent();
    }

    public WerFile getByWerFileByFileName(String fileName)
    {
        Optional<WerFile> werFileOptional = werFiles.stream().filter(werFile -> werFile.getFileName().equals(fileName)).findFirst();

        if(werFileOptional.isEmpty())
            return null;

        return werFileOptional.get();
    }

    public List<WerFile> findByAttendingPlayer(String DCI,  int year)
    {
       return  werFiles.stream().filter(werFile -> werFile.getTournamentDate().getYear() == year - 1900 && werFile.hasPlayerWithDCI(DCI)).collect(Collectors.toList());
    }

    public List<WerFile> findByAmountJudged(String DCI, int year)
    {
        return  werFiles.stream().filter(werFile -> werFile.getTournamentDate().getYear() == year - 1900 && werFile.getHeadJudge().getDci().equals(DCI)).collect(Collectors.toList());
    }

    public ArrayList<WerFile> findAll()
    {
        return werFiles;
    }


    public WerFile save(WerFile werFile)
    {
        return werFiles.save(werFile, getByWerFileByFileName(werFile.getFileName()));
    }

    public List<WerFile> findAllInRange(Date start, Date end) {
        return  werFiles.stream().filter(werFile -> werFile.getTournamentDate().before(end) && werFile.getTournamentDate().after(start)).collect(Collectors.toList());
    }
}