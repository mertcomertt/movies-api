package com.example.moviesystem.service;

import java.util.List;

import com.example.moviesystem.model.Movie;

//To open flexibility in development for similar datasources on our code MovieService created
public interface IMovieService {
	
	 List<Movie> getMovieByName(String name);
	 Movie getMovieById (String id);
}