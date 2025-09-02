package com.exporum.client.domain.exhibit.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExhibitionYears {
    private List<Integer> exhibitionYears;

    public ExhibitionYears(List<Integer> exhibitionYears) {
        this.exhibitionYears = exhibitionYears;
    }
}
