package com.example.moviesystem.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.moviesystem.model.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;


@Component
public class MovieService implements IMovieService {
	
	
	//In order to access movie details by name parameter
	public List<Movie> getMovieByName(String name) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "application/json");
		headers.add("authorization", "<your-api-key>");
		String url="https://api.collectapi.com/imdb/imdbSearchByName?query=" + name;
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = client.exchange(url, HttpMethod.GET,requestEntity,String.class);
		String outcome = response.getBody();
		System.out.println(outcome);
		
		//Parsing JSON fields and assign them to Movie List
		ObjectMapper objectMapper = new ObjectMapper();
		List<Movie> movies = new ArrayList<Movie>();
		try {
			JsonNode node = objectMapper.readTree(outcome);
			JsonNode parse = node.get("result");
			if(parse.isArray()) {
				ArrayNode moviesNode =(ArrayNode) parse;
				for(int i = 0; i < moviesNode.size() ;i++) {
					JsonNode tmp = moviesNode.get(i);
					String title = tmp.get("Title").toString();
					String year = tmp.get("Year").toString();
					String imdbId = tmp.get("imdbID").toString();
					String type = tmp.get("Type").toString();
					String poster = tmp.get("Poster").toString();
					Movie movie = new Movie();
					movie.setImdbID(imdbId);
					movie.setTitle(title);
					movie.setType(type);
					movie.setYear(year);
					movie.setPoster(poster);
					movies.add(movie);
				
				}
			}
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		return movies;
	}
	
	
	//Returning details of movie for given id parameter
	@Override
	public Movie getMovieById(String id) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "application/json");
		headers.add("authorization", "<your-api-key>");
		String url = "https://api.collectapi.com/imdb/imdbSearchById?movieId=" + id;
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = client.exchange(url, HttpMethod.GET,requestEntity,String.class);
		String outcome = response.getBody();
		System.out.println(outcome);
		
		//Parsing JSON fields and assign them to Movie Object
		ObjectMapper objectMapper = new ObjectMapper();
		Movie movie = new Movie();
		try {
			JsonNode node = objectMapper.readTree(outcome);
			JsonNode parse = node.get("result");
			String title = parse.get("Title").toString();
			String year = parse.get("Year").toString();
			String imdbId = parse.get("imdbID").toString();
			String type = parse.get("Type").toString();
			String poster = parse.get("Poster").toString();
			movie.setTitle(title);
			movie.setYear(year);
			movie.setImdbID(imdbId);
			movie.setType(type);
			movie.setPoster(poster);
			
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movie;
	}

	

	

}
