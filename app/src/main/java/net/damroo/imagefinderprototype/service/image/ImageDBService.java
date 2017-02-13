package net.damroo.imagefinderprototype.service.image;


import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.damroo.imagefinderprototype.database.model.GettyImage;
import net.damroo.imagefinderprototype.service.StorageEventService;
import net.damroo.imagefinderprototype.service.DaggerComponent;
import net.damroo.imagefinderprototype.service.DaggerDaggerComponent;

import javax.inject.Inject;

public class ImageDBService {

    private DaggerComponent network;

    private StorageEventService service;

    @Inject
    public ImageDBService(StorageEventService service) {
        network = DaggerDaggerComponent.create();
        network.inject(this);
        this.service = service;
    }


    public boolean removeImages() {
        Delete.table(GettyImage.class);
        GettyImage orderModel = SQLite.select().from(GettyImage.class).querySingle();
        if (orderModel == null)
            return true;
        return false;
    }




}
