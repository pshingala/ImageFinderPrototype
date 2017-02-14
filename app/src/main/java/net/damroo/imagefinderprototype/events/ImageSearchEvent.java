package net.damroo.imagefinderprototype.events;


public class ImageSearchEvent {

    public final String query;

    public final ImageSearchEventType imageSearchEventType;

    public ImageSearchEvent(String query, ImageSearchEventType imageSearchEventType) {
        this.query = query;
        this.imageSearchEventType = imageSearchEventType;
    }

}
