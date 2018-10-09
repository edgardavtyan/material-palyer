package com.edavtyan.materialplayer.player;

import com.edavtyan.materialplayer.db.Track;
import com.edavtyan.materialplayer.player.engines.AudioEngine;

import java.util.ArrayList;
import java.util.List;

public class Player
		implements AudioEngine.OnPreparedListener,
				   AudioEngine.OnCompletedListener {

	private final AudioEngine audioEngine;
	private final PlayerPrefs prefs;
	private final PlayerQueue queue;
	private final PlayerQueueStorage queueStorage;
	private final List<OnNewTrackListener> onNewTrackListeners;
	private final List<OnPlayPauseListener> onPlayPauseListeners;
	private final ArrayList<PlayerPlugin> plugins;

	public interface OnNewTrackListener {
		void onNewTrack();
	}

	public interface OnPlayPauseListener {
		void onPlayPause();
	}

	public Player(
			AudioEngine audioEngine,
			PlayerQueue queue,
			PlayerPrefs prefs,
			PlayerQueueStorage queueStorage) {
		this.prefs = prefs;
		this.audioEngine = audioEngine;
		this.queueStorage = queueStorage;
		this.audioEngine.setOnPreparedListener(this);
		this.audioEngine.setOnCompletedListener(this);

		this.queue = queue;
		this.queue.setRepeatMode(prefs.getRepeatMode());
		this.queue.setShuffleMode(prefs.getShuffleMode());
		this.queue.addManyTracks(queueStorage.load());

		if (queue.hasData()) {
			prepareTrackAt(prefs.getCurrentIndex());
		}

		onNewTrackListeners = new ArrayList<>();
		onPlayPauseListeners = new ArrayList<>();
		plugins = new ArrayList<>();
	}

	public void addPlugin(PlayerPlugin plugin) {
		plugins.add(plugin);
		plugin.onPlayerPluginConnected(this);
	}

	public void setOnNewTrackListener(OnNewTrackListener listener) {
		onNewTrackListeners.add(listener);
	}

	public void removeOnNewTrackListener(OnNewTrackListener listener) {
		onNewTrackListeners.remove(listener);
	}

	public void setOnPlayPauseListener(OnPlayPauseListener listener) {
		onPlayPauseListeners.add(listener);
	}

	public void removeOnPlayPauseListener(OnPlayPauseListener listener) {
		onPlayPauseListeners.remove(listener);
	}

	public int getSessionId() {
		return audioEngine.getSessionId();
	}

	public void addTrack(Track track) {
		queue.addTrack(track);
		queueStorage.save(queue.getTracks());
	}

	public void addManyTracks(List<Track> tracks) {
		queue.addManyTracks(tracks);
		queueStorage.save(queue.getTracks());
	}

	public void removeTrackAt(int position) {
		queue.removeTrackAt(position);
		queueStorage.save(queue.getTracks());
	}

	public void prepareTrackAt(int position) {
		queue.setCurrentIndex(position);
		audioEngine.prepareTrack(queue.getCurrentTrack().getPath());
	}

	public void playNewTracks(List<Track> tracks, int position) {
		queue.replaceTracks(tracks, position);
		queueStorage.save(queue.getTracks());
		audioEngine.playTrack(queue.getCurrentTrack().getPath());
	}

	public void playTrackAt(int position) {
		queue.setCurrentIndex(position);
		prefs.saveCurrentIndex(position);
		audioEngine.playTrack(queue.getCurrentTrack().getPath());
	}

	public Track getTrackAt(int position) {
		return queue.getTrackAt(position);
	}

	public Track getCurrentTrack() {
		return queue.getCurrentTrack();
	}

	public int getTracksCount() {
		return queue.getTracksCount();
	}

	public boolean hasData() {
		return queue.hasData();
	}

	public boolean isPlaying() {
		return audioEngine.isPlaying();
	}

	public void play() {
		audioEngine.play();
		raisePlayPauseListener();
	}

	public void pause() {
		audioEngine.pause();
		raisePlayPauseListener();
	}

	public void playPause() {
		if (audioEngine.isPlaying()) {
			audioEngine.pause();
		} else {
			audioEngine.play();
		}

		raisePlayPauseListener();
	}

	public void skipToNext() {
		if (!hasData()) return;
		queue.moveToNext();
		prefs.saveCurrentIndex(queue.getCurrentIndex());
		if (queue.isEnded()) return;
		audioEngine.playTrack(queue.getCurrentTrack().getPath());
	}

	public void skipToPrevious() {
		if (audioEngine.getSeek() >= 5000) {
			audioEngine.setSeek(0);
		} else {
			queue.moveToPrev();
			prefs.saveCurrentIndex(queue.getCurrentIndex());
			audioEngine.playTrack(queue.getCurrentTrack().getPath());
		}
	}

	public long getSeek() {
		return audioEngine.getSeek();
	}

	public void setSeek(int position) {
		audioEngine.setSeek(position);
	}

	public ShuffleMode getShuffleMode() {
		return queue.getShuffleMode();
	}

	public void toggleShuffleMode() {
		queue.toggleShuffleMode();
		prefs.saveShuffleMode(getShuffleMode());
	}

	public RepeatMode getRepeatMode() {
		return queue.getRepeatMode();
	}

	public void toggleRepeatMode() {
		queue.toggleRepeatMode();
		prefs.saveRepeatMode(getRepeatMode());
	}

	public void lowerVolume() {
		audioEngine.setVolume(0.3f);
	}

	public void restoreVolume() {
		audioEngine.setVolume(1.0f);
	}

	public void onPrepared() {
		for (OnNewTrackListener onNewTrackListener : onNewTrackListeners) {
			onNewTrackListener.onNewTrack();
		}

		for (PlayerPlugin plugin : plugins) {
			plugin.onNewTrack(getCurrentTrack());
		}
	}

	public void onCompleted() {
		skipToNext();
	}

	private void raisePlayPauseListener() {
		for (OnPlayPauseListener onPlayPauseListener : onPlayPauseListeners) {
			onPlayPauseListener.onPlayPause();
		}
	}
}
