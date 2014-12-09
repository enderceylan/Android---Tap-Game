package com.example.username.tapgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HighScoreActivity extends Activity {

    private Button returnButton;
    private String[] names = {"","","","",""};
    private int[] scores = {0,0,0,0,0};
    private TextView[] nameText;
    private TextView[] scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("scoreBundle");
        if (bundle != null) {
            scores = bundle.getIntArray("scoreArray");
            names = bundle.getStringArray("nameArray");
        }
        returnButton = (Button) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(returnListener);

        nameText = new TextView[5];
        scoreText = new TextView[5];

        for(int i = 0; i < 5; i++) {
            nameText[i] = new TextView(this);
            scoreText[i] = new TextView(this);
        }

        nameText[0] = (TextView) findViewById(R.id.name1);
        nameText[1] = (TextView) findViewById(R.id.name2);
        nameText[2] = (TextView) findViewById(R.id.name3);
        nameText[3] = (TextView) findViewById(R.id.name4);
        nameText[4] = (TextView) findViewById(R.id.name5);

        scoreText[0] = (TextView) findViewById(R.id.score1);
        scoreText[1] = (TextView) findViewById(R.id.score2);
        scoreText[2] = (TextView) findViewById(R.id.score3);
        scoreText[3] = (TextView) findViewById(R.id.score4);
        scoreText[4] = (TextView) findViewById(R.id.score5);

        setBoard();
    }

    private void setBoard()
    {
        for (int i = 0; i < 5; i++)
        {
            if (names[i] != "") {
                nameText[i].setText(names[i]);
                scoreText[i].setText(String.valueOf(scores[i]));
            }
        }
    }

    private View.OnClickListener returnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            Bundle bundle = new Bundle();
            bundle.putIntArray("scoreArray",scores);
            bundle.putStringArray("nameArray",names);
            Intent intent = new Intent(HighScoreActivity.this,MainActivity.class);
            intent.putExtra("scoreBundle",bundle);
            startActivity(intent);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.high_score, menu);
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
