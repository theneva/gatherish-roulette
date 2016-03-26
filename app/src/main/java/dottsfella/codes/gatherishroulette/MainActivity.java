package dottsfella.codes.gatherishroulette;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView serviceStatusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("roulette", "onCreate");

        final Button button = (Button) this.findViewById(R.id.playButton);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cena);

        assert button != null;

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mp.start();
            }
        });

        this.serviceStatusTextView = (TextView) findViewById(R.id.serviceStatusTextView);
        final Button updateServiceStatusButton = (Button) findViewById(R.id.updateServiceStatusButton);

        updateServiceStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                updateServiceRunningStatus();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateServiceRunningStatus();
    }

    private void updateServiceRunningStatus() {
        if (isMyServiceRunning(RouletteService.class)) {
            serviceStatusTextView.setText(getResources().getString(R.string.running));
        } else {
            serviceStatusTextView.setText(getResources().getString(R.string.not_running));
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        final ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
