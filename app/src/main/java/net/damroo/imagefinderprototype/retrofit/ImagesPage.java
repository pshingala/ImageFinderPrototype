package net.damroo.imagefinderprototype.retrofit;

import net.damroo.imagefinderprototype.database.model.GettyImage;

import java.util.List;

public class ImagesPage {

    private List<GettyImage> images;

    private int result_count;

    public List<GettyImage> getImages() {
        return images;
    }

    public void setImages(List<GettyImage> images) {
        this.images = images;
    }

    public int getResult_count() {
        return result_count;
    }

    public void setResult_count(int result_count) {
        this.result_count = result_count;
    }

}
