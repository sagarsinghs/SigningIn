package sangamsagar.signingin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class ActivitySettings extends AppCompatActivity {



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings);
        }

        public static class LocalliPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{





            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                addPreferencesFromResource(R.xml.settings_main);


                Preference restaurantName=findPreference(getString(R.string.pref_name_key));
                bindPreferenceSummaryToValue(restaurantName);

                Preference restaurantLocation=findPreference(getString(R.string.pref_mobile_mobile_key
                ));
                bindPreferenceSummaryToValue(restaurantLocation);

                Preference restaurantPhone=findPreference(getString(R.string.pref_birth_key));
                bindPreferenceSummaryToValue(restaurantPhone);

                Preference restaurantOpeningTime=findPreference(getString(R.string.pref_gender_gender_key));
                bindPreferenceSummaryToValue(restaurantOpeningTime);


            }

            private void bindPreferenceSummaryToValue(Preference pref) {
                pref.setOnPreferenceChangeListener(this);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(pref.getContext());
                String preferenceString=preferences.getString(pref.getKey(),"");
                onPreferenceChange(pref,preferenceString);
            }


            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String stringValue=newValue.toString();
                preference.setSummary(stringValue);
                return true;
            }
        }
    }

