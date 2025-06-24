package model;

import java.util.ArrayList;

public class Product {
    public int id;
    public String name;
    public String ingredient;
    public String category;
    public String description;
    public String direction;
    public String thumbnail;
    public int prominence;
    public int avai;

    public Product(int id, String name, String ingredient, String category, String description, String direction, String thumbnail, int prominence, int avai) {
        this.id = id;
        this.name = name;
        this.ingredient = ingredient;
        this.category = category;
        this.description = description;
        this.direction = direction;
        this.thumbnail = thumbnail;
        this.prominence = prominence;
        this.avai = avai;
    }

    // cartUnit, comment
    public Product(int id, String name,String thumbnail) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    // order detail
    public Product(int id, String thumbnail) {
        this.id = id;
        this.thumbnail = thumbnail;
    }

    // product admin
    public Product(int id, String name, String category, String thumbnail, int prominence, int avai) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.thumbnail = thumbnail;
        this.prominence = prominence;
        this.avai = avai;
    }

    public Product(int id) {
        this.id = id;
    }
}
