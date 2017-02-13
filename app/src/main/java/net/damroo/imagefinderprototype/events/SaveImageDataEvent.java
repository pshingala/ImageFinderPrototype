package net.damroo.imagefinderprototype.events;

import net.damroo.imagefinderprototype.database.model.GettyImage;

public class SaveImageDataEvent {

    public final GettyImage image;

    public SaveImageDataEvent(GettyImage image) {
        this.image = image;
    }

}
