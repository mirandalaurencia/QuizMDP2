package edu.stts.quizmdp6541;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LevelFragment.OnFragmentInteractionListener,
                                                                ScoreFragment.OnFragmentInteractionListener {

    FragmentTransaction ft;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent() != null) {
            Intent pemanggil = getIntent();
            username = pemanggil.getStringExtra("username");
        }

        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFragment, new LevelFragment());
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuLevel) {
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFragment, new LevelFragment());
            ft.commit();
        } else if (item.getItemId() == R.id.menuScore) {
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFragment, new ScoreFragment());
            ft.commit();
        } else if (item.getItemId() == R.id.menuLogout) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(String i) {
        Intent in = new Intent(this, GameActivity.class);
        in.putExtra("lv", i);
        in.putExtra("username", username);
        startActivity(in);
    }
}
