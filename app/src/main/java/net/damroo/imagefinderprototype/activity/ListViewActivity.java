package net.damroo.imagefinderprototype.activity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.damroo.imagefinderprototype.R;
import net.damroo.imagefinderprototype.events.ImageSearchEvent;
import net.damroo.imagefinderprototype.events.ImageSearchEventType;
import net.damroo.imagefinderprototype.events.RemoveImageDataEvent;
import net.damroo.imagefinderprototype.service.DBEventService;
import net.damroo.imagefinderprototype.service.DaggerComponent;
import net.damroo.imagefinderprototype.service.DaggerDaggerComponent;
import net.damroo.imagefinderprototype.service.NetworkEventService;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;


public class ListViewActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private SimpleCursorAdapter simpleCursorAdapter;

    @Inject
    public NetworkEventService networkEventService;

    @Inject
    public DBEventService dbEventService;

    // changing in PROJECTION means change in setViewBinder and SimpleCursorAdapter
    static String[] PROJECTION = new String[]{"id as _id", "caption", "title", "imageUrl"}; // select(PROJECTION)

    static String SELECTION = null; // where(SELECTION), SELECTION = "id > 1300"

    @SuppressWarnings("SpellCheckingInspection")
    static String SORTORDER = ""; // order by SORTORDER,  SORTORDER = "id DESC"


    private static String searchQuery = "";

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(networkEventService);
        EventBus.getDefault().register(dbEventService);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        // dagger - register for di
        DaggerComponent daggerComponent = DaggerDaggerComponent.create();
        daggerComponent.inject(this);

        // clean cache
        dbEventService.removeImageData(new RemoveImageDataEvent());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarImageList);
        setSupportActionBar(toolbar);

        final ListView listView = (ListView) findViewById(R.id.imageList);

        // please notice that some values/styles are set in view binder
        String[] fromColumns = {"_id", "title", "imageUrl"};
        int[] toViews = {R.id.imageId, R.id.imageTitle, R.id.imageListImage}; // The TextView in simple_list_item_1

        // Create an empty adapter, pass null for the cursor, then update it in onLoadFinished()
        simpleCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.content_imagelist, null,
                fromColumns, toViews, 0);

        simpleCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.imageId) {
                    TextView tv = (TextView) view;
                    tv.setText(cursor.getString(cursor.getColumnIndex("_id")));
                    return true;
                }
                if (view.getId() == R.id.imageTitle) {
                    TextView tv = (TextView) view;
                    tv.setText(cursor.getString(cursor.getColumnIndex("title")));
                    return true;
                }
                // Set Image
                if (view.getId() == R.id.imageListImage) {
                    ImageView iv = (ImageView) view;
                    Glide.with(view.getContext())
                            .load(cursor.getString(cursor.getColumnIndex("imageUrl")))
                            .placeholder(R.mipmap.placeholder_image)
                            .skipMemoryCache(false)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(iv);
                    return true;
                }
                return false;
            }
        });
        listView.setAdapter(simpleCursorAdapter);

        // Prepare the loader.  Either re-connect with an existing one, or start a new one.
        getLoaderManager().initLoader(0, null, this);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;

            }

            private void isScrollCompleted() {
                // check if scroll is completed and hit bottom.
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                        && this.currentScrollState == SCROLL_STATE_IDLE) {

                    Log.d("loading ... ", "new images");
                    EventBus.getDefault().post(new ImageSearchEvent(ListViewActivity.searchQuery, ImageSearchEventType.SCROLL));
                }
            }

        });

        // show image caption on item click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                String caption = cursor.getString(cursor.getColumnIndex("caption"));

                AlertDialog.Builder builder = new AlertDialog.Builder(ListViewActivity.this);
                builder.setMessage(caption)
                        .setCancelable(true)
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // search user input text
                EventBus.getDefault().post(new ImageSearchEvent(query, ImageSearchEventType.SEARCH));
                ListViewActivity.searchQuery = query;
                ListViewActivity.this.getLoaderManager().restartLoader(0, null, ListViewActivity.this);
                searchView.setIconified(false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }


    // Called when a new Loader needs to be created
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Uri.parse("content://net.damroo.imagefinderprototype.provider/GettyImage"),
                PROJECTION, SELECTION, null, SORTORDER);
    }


    // Called when a previously created loader has finished loading
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        simpleCursorAdapter.swapCursor(data);
    }


    // Called when a previously created loader is reset, making the data unavailable
    public void onLoaderReset(Loader<Cursor> loader) {
        simpleCursorAdapter.swapCursor(null);
    }


    @Override
    public void onStop() {
        EventBus.getDefault().unregister(networkEventService);
        EventBus.getDefault().unregister(dbEventService);
        super.onStop();
    }

}
