package com.fracturedscale.spectrar.dicemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static com.fracturedscale.spectrar.dicemanager.MainActivity.MYPREFS;

//creates settings/preference screen
public class SettingsActivity extends AppCompatActivity {


    private SharedPreferences myPref;

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
        //loads fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        //adds action bar for back button in top left
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
     */
    public static class MyPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
        private CheckBoxPreference btnRoll;
        private CheckBoxPreference btnShake;
        private CheckBoxPreference btnSwipe;
        private CheckBoxPreference btnLight;
        private CheckBoxPreference btnDark;
        private SharedPreferences myPref;

        /**
         *
         * @param savedInstanceState
         */
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            myPref = this.getActivity().getSharedPreferences(MainActivity.MYPREFS, 0);
            //chooses which theme to apply
            if (myPref.getString("theme", "light").equals("dark")) {

                this.getActivity().setTheme(R.style.FeedActivityThemeDark);
            } else {
                this.getActivity().setTheme(R.style.FeedActivityThemeLight);
            }
            addPreferencesFromResource(R.xml.preferences);


            //add buttons to listener
            btnRoll = (CheckBoxPreference) findPreference("btn");
            btnRoll.setOnPreferenceClickListener(this);
            btnShake = (CheckBoxPreference) findPreference("shake");
            btnShake.setOnPreferenceClickListener(this);
            btnSwipe = (CheckBoxPreference) findPreference("swipe");
            btnSwipe.setOnPreferenceClickListener(this);

            btnLight = (CheckBoxPreference) findPreference("light");
            btnLight.setOnPreferenceClickListener(this);
            btnDark = (CheckBoxPreference) findPreference("dark");
            btnDark.setOnPreferenceClickListener(this);

        }

        /**
         *listener
         * @param preference
         * @return
         */
        @Override
        public boolean onPreferenceClick(Preference preference) {
            SharedPreferences.Editor editor = myPref.edit();
            switch (preference.getKey()) {
                case "btn":
                    btnRoll.setChecked(true);
                    btnShake.setChecked(false);
                    btnSwipe.setChecked(false);
                    editor.putString("rollType", "btn");
                    editor.apply();
                    break;
                case "shake":
                    btnRoll.setChecked(false);
                    btnShake.setChecked(true);
                    btnSwipe.setChecked(false);
                    editor.putString("rollType", "shake");
                    editor.apply();
                    break;
                case "swipe":
                    btnRoll.setChecked(false);
                    btnShake.setChecked(false);
                    btnSwipe.setChecked(true);
                    editor.putString("rollType", "swipe");
                    editor.apply();
                    break;
                case "light":
                    editor.putBoolean("themeChanged", true);
                    btnLight.setChecked(true);
                    btnDark.setChecked(false);
                    editor.putString("theme", "light");
                    editor.apply();
                    fragRefresh();
                    break;
                case "dark":
                    editor.putBoolean("themeChanged", true);
                    btnLight.setChecked(false);
                    btnDark.setChecked(true);
                    editor.putString("theme", "dark");
                    editor.apply();
                    fragRefresh();
            }


            return false;
        }

        /**
         * Refreshes the fragment
         */
        private void fragRefresh() {//refreshes activity when theme is changed
            Intent i = new Intent(this.getActivity(), SettingsActivity.class);
            i.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }

    }
}
