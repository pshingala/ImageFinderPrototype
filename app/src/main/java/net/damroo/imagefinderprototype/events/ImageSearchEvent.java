package net.damroo.imagefinderprototype.events;


public class ImageSearchEvent {

    public final String query;

    public final NetworkEventType networkEventType;

    public ImageSearchEvent(String query, NetworkEventType networkEventType) {
        this.query = query;
        this.networkEventType = networkEventType;
    }

}
