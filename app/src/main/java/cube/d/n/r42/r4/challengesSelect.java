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

        Button c_1_1 = (Button) findViewById(R.id.c_1_1);
        c_1_1.setOnClickListener(factory(1, 1, this));
        bz.put(c_1_1, new Integer[]{1, 1});

        Button c_1_2 = (Button) findViewById(R.id.c_1_2);
        c_1_2.setOnClickListener(factory(1, 2, this));
        bz.put(c_1_2, new Integer[]{1, 2});

        Button c_1_3 = (Button) findViewById(R.id.c_1_3);
        c_1_3.setOnClickListener(factory(1, 3, this));
        bz.put(c_1_3, new Integer[]{1, 3});

        Button c_1_4 = (Button) findViewById(R.id.c_1_4);
        c_1_4.setOnClickListener(factory(1, 4, this));
        bz.put(c_1_4, new Integer[]{1, 4});

        Button c_1_5 = (Button) findViewById(R.id.c_1_5);
        c_1_5.setOnClickListener(factory(1, 5, this));
        bz.put(c_1_5, new Integer[]{1, 5});

        Button c_1_6 = (Button) findViewById(R.id.c_1_6);
        c_1_6.setOnClickListener(factory(1, 6, this));
        bz.put(c_1_6, new Integer[]{1, 6});

        Button c_1_7 = (Button) findViewById(R.id.c_1_7);
        c_1_7.setOnClickListener(factory(1, 7, this));
        bz.put(c_1_7, new Integer[]{1, 7});

        Button c_1_8 = (Button) findViewById(R.id.c_1_8);
        c_1_8.setOnClickListener(factory(1, 8, this));
        bz.put(c_1_8, new Integer[]{1, 8});

        Button c_1_9 = (Button) findViewById(R.id.c_1_9);
        c_1_9.setOnClickListener(factory(1, 9, this));
        bz.put(c_1_9, new Integer[]{1, 9});

        Button c_1_10 = (Button) findViewById(R.id.c_1_10);
        c_1_10.setOnClickListener(factory(1, 10, this));
        bz.put(c_1_10, new Integer[]{1, 10});

        Button c_1_11 = (Button) findViewById(R.id.c_1_11);
        c_1_11.setOnClickListener(factory(1, 11, this));
        bz.put(c_1_11, new Integer[]{1, 11});

        Button c_1_12 = (Button) findViewById(R.id.c_1_12);
        c_1_12.setOnClickListener(factory(1, 12, this));
        bz.put(c_1_12, new Integer[]{1, 12});

        Button c_2_1 = (Button) findViewById(R.id.c_2_1);
        c_2_1.setOnClickListener(factory(2, 1, this));
        bz.put(c_2_1, new Integer[]{2, 1});

        Button c_2_2 = (Button) findViewById(R.id.c_2_2);
        c_2_2.setOnClickListener(factory(2, 2, this));
        bz.put(c_2_2, new Integer[]{2, 2});

        Button c_2_3 = (Button) findViewById(R.id.c_2_3);
        c_2_3.setOnClickListener(factory(2, 3, this));
        bz.put(c_2_3, new Integer[]{2, 3});

        Button c_2_4 = (Button) findViewById(R.id.c_2_4);
        c_2_4.setOnClickListener(factory(2, 4, this));
        bz.put(c_2_4, new Integer[]{2, 4});

        Button c_2_5 = (Button) findViewById(R.id.c_2_5);
        c_2_5.setOnClickListener(factory(2, 5, this));
        bz.put(c_2_5, new Integer[]{2, 5});

        Button c_2_6 = (Button) findViewById(R.id.c_2_6);
        c_2_6.setOnClickListener(factory(2, 6, this));
        bz.put(c_2_6, new Integer[]{2, 6});

        Button c_2_7 = (Button) findViewById(R.id.c_2_7);
        c_2_7.setOnClickListener(factory(2, 7, this));
        bz.put(c_2_7, new Integer[]{2, 7});

        Button c_2_8 = (Button) findViewById(R.id.c_2_8);
        c_2_8.setOnClickListener(factory(2, 8, this));
        bz.put(c_2_8, new Integer[]{2, 8});

        Button c_2_9 = (Button) findViewById(R.id.c_2_9);
        c_2_9.setOnClickListener(factory(2, 9, this));
        bz.put(c_2_9, new Integer[]{2, 9});

        Button c_2_10 = (Button) findViewById(R.id.c_2_10);
        c_2_10.setOnClickListener(factory(2, 10, this));
        bz.put(c_2_10, new Integer[]{2, 10});

        Button c_2_11 = (Button) findViewById(R.id.c_2_11);
        c_2_11.setOnClickListener(factory(2, 11, this));
        bz.put(c_2_11, new Integer[]{2, 11});

        Button c_2_12 = (Button) findViewById(R.id.c_2_12);
        c_2_12.setOnClickListener(factory(2, 12, this));
        bz.put(c_2_12, new Integer[]{2, 12});


    }

    public View.OnClickListener factory(final int clmn, final int rw, final Activity that) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unlocked(clmn, rw)) {
                    Intent myIntent = new Intent(that, ChallengeActivity.class);
                    myIntent.putExtra("KEY", "c_" + clmn + "_" + rw);
                    that.startActivity(myIntent);
                }
            }
        };
    }

    private boolean unlocked(final int clmn, final int rw) {
        if (rw == 1) {
            return true;
        }
        return Challenge.getChallenge("c_" + clmn + "_" + (rw - 1)).hasSolved();
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
