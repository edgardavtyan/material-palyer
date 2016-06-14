package com.edavtyan.materialplayer.app.views.artistdetail;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;

import com.bumptech.glide.Glide;
import com.edavtyan.materialplayer.app.R;
import com.edavtyan.materialplayer.app.models.album.AlbumsProvider;
import com.edavtyan.materialplayer.app.models.artist.Artist;
import com.edavtyan.materialplayer.app.models.artist.ArtistsProvider;
import com.edavtyan.materialplayer.app.views.albumslist.AlbumsListAdapter;
import com.edavtyan.materialplayer.app.views.lib.activities.CollapsingHeaderListActivity;
import com.edavtyan.materialplayer.app.views.lib.adapters.RecyclerViewCursorAdapter;

public class ArtistDetailActivity extends CollapsingHeaderListActivity {
	public static final String EXTRA_ARTIST_NAME = "artist_name";

	private AlbumsListAdapter albumsAdapter;
	private AlbumsProvider albumsProvider;
	private ArtistsProvider artistsProvider;

	/*
	 * AsyncTasks
	 */

	private class ArtistLoadTask extends AsyncTask<String, Void, Artist> {
		@Override
		protected Artist doInBackground(String... artistTitles) {
			return artistsProvider.getArtistFromTitle(artistTitles[0]);
		}

		@Override
		protected void onPostExecute(Artist artist) {
			titleView.setText(artist.getTitle());

			String albumsPlural = getResources().getQuantityString(
					R.plurals.albums, artist.getAlbumsCount(), artist.getAlbumsCount());
			String tracksPlural = getResources().getQuantityString(
					R.plurals.tracks, artist.getTracksCount(), artist.getTracksCount());
			String artistInfo = getResources().getString(
					R.string.pattern_artist_info, albumsPlural, tracksPlural);
			infoView.setText(artistInfo);
		}
	}

	//---

	public static void startActivity(Context context, String artistName) {
		Intent intent = new Intent(context, ArtistDetailActivity.class);
		intent.putExtra(EXTRA_ARTIST_NAME, artistName);
		context.startActivity(intent);
	}

	/*
	 * CollapsingHeaderListActivity
	 */

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		albumsAdapter = new AlbumsListAdapter(this, null);
		albumsProvider = new AlbumsProvider(this);
		artistsProvider = new ArtistsProvider(this);
		super.onCreate(savedInstanceState);

		Glide.with(this).load(R.drawable.fallback_artist).into(imageView);
		new ArtistLoadTask().execute(getIntent().getStringExtra(EXTRA_ARTIST_NAME));
	}

	@Override
	protected void onResume() {
		super.onResume();
		albumsAdapter.bindService();
	}

	@Override
	protected void onPause() {
		super.onPause();
		albumsAdapter.unbindService();
	}

	@Override
	public Loader<Cursor> getLoader() {
		String artist = getIntent().getStringExtra(EXTRA_ARTIST_NAME);
		return albumsProvider.getArtistAlbumsLoader(artist);
	}

	@Override
	public RecyclerViewCursorAdapter getAdapter() {
		return albumsAdapter;
	}
}
