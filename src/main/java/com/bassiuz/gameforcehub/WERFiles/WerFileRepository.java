package com.bassiuz.gameforcehub.WERFiles;

import com.bassiuz.gameforcehub.tools.LocalRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xml.sax.SAXException;

import io.quarkus.scheduler.Scheduled;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WerFileRepository{

    private static LocalRepository<WerFile> werFiles = new LocalRepository<>();

    public boolean existsWerFileByFileName(final String fileName) {
        final Optional<WerFile> werFileOptional = werFiles.stream()
                .filter(werFile -> werFile.getFileName().equals(fileName)).findFirst();
        return werFileOptional.isPresent();
    }

    public WerFile getByWerFileByFileName(final String fileName) {
        final Optional<WerFile> werFileOptional = werFiles.stream()
                .filter(werFile -> werFile.getFileName().equals(fileName)).findFirst();

        if(!werFileOptional.isPresent())
            return null;

        return werFileOptional.get();
    }

    public List<WerFile> findByAttendingPlayer(final String DCI, final int year) {
        return werFiles.stream().filter(
                werFile -> werFile.getTournamentDate().getYear() == year - 1900 && werFile.hasPlayerWithDCI(DCI))
                .collect(Collectors.toList());
    }

    public List<WerFile> findByAmountJudged(final String DCI, final int year) {
        return werFiles.stream().filter(werFile -> werFile.getTournamentDate().getYear() == year - 1900
                && werFile.getHeadJudge().getDci().equals(DCI)).collect(Collectors.toList());
    }

    public ArrayList<WerFile> findAll() {
        return werFiles;
    }

    public WerFile save(final WerFile werFile) {
        return werFiles.save(werFile, getByWerFileByFileName(werFile.getFileName()));
    }

    public List<WerFile> findAllInRange(final Date start, final Date end) {
        return werFiles.stream()
                .filter(werFile -> werFile.getTournamentDate().before(end) && werFile.getTournamentDate().after(start))
                .collect(Collectors.toList());
    }

    private static int lastSavedHash = 0;
    private final static String filename = "werfiles.json";

    @Scheduled(every = "30s")
    void saveRepositoryToFile() throws IOException, SAXException {
        if (lastSavedHash == 0) {
            // list is not loaded yet, initialize the werlist
            // discrepancy does not matter; gets synced eventually if files are missing,

            File file = new File(filename);

            if(file.exists())
            {
                loadWerfilesFromFile();
            }
            else{
                System.out.println("file doensn't exist");
            }
        }

        if (lastSavedHash != werFiles.hashCode()) {
            writeWerfilesToFile();
            lastSavedHash = werFiles.hashCode();
        }

    }

    private void loadWerfilesFromFile() {
        try {
            final FileInputStream fileIn = new FileInputStream(filename);
            final ObjectInput objectIn = new ObjectInputStream(fileIn);

            String werFilesAsJson = (String) objectIn.readObject();
            Gson gson = new Gson();
            werFiles = gson.fromJson(werFilesAsJson, new TypeToken<LocalRepository<WerFile>>(){}.getType());

            System.out.println("The WerFiles have been read from the file");
            objectIn.close();

        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    private void writeWerfilesToFile() {
        try {
            Gson gson = new Gson();
            String werFilesAsJson = gson.toJson(werFiles);

            final FileOutputStream fileOut = new FileOutputStream(filename);
            final ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(werFilesAsJson);
            objectOut.close();
            System.out.println("The WerFiles were succesfully written to a file");

        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
}