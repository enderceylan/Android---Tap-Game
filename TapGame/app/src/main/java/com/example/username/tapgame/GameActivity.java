package com.example.username.tapgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class GameActivity extends Activity {

    private int time = 10;
    private int index = 0;
    private int score = 0;
    private int[] highScore = {0,0,0,0,0};
    private String[] names = {"","","","",""};
    private Handler handler;
    private Button bigButton;
    private TextView scoreText;

    public static final Typeface VAGRoundedBlack(Context context)
    {
        Typeface typeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/vag-rounded-black.ttf");
        return typeface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("scoreBundle");
        if (bundle != null) {
            highScore = bundle.getIntArray("scoreArray");
            names = bundle.getStringArray("nameArray");
        }
        bigButton = (Button) findViewById(R.id.bigButton);
        bigButton.setTypeface(VAGRoundedBlack(this));
        scoreText = (TextView) findViewById(R.id.score);
        scoreText.setTypeface(VAGRoundedBlack(this));
        bigButton.setOnClickListener(bigListener);
        handler = new Handler();
        handler.post(runnable);
    }

    private Runnable runnable = new Runnable() {

        @Override
        public void run()
        {
            if (time <= 0)
            {
                finishGame();
            }
            else
            {
                time--;
                bigButton.setText(""+time);
                handler.postDelayed(runnable,1000);
            }
        }
    };

    private View.OnClickListener bigListener = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            score += 1;
            scoreText.setText("Score: "+score);
        }

    };

    private int highScoreIndex(int score)
    {
        int index = -1;
        for (int i = 0; i < 5; i++)
        {
            if (score > highScore[i] && index < 0)
            {
                index = i;
            }
        }
        return index;
    }

    private int[] updateScores(int score, int index)
    {
        int[] newList = new int[5];
        int i;
        for (i = 0; i < index; i++)
        {
            newList[i] = highScore[i];
        }
        newList[index] = score;
        for (int j = index+1; j < 5; j++)
        {
            newList[j] = highScore[i];
            i++;
        }
        return newList;
    }

    private String[] updateNames(String name, int index)
    {
        String[] newList = new String[5];
        int i;
        for (i = 0; i < index; i++)
        {
            newList[i] = names[i];
        }
        newList[index] = name;
        for (int j = index+1; j < 5; j++)
        {
            newList[j] = names[i];
            i++;
        }
        return newList;
    }

    private void finishGame()
    {
        index = highScoreIndex(score);
        if (index >= 0)
        {
            ContextThemeWrapper ctw = new ContextThemeWrapper( this, R.style.CustomDialogTheme);
            AlertDialog alert = new AlertDialog.Builder(ctw).create();
            LayoutInflater inflater = GameActivity.this.getLayoutInflater();
            alert.setTitle("New High Score!");
            alert.setMessage("Enter your name into the leaderboard!");
            final EditText input = new EditText(this);
            alert.setView(input);
            alert.setButton(DialogInterface.BUTTON_NEUTRAL,"Submit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    highScore = updateScores(score,index);
                    names = updateNames(input.getText().toString(),index);
                    Bundle bundle = new Bundle();
                    bundle.putIntArray("scoreArray",highScore);
                    bundle.putStringArray("nameArray",names);
                    Intent intent = new Intent(GameActivity.this,HighScoreActivity.class);
                    intent.putExtra("scoreBundle",bundle);
                    startActivity(intent);
                }
            });
            alert.show();
        }
        else {
        score = 0;
        Bundle bundle = new Bundle();
        bundle.putIntArray("scoreArray", highScore);
        bundle.putStringArray("nameArray",names);
        Intent intent = new Intent(GameActivity.this,MainActivity.class);
        intent.putExtra("scoreBundle", bundle);
        startActivity(intent); }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
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
