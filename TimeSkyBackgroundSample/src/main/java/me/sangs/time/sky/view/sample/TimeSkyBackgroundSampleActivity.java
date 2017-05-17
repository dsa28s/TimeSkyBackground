package me.sangs.time.sky.view.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import me.sangs.time.sky.view.SkyTimeBackgroundView;

public class TimeSkyBackgroundSampleActivity extends AppCompatActivity {

    private SkyTimeBackgroundView mBackgroundView;

    private Button mAfternoonButton;
    private Button mEarlyNightButton;
    private Button mNightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tsb_sample_activity);

        mBackgroundView = (SkyTimeBackgroundView)findViewById(R.id.timeBackgroundView);

        mAfternoonButton = (Button)findViewById(R.id.afternoon_btn);
        mEarlyNightButton = (Button)findViewById(R.id.earlynight_btn);
        mNightButton = (Button)findViewById(R.id.night_btn);

        mAfternoonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBackgroundView.changeTime(SkyTimeBackgroundView.Time.AFTERNOON);
            }
        });

        mEarlyNightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBackgroundView.changeTime(SkyTimeBackgroundView.Time.EARLY_NIGHT);
                mBackgroundView.setStarVisibility(true);
                mBackgroundView.setStarLineVisibility(false);
            }
        });

        mNightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBackgroundView.changeTime(SkyTimeBackgroundView.Time.NIGHT);
                mBackgroundView.setStarVisibility(false);
                mBackgroundView.setStarLineVisibility(true);
            }
        });
    }
}
