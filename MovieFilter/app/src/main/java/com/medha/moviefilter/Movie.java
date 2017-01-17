package com.medha.moviefilter;

import java.io.Serializable;

/**
 * Created by Prathyusha on 1/8/17.
 */

public class Movie implements Serializable {

    String posterUrl,backdropUrl;
    String title;
    String release_date;
    String synopsis;
    long movieId;

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    String rating;

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public void setBackdropUrl(String backdropUrl) {
        this.backdropUrl = backdropUrl;
    }




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "posterUrl='" + posterUrl + '\'' +
                ", backdropUrl='" + backdropUrl + '\'' +
                ", title='" + title + '\'' +
                ", release_date='" + release_date + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", movieId=" + movieId +
                ", rating='" + rating + '\'' +
                '}';
    }
}
