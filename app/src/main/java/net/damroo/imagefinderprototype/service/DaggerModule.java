package net.damroo.imagefinderprototype.service;

import net.damroo.imagefinderprototype.retrofit.GettyRestAdapter;
import net.damroo.imagefinderprototype.service.image.DBService;
import net.damroo.imagefinderprototype.service.image.NetworkService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class DaggerModule {

    @Provides
    @Singleton
    GettyRestAdapter provideEPRestAdapter() {
        return new GettyRestAdapter();
    }


    @Provides
    @Singleton
    NetworkEventService provideNetworkEventService() {
        NetworkService service = new NetworkService(new GettyRestAdapter(), new DBService());
        return new NetworkEventService(service);
    }


    @Provides
    @Singleton
    DBEventService provideDBEventServiceBac() {
        return new DBEventService(new DBService());
    }


    @Provides
    @Singleton
    NetworkService provideOrderNetworkService() {
        return new NetworkService(new GettyRestAdapter(), new DBService());
    }

}