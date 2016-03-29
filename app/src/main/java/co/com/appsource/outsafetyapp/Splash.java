package co.com.appsource.outsafetyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import co.com.appsource.outsafetyapp.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class Splash extends Activity {

    private Handler objHandler;
    private Runnable objCallback;
    private LinearLayout objSplashlayout;
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        objHandler = new Handler();

        objCallback = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),
                        MainActivity.class));
                finish();
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }
        };

        objSplashlayout = (LinearLayout) findViewById(R.id.fullscreen_content_controls);
        objSplashlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),
                        MainActivity.class));
                finish();
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                onPause();
            }
        });

        objHandler.postDelayed(objCallback, 5000);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
    }

    @Override
    protected void onPause() {
        super.onPause();
        objHandler.removeCallbacks(objCallback);
    }



}
