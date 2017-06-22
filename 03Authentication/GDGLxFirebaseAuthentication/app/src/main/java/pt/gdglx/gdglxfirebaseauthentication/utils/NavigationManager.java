package pt.gdglx.gdglxfirebaseauthentication.utils;

import android.app.Activity;
import android.content.Intent;

import pt.gdglx.gdglxfirebaseauthentication.SuccessActivity;

public class NavigationManager {

    public static void navigateSuccess(Activity activity, String user)
    {
        Intent intent = SuccessActivity.calling(activity, user);
        activity.startActivity(intent);
    }
}
