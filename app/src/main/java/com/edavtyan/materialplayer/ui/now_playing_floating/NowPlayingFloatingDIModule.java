package com.edavtyan.materialplayer.ui.now_playing_floating;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.edavtyan.materialplayer.ui.FragmentScope;
import com.edavtyan.materialplayer.lib.album_art.AlbumArtProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class NowPlayingFloatingDIModule {
	private final NowPlayingFloatingFragment view;
	private final FragmentActivity activity;

	public NowPlayingFloatingDIModule(FragmentActivity activity, NowPlayingFloatingFragment view) {
		this.activity = activity;
		this.view = view;
	}

	@Provides
	@FragmentScope
	public FragmentActivity provideActivity() {
		return activity;
	}

	@Provides
	@FragmentScope
	public Fragment provideFragment() {
		return view;
	}

	@Provides
	@FragmentScope
	public NowPlayingFloatingFragment provideView() {
		return view;
	}

	@Provides
	@FragmentScope
	public NowPlayingFloatingModel provideModel(
			FragmentActivity activity, AlbumArtProvider albumArtProvider) {
		return new NowPlayingFloatingModel(activity, albumArtProvider);
	}

	@Provides
	@FragmentScope
	public NowPlayingFloatingPresenter providePresenter(
			NowPlayingFloatingModel model,
			NowPlayingFloatingFragment view) {
		return new NowPlayingFloatingPresenter(model, view);
	}
}