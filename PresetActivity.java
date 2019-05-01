package com.fracturedscale.spectrar.dicemanager;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fracturedscale.spectrar.dicemanager.data.PresetStorage;

import java.util.ArrayList;

import static com.fracturedscale.spectrar.dicemanager.MainActivity.MYPREFS;

public class PresetActivity extends AppCompatActivity implements View.OnClickListener {

    Bundle extras;
    EditText nameField;
    Spinner backgroundDropdown;
    TextView text4Sided;
    TextView text6Sided;
    TextView text8Sided;
    TextView text10Sided;
    TextView text12Sided;
    TextView text20Sided;
    Spinner diceColorDropdown;
    SharedPreferences myPref;
    int totalDice;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myPref = getSharedPreferences(MYPREFS, 0);
        //chooses which theme to apply
        if (myPref.getString("theme", "light").equals("dark")) {

            setTheme(R.style.FeedActivityThemeDark);
        } else {
            setTheme(R.style.FeedActivityThemeLight);
        }

        setContentView(R.layout.activity_preset);
        //adds action bar for back button in top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myPref = getSharedPreferences(MYPREFS, 0);

        myPref.getStringSet("allBackgrounds", null);

        //create all views that will need to be modified and add buttons to listener
        nameField = findViewById(R.id.nameTextField);

        backgroundDropdown = findViewById(R.id.backgroundSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<String>(myPref.getStringSet("allBackgrounds", null)));
        backgroundDropdown.setAdapter(adapter);

        diceColorDropdown = findViewById(R.id.diceColorSpinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<String>(myPref.getStringSet("allDiceColors", null)));
        diceColorDropdown.setAdapter(adapter2);

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

        Button saveBtn = findViewById(R.id.saveButton);
        saveBtn.setOnClickListener(this);


        extras = getIntent().getExtras();

        //if edit intent is sent the load currently used preset to be edited
        if (extras.getString("from").equals("edit")) {
            populate(PresetStorage.getPresetList().get(myPref.getInt("currentPreset", 0)));
        }
    }

    /**
     *fill views with appropriate data
     * @param p
     */
    private void populate(Presets p) {//fill views with appropriate data
        nameField.setText(p.getPresetName());
        backgroundDropdown.setSelection(getIndex(backgroundDropdown, p.getBackground()));
        text4Sided.setText(String.valueOf(p.getNumFourSided()));
        text6Sided.setText(String.valueOf(p.getNumSixSided()));
        text8Sided.setText(String.valueOf(p.getNumEightSided()));
        text10Sided.setText(String.valueOf(p.getNumTenSided()));
        text12Sided.setText(String.valueOf(p.getNumTwelveSided()));
        text20Sided.setText(String.valueOf(p.getNumTwentySided()));
        diceColorDropdown.setSelection(getIndex(diceColorDropdown, p.getDiceColor()));
        totalDice = p.getNumAllDice();

    }

    /**
     *
     * @param spinner
     * @param myString
     * @return the index position of a string in a spinner
     */
    private int getIndex(Spinner spinner, String myString) {
        //returns the index position of a string in a spinner
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
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
            case R.id.saveButton:
                //try to save
                boolean success = save();

                if (success) { //if allowed to save then return to main activity
                    if (extras.getString("from").equals("edit")) {
                        MainActivity.presetChanged = true;
                        MainActivity.quickAdd = null;
                    }
                    this.finish();
                }
                break;
            default:
                Toast.makeText(PresetActivity.this, "default", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    /**
     *
     * @return boolean of whether user is allowed to save
     */
    private Boolean save() {
        Presets p;
        Boolean editMode = extras.getString("from").equals("edit");
        if (editMode) {
            p = PresetStorage.getPresetList().get(myPref.getInt("currentPreset", 0));

        } else {
            p = new Presets();
        }
        // save info on screen to preset p
        p.setPresetName(nameField.getText().toString());
        p.setBackground(backgroundDropdown.getSelectedItem().toString());
        p.setNumFourSided(Integer.parseInt(String.valueOf(text4Sided.getText())));
        p.setNumSixSided(Integer.parseInt(String.valueOf(text6Sided.getText())));
        p.setNumEightSided(Integer.parseInt(String.valueOf(text8Sided.getText())));
        p.setNumTenSided(Integer.parseInt(String.valueOf(text10Sided.getText())));
        p.setNumTwelveSided(Integer.parseInt(String.valueOf(text12Sided.getText())));
        p.setNumTwentySided(Integer.parseInt(String.valueOf(text20Sided.getText())));
        p.setDiceColor(diceColorDropdown.getSelectedItem().toString());
        //make sure there is at least one dice in selection
        if (p.getNumAllDice() > 0) {
            if (!editMode) {
                PresetStorage.addPreset(p, PresetActivity.this);
            } else {
                PresetStorage.updatePreset(p, myPref.getInt("currentPreset", 0), PresetActivity.this);
            }
        } else {//pop up message to tell user to have at least 1 dice
            Toast.makeText(PresetActivity.this, "There must be at least one dice", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
