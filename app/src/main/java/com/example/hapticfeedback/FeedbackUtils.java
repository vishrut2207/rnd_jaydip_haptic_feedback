package com.example.hapticfeedback;

import android.content.Context;
import android.os.Vibrator;
import android.provider.Settings;

public class FeedbackUtils {
    public void success(Vibrator vibrator) {
        vibrator.vibrate(50);
    }

    public void warning(Vibrator vibrator) {
        vibrator.vibrate(250);
    }

    public void error(Vibrator vibrator) {
        vibrator.vibrate(500);
    }

    public void light(Vibrator vibrator) {
        vibrator.vibrate(20);
    }

    public void medium(Vibrator vibrator) {
        vibrator.vibrate(30);
    }

    public void heavy(Vibrator vibrator) {
        vibrator.vibrate(100);
    }

    public void selection(Vibrator vibrator) {
        vibrator.vibrate(20);
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
}
