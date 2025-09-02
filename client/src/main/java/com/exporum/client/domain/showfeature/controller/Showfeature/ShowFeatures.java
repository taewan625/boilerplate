package com.exporum.client.domain.showfeature.controller.Showfeature;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShowFeatures {

    @GetMapping("/roaster-village")
    public String roasterVillage() {
        return "show-features/roaster-village";
    }

    @GetMapping("/producer-village")
    public String producerVillage() {
        return "show-features/producer-village";
    }

    @GetMapping("/cupping-rooms")
    public String cuppingRooms() {
        return "show-features/cupping-rooms";
    }

    @GetMapping("/sca-lecture-series")
    public String scaLectureSeries() {
        return "show-features/sca-lecture-series";
    }

    @GetMapping("/sca-community-lounge")
    public String scaCommunityLounge() {
        return "show-features/sca-community-lounge";
    }

    @GetMapping("/world-brewers-cup")
    public String worldBrewersCup() {
        return "show-features/world-brewers-cup";
    }

    @GetMapping("/brew-bars")
    public String brewBars() {
        return "show-features/brew-bars";
    }
}