package com.example.tech6.sampleapp.setters;

public class Movies {

    private String movieid;
    private String moviename;
    private String moviestarring;
    private String image;
    private String bigimage;
    private String description;
    private String category;

    public Movies() {
        // TODO Auto-generated constructor stub
    }

    public Movies(String movieid,String moviename, String moviestarring,String image, String bigimage, String description, String category) {
        super();
        this.movieid = movieid;
        this.moviename = moviename;
        this.moviestarring = moviestarring;
        this.image = image;
        this.bigimage = bigimage;
        this.description = description;
        this.category = category;
    }

    public String getMovieid() {
        return movieid;
    }

    public void setMovieid(String movieid) {
        this.movieid = movieid;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getMoviestarring() {
        return moviestarring;
    }

    public void setMoviestarring(String moviestarring) {
        this.moviestarring = moviestarring;
    }

    public String getBigImage() {
        return bigimage;
    }

    public void setBigImage(String bigimage) {
        this.bigimage = bigimage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
