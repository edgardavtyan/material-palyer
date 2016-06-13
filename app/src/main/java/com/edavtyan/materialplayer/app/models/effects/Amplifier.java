package com.edavtyan.materialplayer.app.models.effects;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.h6ah4i.android.media.audiofx.IPreAmp;

public class Amplifier implements StrengthBasedEffect {
	private static final String PREF_STRENGTH = "pref_amplifier_strength";
	private static final int MAX_STRENGTH = 100;
	private static final int DEFAULT_STRENGTH = 50;


	private final IPreAmp amplifier;
	private final SharedPreferences prefs;


	public Amplifier(Context context, IPreAmp amplifier) {
		this.amplifier = amplifier;
		this.amplifier.setEnabled(true);

		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		setStrength(prefs.getInt(PREF_STRENGTH, DEFAULT_STRENGTH));
	}

	/* StrengthBasedEffect */

	@Override
	public int getMaxStrength() {
		return MAX_STRENGTH;
	}

	@Override
	public int getStrength() {
		return (int) ((amplifier.getLevel() - 1) * MAX_STRENGTH);
	}

	@Override
	public void setStrength(int strength) {
		float strengthFloat = (strength / (float) MAX_STRENGTH) + 1;
		if (strengthFloat > 2.0f) strengthFloat = 2.0f;
		amplifier.setLevel(strengthFloat);
	}

	@Override
	public void saveSettings() {
		prefs.edit().putInt(PREF_STRENGTH, getStrength()).apply();
	}
}
