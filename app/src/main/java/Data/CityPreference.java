package Data;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Moein on 7/23/2017.
 */

public class CityPreference {
    SharedPreferences prefs;

    public CityPreference(Activity activity) {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    //If the user has not chosen a city, return default

    public String getCity() {
        return prefs.getString("city", "Seattle,US");
    }

    public void setCity(String city) {
        prefs.edit().putString("city", city).commit();
    }
}
