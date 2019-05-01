package com.fracturedscale.spectrar.dicemanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fracturedscale.spectrar.dicemanager.data.PresetStorage;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final static String MYPREFS = "MyPreferences_001";
    public static Presets quickAdd = null;
    public static Boolean presetChanged = false;

    private Boolean initialLoadDone = false;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private Button rollBtn;
    private boolean emptyOutOfSync;
    private SharedPreferences myPref;
    private ArrayList<ImageView> listOfDiceImages = new ArrayList<>();
    private ArrayList<Dice> listOfDice = new ArrayList<>();
    private ArrayList<Boolean> listOfDiceBoolean = new ArrayList<>();
    private RelativeLayout diceBoard;
    private SensorManager sensorMan;
    private Sensor accelerometer;
    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    /**
     *
     * @param savedInstanceState
     */
    @SuppressLint("NewApi")
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

        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        //creates drawer button in navigation bar
        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();

        //applys the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //adds actions to buttons in drawer
        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.addDice:
                        Intent i = new Intent(MainActivity.this, PresetActivity.class);
                        //tells presetactivity it is in edit mode
                        i.putExtra("from", "edit");
                        startActivity(i);
                        break;
                    case R.id.presets:
                        startActivity(new Intent(MainActivity.this, AllPresetsActivity.class));
                        break;
                    case R.id.otherGames:
                        startActivity(new Intent(MainActivity.this, OtherGamesActivity.class));
                        break;

                    default:
                        return true;
                }
                return true;


            }
        });

        //Shake sensor setup
        sensorMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;


        loadPresets();


        //mainactivity bottom buttons setup (roll, edit)
        Button editBtn = (Button) findViewById(R.id.quickEditButton);
        editBtn.setOnClickListener(this);
        rollBtn = (Button) findViewById(R.id.rollButton);
        rollBtn.setOnClickListener(this);

        // add dice board for touch rolling effect
        diceBoard = findViewById(R.id.diceBoard);
        diceBoard.setOnClickListener(this);

        // determines if there are any presets or if the preset storage has dangerously changed order
        //emptyOutOfSync will tell certain functions/methods if it is safe to execute, without it a lot of crashes can occur
        if (!PresetStorage.getPresetList().isEmpty() && myPref.getInt("currentPreset", 0) < PresetStorage.getPresetList().size()) {
            emptyOutOfSync = true;
        } else {
            emptyOutOfSync = false;
        }

        //sets up dice and background for the last preset selected before the app was last closed
        if (emptyOutOfSync) {
            setBackground(PresetStorage.getPresetList().get(myPref.getInt("currentPreset", 0)));
            addDice(PresetStorage.getPresetList().get(myPref.getInt("currentPreset", 0)));
        }


    }

    /**
     *
     * @param item
     * @return
     */
    //used for drawer and settings button in action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //action for pressing settings button
        if (item.getItemId() == R.id.menucog) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }

        //action for pressing drawer button
        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @param menu
     * @return
     */
    //used for drawer and settings button in action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settingsmenu, menu);
        return true;
    }

    /**
     *
     * @param v
     */
    @SuppressLint("NewApi")
    @Override
    public void onClick(final View v) {
        switch (v.getId()) {

            case R.id.rollButton:
                //roll code
                if (!listOfDiceImages.isEmpty()) {
                    if (quickAdd == null) {
                        roll(PresetStorage.getPresetList().get(myPref.getInt("currentPreset", 0)));
                    } else {
                        roll(quickAdd);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No Preset", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.quickEditButton:
                Intent i = new Intent(MainActivity.this, AddRemoveActivity.class);
                startActivity(i);
                break;
            case R.id.diceBoard://for touch roll
                if (myPref.getString("rollType", "btn").equals("swipe")) {
                    if (!listOfDiceImages.isEmpty()) {
                        if (quickAdd == null) {
                            roll(PresetStorage.getPresetList().get(myPref.getInt("currentPreset", 0)));
                        } else {
                            roll(quickAdd);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "No Preset", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default://for when a dice is touched to toggle roll or don't roll
                final ContextThemeWrapper wrapper;
                final Drawable drawable;

                //change attribute for fill color on dice vectors
                if (listOfDiceBoolean.get(v.getId())) {
                    wrapper = new ContextThemeWrapper(this, R.style.noRoll);
                    drawable = getResources().getDrawable(listOfDice.get(v.getId()).getImage(), wrapper.getTheme());
                    listOfDiceBoolean.set(v.getId(), false);
                } else {
                    wrapper = new ContextThemeWrapper(this, R.style.yesRoll);
                    drawable = getResources().getDrawable(listOfDice.get(v.getId()).getImage(), wrapper.getTheme());
                    listOfDiceBoolean.set(v.getId(), true);
                }

                //invalidate requires a UI thread to complete a cycle so create a separate one
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listOfDiceImages.get(v.getId()).setImageDrawable(drawable);
                        listOfDiceImages.get(v.getId()).invalidateDrawable(drawable);
                    }
                });

                break;

        }
    }

    /**
     *
     */
    @Override
    public void onResume() {
        dl.closeDrawer(nv);
        super.onResume();

        // checks emptyOutOfSync status
        if (!PresetStorage.getPresetList().isEmpty() && myPref.getInt("currentPreset", 0) < PresetStorage.getPresetList().size()) {
            emptyOutOfSync = true;
        } else {
            emptyOutOfSync = false;
        }

        //activate listener and hide roll button if shake roll setting is selected
        if (myPref.getString("rollType", "btn").equals("shake")) {
            rollBtn.setVisibility(View.GONE);
            sensorMan.registerListener(mListener, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }

        //hide rool button if touch roll setting is choosen
        if (myPref.getString("rollType", "btn").equals("swipe")) {
            rollBtn.setVisibility(View.GONE);
        }

        // recreates Main activity if theme is changed
        if (myPref.getBoolean("themeChanged", false)) {
            MainActivity.this.recreate();
            SharedPreferences.Editor edit = myPref.edit();
            edit.putBoolean("themeChanged", false);
            edit.apply();
        }

        // re sets up dice if any changes are made to a preset without expensively remaking MainActivity
        if (presetChanged) {
            //clear board and dice data
            diceBoard.removeAllViews();
            listOfDiceImages.clear();
            listOfDice.clear();
            listOfDiceBoolean.clear();

            //add appropriate dice to board and background
            if (quickAdd == null) {
                setBackground(PresetStorage.getPresetList().get(myPref.getInt("currentPreset", 0)));
                addDice(PresetStorage.getPresetList().get(myPref.getInt("currentPreset", 0)));
            } else {
                setBackground(quickAdd);
                addDice(quickAdd);
            }
            presetChanged = false;

            //update drawer text to the preset being displayed
            if (quickAdd == null) {
                if (emptyOutOfSync) {
                    View h = nv.getHeaderView(0);
                    TextView headerText = (TextView) h.findViewById(R.id.currentTextView);

                    Menu m = nv.getMenu();
                    MenuItem navBtn = m.getItem(0);//findViewById(R.id.addDice);
                    String temp;

                    headerText.setText(PresetStorage.getPresetList().get(myPref.getInt("currentPreset", 0)).getPresetName());
                    temp = "Edit: " + PresetStorage.getPresetList().get(myPref.getInt("currentPreset", 0)).getPresetName();

                    navBtn.setTitle(temp);
                } else {
                    View h = nv.getHeaderView(0);
                    TextView headerText = (TextView) h.findViewById(R.id.currentTextView);
                    headerText.setText("No Preset");
                    Menu m = nv.getMenu();
                    MenuItem navBtn = m.getItem(0);//findViewById(R.id.addDice);
                    String temp = "Edit: ";
                    navBtn.setTitle(temp);
                }
            } else {
                View h = nv.getHeaderView(0);
                TextView headerText = (TextView) h.findViewById(R.id.currentTextView);
                headerText.setText(quickAdd.getPresetName());
                Menu m = nv.getMenu();
                MenuItem navBtn = m.getItem(0);//findViewById(R.id.addDice);
                String temp = "Edit: " + quickAdd.getPresetName();
                navBtn.setTitle(temp);
            }
        }

        //code only executed when app is first loaded
        if (!initialLoadDone) {
            //update drawer text
            if (emptyOutOfSync) {
                View h = nv.getHeaderView(0);
                TextView headerText = (TextView) h.findViewById(R.id.currentTextView);
                headerText.setText(PresetStorage.getPresetList().get(myPref.getInt("currentPreset", 0)).getPresetName());
                Menu m = nv.getMenu();
                MenuItem navBtn = m.getItem(0);//findViewById(R.id.addDice);
                String temp = "Edit: " + PresetStorage.getPresetList().get(myPref.getInt("currentPreset", 0)).getPresetName();
                navBtn.setTitle(temp);
            }

            //load data for presets
            LinkedHashSet<String> backgrounds = new LinkedHashSet<>();
            LinkedHashSet<String> diceColor = new LinkedHashSet<>();

            backgrounds.add("Blue");
            backgrounds.add("Red");
            backgrounds.add("Yellow");
            backgrounds.add("Green");
            backgrounds.add("Purple");
            backgrounds.add("Pink");
            backgrounds.add("White");

            diceColor.add("white");
            diceColor.add("red");
            diceColor.add("blue");

            //add lists to shared preferences
            SharedPreferences.Editor editor = myPref.edit();

            editor.putStringSet("allBackgrounds", backgrounds);
            editor.putStringSet("allDiceColors", diceColor);

            editor.apply();
            initialLoadDone = true;
        }


    }

    /**
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
        //turn off shake listener
        sensorMan.unregisterListener(mListener);
    }

    /**
     *
     */
    @Override
    protected void onStop() {
        super.onStop();
        //make roll button visible for if the roll setting is changed
        rollBtn.setVisibility(View.VISIBLE);
    }

    /**
     * load data from database
     */
    private void loadPresets() {
        //initial load for preset database
        PresetStorage.loadList(MainActivity.this);

    }

    /**
     *takes dice from preset and creates them
     * @param p
     */
    @SuppressLint("NewApi")
    private void addDice(Presets p) {

        int diceCounter = 0;

        if (emptyOutOfSync || quickAdd != null) {
            //get display size
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int measureReference = size.x;
            int x = (measureReference / 56);
            int y = 0;

            // loops through each dice type
            int whichDice = 4;
            for (int j = 0; j < 6; j++) {
                int d = 0;
                int diceNumber = 0;

                switch (whichDice) {
                    case 4:
                        d = p.getNumFourSided();
                        diceNumber = 4;
                        whichDice = 6;
                        break;
                    case 6:
                        d = p.getNumSixSided();
                        diceNumber = 6;
                        whichDice = 8;
                        break;
                    case 8:
                        d = p.getNumEightSided();
                        diceNumber = 8;
                        whichDice = 10;
                        break;
                    case 10:
                        d = p.getNumTenSided();
                        diceNumber = 10;
                        whichDice = 12;
                        break;
                    case 12:
                        d = p.getNumTwelveSided();
                        diceNumber = 12;
                        whichDice = 20;
                        break;
                    case 20:
                        d = p.getNumTwentySided();
                        diceNumber = 20;
                        break;

                }

                // loops through all dice for the selected type of dice
                for (int i = 0; i < d; i++) {
                    //create dice class and image view
                    Dice tempD = new Dice(diceNumber, this, p.getDiceColor());
                    ImageView tempI = new ImageView(this);

                    //applies roll theme to drawable
                    ContextThemeWrapper wrapper = new ContextThemeWrapper(this, R.style.yesRoll);
                    Drawable drawable = getResources().getDrawable(tempD.getImage());
                    drawable.applyTheme(wrapper.getTheme());
                    tempI.setImageDrawable(drawable);

                    //add id to keep track of which dice is which
                    tempI.setId(diceCounter);

                    //add dice image to listener
                    tempI.setOnClickListener(this);

                    //add image to diaceboard layout
                    diceBoard.addView(tempI);

                    //position images on relative layout evenly
                    if (x < measureReference - (measureReference / 5)) {
                        tempI.setX(tempI.getX() + x);
                        tempI.setY(tempI.getY() + y);
                    } else {
                        x = (measureReference / 56);
                        y += (measureReference / 3) - (measureReference / 32);
                        tempI.setX(tempI.getX() + x);
                        tempI.setY(tempI.getY() + y);
                    }
                    x += (measureReference / 3) - (measureReference / 32);

                    //add dice data to lists
                    listOfDiceImages.add(tempI);
                    listOfDice.add(tempD);
                    listOfDiceBoolean.add(true);

                    diceCounter++;
                }
            }
        }
    }

    /**
     *applies animation to dice
     * @param p
     */
    @SuppressLint("NewApi")
    private void roll(Presets p) {
        if (!listOfDiceImages.isEmpty()) {
            //loop through every dice on board
            for (int i = 0; i < p.getNumAllDice(); i++) {
                //if allowed to roll and animation
                if (listOfDiceBoolean.get(i)) {
                    listOfDice.get(i).roll();

                    //add proper theme
                    ContextThemeWrapper wrapper = new ContextThemeWrapper(this, R.style.yesRoll);
                    AnimatedVectorDrawable d = (AnimatedVectorDrawable) getDrawable(listOfDice.get(i).getImage());
                    d.applyTheme(wrapper.getTheme());
                    listOfDiceImages.get(i).setImageDrawable(d);

                    //start rotation animation embedded in the vector
                    d.start();

                    //apply a translation animation
                    Animation anm = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate);
                    //start translation animation
                    listOfDiceImages.get(i).startAnimation(anm);
                }

            }
        }
    }

    /**
     *applies background color
     * @param p
     */
    @SuppressLint("NewApi")
    private void setBackground(Presets p) {
        if (emptyOutOfSync) {
            //apply background to board
            switch (p.getBackground()) {
                case "Red":
                    diceBoard.setBackgroundColor(getResources().getColor(R.color.red));
                    if (myPref.getString("theme", "light").equals("dark")) {
                        DarkenColor(getResources().getColor(R.color.red));
                    }
                    break;
                case "Blue":
                    diceBoard.setBackgroundColor(getResources().getColor(R.color.blue));
                    if (myPref.getString("theme", "light").equals("dark")) {
                        DarkenColor(getResources().getColor(R.color.blue));
                    }
                    break;
                case "Yellow":
                    diceBoard.setBackgroundColor(getResources().getColor(R.color.yellow));
                    if (myPref.getString("theme", "light").equals("dark")) {
                        DarkenColor(getResources().getColor(R.color.yellow));
                    }
                    break;
                case "Green":
                    diceBoard.setBackgroundColor(getResources().getColor(R.color.green));
                    if (myPref.getString("theme", "light").equals("dark")) {
                        DarkenColor(getResources().getColor(R.color.green));
                    }
                    break;
                case "Purple":
                    diceBoard.setBackgroundColor(getResources().getColor(R.color.purple));
                    if (myPref.getString("theme", "light").equals("dark")) {
                        DarkenColor(getResources().getColor(R.color.purple));
                    }
                    break;
                case "Pink":
                    diceBoard.setBackgroundColor(getResources().getColor(R.color.pink));
                    if (myPref.getString("theme", "light").equals("dark")) {
                        DarkenColor(getResources().getColor(R.color.pink));
                    }
                    break;
                case "White":
                    diceBoard.setBackgroundColor(getResources().getColor(R.color.White));
                    if (myPref.getString("theme", "light").equals("dark")) {
                        DarkenColor(getResources().getColor(R.color.white));
                    }

            }
        } else {//default background
            diceBoard.setBackgroundColor(getResources().getColor(R.color.White));
        }

    }

    /**
     *tints the background
     * @param c
     */
    @SuppressLint("NewApi")
    private void DarkenColor(int c){
        float[] hsv = new float[3];
        int color = c;
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f; // value component
        color = Color.HSVToColor(hsv);
        diceBoard.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    /**
     *motion sensor
     */
    private final SensorEventListener mListener = new SensorEventListener() {
        /**
         *
         * @param event
         */
        @Override//code for shake roll
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mGravity = event.values.clone();
                // Shake detection
                float x = mGravity[0];
                float y = mGravity[1];
                float z = mGravity[2];
                mAccelLast = mAccelCurrent;
                mAccelCurrent = (float) Math.sqrt(x * x + y * y + z * z);
                float delta = mAccelCurrent - mAccelLast;
                mAccel = mAccel * 0.2f + delta;
                // Make this higher or lower according to how much
                // motion you want to detect
                if (mAccel > 3) {
                    // do something
                    if (emptyOutOfSync) {
                        if (quickAdd == null) {
                            roll(PresetStorage.getPresetList().get(myPref.getInt("currentPreset", 0)));
                        } else {
                            roll(quickAdd);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "No Preset", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }

        /**
         *
         * @param sensor
         * @param accuracy
         */
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // required method
        }
    };
}
