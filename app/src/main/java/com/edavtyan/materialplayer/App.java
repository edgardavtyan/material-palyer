package com.edavtyan.materialplayer;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.edavtyan.materialplayer.components.SdkFactory;
import com.edavtyan.materialplayer.components.audio_effects.AudioEffectsFactory;
import com.edavtyan.materialplayer.components.main.MainActivity;
import com.edavtyan.materialplayer.components.main.MainFactory;
import com.edavtyan.materialplayer.components.notification.PlayerNotificationFactory;
import com.edavtyan.materialplayer.components.player.Player;
import com.edavtyan.materialplayer.components.player.PlayerFactory;
import com.edavtyan.materialplayer.components.player.receivers.ReceiversFactory;
import com.edavtyan.materialplayer.components.search.album.SearchAlbumFactory;
import com.edavtyan.materialplayer.components.search.album.SearchAlbumFragment;
import com.edavtyan.materialplayer.components.search.artist.SearchArtistFactory;
import com.edavtyan.materialplayer.components.search.artist.SearchArtistFragment;
import com.edavtyan.materialplayer.components.search.tracks.SearchTrackFactory;
import com.edavtyan.materialplayer.components.search.tracks.SearchTrackFragment;
import com.edavtyan.materialplayer.lib.base.BaseFactory;

import lombok.Setter;

public class App extends Application {

	private @Setter BaseFactory baseFactory;
	private @Setter AudioEffectsFactory audioEffectsFactory;
	private @Setter SdkFactory sdkFactory;
	private @Setter MainFactory mainFactory;
	private @Setter SearchArtistFactory searchArtistFactory;
	private @Setter SearchAlbumFactory searchAlbumFactory;
	private @Setter SearchTrackFactory searchTrackFactory;
	private @Setter ReceiversFactory receiversFactory;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public BaseFactory getBaseFactory(Activity activity) {
		return (baseFactory == null)
				? new BaseFactory(activity)
				: baseFactory;
	}

	public AudioEffectsFactory getAudioEffectsFactory(Context context, Player player) {
		return (audioEffectsFactory == null)
				? new AudioEffectsFactory(context, player)
				: audioEffectsFactory;
	}

	public PlayerFactory getPlayerFactory(Context context) {
		return new PlayerFactory(context);
	}

	public PlayerNotificationFactory getPlayerNotificationFactory(Context context) {
		return new PlayerNotificationFactory(context);
	}

	public SdkFactory getSdkFactory() {
		return (sdkFactory == null)
				? new SdkFactory()
				: sdkFactory;
	}

	public SearchArtistFactory getSearchArtistFactory(Context context, SearchArtistFragment view) {
		return (searchArtistFactory == null)
				? new SearchArtistFactory(context, view)
				: searchArtistFactory;
	}

	public SearchAlbumFactory getSearchAlbumFactory(Context context, SearchAlbumFragment view) {
		return (searchAlbumFactory == null)
				? new SearchAlbumFactory(context, view)
				: searchAlbumFactory;
	}

	public SearchTrackFactory getSearchTrackFactory(Context context, SearchTrackFragment view) {
		return (searchTrackFactory == null)
				? new SearchTrackFactory(context, view)
				: searchTrackFactory;
	}

	public ReceiversFactory getReceiversFactory(Context context, Player player) {
		return (receiversFactory == null)
				? new ReceiversFactory(context, player)
				: receiversFactory;
	}

	public MainFactory getMainFactory(MainActivity activity) {
		return (mainFactory == null) ? new MainFactory(activity) : mainFactory;
	}
}
