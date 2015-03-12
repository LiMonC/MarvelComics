package ligia.unid.com.marvel;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by LiMon on 3/9/15.
 */
public class MainFragment extends Fragment {

    Handler handler;
    ListView lsPers;

    public  MainFragment(){
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_characters, container, false);
        lsPers = (ListView)getActivity().findViewById(R.id.lsCharacters);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateData();
    }

    private void updateData(){
        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetch.getJSON(getActivity());
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
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
}
