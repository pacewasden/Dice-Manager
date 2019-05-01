package com.fracturedscale.spectrar.dicemanager;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import static com.fracturedscale.spectrar.dicemanager.MainActivity.MYPREFS;

public class OtherGamesActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences myPref;
    private Presets p = new Presets();

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //chooses which theme to apply
        myPref = getSharedPreferences(MYPREFS, 0);
        if (myPref.getString("theme", "light").equals("dark")) {

            setTheme(R.style.FeedActivityThemeDark);
            //super.onCreate(savedInstanceState);
            //setContentView(R.layout.activity_feed);
        } else {
            setTheme(R.style.FeedActivityThemeLight);
        }

        setContentView(R.layout.activity_other_games);
        //adds action bar for back button in top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //add buttons and assign their listener
        Button yB = findViewById(R.id.yahtzeeBtn);
        yB.setOnClickListener(this);

        Button cB = findViewById(R.id.crapsBtn);
        cB.setOnClickListener(this);

        Button fB = findViewById(R.id.farkleBtn);
        fB.setOnClickListener(this);

        Button dB = findViewById(R.id.dndBtn);
        dB.setOnClickListener(this);

        Button bB = findViewById(R.id.bunkoBtn);
        bB.setOnClickListener(this);

        Button tB = findViewById(R.id.tenziBtn);
        tB.setOnClickListener(this);
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //when back button is pressed, navigate to main activity
        this.finish();
        return true;
    }

    /**
     *
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.yahtzeeBtn:
                p.setPresetName("Yahtzee");
                p.setBackground("white");
                p.setNumFourSided(0);
                p.setNumSixSided(5);
                p.setNumEightSided(0);
                p.setNumTenSided(0);
                p.setNumTwelveSided(0);
                p.setNumTwentySided(0);
                p.setDiceColor("white");
                loadPreset();
                break;
            case R.id.crapsBtn:
                p.setPresetName("Craps");
                p.setBackground("white");
                p.setNumFourSided(0);
                p.setNumSixSided(2);
                p.setNumEightSided(0);
                p.setNumTenSided(0);
                p.setNumTwelveSided(0);
                p.setNumTwentySided(0);
                p.setDiceColor("white");
                loadPreset();
                break;
            case R.id.farkleBtn:
                p.setPresetName("Farkle");
                p.setBackground("white");
                p.setNumFourSided(0);
                p.setNumSixSided(6);
                p.setNumEightSided(0);
                p.setNumTenSided(0);
                p.setNumTwelveSided(0);
                p.setNumTwentySided(0);
                p.setDiceColor("white");
                loadPreset();
                break;
            case R.id.dndBtn:
                p.setPresetName("D&D");
                p.setBackground("white");
                p.setNumFourSided(1);
                p.setNumSixSided(1);
                p.setNumEightSided(1);
                p.setNumTenSided(2);
                p.setNumTwelveSided(1);
                p.setNumTwentySided(1);
                p.setDiceColor("white");
                loadPreset();
                break;
            case R.id.bunkoBtn:
                p.setPresetName("Bunko");
                p.setBackground("white");
                p.setNumFourSided(0);
                p.setNumSixSided(3);
                p.setNumEightSided(0);
                p.setNumTenSided(0);
                p.setNumTwelveSided(0);
                p.setNumTwentySided(0);
                p.setDiceColor("white");
                loadPreset();
                break;
            case R.id.tenziBtn:
                p.setPresetName("Tenzi");
                p.setBackground("white");
                p.setNumFourSided(0);
                p.setNumSixSided(10);
                p.setNumEightSided(0);
                p.setNumTenSided(0);
                p.setNumTwelveSided(0);
                p.setNumTwentySided(0);
                p.setDiceColor("white");
                loadPreset();
                break;
        }
        this.finish();
    }

    /**
     * loads selected preset
     */
    private void loadPreset() {
        //add selected preset to quick add
        MainActivity.quickAdd = p;
        MainActivity.presetChanged = true;
    }
}
