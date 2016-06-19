import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button mConcertButton;
    private Button mSongButton;

    //functions to start activities
    private void StartConcert(){
        Intent intent = new Intent(this, ConcertActivity.class);
        startActivity(intent);
    }

    private void StartSong(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //method to acquire the JSON data
    public JSONArray getJSON(String url) {
        JSONArray jsonArray = null;
        String jsonString = null;

        HttpURLConnection httpURLConnection = null;
        try {
            URL u = new URL(url);
            httpURLConnection = (HttpURLConnection) u.openConnection();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "/n");
            }
            jsonString = stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }

        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get latitude, longitude coordinates
        GPSTracker gps = new GPSTracker(this);
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();

        //converts the latitude and longitude into a string that can be read by the php code
        String queryurl = "http://eqo.com/getsongjson.php?latitude="+latitude+"&longitude="+longitude;

        //the php url will be entered here to call the method, get JSON data
        JSONArray jsonArray = getJSON(queryurl);

        //declaration of song array
        Song song_data[] = new Song[]
                {
                };


        //loop through the JSON data, get info, put into a new Song row in the array above
            if(jsonArray.length() > 0){
                int len = jsonArray.length();
                for (int i=0;i<len;i++){
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Song = object.getString("Song");
                        String Artist = object.getString("Artist");
                        String Genre = object.getString("Genre");

                        new Song(Song, Artist, Genre,"");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        //creates adapter, sets it
        SongAdapter adapter = new SongAdapter(this, R.layout.listview_item_row, song_data);
        ListView listView1 = (ListView) findViewById(R.id.listView1);
        listView1.setAdapter(adapter);

        //binds fields to buttons
        mConcertButton = (Button)findViewById(R.id.ConcertButton);
        mSongButton = (Button)findViewById(R.id.SongButton);

        //sets on click listeners to switch between activities
        mConcertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartConcert();
            }
        });

        mSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartSong();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
