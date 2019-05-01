package com.fracturedscale.spectrar.dicemanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.fracturedscale.spectrar.dicemanager.data.PresetStorage;

import static com.fracturedscale.spectrar.dicemanager.MainActivity.MYPREFS;

public class AllPresetsActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences myPref;

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

        setContentView(R.layout.activity_all_presets);
        //adds action bar for back button in top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    /**
     *fills table with buttons representing saved presets dynamically
     */
    private void addButtons() {
        if (PresetStorage.getPresetList().size() != 0) {
            // pull table that rows will be added to
            TableLayout tblAddLayout = (TableLayout) findViewById(R.id.presettable);
            tblAddLayout.setShrinkAllColumns(true);
            tblAddLayout.setStretchAllColumns(true);
            TableRow currentTable = null;


            int numberOfBtns = PresetStorage.getPresetList().size();
            int rowCounter = 0;
            //loop to dynamically add preset buttons for all saved presets. 3 buttons for every row

            for (int i = 0; i < numberOfBtns; i++) {
                //create generic row and button for use
                TableRow inflateRow = (TableRow) View.inflate(AllPresetsActivity.this, R.layout.presetrow, null);
                Button inflateButton = (Button) View.inflate(AllPresetsActivity.this, R.layout.presetbutton, null);
                //add a row if one is needed
                if (i == 0 || i % 2 == 0) {
                    rowCounter++;
                    TableRow tempInflateRow = inflateRow;
                    //set tag for each TableRow
                    tempInflateRow.setId(rowCounter);
                    //add TableRows to TableLayout
                    tblAddLayout.addView(tempInflateRow);
                    //save tablerow by Id so that buttons can be added
                    currentTable = (TableRow) findViewById(tempInflateRow.getId());
                }
                //add a button to current row
                Button tempInflateButton = inflateButton;
                tempInflateButton.setText(PresetStorage.getPresetList().get(i).getPresetName());
                tempInflateButton.setTag(i);
                tempInflateButton.setHeight(400);
                tempInflateButton.setOnClickListener(this);
                currentTable.addView(tempInflateButton);

            }
        }


        Button newPreset = (Button) findViewById(R.id.newPreset);
        newPreset.setOnClickListener(this);
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //close activity for back button
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

            case R.id.newPreset:
                Intent i = new Intent(AllPresetsActivity.this, PresetActivity.class);
                i.putExtra("from", "new");
                startActivity(i);
                break;
            default:
                //start pop up dialog for selecting a preset
                AlertDialog diaBox = AskOption(v);
                diaBox.show();
                break;

        }
    }

    /**
     *
     */
    @Override
    public void onResume() {
        super.onResume();
        //clear and then remake the table
        TableLayout tblAddLayout = (TableLayout) findViewById(R.id.presettable);
        tblAddLayout.removeAllViews();
        addButtons();
    }

    /**
     *pop up window when button preset is pressed
     * @param v
     * @return
     */
    private AlertDialog AskOption(View v) {
        final Button temp = (Button) v;
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT)
                //set message, title, and icon
                .setTitle(PresetStorage.getPresetList().get((Integer)temp.getTag()).getPresetName())
                .setMessage("Load or Delete?")
                .setIcon(R.drawable.ic_dice)

                .setNeutralButton("Load", new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int whichButton) {
                        //load selected preset and return to main activity
                        SharedPreferences.Editor editor = myPref.edit();
                        editor.putInt("currentPreset", (Integer) temp.getTag());
                        editor.apply();
                        MainActivity.presetChanged = true;
                        MainActivity.quickAdd = null;
                        AllPresetsActivity.this.finish();
                        dialog.dismiss();
                    }

                })

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //delete the selected preset and recreate table
                        PresetStorage.removePreset((Integer) temp.getTag(), AllPresetsActivity.this);
                        MainActivity.presetChanged = true;
                        TableLayout tblAddLayout = (TableLayout) findViewById(R.id.presettable);
                        tblAddLayout.removeAllViews();
                        addButtons();
                        MainActivity.presetChanged = true;
                        dialog.dismiss();
                    }

                })


                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //close dialog
                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }
}
