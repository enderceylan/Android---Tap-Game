package com.example.username.tapgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    private Button playButton;
    private Button instructionsButton;
    private Button highScoreButton;
    private TextView titleText;
    private int[] highScore = {0,0,0,0,0};
    private String[] names = {"","","","",""};

    public static final Typeface VAGRoundedBlack(Context context)
    {
        Typeface typeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/vag-rounded-black.ttf");
        return typeface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    CalligraphyConfig.initDefault("fonts/Roboto-Regular.ttf", R.attr.fontPath);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("scoreBundle");
        if (bundle != null) {
            highScore = bundle.getIntArray("scoreArray");
            names = bundle.getStringArray("nameArray");
        }
        playButton = (Button) findViewById(R.id.mainButton);
        playButton.setTypeface(VAGRoundedBlack(this));
        instructionsButton = (Button) findViewById(R.id.instructionsButton);
        instructionsButton.setTypeface(VAGRoundedBlack(this));
        highScoreButton = (Button) findViewById(R.id.highScoreButton);
        highScoreButton.setTypeface(VAGRoundedBlack(this));
        titleText = (TextView) findViewById(R.id.mainTitle);
        titleText.setTypeface(VAGRoundedBlack(this));

        playButton.setOnClickListener(playListener);
        instructionsButton.setOnClickListener(instructionsListener);
        highScoreButton.setOnClickListener(highScoreListener);
    }


    private View.OnClickListener playListener = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            Bundle bundle = new Bundle();
            bundle.putIntArray("scoreArray",highScore);
            bundle.putStringArray("nameArray",names);
            Intent intent = new Intent(MainActivity.this,GameActivity.class);
            intent.putExtra("scoreBundle",bundle);
            startActivity(intent);
        }

    };

    private View.OnClickListener instructionsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
            LayoutInflater inflater = MainActivity.this.getLayoutInflater();
            alert.setView(inflater.inflate(R.layout.dialog_instructions, null));
            alert.setButton(DialogInterface.BUTTON_NEUTRAL,"OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            alert.show();
        }

    };

    private View.OnClickListener highScoreListener = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            Bundle bundle = new Bundle();
            bundle.putIntArray("scoreArray",highScore);
            bundle.putStringArray("nameArray",names);
            Intent intent = new Intent(MainActivity.this,HighScoreActivity.class);
            intent.putExtra("scoreBundle",bundle);
            startActivity(intent);
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
