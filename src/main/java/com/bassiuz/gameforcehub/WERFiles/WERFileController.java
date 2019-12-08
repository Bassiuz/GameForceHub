package com.bassiuz.gameforcehub;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/WERFiles")
public class WERFileController {

    @GetMapping("/test")
    public String test() {
        return "testerquester";
    }

}