package com.example.lenovo.moviedb3;


public class MovieDetails {
    private Double vote_count,id,vote_average,popularity;
    private String title,poster_path,original_language,original_title,backdrop_path,overview,release_date;
    private Boolean video,adult;


    public void setId(Double id) {
        this.id = id;
    }

     void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

     void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

     void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public Double getVote_count() {

        return vote_count;
    }

    public Double getId() {
        return id;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getBackdrop_path() {
        return "https://image.tmdb.org/t/p/w500"+backdrop_path;

    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Boolean getVideo() {
        return video;
    }

    public Boolean getAdult() {
        return adult;
    }

    public MovieDetails() {

    }
}