package com.edavtyan.materialplayer.components.now_playing_queue;

import com.edavtyan.materialplayer.components.CompactPrefsModule;
import com.edavtyan.materialplayer.lib.prefs.AdvancedSharedPrefsModule;
import com.edavtyan.materialplayer.modular.model.ModelModulesModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
		NowPlayingQueueModule.class,
		ModelModulesModule.class,
		CompactPrefsModule.class,
		AdvancedSharedPrefsModule.class})
public interface NowPlayingQueueComponent {
	void inject(NowPlayingQueueActivity activity);
}