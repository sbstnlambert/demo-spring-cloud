package be.technifutur.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/communication")
public class CommunicationController {

    private final RestTemplate restTemplate;

    public CommunicationController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/client")
    public String startCommunicationWithClientService() {
        return restTemplate.getForObject("http://client-service/communication", String.class);
    }

    @GetMapping("/movie")
    public String startCommunicationWithMovieService() {
        return restTemplate.getForObject("http://movie-service/communication", String.class);
    }
}
