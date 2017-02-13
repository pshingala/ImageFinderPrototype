package net.damroo.imagefinderprototype.database.model;

import com.google.gson.annotations.Expose;

public class Image {

    @Expose
    private String uri;

    @Expose
    private String name;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
