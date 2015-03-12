package ligia.unid.com.marvel;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by LiMon on 3/11/15.
 */
public class ProviderCharacter {
    AsyncHttpClient client;
    RequestParams params;
    private String Response;
    JSONArray dataArray;
    ArrayList<Character> arr;


    public static long timeStamp = System.currentTimeMillis();
    public static final String privateKey = "208c8d07c3beffd59b38b0c75cc31c7f329918a2";
    public static final String publicKey = "b60628e40478d0bbeb32387385819709";
    static int limit = 5;

    private DownloadCharacterListener Action;
    public interface DownloadCharacterListener{
        public void Download();
    }
    public void setDownloadCharacterListener (DownloadCharacterListener listener){
        this.Action = listener;
    }

    public ProviderCharacter(){
        params = new RequestParams();
        client = new AsyncHttpClient();
    }

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
                                    dataArray = suc.getJSONArray("results");
                                    if (dataArray != null) {
                                        // setRespuesta(true);
                                        arr = new ArrayList<Character>();
                                        arr = Character.getCharacters(dataArray);
                                    }

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
