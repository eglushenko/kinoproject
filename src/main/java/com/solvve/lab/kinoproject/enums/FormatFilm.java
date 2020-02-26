package com.solvve.lab.kinoproject.enums;

public enum FormatFilm {
    FORMAT_FILM_2D("2D"),
    FORMAT_FILM_3D("3D"),
    FORMAT_FILM_3D_IMAX("3D IMAX");

    private String typeOfStatus;

    FormatFilm(String typeOfStatus) {
        this.typeOfStatus = typeOfStatus;
    }
}
