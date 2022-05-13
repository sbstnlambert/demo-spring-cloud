package be.technifutur.movie.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/communication")
public class CommunicationController {

    @GetMapping
    public String testCommunication() {
        return "Successful communication";
    }

}
