package cube.d.n.r42.r4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.xgc1986.ripplebutton.widget.RippleButton;

import java.lang.Integer;import java.lang.Override;import java.util.HashMap;

import cube.d.four.r4.R;
import cube.d.n.r42.r4.Challenges.Challenge;


public class challengesSelect extends Activity {

    HashMap<Button, Integer[]> bz = new HashMap<Button, Integer[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_challenges_select);

        MainView mainView = (MainView) findViewById(R.id.challenges_bkg_cube);
        mainView.setCube(new Cube(3));
        mainView.getCube().spin();

        // Get tracker.
        Tracker t = RFour.getInstance().getTracker();

        // Set screen name.
        t.setScreenName("Menu");

        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());

        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Button b;

        b = (Button) findViewById(R.id.c_1_1);
        b.setOnClickListener(factory(1, 1, this));
        bz.put(b, new Integer[]{1, 1});

        b = (Button) findViewById(R.id.c_1_2);
        b.setOnClickListener(factory(1, 2, this));
        bz.put(b, new Integer[]{1, 2});

        b = (Button) findViewById(R.id.c_1_3);
        b.setOnClickListener(factory(1, 3, this));
        bz.put(b, new Integer[]{1, 3});

        b = (Button) findViewById(R.id.c_1_4);
        b.setOnClickListener(factory(1, 4, this));
        bz.put(b, new Integer[]{1, 4});

        b = (Button) findViewById(R.id.c_1_5);
        b.setOnClickListener(factory(1, 5, this));
        bz.put(b, new Integer[]{1, 5});

        b = (Button) findViewById(R.id.c_1_6);
        b.setOnClickListener(factory(1, 6, this));
        bz.put(b, new Integer[]{1, 6});

        b = (Button) findViewById(R.id.c_1_7);
        b.setOnClickListener(factory(1, 7, this));
        bz.put(b, new Integer[]{1, 7});

        b = (Button) findViewById(R.id.c_1_8);
        b.setOnClickListener(factory(1, 8, this));
        bz.put(b, new Integer[]{1, 8});

        b = (Button) findViewById(R.id.c_1_9);
        b.setOnClickListener(factory(1, 9, this));
        bz.put(b, new Integer[]{1, 9});

        b = (Button) findViewById(R.id.c_1_10);
        b.setOnClickListener(factory(1, 10, this));
        bz.put(b, new Integer[]{1, 10});

        b = (Button) findViewById(R.id.c_1_11);
        b.setOnClickListener(factory(1, 11, this));
        bz.put(b, new Integer[]{1, 11});

        b = (Button) findViewById(R.id.c_1_12);
        b.setOnClickListener(factory(1, 12, this));
        bz.put(b, new Integer[]{1, 12});

        b = (Button) findViewById(R.id.c_2_1);
        b.setOnClickListener(factory(2, 1, this));
        bz.put(b, new Integer[]{2, 1});

        b = (Button) findViewById(R.id.c_2_2);
        b.setOnClickListener(factory(2, 2, this));
        bz.put(b, new Integer[]{2, 2});

        b = (Button) findViewById(R.id.c_2_3);
        b.setOnClickListener(factory(2, 3, this));
        bz.put(b, new Integer[]{2, 3});

        b = (Button) findViewById(R.id.c_2_4);
        b.setOnClickListener(factory(2, 4, this));
        bz.put(b, new Integer[]{2, 4});

        b = (Button) findViewById(R.id.c_2_5);
        b.setOnClickListener(factory(2, 5, this));
        bz.put(b, new Integer[]{2, 5});

        b = (Button) findViewById(R.id.c_2_6);
        b.setOnClickListener(factory(2, 6, this));
        bz.put(b, new Integer[]{2, 6});

        b = (Button) findViewById(R.id.c_2_7);
        b.setOnClickListener(factory(2, 7, this));
        bz.put(b, new Integer[]{2, 7});

        b = (Button) findViewById(R.id.c_2_8);
        b.setOnClickListener(factory(2, 8, this));
        bz.put(b, new Integer[]{2, 8});

        b = (Button) findViewById(R.id.c_2_9);
        b.setOnClickListener(factory(2, 9, this));
        bz.put(b, new Integer[]{2, 9});

        b = (Button) findViewById(R.id.c_2_10);
        b.setOnClickListener(factory(2, 10, this));
        bz.put(b, new Integer[]{2, 10});

        b = (Button) findViewById(R.id.c_2_11);
        b.setOnClickListener(factory(2, 11, this));
        bz.put(b, new Integer[]{2, 11});

        b = (Button) findViewById(R.id.c_2_12);
        b.setOnClickListener(factory(2, 12, this));
        bz.put(b, new Integer[]{2, 12});

        b = (Button) findViewById(R.id.c_3_1);
        b.setOnClickListener(factory(3, 1, this));
        bz.put(b, new Integer[]{3, 1});

        b = (Button) findViewById(R.id.c_3_2);
        b.setOnClickListener(factory(3, 2, this));
        bz.put(b, new Integer[]{3, 2});

        b = (Button) findViewById(R.id.c_3_3);
        b.setOnClickListener(factory(3, 3, this));
        bz.put(b, new Integer[]{3, 3});

        b = (Button) findViewById(R.id.c_3_4);
        b.setOnClickListener(factory(3, 4, this));
        bz.put(b, new Integer[]{3, 4});

        b = (Button) findViewById(R.id.c_3_5);
        b.setOnClickListener(factory(3, 5, this));
        bz.put(b, new Integer[]{3, 5});

        b = (Button) findViewById(R.id.c_3_6);
        b.setOnClickListener(factory(3, 6, this));
        bz.put(b, new Integer[]{3, 6});

        b = (Button) findViewById(R.id.c_3_7);
        b.setOnClickListener(factory(3, 7, this));
        bz.put(b, new Integer[]{3, 7});

        b = (Button) findViewById(R.id.c_3_8);
        b.setOnClickListener(factory(3, 8, this));
        bz.put(b, new Integer[]{3, 8});

        b = (Button) findViewById(R.id.c_3_9);
        b.setOnClickListener(factory(3, 9, this));
        bz.put(b, new Integer[]{3, 9});

        b = (Button) findViewById(R.id.c_3_10);
        b.setOnClickListener(factory(3, 10, this));
        bz.put(b, new Integer[]{3, 10});

        b = (Button) findViewById(R.id.c_3_11);
        b.setOnClickListener(factory(3, 11, this));
        bz.put(b, new Integer[]{3, 11});

        b = (Button) findViewById(R.id.c_3_12);
        b.setOnClickListener(factory(3, 12, this));
        bz.put(b, new Integer[]{3, 12});

    }

    public View.OnClickListener factory(final int clmn, final int rw, final Activity that) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unlocked(clmn, rw)) {
                    Intent myIntent = new Intent(that, ChallengeActivity.class);
                    myIntent.putExtra("KEY", "r3_" + clmn + "_" + rw);
                    that.startActivity(myIntent);
                }
            }
        };
    }

    private boolean unlocked(final int clmn, final int rw) {
        if (rw == 1) {
            return true;
        }
        return Challenge.getChallenge("r3_" + clmn + "_" + (rw - 1)).hasSolved();
    }


    @Override
    public void onResume() {
        super.onResume();
        updateLocks();
    }

    public void updateLocks() {
        for (Button b : bz.keySet()) {
            Integer[] index = bz.get(b);
            int clmn = index[0];
            int rw = index[1];
            boolean unlock = unlocked(clmn, rw);
            ((RippleButton)b).setColors((unlock?0xffffffff:0xff888888), (unlock?0xffbbbbbb:0xff666666));
            b.setEnabled(unlock);
        }
    }
}
