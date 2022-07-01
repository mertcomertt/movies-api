package com.example.moviesystem.model;
import java.util.List;

//MovieResponse Object all data fields declared
public class MovieResponse {

		private boolean success;
		private List<Movie> result;
		
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		public List<Movie> getResult() {
			return result;
		}
		public void setResult(List<Movie> result) {
			this.result = result;
		}
		
	}

