package edu.stts.quizmdp6541;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private appDatabase db;

    Random rnd = new Random();
    String level, username, tampLv;
    EditText et;
    TextView life;
    TextView lv;
    TextView temp1, temp2;
    int angka1, angka2, nyawa=5;
    int hasil;
    Button btnDone;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        pref = getApplicationContext().getSharedPreferences("userpref", Context.MODE_PRIVATE);
        et = findViewById(R.id.editText);
        lv = findViewById(R.id.tvLevel);
        life = findViewById(R.id.tvLife);
        temp1 = findViewById(R.id.textView6);
        temp2 = findViewById(R.id.textView8);
        btnDone = findViewById(R.id.btnDone);

        db = Room.databaseBuilder(getApplicationContext(), appDatabase.class,
                "userdb").build();

        if (getIntent() != null) {
            Intent pemanggil = getIntent();
            level = pemanggil.getStringExtra("lv");
            username = pemanggil.getStringExtra("username");

            if (level.equals("1")) {
                lv.setText("Easy");
                tampLv = "Easy";
                life.setText(nyawa + "");
                angka1 = rnd.nextInt(10);
                angka2 = rnd.nextInt(10);
            } else if (level.equals("2")) {
                lv.setText("Medium"); tampLv = "Medium";
                life.setText(nyawa + "");
                angka1 = rnd.nextInt(100);
                angka2 = rnd.nextInt(100);
            } else if (level.equals("3")) {
                lv.setText("Hard");
                life.setText(nyawa + ""); tampLv = "Hard";
                angka1 = rnd.nextInt(1000);
                angka2 = rnd.nextInt(1000);
            }
            hasil = angka1 + angka2;
            temp1.setText(angka1 + "");
            temp2.setText(angka2 + "");

            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!et.getText().toString().equals("")){
                        int jaw = Integer.parseInt(et.getText().toString());
                        if (jaw == hasil && nyawa > 0) {
                            btnDone.setText("DONE");
                        } else {
                            btnDone.setText("GO");
                        }
                    }
                }
            });
        }
    }

    public void doneClick(View v){
        String cekButton = btnDone.getText().toString();
        if (cekButton.equals("DONE")) {
            int score = nyawa * 20;
            Toast.makeText(this, "You Win!\nYour Score: " + score, Toast.LENGTH_LONG).show();

            if (username == null) {
                String username = pref.getString("username","");
                insertScore(new User(tampLv, username, score));
            } else {
                insertScore(new User(tampLv, username, score));
            }

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else {
            if(et.getText().toString().equals("")) {
                Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_LONG).show();
            } else {
                int jaw = Integer.parseInt(et.getText().toString());
                if (jaw == hasil) {
                    Toast.makeText(this, "You are right", Toast.LENGTH_LONG).show();
                } else {
                    nyawa--;
                    if (nyawa <= 0) {
                        nyawa = 0;
                        Toast.makeText(this, "Game Over!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(this, "You are wrong", Toast.LENGTH_LONG).show();
                        et.setText("");
                    }
                    life.setText(nyawa + "");
                }
            }
        }
    }

    public void giveClick(View v){
        Toast.makeText(this, "You give up", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void insertScore(final User u){
        new AsyncTask<Void,Void,Long>(){

            @Override
            protected Long doInBackground(Void... voids) {
                long status = db.userDAO().insertUser(u);
                return status;
            }

            @Override
            protected void onPostExecute(Long aLong) {
                startActivity(new Intent(GameActivity.this, MainActivity.class));
                finish();
            }
        }.execute();
    }
}
