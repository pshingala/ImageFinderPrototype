package net.damroo.imagefinderprototype.service.image;

import net.damroo.imagefinderprototype.database.model.GettyImage;
import net.damroo.imagefinderprototype.events.UIEventType;
import net.damroo.imagefinderprototype.events.UIChangeEvent;
import net.damroo.imagefinderprototype.events.ImageSearchEvent;
import net.damroo.imagefinderprototype.events.SaveImageDataEvent;
import net.damroo.imagefinderprototype.retrofit.GettyRestAdapter;
import net.damroo.imagefinderprototype.retrofit.ImagesPage;
import net.damroo.imagefinderprototype.service.DaggerComponent;
import net.damroo.imagefinderprototype.service.DaggerDaggerComponent;
import net.damroo.imagefinderprototype.service.util.Utility;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;


public class OrderNetworkService {

    DaggerComponent network;

    private GettyRestAdapter rest;

    private ImageDBService imageDBService;
    private final String RESULTS_PER_PAGE = "100";


    @Inject
    public OrderNetworkService(GettyRestAdapter rest, ImageDBService imageDBService) {
        network = DaggerDaggerComponent.create();
        network.inject(this);
        this.rest = rest;
        this.imageDBService = imageDBService;
    }

    // allows downloading orders in three modes
    // 1. for first use (from last year)
    // 2. check and download for new orders (while swiping down)
    // 3. download old orders (while swiping up if page is over)
    public void downloadOrder(ImageSearchEvent event) {
        if (event.query.equals("firstUse")) {
        } else if (event.query.equals("downloadOldOrders")) {
        } else if (event.query.equals("downloadNewOrders")) {
            try {
            } finally {
                EventBus.getDefault().post(new UIChangeEvent(UIEventType.STOP_ANIMATION));
            }
        }

    }










    // In case of multiple order pages this method iterates over order pages calls downloadImagesPage
    private void iterateImagesPages(Map<String, String> params) {
        ImagesPage page = getPrepareDownloadImagesPage(params);
        if (page != null) {
            int totalPage = Utility.getLastPageNumber(page.getResult_count(), Double.parseDouble(RESULTS_PER_PAGE));

            for (int i = 1; i <= totalPage; i++) {
                Map<String, String> paramsForDownload = new HashMap<>();
                paramsForDownload.putAll(params);
                paramsForDownload.put("RESULTS_PER_PAGE", RESULTS_PER_PAGE);
                paramsForDownload.put("page", String.valueOf(i));
                downloadImagesPage(paramsForDownload);
            }

        }

    }


    // this method will iterate over orders in a given order page and trigger downloadDetailedOrder for the
    private void downloadImagesPage(Map<String, String> params) {
        try {
            Call<ImagesPage> call = rest.getImageService(params);
            ImagesPage page = call.execute().body();
            if (page.getResult_count() == 0)
                return;
            // Save orders
            for (GettyImage GettyImage : page.getImages()) {
                EventBus.getDefault().post(new SaveImageDataEvent(GettyImage));
            }
        } catch (Exception e) {
        }
    }



    // this method makes a call for computing total number of order pages before downloading
    private ImagesPage getPrepareDownloadImagesPage(Map<String, String> params) {
        params.put("RESULTS_PER_PAGE", "1");
        params.put("page", "1");
        Call<ImagesPage> call = rest.getImageService(params);
        try {
            return call.execute().body();
        } catch (Exception e) {
            return null;
        }
    }





}