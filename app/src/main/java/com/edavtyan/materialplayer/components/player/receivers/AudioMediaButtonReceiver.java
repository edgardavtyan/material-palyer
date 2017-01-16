package com.edavtyan.materialplayer.components.player.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

import com.edavtyan.materialplayer.components.player.PlayerMvp;

public class AudioMediaButtonReceiver extends BroadcastReceiver {
	private final PlayerMvp.Player player;

	public AudioMediaButtonReceiver(PlayerMvp.Player player) {
		this.player = player;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		int keyEvent = intent.getIntExtra(Intent.EXTRA_KEY_EVENT, -1);
		switch (keyEvent) {
		case KeyEvent.KEYCODE_MEDIA_PLAY:
			player.play();
			break;
		case KeyEvent.KEYCODE_MEDIA_PAUSE:
			player.pause();
			break;
		case KeyEvent.KEYCODE_MEDIA_NEXT:
			player.skipToNext();
			break;
		case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
			player.skipToPrevious();
		}
	}
}
