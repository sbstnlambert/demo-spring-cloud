package be.technifutur.client.controller;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/communication")
public class CommunicationController {

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    public CommunicationController(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String startCommunication() {

        String responseBody = restTemplate.getForObject("http://movie-service/communication", String.class);

//        List<ServiceInstance> instances =  discoveryClient.getInstances("movie-service");
//        URI movieCommunicationUri = instances
//                .get(new Random().nextInt(instances.size()))
//                .getUri()
//                .resolve("/communication");
//        String responseBody = restTemplate.getForObject(movieCommunicationUri, String.class);
        return "Passed by client: " + responseBody;
    }
}
