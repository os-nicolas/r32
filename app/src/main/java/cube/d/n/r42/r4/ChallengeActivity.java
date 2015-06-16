package cube.d.n.r42.r4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.xgc1986.ripplebutton.widget.RippleButton;

import cube.d.four.r4.R;
import cube.d.n.r42.r4.Challenges.Challenge;


public class ChallengeActivity extends Activity {

    MainView mainView;
    int row = -1;
    int column = -1;
    private Challenge myChallenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_challenges);

        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("KEY");
            myChallenge = Challenge.getChallenge(value);
            String[] split = value.split("_");
            column = new Integer(split[1]);
            row = new Integer(split[2]);
            myChallenge.myActivity = this;
        }

        mainView = (MainView) findViewById(R.id.challenge_cube);
        mainView.setCube(myChallenge.initChallange());
        mainView.activate();

        updateText();

        Button tut = (Button) findViewById(R.id.challenge_reset);
        tut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainView.setCube(myChallenge.initChallange());
            }
        });

        Button yay = (Button) findViewById(R.id.yay);
        yay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout popUp = (LinearLayout) findViewById(R.id.popup);
                popUp.setVisibility(View.GONE);
            }
        });

        Button menu = (Button) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Button next = (Button) findViewById(R.id.next);
        Button right = (Button) findViewById(R.id.right);
        Button left = (Button) findViewById(R.id.left);
        if (row == 12) {
            next.setEnabled(false);
            right.setEnabled(false);
            ((RippleButton)right).setColors(0xff888888, 0xff666666);
        } else if (row == 1) {
            left.setEnabled(false);
            ((RippleButton)left).setColors(0xff888888, 0xff666666);
        }
        if (!Challenge.getChallenge("c_" + column + "_" + (row)).hasSolved()) {
            ((RippleButton)right).setColors(0xff888888, 0xff666666);
            right.setEnabled(false);
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right();
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                left();
            }
        });
    }

    private void right() {
        final Activity that = this;
        Intent myIntent = new Intent(that, ChallengeActivity.class);
        myIntent.putExtra("KEY", "c_" + column + "_" + (row + 1));
        that.startActivity(myIntent);
        finish();
    }

    private void left() {
        final Activity that = this;
        Intent myIntent = new Intent(that, ChallengeActivity.class);
        myIntent.putExtra("KEY", "c_" + column + "_" + (row - 1));
        that.startActivity(myIntent);
        finish();
    }

    private void updateText() {
        TextView line1 = (TextView) findViewById(R.id.challenge_line1);
        TextView line2 = (TextView) findViewById(R.id.challenge_line2);
        TextView line3 = (TextView) findViewById(R.id.challenge_name);
        line3.setText(myChallenge.getName());


        int numTries = myChallenge.getTries();
        if (numTries == -1) {
            line1.setText("");
        } else {
            if (numTries == 0) {
                line1.setText("Only you have attempted this challenge");
            } else {
                line1.setText((numTries + 1) + " users have attempted this challenge");
            }
        }
        int numPassed = myChallenge.getPasses();
        ;
        if (numTries == -1) {
            if (myChallenge.hasSolved()) {
                line2.setText("You have complete this challenge");
            } else {
                line2.setText("");
            }
        } else {
            if (numPassed == 0) {
                if (myChallenge.hasSolved()) {
                    line2.setText("Only you have solved it");
                } else {
                    line2.setText("No one has solved it");
                }
            } else {
                if (myChallenge.hasSolved()) {
                    if (numPassed == 2) {
                        line2.setText("You and " + (numPassed - 1) + " other has completed it");
                    } else if (numPassed == 1) {
                        line2.setText("Only you have solved it");
                    } else {
                        line2.setText("You and " + (numPassed - 1) + " others have completed it");
                    }
                } else {
                    if (numPassed == 1) {
                        line2.setText(numPassed + " has completed it");
                    } else {
                        line2.setText(numPassed + " have completed it");
                    }
                }
            }
        }

    }

    public void solved() {
        LinearLayout popUp = (LinearLayout) findViewById(R.id.popup);
        popUp.setVisibility(View.VISIBLE);
        if (row != 12) {
            Button right = (Button) findViewById(R.id.right);
            ((RippleButton)right).setColors(0xffffffff, 0xffbbbbbb);
            right.setEnabled(true);
        }
        updateText();
    }
}
