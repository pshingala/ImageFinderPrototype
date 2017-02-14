package net.damroo.imagefinderprototype.service.image;

import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import net.damroo.imagefinderprototype.database.model.GettyImage;

public class DBService {

    public DBService() {
    }


    public boolean removeImages() {
        Delete.table(GettyImage.class);
        GettyImage orderModel = SQLite.select().from(GettyImage.class).querySingle();
        if (orderModel == null)
            return true;
        return false;
    }


    public long getNextPageNumber(int pageSize) {
        try {
            long totalItems = SQLite.selectCountOf().from(GettyImage.class).count();
            long currentPage = totalItems / pageSize;
            return currentPage + 1;
        } catch (Exception e) {
            // if table does not exist or is without any record then return 0. sufficient for now.
            return 0;
        }
    }

}
