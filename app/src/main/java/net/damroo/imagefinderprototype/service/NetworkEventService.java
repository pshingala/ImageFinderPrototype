package net.damroo.imagefinderprototype.service;


import net.damroo.imagefinderprototype.events.ImageSearchEvent;
import net.damroo.imagefinderprototype.service.image.NetworkService;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;


public class NetworkEventService {

    DaggerComponent network;

    private NetworkService networkService;


    @Inject
    public NetworkEventService(NetworkService networkService) {
        network = DaggerDaggerComponent.create();
        network.inject(this);
        this.networkService = networkService;
    }


    // network jobs in asynchronous mode
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void searchImages(ImageSearchEvent event) {
        networkService.getImages(event);
    }


}

