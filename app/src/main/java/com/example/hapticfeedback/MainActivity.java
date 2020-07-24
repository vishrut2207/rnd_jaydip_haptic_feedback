package com.example.hapticfeedback;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Button default_sound_vibration, feedback_off, feedback_on, default_Vibration, default_Vibration_sound, d_long_press, error, success, warning, light, medium, heavy, selection, vib_pattern1, vib_pattern2, vibrate_sound;

    Vibrator myVib;
    MediaPlayer player;
    int Action_code = 1;
    boolean isisHapticFeedbackEnabled = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        default_Vibration = findViewById(R.id.btn);
        default_Vibration_sound = findViewById(R.id.btn1);
        d_long_press = findViewById(R.id.d_long_press);
        error = findViewById(R.id.error);
        success = findViewById(R.id.success);
        warning = findViewById(R.id.warning);
        light = findViewById(R.id.light);
        medium = findViewById(R.id.medium);
        heavy = findViewById(R.id.heavy);
        selection = findViewById(R.id.selection);
        vib_pattern1 = findViewById(R.id.pattern1);
        vib_pattern2 = findViewById(R.id.pattern2);
        vibrate_sound = findViewById(R.id.vibrate_sound);
        feedback_off = findViewById(R.id.feedback_off);
        feedback_on = findViewById(R.id.feedback_ON);
        default_sound_vibration = findViewById(R.id.default_sound_vibration);

        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        player = MediaPlayer.create(MainActivity.this, R.raw.mouseclickcom);

        Log.i(TAG, "onCreate: isVibration  " + myVib.hasVibrator());

        default_sound_vibration.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (default_sound_vibration.getText().toString().equals("Default sound + vibration ON")) {
                    default_sound_vibration.setText("Default sound + vibration OFF");
                    default_sound_vibration.setBackgroundColor(Color.RED);
                    Settings.System.putInt(getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED, 1);
                    Settings.System.putInt(getContentResolver(), Settings.System.HAPTIC_FEEDBACK_ENABLED, 1);
                } else { /*(default_sound_vibration.getText().toString().equals("Default sound + vibration OFF")){*/
                    default_sound_vibration.setText("Default sound + vibration ON");
                    default_sound_vibration.setBackgroundColor(Color.GREEN);
                    Settings.System.putInt(getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED, 0);
                    Settings.System.putInt(getContentResolver(), Settings.System.HAPTIC_FEEDBACK_ENABLED, 0);
                }

            }
        });

        default_Vibration.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.O_MR1)
            @Override
            public boolean onTouch(final View v, MotionEvent event) {

                boolean isVibrateOnTouchEnabled = Settings.System.getInt(getContentResolver(),
                        Settings.System.HAPTIC_FEEDBACK_ENABLED, 1) != 0;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if (!isVibrateOnTouchEnabled) {
                            isHapticFeedbackEnabled(MainActivity.this, v);
                        }
                        // For default Vibration
                        else
                            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                        break;

                    case MotionEvent.ACTION_UP:

                        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY_RELEASE);
                        break;
                }
                return true;
            }
        });

        d_long_press.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {

                boolean isVibrateOnTouchEnabled = Settings.System.getInt(getContentResolver(),
                        Settings.System.HAPTIC_FEEDBACK_ENABLED, 1) != 0;

                Log.i(TAG, "onTouch: isVibrateOnTouchEnabled " + isVibrateOnTouchEnabled);

                if (!isVibrateOnTouchEnabled) {
                    isHapticFeedbackEnabled(MainActivity.this, v);
                } else
                    v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return false;
            }
        });

        default_Vibration_sound.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.O_MR1)
            @Override
            public boolean onTouch(final View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        boolean isVibrateOnTouchEnabled = Settings.System.getInt(getContentResolver(),
                                Settings.System.HAPTIC_FEEDBACK_ENABLED, 1) != 0;

                        boolean isTouchSoundsEnabled = Settings.System.getInt(getContentResolver(),
                                Settings.System.SOUND_EFFECTS_ENABLED, 1) != 0;

                        Log.i(TAG, "onTouch: isVibrateOnTouchEnabled " + isVibrateOnTouchEnabled);
                        settingPermission();
                        if (!isVibrateOnTouchEnabled) {
                            isHapticFeedbackEnabled(MainActivity.this, view);

                        } else if (!isTouchSoundsEnabled) {
                            isSoundEffectConstants(MainActivity.this, view);

                        } else {
                            // For default Vibration
                            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                            // For default Sound
                            view.playSoundEffect(SoundEffectConstants.CLICK);
                        }
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
            @Override
            public void onClick(View v) {
                v.setSoundEffectsEnabled(false);
                long[] vv1 = {0, 100, 200, 100, 1000, 100, 200, 100};
                int[] vv = {0, 255, 0, 1, 0, 255, 0, 1};
                if (Action_code == 1) {
                    // Vibrate method for below API Level 26
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        myVib.vibrate(VibrationEffect.createWaveform(vv1, vv, -1));
                        Log.i(TAG, "onClick: " + myVib.hasAmplitudeControl());
                    } else {
                        long[] mVibratePattern = new long[]{0, 100, 200, 100, 1000, 100, 200, 100};
                        myVib.vibrate(mVibratePattern, -1);
                    }
                }
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

    public void settingPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!Settings.System.canWrite(getApplicationContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 200);

            }
        }
    }

    private boolean checkSystemWritePermission() {
        boolean retVal = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            retVal = Settings.System.canWrite(this);
            Log.d(TAG, "Can Write Settings: " + retVal);
            if (retVal) {
//                Toast.makeText(this, "Write allowed :-)", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(this, "Write not allowed :-(", Toast.LENGTH_LONG).show();
            }
        }
        return retVal;
    }


    public void isSoundEffectConstants(Context context, final View v) {
        settingPermission();
        if (checkSystemWritePermission()) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);

            dialogBuilder.setMessage("Do you want to turn on Touch on Sound ?");
            dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Settings.System.putInt(getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED, 1);
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    v.playSoundEffect(SoundEffectConstants.CLICK);
                }
            });
            dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
        }
    }

    public void isHapticFeedbackEnabled(Context context, final View v) {

        settingPermission();
        if (checkSystemWritePermission()) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

            dialogBuilder.setMessage("Do you want to turn on Touch on Vibrate ?");
            dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Settings.System.putInt(getContentResolver(), Settings.System.HAPTIC_FEEDBACK_ENABLED, 1);
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    isisHapticFeedbackEnabled = true;
                }
            });
            dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

        }
    }

    public void success(Vibrator vibrator) {
        if (vibrator.hasVibrator())
            vibrator.vibrate(50);
    }

    public void warning(Vibrator vibrator) {
        if (vibrator.hasVibrator())
            vibrator.vibrate(250);
    }

    public void error(Vibrator vibrator) {
        if (vibrator.hasVibrator())
            vibrator.vibrate(500);
    }

    public void light(Vibrator vibrator) {
        if (vibrator.hasVibrator())
            vibrator.vibrate(20);
    }

    public void medium(Vibrator vibrator) {
        if (vibrator.hasVibrator())
            vibrator.vibrate(50);
    }

    public void heavy(Vibrator vibrator) {
        if (vibrator.hasVibrator())
            vibrator.vibrate(100);
    }

    public void selection(Vibrator vibrator) {
        if (vibrator.hasVibrator())
            vibrator.vibrate(20);
    }


}
