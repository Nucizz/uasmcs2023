package com.example.uas.System.Object;

import androidx.annotation.Nullable;

public class Post {
    private int userId = 0; // incase Null
    private String id;
    private String title;
    private String description;

    public Post(@Nullable int userId, String id, String title, String description) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
