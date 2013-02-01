package com.antosha;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * User: arsentyev
 * Date: 28.01.13
 */
public class CardActivity extends Activity {
    private SoundPool soundPool;
    private int soundID;
    boolean loaded = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        //android:theme="@android:style/Theme.Black.NoTitleBar"
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.card);

        final Button button = (Button) findViewById(R.id.next);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ImageView image = (ImageView) findViewById(R.id.imageView);
                image.setImageResource(R.drawable.orange);
            }
        });

        final Button playButton = (Button) findViewById(R.id.play);
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Getting the user sound settings
                AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                float volume = actualVolume / maxVolume;
                Toast.makeText(getApplicationContext(), "Ready to play sound", Toast.LENGTH_LONG);
                // Is the sound loaded already?
                if (loaded) {
                    soundPool.play(soundID, volume, volume, 1, 0, 1f);
                    Toast.makeText(getApplicationContext(), "Play sound", Toast.LENGTH_LONG);
                }
            }
        });

        // Load the sound
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });
        soundID = soundPool.load(this, R.raw.apple, 1);
    }
}