package com.edavtyan.materialplayer;

import android.app.Application;

import com.edavtyan.materialplayer.screens.SdkFactory;
import com.edavtyan.materialplayer.service.DaggerPlayerServiceComponent;
import com.edavtyan.materialplayer.service.PlayerService;
import com.edavtyan.materialplayer.service.PlayerServiceComponent;

import lombok.Setter;

public class App extends Application {

	private @Setter SdkFactory sdkFactory;

	private @Setter PlayerServiceComponent playerServiceComponent;
	private @Setter AppComponent appComponent;

	public PlayerServiceComponent getPlayerServiceComponent(PlayerService service) {
		if (playerServiceComponent != null) {
			return playerServiceComponent;
		} else {
			return DaggerPlayerServiceComponent
					.builder()
					.appComponent(getAppComponent())
					.build();
		}
	}

	public AppComponent getAppComponent() {
		if (appComponent == null) {
			appComponent = DaggerAppComponent
					.builder()
					.appFactory(new AppFactory(this))
					.build();
		}

		return appComponent;
	}

	public SdkFactory getSdkFactory() {
		return (sdkFactory == null)
				? new SdkFactory()
				: sdkFactory;
	}
}
