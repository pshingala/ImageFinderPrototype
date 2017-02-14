package net.damroo.imagefinderprototype.service;

import net.damroo.imagefinderprototype.activity.ListViewActivity;
import net.damroo.imagefinderprototype.service.image.NetworkService;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {DaggerModule.class})
public interface DaggerComponent {

    void inject(NetworkEventService service);

    void inject(ListViewActivity listViewActivity);

    void inject(NetworkService networkService);

    void inject(DBEventService dbEventService);

}
