package com.bassiuz.gameforcehub;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/WerFile")
public class WerFileController {

    @GetMapping("/{name}")
    public String hello(@PathVariable(name = "name") String name) {
        return name;
    }
}
