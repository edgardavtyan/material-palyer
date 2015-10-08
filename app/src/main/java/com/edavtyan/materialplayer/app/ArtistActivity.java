package com.edavtyan.materialplayer.app;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.edavtyan.materialplayer.app.music.adapters.TrackAdapter;

public class ArtistActivity
        extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String EXTRA_ARTIST_TITLE = "artist_title";

    private static final int LOADER_ID = 1;



    private TrackAdapter trackAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        getLoaderManager().initLoader(LOADER_ID, null, this);

        trackAdapter = new TrackAdapter(this, null, TrackAdapter.TrackInfoAmount.TIME_AND_ALBUM);

        initToolbar();
        initCollapsingToolbar();
        initTracksView();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new TracksLoader(this, getIntent().getStringExtra(EXTRA_ARTIST_TITLE));
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        trackAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        trackAdapter.swapCursor(null);
    }

    private static class TracksLoader extends CursorLoader {
        String artistTitle;

        public TracksLoader(Context context, String artistTitle) {
            super(context);
            this.artistTitle = artistTitle;
        }

        @Override
        public Cursor loadInBackground() {
            return getContext().getContentResolver().query(
                    TrackAdapter.URI,
                    TrackAdapter.PROJECTION,
                    MediaStore.Audio.Media.ARTIST + "='" + artistTitle + "'",
                    null,
                    TrackAdapter.SORT_ORDER);
        }

    }



    private void initTracksView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.artist_tracks_listview);
        recyclerView.setAdapter(trackAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initCollapsingToolbar() {
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.artist_collapsingToolbar);

        collapsingToolbar.setTitleEnabled(true);
        collapsingToolbar.setTitle(getIntent().getStringExtra(EXTRA_ARTIST_TITLE));
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.artist_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
