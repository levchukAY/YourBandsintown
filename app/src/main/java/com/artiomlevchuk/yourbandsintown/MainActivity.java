package com.artiomlevchuk.yourbandsintown;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.artiomlevchuk.yourbandsintown.api.ApiConstants;
import com.artiomlevchuk.yourbandsintown.api.BITClient;
import com.artiomlevchuk.yourbandsintown.api.ServiceGenerator;
import com.artiomlevchuk.yourbandsintown.data.DAO;
import com.artiomlevchuk.yourbandsintown.entities.Artist;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private BITClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("PREF_IS_FIRST", true))
            startActivity(new Intent(this, WelcomeActivity.class));

        mClient = ServiceGenerator.createService(BITClient.class);

        final SearchView nameRequestSearchView = (SearchView) findViewById(R.id.text_name_request);
        nameRequestSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ((InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        nameRequestSearchView.getWindowToken(), 0);
                String requestName = nameRequestSearchView.getQuery().toString().trim();
                if (!requestName.equals(""))
                    searchUserByName(requestName);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_history) {
            startActivity(new Intent(this, SearchHistoryActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void searchUserByName(final String username) {

        mClient.searchUser(username, ApiConstants.APP_ID).enqueue(new Callback<Artist>() {

            @Override
            public void onResponse(Call<Artist> call, Response<Artist> response) {
                if (response.isSuccessful()) {
                    Artist artist = response.body();
                    if (artist.getUrl().isEmpty()) {
                        Toast.makeText(MainActivity.this,
                                "Musician not found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    saveArtist(artist);
                    Intent artistInfoIntent = new Intent(MainActivity.this, WebArtistInfoActivity.class);
                    artistInfoIntent.putExtra("EXTRA_NAME", artist.getName());
                    artistInfoIntent.putExtra("EXTRA_IMAGE_URL", artist.getImageUrl());
                    startActivity(artistInfoIntent);
                } else {
                    Toast.makeText(MainActivity.this,
                            "Bad response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Artist> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Check connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveArtist(Artist artist) {
        DAO dao = new DAO(this);
        dao.open();
        dao.putArtist(artist);
        dao.close();
    }

}
