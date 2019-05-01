package com.fracturedscale.spectrar.dicemanager;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fracturedscale.spectrar.dicemanager.data.PresetStorage;

import static com.fracturedscale.spectrar.dicemanager.MainActivity.MYPREFS;

public class AddRemoveActivity extends AppCompatActivity implements View.OnClickListener {

    TextView text4Sided;
    TextView text6Sided;
    TextView text8Sided;
    TextView text10Sided;
    TextView text12Sided;
    TextView text20Sided;
    private SharedPreferences myPref;
    private int totalDice;

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
        } else {
            setTheme(R.style.FeedActivityThemeLight);
        }

        setContentView(R.layout.activity_add_remove);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //setup all buttons and textviews and add them to the listener
        text4Sided = (TextView) findViewById(R.id.numberText4Sided);
        ImageButton minus4Sided = (ImageButton) findViewById(R.id.minus4Sided);
        minus4Sided.setOnClickListener(this);
        ImageButton plus4Sided = (ImageButton) findViewById(R.id.plus4Sided);
        plus4Sided.setOnClickListener(this);

        text6Sided = (TextView) findViewById(R.id.numberText6Sided);
        ImageButton minus6Sided = (ImageButton) findViewById(R.id.minus6Sided);
        minus6Sided.setOnClickListener(this);
        ImageButton plus6Sided = (ImageButton) findViewById(R.id.plus6Sided);
        plus6Sided.setOnClickListener(this);

        text8Sided = (TextView) findViewById(R.id.numberText8Sided);
        ImageButton minus8Sided = (ImageButton) findViewById(R.id.minus8Sided);
        minus8Sided.setOnClickListener(this);
        ImageButton plus8Sided = (ImageButton) findViewById(R.id.plus8Sided);
        plus8Sided.setOnClickListener(this);

        text10Sided = (TextView) findViewById(R.id.numberText10Sided);
        ImageButton minus10Sided = (ImageButton) findViewById(R.id.minus10Sided);
        minus10Sided.setOnClickListener(this);
        ImageButton plus10Sided = (ImageButton) findViewById(R.id.plus10Sided);
        plus10Sided.setOnClickListener(this);

        text12Sided = (TextView) findViewById(R.id.numberText12Sided);
        ImageButton minus12Sided = (ImageButton) findViewById(R.id.minus12Sided);
        minus12Sided.setOnClickListener(this);
        ImageButton plus12Sided = (ImageButton) findViewById(R.id.plus12Sided);
        plus12Sided.setOnClickListener(this);

        text20Sided = (TextView) findViewById(R.id.numberText20Sided);
        ImageButton minus20Sided = (ImageButton) findViewById(R.id.minus20Sided);
        minus20Sided.setOnClickListener(this);
        ImageButton plus20Sided = (ImageButton) findViewById(R.id.plus20Sided);
        plus20Sided.setOnClickListener(this);

        Button doneBtn = findViewById(R.id.doneButton);
        doneBtn.setOnClickListener(this);

        Button reset = findViewById(R.id.resetBtn);
        reset.setOnClickListener(this);

        //decide whether to populate the screen with current preset or previously made quickadd
        if (MainActivity.quickAdd == null) {
            populate(PresetStorage.getPresetList().get(myPref.getInt("currentPreset", 0)));
        } else {
            populate(MainActivity.quickAdd);
        }

    }

    /**
     *
     * @param p
     */
    private void populate(Presets p) {
        //fill fields with proper info
        text4Sided.setText(String.valueOf(p.getNumFourSided()));
        text6Sided.setText(String.valueOf(p.getNumSixSided()));
        text8Sided.setText(String.valueOf(p.getNumEightSided()));
        text10Sided.setText(String.valueOf(p.getNumTenSided()));
        text12Sided.setText(String.valueOf(p.getNumTwelveSided()));
        text20Sided.setText(String.valueOf(p.getNumTwentySided()));
        totalDice = p.getNumAllDice();

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
        int maxDice = 15;
        switch (v.getId()) {

            case R.id.minus4Sided:
                if (Integer.parseInt(text4Sided.getText().toString()) != 0) {
                    text4Sided.setText(String.valueOf(Integer.parseInt(text4Sided.getText().toString()) - 1));
                    totalDice--;
                }
                break;
            case R.id.plus4Sided:
                if (totalDice != maxDice) {
                    text4Sided.setText(String.valueOf(Integer.parseInt(text4Sided.getText().toString()) + 1));
                    totalDice++;
                }
                break;
            case R.id.minus6Sided:
                if (Integer.parseInt(text6Sided.getText().toString()) != 0) {
                    text6Sided.setText(String.valueOf(Integer.parseInt(text6Sided.getText().toString()) - 1));
                    totalDice--;
                }
                break;
            case R.id.plus6Sided:
                if (totalDice != maxDice) {
                    text6Sided.setText(String.valueOf(Integer.parseInt(text6Sided.getText().toString()) + 1));
                    totalDice++;
                }
                break;
            case R.id.minus8Sided:
                if (Integer.parseInt(text8Sided.getText().toString()) != 0) {
                    text8Sided.setText(String.valueOf(Integer.parseInt(text8Sided.getText().toString()) - 1));
                    totalDice--;
                }
                break;
            case R.id.plus8Sided:
                if (totalDice != maxDice) {
                    text8Sided.setText(String.valueOf(Integer.parseInt(text8Sided.getText().toString()) + 1));
                    totalDice++;
                }
                break;
            case R.id.minus10Sided:
                if (Integer.parseInt(text10Sided.getText().toString()) != 0) {
                    text10Sided.setText(String.valueOf(Integer.parseInt(text10Sided.getText().toString()) - 1));
                    totalDice--;
                }
                break;
            case R.id.plus10Sided:
                if (totalDice != maxDice) {
                    text10Sided.setText(String.valueOf(Integer.parseInt(text10Sided.getText().toString()) + 1));
                    totalDice++;
                }
                break;
            case R.id.minus12Sided:
                if (Integer.parseInt(text12Sided.getText().toString()) != 0) {
                    text12Sided.setText(String.valueOf(Integer.parseInt(text12Sided.getText().toString()) - 1));
                    totalDice--;
                }
                break;
            case R.id.plus12Sided:
                if (totalDice != maxDice) {
                    text12Sided.setText(String.valueOf(Integer.parseInt(text12Sided.getText().toString()) + 1));
                    totalDice++;
                }
                break;
            case R.id.minus20Sided:
                if (Integer.parseInt(text20Sided.getText().toString()) != 0) {
                    text20Sided.setText(String.valueOf(Integer.parseInt(text20Sided.getText().toString()) - 1));
                    totalDice--;
                }
                break;
            case R.id.plus20Sided:
                if (totalDice != maxDice) {
                    text20Sided.setText(String.valueOf(Integer.parseInt(text20Sided.getText().toString()) + 1));
                    totalDice++;
                }
                break;
            case R.id.doneButton:
                //try to save
                boolean success = save();

                if (success) {
                    //if allowed to save then return to main activity
                    MainActivity.presetChanged = true;
                    this.finish();
                }
                break;
            case R.id.resetBtn://reset data to current preset selected
                MainActivity.quickAdd = null;
                populate(PresetStorage.getPresetList().get(myPref.getInt("currentPreset", 0)));
                MainActivity.presetChanged = true;
                break;
            default:
                Toast.makeText(AddRemoveActivity.this, "default", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    /**
     *
     * @return
     */
    private Boolean save() {
        Presets p;


        p = new Presets();
        // save info on screen to preset p
        p.setPresetName(PresetStorage.getPresetList().get(myPref.getInt("currentPreset", 0)).getPresetName());
        p.setBackground(PresetStorage.getPresetList().get(myPref.getInt("currentPreset", 0)).getBackground());
        p.setNumFourSided(Integer.parseInt(String.valueOf(text4Sided.getText())));
        p.setNumSixSided(Integer.parseInt(String.valueOf(text6Sided.getText())));
        p.setNumEightSided(Integer.parseInt(String.valueOf(text8Sided.getText())));
        p.setNumTenSided(Integer.parseInt(String.valueOf(text10Sided.getText())));
        p.setNumTwelveSided(Integer.parseInt(String.valueOf(text12Sided.getText())));
        p.setNumTwentySided(Integer.parseInt(String.valueOf(text20Sided.getText())));
        p.setDiceColor(PresetStorage.getPresetList().get(myPref.getInt("currentPreset", 0)).getDiceColor());
        //make sure there is at least one dice in selection
        if (p.getNumAllDice() > 0) {
            MainActivity.quickAdd = p;
        } else {
            //pop up message to tell user to have at least 1 dice
            Toast.makeText(AddRemoveActivity.this, "There must be at least one dice", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
