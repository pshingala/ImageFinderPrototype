package net.damroo.imagefinderprototype.database;

import android.net.Uri;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.provider.ContentProvider;
import com.raizlabs.android.dbflow.annotation.provider.ContentUri;
import com.raizlabs.android.dbflow.annotation.provider.TableEndpoint;


@ContentProvider(authority = MyDataBase.AUTHORITY,
        database = MyDataBase.class,
        baseContentUri = MyDataBase.BASE_CONTENT_URI)
@Database(name = MyDataBase.NAME, version = MyDataBase.VERSION)
public class MyDataBase {

    public static final String NAME = "MyDataBase";

    public static final int VERSION = 1;

    public static final String AUTHORITY = "net.damroo.imagefinderprototype.provider";

    public static final String BASE_CONTENT_URI = "content://";

    private static Uri buildUri(String... paths){
        Uri.Builder builder = Uri.parse(MyDataBase.BASE_CONTENT_URI + MyDataBase.AUTHORITY).buildUpon();
        for(String path : paths){
            builder.appendPath(path);
        }
        return builder.build();
    }


    @TableEndpoint(name = GettyImage.ENDPOINT, contentProvider = MyDataBase.class)
    public static class GettyImage {
        public static final String ENDPOINT = "GettyImage";

        private static Uri buildUri(String... paths) {
            Uri.Builder builder = Uri.parse(BASE_CONTENT_URI + AUTHORITY).buildUpon();
            for (String path : paths) {
                builder.appendPath(path);
            }
            return builder.build();
        }

        @ContentUri(path = GettyImage.ENDPOINT, type = ContentUri.ContentType.VND_MULTIPLE + ENDPOINT)
        public static Uri CONTENT_URI = buildUri(ENDPOINT);
    }

}