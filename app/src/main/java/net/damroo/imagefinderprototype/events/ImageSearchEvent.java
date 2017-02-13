package net.damroo.imagefinderprototype.events;


public class ImageSearchEvent {

    public final String query;

    public final int page;

    public ImageSearchEvent(String query, int page) {
        this.query = query;
        this.page = page;
    }

}
