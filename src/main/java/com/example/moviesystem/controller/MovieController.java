package com.example.moviesystem.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviesystem.model.Movie;
import com.example.moviesystem.service.IMovieService;
import com.example.moviesystem.service.MovieService;

@RestController
public class MovieController {
	
	@Autowired
	private IMovieService movieService;
	
	//Given name of move parameter returning details of specified movie
	@GetMapping(path="/movies/search")
	public List<Movie> search(@RequestParam(name="movie_name") String name) {
		return this.movieService.getMovieByName(name);
	}
	
	//Given id parameter to save the details of returning movie from CollectApi to details.txt file
	@PostMapping(path="/movies/saveToList/{id}")
	public boolean addToList(@PathVariable String id) {
		Movie movie = this.movieService.getMovieById(id);
		String row = movie.getImdbID()+ "," + movie.getTitle()+  "," + movie.getYear()+ "," + movie.getType()+ "," +  movie.getPoster();
		
		
		
		try {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("details.txt"),true));
		writer.write(row);
		writer.newLine();
		writer.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	//Given id parameter by checking whether details of movie is saved our local file or not. If it does not exist send request to Collect API to return details
	@PostMapping(path="/movies/detail/{id}")
	public Movie detail(@PathVariable String id) throws IOException{
		
		String movieID = String.format("\"" + id + "\"");
		
		System.out.println(movieID);
		try {
			BufferedReader reader = new BufferedReader(new FileReader("details.txt"));
			String row = reader.readLine();
			while (row != null) {
				String[] field = row.split(",");
				if(field[0].equals(movieID)) {
					Movie movie  = new Movie();
					movie.setImdbID(field[0]);
					movie.setTitle(field[1]);
					movie.setYear(field[2]);
					movie.setType(field[3]);
					movie.setPoster(field[4]);
					reader.close();
					return movie;
				}else {
					row = reader.readLine();
				}
				
			}     
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.movieService.getMovieById(id);
	}
	
}