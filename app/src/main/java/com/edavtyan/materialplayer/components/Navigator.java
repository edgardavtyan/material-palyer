package com.edavtyan.materialplayer.components;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.edavtyan.materialplayer.R;
import com.edavtyan.materialplayer.components.album_detail.AlbumDetailActivity;
import com.edavtyan.materialplayer.components.album_detail.AlbumDetailActivityCompact;
import com.edavtyan.materialplayer.components.album_detail.AlbumDetailMvp;
import com.edavtyan.materialplayer.components.artist_detail.ArtistDetailActivity;
import com.edavtyan.materialplayer.components.artist_detail.ArtistDetailActivityCompact;
import com.edavtyan.materialplayer.components.artist_detail.ArtistDetailMvp;
import com.edavtyan.materialplayer.components.audioeffects.AudioEffectsActivity;
import com.edavtyan.materialplayer.components.now_playing.NowPlayingActivity;
import com.edavtyan.materialplayer.components.now_playing_queue.NowPlayingQueueActivity;
import com.edavtyan.materialplayer.components.prefs.PrefActivity;

public class Navigator {
	private final Context context;
	private final CompactDetailPref compactDetailPref;

	public Navigator(Context context) {
		this.context = context;
		this.compactDetailPref = new CompactDetailPref(context);
	}

	public void gotoArtistDetail(String artistTitle) {
		Class activityClass = compactDetailPref.getValue()
				? ArtistDetailActivityCompact.class : ArtistDetailActivity.class;

		Intent intent = new Intent(context, activityClass);
		intent.putExtra(ArtistDetailMvp.EXTRA_ARTIST_TITLE, artistTitle);
		context.startActivity(intent);
	}

	public void gotoAlbumDetail(int albumId) {
		Class activityClass = compactDetailPref.getValue()
				? AlbumDetailActivityCompact.class : AlbumDetailActivity.class;

		Intent intent = new Intent(context, activityClass);
		intent.putExtra(AlbumDetailMvp.EXTRA_ALBUM_ID, albumId);
		context.startActivity(intent);
	}

	public void gotoNowPlaying() {
		Intent intent = new Intent(context, NowPlayingActivity.class);
		context.startActivity(intent);
	}

	public void gotoNowPlayingQueue() {
		Intent intent = new Intent(context, NowPlayingQueueActivity.class);
		context.startActivity(intent);
		((AppCompatActivity) context).overridePendingTransition(R.anim.fade_in, android.R.anim.fade_out);
	}

	public void gotoAudioEffects() {
		Intent intent = new Intent(context, AudioEffectsActivity.class);
		context.startActivity(intent);
	}

	public void gotoSettings() {
		Intent intent = new Intent(context, PrefActivity.class);
		context.startActivity(intent);
	}
}
