package cube.d.n.r42.r4;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.*;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.lang.Exception;import java.lang.Object;import java.lang.Override;import java.lang.String;
import cube.d.n.r42.r4.Challenges.Challenge;

/**
 * Created by Colin_000 on 4/9/2015.
 */
public class RFour extends Application {
    private static final String PROPERTY_ID = "UA-59613283-2";
    private static RFour ourInstance;
    private AmazonDynamoDBClient ddbClient;
    private DynamoDBMapper mapper;
    private Tracker myTracker = null;

    public static RFour getInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
        load();
    }

    public synchronized Tracker getTracker() {
        if (myTracker != null) {
            return myTracker;
        } else {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            myTracker = analytics.newTracker(PROPERTY_ID);
            return myTracker;
        }
    }

    private void load() {
        final Application that = this;
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {

                    // Initialize the Amazon Cognito credentials provider
                    CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                            that, // Context
                            "us-east-1:4abb2fce-2e0b-407b-baae-dec41af0d9b1", // Identity Pool ID
                            Regions.US_EAST_1 // Region
                    );

                    Log.d("LogTag", "my ID is " + credentialsProvider.getIdentityId());

                    RFour.getInstance().ddbClient = new AmazonDynamoDBClient(credentialsProvider);

                    RFour.getInstance().mapper = new DynamoDBMapper(ddbClient);

                    for (String s : Challenge.challenges.keySet()) {

                        ChallengeMap res = mapper.load(ChallengeMap.class, s);
                        if (res == null) {
                            res = new ChallengeMap(s);
                            mapper.save(res);
                        }
                        Challenge.challenges.get(s).setChallengeMap(res);
                    }
                } catch (Exception e) {
                }

                return null;
            }
        };
        asyncTask.execute();
    }

    public void recordTry(final String sp_key) {
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    ChallengeMap mine = mapper.load(ChallengeMap.class, sp_key);
                    mine.setTrys(mine.getTrys() + 1);
                    mapper.save(mine);
                    Challenge.challenges.get(sp_key).setChallengeMap(mine);
                } catch (Exception e) {
                }
                return null;
            }
        };
        asyncTask.execute();
    }

    public void recordSolve(final String sp_key) {
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    ChallengeMap mine = mapper.load(ChallengeMap.class, sp_key);
                    mine.setPasses(mine.getPasses() + 1);
                    mapper.save(mine);
                    Challenge.challenges.get(sp_key).setChallengeMap(mine);

                } catch (Exception e) {
                }
                return null;
            }
        };
        asyncTask.execute();
    }
}
