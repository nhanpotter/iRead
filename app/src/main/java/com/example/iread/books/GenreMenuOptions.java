package com.example.iread.books;

public enum GenreMenuOptions {
    FANTASY("Fantasy"), ADVENTURE("Adventure"), ROMANCE("Romance"), MYSTERY("Mystery"),
    HORROR("Horror"), FICTION("Science Fiction"), HUMOR("Humor");

    private String genre;

    private GenreMenuOptions(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return this.genre;
    }
}
