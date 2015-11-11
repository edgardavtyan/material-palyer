package com.edavtyan.materialplayer.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class EqualizerView extends FrameLayout {
    private List<EqualizerBandView> bands;
    private LinearLayout bandsContainer;

    public EqualizerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bands = new ArrayList<>();
        bandsContainer = new LinearLayout(context, attrs);
        bandsContainer.setOrientation(LinearLayout.VERTICAL);
        addView(bandsContainer);

        if (isInEditMode()) {
            int bandsCount = 10;
            int[] frequencies = new int[] {
                    31250, 62500, 125000, 250000, 500000, 1000000,
                    2000000, 4000000, 8000000, 16000000 };
            int[] gains = new int[] { 2, 5, -7, 1, 3, -4, 15, 12, 6, 9 };

            for (int i = 0; i < bandsCount; i++) {
                EqualizerBandView band = new EqualizerBandView(context);
                band.setFrequency(frequencies[i]);
                band.setGainLimit(15);
                band.setGain(gains[i]);
                bandsContainer.addView(band);
            }
        }
    }

    /*
     * Public methods
     */

    public void addBand(EqualizerBandView bandView) {
        addView(bandView);
        bands.add(bandView);
    }

    public void setGainLimit(int gain) {
        for (EqualizerBandView band : bands) band.setGainLimit(gain);
    }

    public void setBandGain(int band, int gain) {
        bands.get(band).setGain(gain);
    }
}