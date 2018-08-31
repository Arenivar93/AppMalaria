package Utils;

import android.content.SharedPreferences;

public class Util {

    public  static String getUserPrefs(SharedPreferences prefs){
        return prefs.getString("userRem","");
    }
    public  static String getPassPrefs(SharedPreferences prefs){
        return prefs.getString("passRem","");
    }

}
