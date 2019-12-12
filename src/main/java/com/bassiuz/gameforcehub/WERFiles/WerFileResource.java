package com.bassiuz.gameforcehub.WERFiles;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;


@Path("/WerFiles")
public class WerFileResource {

    private final WerFileRepository werFileRepository;

    public WerFileResource(WerFileRepository werFileRepository) {
        this.werFileRepository = werFileRepository;
    }

    @GET
    @Produces("application/json")
    public Iterable<WerFile> findAll() {
        return werFileRepository.findAll();
    }

    @POST
    @Path("/name/{name}")
    @Produces("application/json")
    public WerFile create(@PathParam String name) {
        return werFileRepository.save(new WerFile(name));
    }

    @POST
    @Path("/postList")
    @Produces("application/json")
    public ArrayList<String> postList(@RequestBody ArrayList<String> list) {

        ArrayList<String> results = new ArrayList<>();

        for (String fileName : list)
            if(!werFileRepository.existsWerFileByFileName(fileName))
                results.add(fileName);

        return results;
    }

    @POST
    @Path("/postFile")
    @Produces("application/json")
    public WerFile postList(@RequestBody WerFile file) {
        file.setUploadDate(LocalDate.now());
        return werFileRepository.save(file);
    }


}
