package com.edavtyan.materialplayer.components.audioeffects;

import com.edavtyan.materialplayer.components.audioeffects.models.Amplifier;
import com.edavtyan.materialplayer.components.audioeffects.models.BassBoost;
import com.edavtyan.materialplayer.components.audioeffects.models.Equalizer;
import com.edavtyan.materialplayer.components.audioeffects.models.Surround;
import com.edavtyan.materialplayer.components.player.PlayerService;
import com.edavtyan.materialplayer.modular.model.ModelServiceModule;
import com.edavtyan.materialplayer.testlib.tests.BaseTest;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AudioEffectsModelTest extends BaseTest {
	private AudioEffectsMvp.Model model;
	private PlayerService service;
	private ModelServiceModule serviceModule;

	@Override
	public void beforeEach() {
		super.beforeEach();

		serviceModule = mock(ModelServiceModule.class);
		service = mock(PlayerService.class);
		when(serviceModule.getService()).thenReturn(service);

		model = new AudioEffectsModel(serviceModule);
	}

	@Test
	@SuppressWarnings("WrongConstant")
	public void init_bindService() {
		model.init();
		verify(serviceModule).bind();
	}

	@Test
	public void close_unbindService() {
		model.close();
		verify(serviceModule).unbind();
	}

	@Test
	public void getEqualizer_returnEqualizerFromService() {
		Equalizer equalizer = mock(Equalizer.class);
		when(service.getEqualizer()).thenReturn(equalizer);
		assertThat(model.getEqualizer()).isEqualTo(equalizer);
	}

	@Test
	public void getBassBoost_returnBassBoostFromService() {
		BassBoost bassBoost = mock(BassBoost.class);
		when(service.getBassBoost()).thenReturn(bassBoost);
		assertThat(model.getBassBoost()).isEqualTo(bassBoost);
	}

	@Test
	public void getSurround_returnSurroundFromService() {
		Surround surround = mock(Surround.class);
		when(service.getSurround()).thenReturn(surround);
		assertThat(model.getSurround()).isEqualTo(surround);
	}

	@Test
	public void getAmplifier_returnAmplifierFromService() {
		Amplifier amplifier = mock(Amplifier.class);
		when(service.getAmplifier()).thenReturn(amplifier);
		assertThat(model.getAmplifier()).isEqualTo(amplifier);
	}
}
