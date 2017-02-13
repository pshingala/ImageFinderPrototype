package net.damroo.imagefinderprototype.service;


import net.damroo.imagefinderprototype.events.ImageSearchEvent;
import net.damroo.imagefinderprototype.service.image.OrderNetworkService;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;


public class NetworkEventService {

    DaggerComponent network;

    private OrderNetworkService orderNetworkService;


    @Inject
    public NetworkEventService(OrderNetworkService orderNetworkService) {
        network = DaggerDaggerComponent.create();
        network.inject(this);
        this.orderNetworkService = orderNetworkService;
    }


    // network jobs in asynchronous mode
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void getFirstUse(ImageSearchEvent event) {
        orderNetworkService.downloadOrder(event);
    }


}

