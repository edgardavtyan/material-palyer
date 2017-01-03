package com.edavtyan.materialplayer.components.player;

import android.content.Context;
import android.media.AudioManager;

public class AudioFocusManager implements AudioManager.OnAudioFocusChangeListener {
	private final PlayerMvp.Player player;
	private final AudioManager audioManager;
	private int currentState;

	public AudioFocusManager(Context context, PlayerMvp.Player player) {
		this.player = player;
		audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
	}

	public void requestFocus() {
		audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
	}

	public void dropFocus() {
		audioManager.abandonAudioFocus(this);
	}

	@Override
	public void onAudioFocusChange(int focusChange) {
		if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
			if (currentState == AudioManager.AUDIOFOCUS_LOSS) {
				onAudioFocusGain();
			} else if (currentState == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
				onAudioFocusGainAfterLossTransient();
			} else if (currentState == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
				onAudioFocusGainAfterTransientLossCanDuck();
			} else {
				onAudioFocusGain();
			}

			currentState = AudioManager.AUDIOFOCUS_GAIN;
		} else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
			onAudioFocusLoss();
			currentState = AudioManager.AUDIOFOCUS_LOSS;
		} else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
			onAudioFocusLossTransient();
			currentState = AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
		} else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
			onAudioFocusLossTransientCanDuck();
			currentState = AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;
		}
	}

	private void onAudioFocusGain() {
		player.play();
	}

	private void onAudioFocusGainAfterLossTransient() {
		player.play();
	}

	private void onAudioFocusGainAfterTransientLossCanDuck() {
		player.restoreVolume();
	}

	private void onAudioFocusLoss() {
		player.pause();
	}

	private void onAudioFocusLossTransient() {
		player.pause();
	}

	private void onAudioFocusLossTransientCanDuck() {
		player.lowerVolume();
	}
}
