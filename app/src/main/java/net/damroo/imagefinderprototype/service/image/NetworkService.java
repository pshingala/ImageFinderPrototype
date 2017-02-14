package net.damroo.imagefinderprototype.service.image;

import net.damroo.imagefinderprototype.database.model.GettyImage;
import net.damroo.imagefinderprototype.events.ImageSearchEvent;
import net.damroo.imagefinderprototype.events.ImageSearchEventType;
import net.damroo.imagefinderprototype.events.SaveImageDataEvent;
import net.damroo.imagefinderprototype.retrofit.GettyRestAdapter;
import net.damroo.imagefinderprototype.retrofit.ImagesPage;
import net.damroo.imagefinderprototype.service.DaggerComponent;
import net.damroo.imagefinderprototype.service.DaggerDaggerComponent;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;


public class NetworkService {

    DaggerComponent network;

    private GettyRestAdapter rest;

    private DBService DBService;

    private final int RESULTS_PER_PAGE = 100;

    @Inject
    public NetworkService(GettyRestAdapter rest, DBService DBService) {
        network = DaggerDaggerComponent.create();
        network.inject(this);
        this.rest = rest;
        this.DBService = DBService;
    }

    public boolean getImages(ImageSearchEvent event) {

        // remove everything from cache for new search
        if (event.imageSearchEventType == ImageSearchEventType.SEARCH)
            DBService.removeImages();

        // get page number for the api call
        long page = event.imageSearchEventType == ImageSearchEventType.SEARCH ? 1 : DBService.getNextPageNumber(RESULTS_PER_PAGE);

        // set parameters
        Map<String, String> params = new HashMap<>();
        params.put("phrase", event.query);
        params.put("page", String.valueOf(page));
        params.put("page_size", String.valueOf(RESULTS_PER_PAGE));

        return downloadImagesPage(params);
    }

    private boolean downloadImagesPage(Map<String, String> params) {
        try {
            Call<ImagesPage> call = rest.getImageService(params);
            ImagesPage page = call.execute().body();
            if (page.getResult_count() == 0)
                return false;

            // Save images
            for (GettyImage GettyImage : page.getImages()) {
                EventBus.getDefault().post(new SaveImageDataEvent(GettyImage));
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}