package com.example.hapticfeedback;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Button feedback_off, feedback_on, default_Vibration, default_Vibration_sound, d_long_press, error, success, warning, light, medium, heavy, selection, vib_pattern1, vib_pattern2, vibrate_sound;

    Vibrator myVib;
    MediaPlayer player;
    int Action_code = 1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        default_Vibration = (Button) findViewById(R.id.btn);
        default_Vibration_sound = (Button) findViewById(R.id.btn1);
        d_long_press = (Button) findViewById(R.id.d_long_press);
        error = (Button) findViewById(R.id.error);
        success = (Button) findViewById(R.id.success);
        warning = (Button) findViewById(R.id.warning);
        light = (Button) findViewById(R.id.light);
        medium = (Button) findViewById(R.id.medium);
        heavy = (Button) findViewById(R.id.heavy);
        selection = (Button) findViewById(R.id.selection);
        vib_pattern1 = (Button) findViewById(R.id.pattern1);
        vib_pattern2 = (Button) findViewById(R.id.pattern2);
        vibrate_sound = (Button) findViewById(R.id.vibrate_sound);
        feedback_off = (Button) findViewById(R.id.feedback_off);
        feedback_on = (Button) findViewById(R.id.feedback_ON);

        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        player = MediaPlayer.create(MainActivity.this, R.raw.mouseclickcom);

        Log.i(TAG, "onCreate: isVibration  " + myVib.hasVibrator());
        Log.i(TAG, "onCreate: isVibration  " + isHapticFeedbackEnabled(MainActivity.this));
        if (!isHapticFeedbackEnabled(MainActivity.this)) {
            Toast.makeText(MainActivity.this, "Please Turn on Vibrate on tap in Global setting", Toast.LENGTH_SHORT).show();
        }

        default_Vibration.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.O_MR1)
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        // For default Vibration
                        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                        break;

                    case MotionEvent.ACTION_UP:

                        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY_RELEASE);
                        break;
                }
                return true;
            }
        });

       /* default_Vibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // For default Vibration
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY,
                        HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
            }
        });*/

        d_long_press.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!isHapticFeedbackEnabled(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, "Please Turn on Vibrate on tap in Global setting", Toast.LENGTH_SHORT).show();
                }
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return false;
            }
        });

        default_Vibration_sound.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.O_MR1)
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (!isHapticFeedbackEnabled(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, "Please Turn on Vibrate on tap in Global setting", Toast.LENGTH_SHORT).show();
                } else if (!isSoundEffectConstants(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, "Please Turn on Touch Sound in Global setting ", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "isSoundEffectConstants: sound.....");
                } else
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                            // For default Vibration
                            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                            // For default Sound
                            view.playSoundEffect(SoundEffectConstants.CLICK);

                            break;

                        case MotionEvent.ACTION_UP:
                            break;
                    }
                return true;
            }
        });
        feedback_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Action_code = 0;
                feedback_off.setVisibility(View.GONE);
                feedback_on.setVisibility(View.VISIBLE);

            }
        });
        feedback_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Action_code = 1;
                success(myVib);
                feedback_off.setVisibility(View.VISIBLE);
                feedback_on.setVisibility(View.GONE);
            }
        });

        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSoundEffectsEnabled(false);
                if (Action_code == 1)
                    success(myVib);
            }
        });
        warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSoundEffectsEnabled(false);
                if (Action_code == 1)
                    warning(myVib);
            }
        });
        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSoundEffectsEnabled(false);
                if (Action_code == 1)
                    error(myVib);
            }
        });
        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSoundEffectsEnabled(false);
                if (Action_code == 1)
                    light(myVib);
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSoundEffectsEnabled(false);
                if (Action_code == 1)
                    medium(myVib);
            }
        });
        heavy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSoundEffectsEnabled(false);
                if (Action_code == 1)
                    heavy(myVib);
            }
        });
        selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSoundEffectsEnabled(false);
                if (Action_code == 1)
                    selection(myVib);
            }
        });
        vib_pattern1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSoundEffectsEnabled(false);
                // 0 : Start without a delay
                // 400 : Vibrate for 400 milliseconds
                // 200 : Pause for 200 milliseconds
                // 400 : Vibrate for 400 milliseconds

                long[] mVibratePattern = new long[]{0, 100, 300, 100, 300, 100};

                // -1 : Do not repeat this pattern
                // pass 0 if you want to repeat this pattern from 0th index
                if (Action_code == 1)
                    myVib.vibrate(mVibratePattern, -1);
            }
        });
        vib_pattern2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                v.setSoundEffectsEnabled(false);
                long[] vv1 = {0, 100, 200, 100, 1000, 100, 200, 100};
                int[] vv = {0, 255, 0, 1, 0, 255, 0, 1};
                if (Action_code == 1)
                    myVib.vibrate(VibrationEffect.createWaveform(vv1, vv, -1));
                Log.i(TAG, "onClick: "+myVib.hasAmplitudeControl());

            }
        });
        vibrate_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSoundEffectsEnabled(false);
                if (Action_code == 1) {
                    player.start();
                    success(myVib);
                }
            }
        });


    }



    public static boolean isSoundEffectConstants(Context context) {
        int enabled = Settings.System.getInt(context.getContentResolver(),
                Settings.System.SOUND_EFFECTS_ENABLED, 0);

        return enabled != 0;
    }

    public static boolean isHapticFeedbackEnabled(Context context) {
        int enabled = Settings.System.getInt(context.getContentResolver(),
                android.provider.Settings.System.HAPTIC_FEEDBACK_ENABLED, 0);
        return enabled != 0;
    }

    public void success(Vibrator vibrator) {
        if(vibrator.hasVibrator())
        vibrator.vibrate(50);
    }

    public void warning(Vibrator vibrator) {
        if(vibrator.hasVibrator())
        vibrator.vibrate(250);
    }

    public void error(Vibrator vibrator) {
        if(vibrator.hasVibrator())
        vibrator.vibrate(500);
    }

    public void light(Vibrator vibrator) {
        if(vibrator.hasVibrator())
        vibrator.vibrate(20);
    }

    public void medium(Vibrator vibrator) {
        if(vibrator.hasVibrator())
        vibrator.vibrate(50);
    }

    public void heavy(Vibrator vibrator) {
        if(vibrator.hasVibrator())
        vibrator.vibrate(100);
    }

    public void selection(Vibrator vibrator) {
        if(vibrator.hasVibrator())
        vibrator.vibrate(20);
    }


}
