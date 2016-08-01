package com.edavtyan.materialplayer.components.nowplaying2;

import com.edavtyan.materialplayer.MusicPlayerService;
import com.edavtyan.materialplayer.components.player.MusicPlayer;
import com.edavtyan.materialplayer.components.player.NowPlayingQueue;
import com.edavtyan.materialplayer.components.player.RepeatMode;
import com.edavtyan.materialplayer.utils.ArtProvider;

import java.io.File;

public class NowPlayingModel {
	private MusicPlayer player;
	private NowPlayingQueue queue;

	public NowPlayingModel(MusicPlayerService service) {
		this.player = service.getPlayer();
		this.queue = service.getQueue();
	}

	public CharSequence getTrackTitle() {
		return queue.getCurrentTrack().getTitle();
	}

	public CharSequence getArtistTitle() {
		return queue.getCurrentTrack().getArtistTitle();
	}

	public CharSequence getAlbumTitle() {
		return queue.getCurrentTrack().getAlbumTitle();
	}

	/*
	 * Player Controls
	 */

	public void toggleShuffle() {
		queue.toggleShuffling();
	}

	public boolean isShuffling() {
		return queue.isShuffling();
	}

	public void toggleRepeat() {
		queue.toggleRepeatMode();
	}

	public RepeatMode getRepeatMode() {
		return queue.getRepeatMode();
	}

	public void rewind() {
		player.movePrev();
		player.prepare();
	}

	public boolean isPlaying() {
		return player.isPlaying();
	}

	public void pause() {
		player.pause();
	}

	public void resume() {
		player.resume();
	}

	public void fastForward() {
		player.moveNext();
		player.prepare();
	}

	public File getArt() {
		return ArtProvider.fromTrack(queue.getCurrentTrack());
	}

	public int getDuration() {
		return player.getDuration();
	}

	public int getPosition() {
		return player.getPosition();
	}

	public void seekTo(int progress) {
		player.setPosition(progress);
	}
}
