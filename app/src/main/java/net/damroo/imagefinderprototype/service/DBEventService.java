package net.damroo.imagefinderprototype.service;


import net.damroo.imagefinderprototype.events.RemoveImageDataEvent;
import net.damroo.imagefinderprototype.events.SaveImageDataEvent;
import net.damroo.imagefinderprototype.service.image.DBService;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;


public class DBEventService {

    private DaggerComponent daggerComponent;

    private net.damroo.imagefinderprototype.service.image.DBService service;

    @Inject
    public DBEventService(DBService DBService) {
        daggerComponent = DaggerDaggerComponent.create();
        daggerComponent.inject(this);
        this.service = DBService;
    }

    // DB jobs in queue (background mode executes in sequence)
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void saveImageData(SaveImageDataEvent event) {
        try {
            event.image.save();
        } catch (Exception e) {
            // do nothing for now.
        }
    }

    // DB jobs in queue
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void removeImageData(RemoveImageDataEvent event) {
        try {
            service.removeImages();
        } catch (Exception e) {
            // do nothing for now.
        }
    }

}
