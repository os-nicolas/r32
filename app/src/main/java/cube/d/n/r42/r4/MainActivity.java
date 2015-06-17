package cube.d.n.r42.r4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import java.lang.Override;

import cube.d.four.r4.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        //ActionBar actionBar = getActionBar();
        //actionBar.hide();

        // Get tracker.
        Tracker t = RFour.getInstance().getTracker();

        // Set screen name.
        t.setScreenName("Menu");

        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());

        MainView mainView = (MainView) findViewById(R.id.main_bkg_cube);
        mainView.setCube(new Cube(3));
        mainView.getCube().spin();

        Button challenges = (Button) findViewById(R.id.button_challenges);
        final Activity that = this;
        challenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(that, challengesSelect.class);
                that.startActivity(myIntent);
            }
        });

        Button free2 = (Button) findViewById(R.id.button_free_2);
        free2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(that, Free.class);
                myIntent.putExtra("len", 2);
                that.startActivity(myIntent);
            }
        });

        Button free3 = (Button) findViewById(R.id.button_free_3);
        free3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(that, Free.class);
                myIntent.putExtra("len", 3);
                that.startActivity(myIntent);
            }
        });

        Button tut = (Button) findViewById(R.id.button_tutorial);
        tut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(that, Tutorial.class);

                that.startActivity(myIntent);
            }
        });

        Button about = (Button) findViewById(R.id.button_about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(that, Tutorial.class);
                that.startActivity(myIntent);
            }
        });

    }

}
