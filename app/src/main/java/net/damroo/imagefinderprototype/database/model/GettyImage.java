package net.damroo.imagefinderprototype.database.model;


import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.provider.ContentUri;
import com.raizlabs.android.dbflow.structure.provider.BaseSyncableProviderModel;
import com.raizlabs.android.dbflow.structure.provider.ContentUtils;

import net.damroo.imagefinderprototype.database.MyDataBase;

import java.util.List;

@Table(database = MyDataBase.class)
public class GettyImage extends BaseSyncableProviderModel{

    public static final String NAME = "GettyImage";

    @ContentUri(path = NAME, type = ContentUri.ContentType.VND_MULTIPLE + NAME)
    public static final Uri CONTENT_URI = ContentUtils.buildUriWithAuthority(MyDataBase.AUTHORITY);

    @Override
    public Uri getDeleteUri() {
        return MyDataBase.GettyImage.CONTENT_URI;
    }

    @Override
    public Uri getInsertUri() {
        return MyDataBase.GettyImage.CONTENT_URI;
    }

    @Override
    public Uri getUpdateUri() {
        return MyDataBase.GettyImage.CONTENT_URI;
    }

    @Override
    public Uri getQueryUri() {
        return MyDataBase.GettyImage.CONTENT_URI;
    }


    // dbflow needs a primary key lets assume id is unique string.
    @PrimaryKey
    @Expose
    @Column
    private String id;

    @Column
    @Expose
    private String title;

    @Column
    @Expose
    private String caption;


    // we save imageUrl however this field is not present in the api.
    @Column
    private String imageUrl;

    // we do not care about other sizes then thumbnail hence we do not need to save them.
    @Expose
    private List<Image> display_sizes;

    public GettyImage() {
    }


    @Override
    public void save() {
        if (display_sizes != null) {
            for (Image image : display_sizes) {
                if (image.getName().equalsIgnoreCase("thumb")) {
                    this.setImageUrl(image.getUri());
                }
            }
        }
        super.save();
    }


    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Image> getDisplay_sizes() {
        return display_sizes;
    }

    public void setDisplay_sizes(List<Image> display_sizes) {
        this.display_sizes = display_sizes;
    }
}