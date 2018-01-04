package com.edavtyan.materialplayer.components.audio_effects;

import com.edavtyan.materialplayer.components.audio_effects.equalizer.Equalizer;
import com.edavtyan.materialplayer.components.audio_effects.equalizer.presets.PresetNameAlreadyExists;
import com.edavtyan.materialplayer.components.audio_effects.views.EqualizerBandView;
import com.edavtyan.materialplayer.components.player.PlayerService;
import com.edavtyan.materialplayer.modular.model.ModelServiceModule;

public class AudioEffectsPresenter implements ModelServiceModule.OnServiceConnectedListener {

	private final AudioEffectsModel model;
	private final AudioEffectsActivity view;

	public AudioEffectsPresenter(AudioEffectsModel model, AudioEffectsActivity view) {
		this.model = model;
		this.view = view;
	}

	public void onCreate() {
		model.init();
		model.setOnServiceConnectedListener(this);
	}

	public void onDestroy() {
		model.close();
	}

	public void onEqualizerEnabledChanged(boolean enabled) {
		if (!model.isConnected()) {
			return;
		}

		model.getEqualizer().setEnabled(enabled);
		model.getEqualizer().saveSettings();
	}

	public void onEqualizerBandChanged(EqualizerBandView band) {
		model.getEqualizer().setBandGain(band.getIndex(), band.getGain());
		view.setEqualizerPresetAsCustomNew();
	}

	public void onEqualizerBandStopTracking() {
		model.getEqualizer().saveSettings();
	}

	public void onPresetSelected(int relativePosition, Equalizer.PresetType presetType) {
		switch (presetType) {
		case CUSTOM_NEW:
			view.setDeletePresetButtonEnabled(false);
			return;
		case CUSTOM:
			view.setDeletePresetButtonEnabled(true);
			model.getEqualizer().useCustomPreset(relativePosition);
			break;
		case BUILT_IN:
			view.setDeletePresetButtonEnabled(false);
			model.getEqualizer().useBuiltInPreset(relativePosition);
			break;
		}

		view.setEqualizerBands(
				model.getEqualizer().getBandsCount(),
				model.getEqualizer().getGainLimit(),
				model.getEqualizer().getFrequencies(),
				model.getEqualizer().getGains());
	}

	public void onNewPresetDialogOkButtonClicked(String name) {
		try {
			model.getEqualizer().savePreset(name);
			view.setEqualizerPresets(
					model.getEqualizer().getBuiltInPresetNames(),
					model.getEqualizer().getCustomPresetNames());
			view.selectLastCustomPreset();
			view.closeNewPresetCreationDialog();
		} catch (PresetNameAlreadyExists presetNameAlreadyExists) {
			view.showPresetAlreadyExists();
		}
	}

	public void onNewPresetDialogCancelButtonClicked() {
		view.closeNewPresetCreationDialog();
	}

	public void onDeletePreset(int position) {
		model.getEqualizer().deletePreset(position);
		view.setEqualizerPresets(
				model.getEqualizer().getBuiltInPresetNames(),
				model.getEqualizer().getCustomPresetNames());
	}

	public void onCreateNewPresetButtonClicked() {
		view.showNewPresetCreationDialog();
	}

	public void onBassBoostStrengthChanged(int strength) {
		model.getBassBoost().setStrength(strength);
	}

	public void onBassBoostStrengthStopChanging() {
		model.getBassBoost().saveSettings();
	}

	public void onSurroundStrengthChanged(int strength) {
		model.getSurround().setStrength(strength);
	}

	public void onSurroundStrengthStopChanging() {
		model.getSurround().saveSettings();
	}

	public void onAmplifierStrengthChanged(int gain) {
		model.getAmplifier().setGain(gain);
	}

	public void onAmplifierStrengthStopChanging() {
		model.getAmplifier().saveSettings();
	}

	public void onServiceConnected(PlayerService service) {
		view.setEqualizerEnabled(model.getEqualizer().isEnabled());
		view.setEqualizerBands(
				model.getEqualizer().getBandsCount(),
				model.getEqualizer().getGainLimit(),
				model.getEqualizer().getFrequencies(),
				model.getEqualizer().getGains());
		view.setEqualizerPresets(
				model.getEqualizer().getBuiltInPresetNames(),
				model.getEqualizer().getCustomPresetNames());
		view.setCurrentEqualizerPreset(
				model.getEqualizer().getCurrentPresetIndex(),
				model.getEqualizer().getCurrentPresetType());
		view.initBassBoost(model.getBassBoost().getMaxStrength(), model.getBassBoost().getStrength());
		view.initSurround(model.getSurround().getMaxStrength(), model.getSurround().getStrength());
		view.initAmplifier(model.getAmplifier().getMaxGain(), model.getAmplifier().getGain());
		view.setDeletePresetButtonEnabled(model.getEqualizer().isUsingSavedCustomPreset());
	}
}
