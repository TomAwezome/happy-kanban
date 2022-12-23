package com.tomawezome.happykanban;

import androidx.annotation.NonNull;

public class Task {
    private String id;
    private String title;
    private String description;
    private String category;

    public Task() {
    }

    public Task(String title, String description, String id, String category) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getId() {
        return this.id;
    }

    public String getCategory() {
        return this.category;
    }

    public String setTitle(String title) {
        this.title = title;
        return this.title;
    }

    public String setDescription(String description) {
        this.description = description;
        return this.description;
    }

    public String setId(String id)
    {
        this.id = id;
        return this.id;
    }

    public String setCategory(String category) {
        this.category = category;
        return this.category;
    }


    @NonNull
    @Override
    public String toString()
    {
        return this.title + ":" + this.description;
    }
}
