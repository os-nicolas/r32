package cube.d.n.r42.r4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import cube.d.four.r4.R;

public class Free extends Activity {

    MainView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_free);

        mainView = (MainView) findViewById(R.id.free_cube);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int value = extras.getInt("len");
            mainView.setCube(new Cube(value));
        }

        mainView.activate();
        // Get tracker.
        Tracker t = RFour.getInstance().getTracker();

        // Set screen name.
        t.setScreenName("3");

        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());

        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Button free = (Button) findViewById(R.id.free_scrable);
        free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainView.getCube().scramble();
            }
        });

        Button tut = (Button) findViewById(R.id.free_reset);
        tut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainView.getCube().reset();
            }
        });

    }
}
