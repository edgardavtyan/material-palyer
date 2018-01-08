package com.edavtyan.materialplayer.components.now_playing_floating;

import com.edavtyan.materialplayer.components.CompactPrefsModule;
import com.edavtyan.materialplayer.components.UtilsModule;
import com.edavtyan.materialplayer.lib.album_art.AlbumArtModule;
import com.edavtyan.materialplayer.lib.prefs.AdvancedSharedPrefsModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
		NowPlayingFloatingModule.class,
		AlbumArtModule.class,
		UtilsModule.class,
		CompactPrefsModule.class,
		AdvancedSharedPrefsModule.class})
public interface NowPlayingFloatingComponent {
	void inject(NowPlayingFloatingFragment fragment);
}
