package com.example.scorekeeper;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int mScore1;
    private int mScore2;


    private TextView team1ScoreTextView;
    private TextView team2ScoreTextView;

    static final String STATE_SCORE_1 = "Team 1 Score";
    static final String STATE_SCORE_2 = "Team 2 Score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        team1ScoreTextView = findViewById(R.id.team1_score);
        team2ScoreTextView = findViewById(R.id.team2_score);

        if(savedInstanceState != null) {

            // Get the saved states using the keys
            mScore1 = savedInstanceState.getInt(STATE_SCORE_1);
            mScore2 = savedInstanceState.getInt(STATE_SCORE_2);

            // Set the saved values in the text views
            team1ScoreTextView.setText(String.valueOf(mScore1));
            team2ScoreTextView.setText(String.valueOf(mScore2));

        }

    }

    public void decreaseScore(View view) {

        switch(view.getId()) {

            case R.id.decrease_team_1:
                if(mScore1 > 0) {
                    mScore1--;
                    team1ScoreTextView.setText(Integer.toString(mScore1));
                }

                else {
                    Toast.makeText(this,"Score cannot be negative!" ,Toast.LENGTH_SHORT ).show();
                }

                break;

            case R.id.decrease_team_2:
                if(mScore2>0) {
                    mScore2--;
                    team2ScoreTextView.setText(Integer.toString(mScore2));
                }

                else {
                    Toast.makeText(this,"Score cannot be negative!" ,Toast.LENGTH_SHORT ).show();
                }

                break;

            default:
                Toast.makeText(this,"Error" ,Toast.LENGTH_SHORT ).show();

        }

    }

    public void increaseScore(View view) {

        switch(view.getId()) {

            case R.id.increase_team_1:
                    mScore1++;
                    team1ScoreTextView.setText(String.valueOf(mScore1));
                    break;

            case R.id.increase_team_2:
                    mScore2++;
                    team2ScoreTextView.setText(String.valueOf(mScore2));
                    break;

            default:
                Toast.makeText(this,"Error" ,Toast.LENGTH_SHORT ).show();

        }

    }

    // Inflate and edit the options in the top app bar
    // In this case, we only have one option, which is night mode
    // We edit its title to change to Day mode when day mode is selected
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.theme_switch_menu,menu );

        /// Change the label of the item based on the current theme

        // Check what mode is currently selected
        int nightMode = AppCompatDelegate.getDefaultNightMode();

        // If night mode is currently selected, change its title to "Day Mode"
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        }

        // If night mode is not selected, set the title to "Night mode"
        else {
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            // If enabled, the code changes night mode to the disabled state; otherwise, the code enables night mode:
            if(item.getItemId()==R.id.night_mode) {
                // Get the night mode state of the app.
                int nightMode = AppCompatDelegate.getDefaultNightMode();
                // If nightMode is already selected (in night mode) change it to not night mode (which is day mode)
                if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode
                            (AppCompatDelegate.MODE_NIGHT_NO);
                // If night mode was not currently selected, set night mode as the theme
                } else {
                    AppCompatDelegate.setDefaultNightMode
                            (AppCompatDelegate.MODE_NIGHT_YES);
                }
                // Recreate the activity for the theme change to take effect.
                recreate();
            }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        // Save the values to the outState bundle
        // The outState bundle will be passed to onCreate as the saved instance state automatically when state needs to be saved
        outState.putInt(STATE_SCORE_1,mScore1);
        outState.putInt(STATE_SCORE_2,mScore2);

        super.onSaveInstanceState(outState);
    }
}
