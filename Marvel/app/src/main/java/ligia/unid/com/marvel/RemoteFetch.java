package ligia.unid.com.marvel;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LiMon on 3/9/15.
 */
public class RemoteFetch {

    public static long timeStamp = System.currentTimeMillis();
    public static final String privateKey = "208c8d07c3beffd59b38b0c75cc31c7f329918a2";
    public static final String publicKey = "b60628e40478d0bbeb32387385819709";
    static int limit = 5;
  //  private static final String MARVEL_URL =
   //         "http://gateway.marvel.com:80/v1/public/characters?apikey=b60628e40478d0bbeb32387385819709";

    public static JSONObject getJSON(Context context){
        try {
            String stringToHash = timeStamp + privateKey + publicKey;
            String hash = Utils.md5(stringToHash);
            //URL url = new URL(String.format(MARVEL_URL, city));
           // String.format("http://gateway.marvel.com/v1/public/characters?ts=%d&apikey=%s&hash=%s&limit=%d", timeStamp, publicKey, hash, limit);
            URL url = new URL(String.format("http://gateway.marvel.com/v1/public/characters?ts=%d&;apikey=%s&hash=%s&limit=%d", timeStamp, publicKey, hash, limit));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

           // connection.addRequestProperty("x-api-key",
           //         context.getString(R.string.open_weather_maps_app_id));

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            Log.i("=======JSONOBJ=======", data.toString());
            // This value will be 404 if the request was not
            // successful
            if(data.getInt("cod") != 200){
                return null;
            }
            else {
                Log.i("=======JSONOBJ=======", data.toString());
            }

            return data;
        }catch(Exception e){
            return null;
        }
    }
}
