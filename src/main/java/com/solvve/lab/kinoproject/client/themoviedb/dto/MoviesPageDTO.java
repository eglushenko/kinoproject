package com.solvve.lab.kinoproject.client.themoviedb.dto;

import lombok.Data;

import java.util.List;

@Data
public class MoviesPageDTO {
    private Integer page;
    private Integer totalResults;
    private Integer totalPages;

    private List<MovieReadShortDTO> results;

}
