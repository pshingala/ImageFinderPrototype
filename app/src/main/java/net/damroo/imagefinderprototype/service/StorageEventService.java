package net.damroo.imagefinderprototype.service;


import net.damroo.imagefinderprototype.events.SaveImageDataEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class StorageEventService {

    public StorageEventService() {
    }

    // DB jobs in queue mode (background mode executes in sequence)
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void writeOrder(SaveImageDataEvent event) {
        try {
            event.image.save();
        } catch (Exception e) {
            // do nothing
        }
    }

}
