package cube.d.n.r42.r4.Challenges;

import android.content.SharedPreferences;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.lang.String;import java.util.HashMap;

import cube.d.n.r42.r4.ChallengeActivity;
import cube.d.n.r42.r4.ChallengeMap;
import cube.d.n.r42.r4.Cube;
import cube.d.n.r42.r4.RFour;


/**
 * Created by Colin_000 on 4/14/2015.
 */
public abstract class Challenge {

    private static final String PREFS_NAME = "Challenge";
    public static HashMap<String, Challenge> challenges = new HashMap<String, Challenge>();
    static {
        Challenge t;

        t = new C_1_1();
        challenges.put(t.getSp_key(), t);

        t = new C_1_2();
        challenges.put(t.getSp_key(), t);

        t = new C_1_3();
        challenges.put(t.getSp_key(), t);

        t = new C_1_4();
        challenges.put(t.getSp_key(), t);

        t = new C_1_5();
        challenges.put(t.getSp_key(), t);

        t = new C_1_6();
        challenges.put(t.getSp_key(), t);

        t = new C_1_7();
        challenges.put(t.getSp_key(), t);

        t = new C_1_8();
        challenges.put(t.getSp_key(), t);

        t = new C_1_9();
        challenges.put(t.getSp_key(), t);

        t = new C_1_10();
        challenges.put(t.getSp_key(), t);

        t = new C_1_11();
        challenges.put(t.getSp_key(), t);

        t = new C_1_12();
        challenges.put(t.getSp_key(), t);

        t = new C_2_1();
        challenges.put(t.getSp_key(), t);

        t = new C_2_2();
        challenges.put(t.getSp_key(), t);

        t = new C_2_3();
        challenges.put(t.getSp_key(), t);

        t = new C_2_4();
        challenges.put(t.getSp_key(), t);

        t = new C_2_5();
        challenges.put(t.getSp_key(), t);

        t = new C_2_6();
        challenges.put(t.getSp_key(), t);

        t = new C_2_7();
        challenges.put(t.getSp_key(), t);

        t = new C_2_8();
        challenges.put(t.getSp_key(), t);

        t = new C_2_9();
        challenges.put(t.getSp_key(), t);

        t = new C_2_10();
        challenges.put(t.getSp_key(), t);

        t = new C_2_11();
        challenges.put(t.getSp_key(), t);

        t = new C_2_12();
        challenges.put(t.getSp_key(), t);

        t = new C_3_1();
        challenges.put(t.getSp_key(), t);

        t = new C_3_2();
        challenges.put(t.getSp_key(), t);

        t = new C_3_3();
        challenges.put(t.getSp_key(), t);

        t = new C_3_4();
        challenges.put(t.getSp_key(), t);

        t = new C_3_5();
        challenges.put(t.getSp_key(), t);

        t = new C_3_6();
        challenges.put(t.getSp_key(), t);

        t = new C_3_7();
        challenges.put(t.getSp_key(), t);

        t = new C_3_8();
        challenges.put(t.getSp_key(), t);

        t = new C_3_9();
        challenges.put(t.getSp_key(), t);

        t = new C_3_10();
        challenges.put(t.getSp_key(), t);

        t = new C_3_11();
        challenges.put(t.getSp_key(), t);

        t = new C_3_12();
        challenges.put(t.getSp_key(), t);

    }
    public ChallengeActivity myActivity = null;
    private ChallengeMap challengeMap = new ChallengeMap();

    public static Challenge getChallenge(String key) {
        return challenges.get(key);
    }

    public abstract String getSp_key();

    private String getTried() {
        return getSp_key() + "_tried";
    }

    public void trying() {
        if (!hasTried()) {
            recordedTried();
        }
    }

    public void solved() {
        if (!hasSolved()) {
            RFour.getInstance().recordSolve(getSp_key());
            myActivity.solved();
        }
        SharedPreferences settings = RFour.getInstance().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(getSolved(), true);
        editor.commit();
    }

    public String getName() {
        String[] nom = getSp_key().split("_");
        return nom[1] + "-" + nom[2];
    }

    private String getSolved() {
        return getSp_key() + "_solved";
    }

    private void recordedTried() {
        if (!hasTried()) {

            SharedPreferences settings = RFour.getInstance().getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(getTried(), true);
            editor.commit();

            RFour.getInstance().recordTry(getSp_key());
        }
    }

    public boolean hasTried() {
        SharedPreferences settings = RFour.getInstance().getSharedPreferences(PREFS_NAME, 0);
        return settings.getBoolean(getTried(), false);
    }

    public boolean hasSolved() {
        SharedPreferences settings = RFour.getInstance().getSharedPreferences(PREFS_NAME, 0);
        return settings.getBoolean(getSolved(), false);
    }

    public Cube initChallange() {
        recordedTried();
        Cube c = privateInitChallange();
        c.myChallenge = this;
        // Get tracker.
        Tracker t = RFour.getInstance().getTracker();

        // Set screen name.
        t.setScreenName(getSp_key());

        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());
        return c;
    }

    protected abstract Cube privateInitChallange();


    public void setChallengeMap(ChallengeMap challengeMap) {
        this.challengeMap = challengeMap;
    }

    //public ChallengeMap getChallengeMap() {
    //    return challengeMap;
    //}

    public int getTries() {
        return this.challengeMap.getTrys();
    }

    public int getPasses() {
        return this.challengeMap.getPasses();
    }

    public boolean isPassed(Cube c) {
        return c.isSolved();
    }
}
