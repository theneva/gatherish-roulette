package dottsfella.codes.gatherishroulette;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("roulette", "HELLO");

        final Button button = (Button) this.findViewById(R.id.playButton);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cena);

        assert button != null;

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mp.start();
            }
        });

    }
}
