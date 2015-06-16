package cube.d.n.r42.r4;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.lang.Override;
import cube.d.four.r4.R;



public class Tutorial extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tutorial);

        // Get tracker.
        Tracker t = RFour.getInstance().getTracker();

        // Set screen name.
        t.setScreenName("Tutorial");

        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());
    }
}
