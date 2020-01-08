package com.solvve.lab.kinoproject.dto;

import java.time.LocalDate;

public class FilmCreateDTO {
    private String title;
    private String country;
    private String lang;
    private float rate;
    private int length;
    private LocalDate lastUpdate;
    private String actor;
    private String category;
    private String filmText;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmCreateDTO that = (FilmCreateDTO) o;

        if (Float.compare(that.rate, rate) != 0) return false;
        if (length != that.length) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (lang != null ? !lang.equals(that.lang) : that.lang != null) return false;
        if (lastUpdate != null ? !lastUpdate.equals(that.lastUpdate) : that.lastUpdate != null) return false;
        if (actor != null ? !actor.equals(that.actor) : that.actor != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        return filmText != null ? filmText.equals(that.filmText) : that.filmText == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (lang != null ? lang.hashCode() : 0);
        result = 31 * result + (rate != +0.0f ? Float.floatToIntBits(rate) : 0);
        result = 31 * result + length;
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        result = 31 * result + (actor != null ? actor.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (filmText != null ? filmText.hashCode() : 0);
        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFilmText() {
        return filmText;
    }

    public void setFilmText(String filmText) {
        this.filmText = filmText;
    }
}
