package com.solvve.lab.kinoproject.client.themoviedb;


import com.solvve.lab.kinoproject.client.themoviedb.dto.MovieReadDTO;
import com.solvve.lab.kinoproject.client.themoviedb.dto.MoviesPageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "api.themoviedb.org", url = "${themoviedb.api.url}", configuration = TheMovieDbClientConfig.class)
public interface TheMovieDbClient {

    @RequestMapping(method = RequestMethod.GET, value = "/movie/{movieId}")
    MovieReadDTO getMovie(@PathVariable("movieId") String movieId, @RequestParam String language);

    @RequestMapping(method = RequestMethod.GET, value = "/movie/{movieId}")
    MovieReadDTO getMovie(@PathVariable("movieId") String externalId);

    @RequestMapping(method = RequestMethod.GET, value = "/movie/top_rated")
    MoviesPageDTO getTopRatedMovies();
}
