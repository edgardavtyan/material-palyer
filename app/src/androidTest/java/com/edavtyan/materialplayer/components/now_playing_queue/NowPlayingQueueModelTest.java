package com.edavtyan.materialplayer.components.now_playing_queue;

import android.content.Context;
import android.content.Intent;

import com.edavtyan.materialplayer.components.player.PlayerMvp;
import com.edavtyan.materialplayer.components.player.PlayerService;
import com.edavtyan.materialplayer.db.Track;
import com.edavtyan.materialplayer.lib.mvp.list.CompactListPref;
import com.edavtyan.materialplayer.testlib.tests.BaseTest;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static com.edavtyan.materialplayer.testlib.asertions.IntentAssert.assertThatIntent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NowPlayingQueueModelTest extends BaseTest {
	private NowPlayingQueueMvp.Model model;
	private PlayerService.PlayerBinder binder;
	private PlayerMvp.Player player;

	@Override
	public void beforeEach() {
		super.beforeEach();

		player = mock(PlayerMvp.Player.class);

		PlayerService service = mock(PlayerService.class);
		when(service.getPlayer()).thenReturn(player);

		binder = mock(PlayerService.PlayerBinder.class);
		when(binder.getService()).thenReturn(service);

		CompactListPref prefs = mock(CompactListPref.class);

		model = new NowPlayingQueueModel(context, prefs);
	}

	@Test
	@SuppressWarnings("WrongConstant")
	public void bind_bindServiceWithCorrectParameters() {
		model.bindService();

		ArgumentCaptor<Intent> intentCaptor = ArgumentCaptor.forClass(Intent.class);
		verify(context).bindService(intentCaptor.capture(), eq(model), eq(Context.BIND_AUTO_CREATE));
		assertThatIntent(intentCaptor.getValue()).hasClass(PlayerService.class);
	}

	@Test
	public void unbind_unbindService() {
		model.bindService();
		model.unbindService();
		verify(context).unbindService(model);
	}

	@Test
	public void playItemAtPosition_callPlayer() {
		model.onServiceConnected(null, binder);
		model.playItemAtPosition(7);
		verify(player).playTrackAt(7);
	}

	@Test
	public void removeItemAtPosition_callService() {
		model.onServiceConnected(null, binder);
		model.removeItemAtPosition(7);
		verify(player).removeTrackAt(7);
	}

	@Test
	public void getTrackAtPosition_serviceConnected_getTrackFromService() {
		Track track = mock(Track.class);
		when(player.getTrackAt(7)).thenReturn(track);

		model.onServiceConnected(null, binder);

		assertThat(model.getTrackAtPosition(7)).isEqualTo(track);
	}

	@Test
	public void getTrackCount_serviceConnected_getCountFromService() {
		when(player.getTracksCount()).thenReturn(7);
		model.onServiceConnected(null, binder);
		assertThat(model.getTrackCount()).isEqualTo(7);
		verify(player).getTracksCount();
	}

	@Test
	public void getTrackCount_serviceNotConnected_zero() {
		assertThat(model.getTrackCount()).isZero();
	}
}
