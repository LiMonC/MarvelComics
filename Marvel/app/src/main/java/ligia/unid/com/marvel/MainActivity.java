package ligia.unid.com.marvel;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    //private Fragment mContent;
    Handler handler;
    //ListView lsPers;

    //ArrayList<Character> arr;
    ItemListCharacter adaptador;
    ListView lsCharac;
    ProviderCharacter pro;

    public  MainActivity(){
        handler = new Handler();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_characters);


        arr = new ArrayList<Character>();
        lsCharac = (ListView) findViewById(R.id.lsCharacters);

       // updateData();

        //ServicioGet();

        params = new RequestParams();
        client = new AsyncHttpClient();
        GetCharacters();
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

    private void ServicioGet(){
        pro = new ProviderCharacter();
        pro.GetCharacters();
        //arr = Character.getCharacters(pro.dataArray);
        pro.setDownloadCharacterListener(new ProviderCharacter.DownloadCharacterListener() {
            @Override
            public void Download() {
                if(lsCharac!= null && pro.arr != null) {
                    adaptador = new ItemListCharacter(MainActivity.this, R.layout.row_character, pro.arr);
                    lsCharac.setAdapter(adaptador);
                }
            }
        });

    }

    private void updateData(){
        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetch.getJSON(MainActivity.this);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(MainActivity.this,
                                    "No hay data que mostrar",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            renderList(json);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderList(JSONObject json){
        try {
            Log.i("renderWeather", json.toString());

        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }


    AsyncHttpClient client;
    RequestParams params;
    private String Response;
    JSONArray dataArray;
    ArrayList<Character> arr;
    public static long timeStamp = System.currentTimeMillis();
    public static final String privateKey = "208c8d07c3beffd59b38b0c75cc31c7f329918a2";
    public static final String publicKey = "b60628e40478d0bbeb32387385819709";
    static int limit = 19;

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }


    public void GetCharacters(){
        String stringToHash = timeStamp + privateKey + publicKey;
        String hash = Utils.md5(stringToHash);

        client.get(String.format("http://gateway.marvel.com/v1/public/characters?ts=%d&apikey=%s&hash=%s&limit=%d",
                        timeStamp, publicKey, hash, limit),
                params, new JsonHttpResponseHandler() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                        Log.i("ON_FAILURE", "" + errorResponse.toString());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable throwable) {
                        Log.i("ON_FAILURE", res + statusCode + throwable.getMessage());
                    }


                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }

                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        JSONObject suc = null;
                        try {
                            suc = (JSONObject) response.get("data");

                            setResponse(response.getString("status"));
                            if (getResponse() != null && getResponse().equals("Ok")) {
                                //setRespuesta(true);
                                dataArray = suc.getJSONArray("results");
                                if (dataArray != null) {
                                    // setRespuesta(true);
                                    arr = new ArrayList<Character>();
                                    arr = Character.getCharacters(dataArray);
                                    adaptador = new ItemListCharacter(MainActivity.this, R.layout.row_character, arr);
                                    lsCharac.setAdapter(adaptador);
                                }

                                // if(Action != null){
                                //  Action.Dowload();
                                //}
                            } else {

                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();

                        }
                    }
                });

    }



}
