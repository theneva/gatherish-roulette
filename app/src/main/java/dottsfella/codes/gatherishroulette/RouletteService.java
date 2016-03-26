package dottsfella.codes.gatherishroulette;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class RouletteService extends NotificationListenerService {
    private static final int TRIGGER_PERCENTAGE = 20;

    private MediaPlayer soundPlayer;
    private AudioManager audioManager;

    @Override
    public IBinder onBind(final Intent intent) {
        Log.d("roulette", "bound yo!");

        this.soundPlayer = MediaPlayer.create(this, R.raw.cena);
        this.audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(final StatusBarNotification statusBarNotification) {
        super.onNotificationPosted(statusBarNotification);

        Log.d("roulette", "caught notification!");

        boolean shouldPlay = new Random().nextInt(100) > TRIGGER_PERCENTAGE;

        if (shouldPlay) {
            Log.d("roulette", "PLAYING NOTIFICATION SOUND YO!");
            say("BANG!");

            final int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            final int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
            soundPlayer.start();

            new Timer("change volume back").schedule(new TimerTask() {
                @Override
                public void run() {
                    Log.d("roulette", "changing notification volume back to what it was after 20 seconds");
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, curVolume, 0);
                }
            }, 10000);
        } else {
            Log.d("roulette", "No sound for you.");
            say("Click!");
        }
    }

    private void say(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RouletteService.this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onUnbind(final Intent intent) {
        Log.d("roulette", "unbound, restarting MainActivity because we're awesome like Spose");
        return super.onUnbind(intent);
    }
}
