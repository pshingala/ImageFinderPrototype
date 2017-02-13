package net.damroo.imagefinderprototype.service;

import net.damroo.imagefinderprototype.retrofit.GettyRestAdapter;
import net.damroo.imagefinderprototype.service.image.ImageDBService;
import net.damroo.imagefinderprototype.service.image.OrderNetworkService;

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
        StorageEventService dbService = new StorageEventService();
        OrderNetworkService service = new OrderNetworkService(new GettyRestAdapter(), new ImageDBService(dbService));
        return new NetworkEventService(service);
    }


    @Provides
    @Singleton
    StorageEventService provideDBEventServiceBac() {
        return new StorageEventService();
    }


    @Provides
    @Singleton
    OrderNetworkService provideOrderNetworkService() {
        StorageEventService dbService = new StorageEventService();
        return new OrderNetworkService(new GettyRestAdapter(), new ImageDBService(dbService));
    }

}