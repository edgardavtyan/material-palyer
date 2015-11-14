package com.edavtyan.materialplayer.app.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.edavtyan.materialplayer.app.R;
import com.edavtyan.materialplayer.app.activities.base.BaseToolbarActivity;

public class AudioEffectsActivity extends BaseToolbarActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effects);
        initToolbar(R.string.audio_effects_title);
    }
}
