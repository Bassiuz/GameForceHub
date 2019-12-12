package com.bassiuz.gameforcehub.WERFiles;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface WerFileRepository extends CrudRepository<WerFile, Long> {

    List<WerFile> findByUploadDate(LocalDate date);

    boolean existsWerFileByFileName(String fileName);

}