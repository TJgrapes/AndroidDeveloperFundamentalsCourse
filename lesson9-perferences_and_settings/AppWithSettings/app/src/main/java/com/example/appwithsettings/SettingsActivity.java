package com.example.appwithsettings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    public static final String KEY_PREF_EXAMPLE_SWITCH_1 = "example_switch_1";
    public static final String KEY_PREF_EXAMPLE_SWITCH_2 = "example_switch_2";
    public static final String KEY_PREF_EXAMPLE_SWITCH_3 = "example_switch_3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Display the settings fragment as the main content in the settings activity
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }
}
