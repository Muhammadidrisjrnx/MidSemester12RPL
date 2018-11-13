package com.example.rplrus021.midsemester12rpl;

import android.annotation.SuppressLint;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class home extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new now_playing(), "Now Playing");
        adapter.addFragment(new upcoming(), "Up Coming");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sign_out_menu) {
            Intent intent = new Intent(getApplicationContext(), login.class);
            SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            startActivity(intent);
            finish();
        } else if (id == R.id.favorite_menu) {
            Intent intent = new Intent(getApplicationContext(), favorite.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


//    @SuppressLint("StaticFieldLeak")
//    public class load extends AsyncTask<Void, Void, JSONObject> {
//
//
//        @Override
//        protected void onPreExecute() {
//
//        }
//
//        @Override
//        protected JSONObject doInBackground(Void... params) {
//            JSONObject jsonObject;
//
//            try {
//                String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=7b91b2135beb96ab098d2f376ee5658b";
//                System.out.println(url);
//                DefaultHttpClient httpClient = new DefaultHttpClient();
//                HttpGet httpGet = new HttpGet(url);
//                HttpResponse httpResponse = httpClient.execute(httpGet);
//                HttpEntity httpEntity = httpResponse.getEntity();
//                InputStream inputStream = httpEntity.getContent();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(
//                        inputStream, "iso-8859-1"
//                ), 8);
//                StringBuilder stringBuilder = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    stringBuilder.append(line).append("\n");
//                }
//                inputStream.close();
//                String json = stringBuilder.toString();
//                jsonObject = new JSONObject(json);
//            } catch (Exception e) {
//                jsonObject = null;
//            }
//            return jsonObject;
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject jsonObject) {
//            Log.d("hasil json ", "onPostExecute: " + jsonObject.toString());
//
//            if (jsonObject != null) {
//                try {
//                    JSONArray Result = jsonObject.getJSONArray("results");
//                    data = new ArrayList<>();
//                    for (int i = 0; i < Result.length(); i++) {
//                        String id_movie = Result.getJSONObject(i).getString("id");
//                        String title = Result.getJSONObject(i).getString("title");
//                        String overview = Result.getJSONObject(i).getString("overview");
//                        String poster_path = Result.getJSONObject(i).getString("poster_path");
//                        String image = only_url.url + poster_path;
//                        long id = database.insertData(title, overview, image);
//                        data = database.getData();
////                        data all = new data();
////                        all.setJudul(title);
////                        all.setDescription(overview);
////                        all.setGambar(image);
////                        data.add(all);
//
//                    }
//
//                    recyclerView.setLayoutManager(new LinearLayoutManager(home.this));
//                    adapter adapter = new adapter(home.this, data);
//                    recyclerView.setAdapter(adapter);
//
//                } catch (Exception ignored) {
//                    ignored.printStackTrace();
//                }
//            }
//        }
//    }

}
