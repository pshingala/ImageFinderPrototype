package net.damroo.imagefinderprototype.service;

import net.damroo.imagefinderprototype.activity.ListViewActivity;
import net.damroo.imagefinderprototype.service.image.ImageDBService;
import net.damroo.imagefinderprototype.service.image.OrderNetworkService;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {DaggerModule.class})
public interface DaggerComponent {

    void inject(NetworkEventService service);

    void inject(ListViewActivity listViewActivity);

    void inject(OrderNetworkService orderNetworkService);

    void inject(ImageDBService imageDBService);

}
