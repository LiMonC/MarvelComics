package ligia.unid.com.marvel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by LiMon on 3/9/15.
 */
public class Character {
    private String nombre;
    private String desc;
    private int id;
    private String img;


    public Character(int id,String nombre, String desc,String img){
        this.id = id;
        this.nombre	= nombre;
        this.desc = desc;
        this.img = img;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id= id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }

    public static ArrayList<Character> getCharacters(JSONArray dat){
        ArrayList<Character> arCharacters = new ArrayList<Character>();
        JSONObject obj = null;
        Character objCharacter = null;
        JSONObject objImg = null;
        try {
            for(int i = 0; i < dat.length() ; i++){
                obj = (JSONObject) dat.get(i);
                objImg = (JSONObject)obj.get("thumbnail");
                objCharacter = new Character(Integer.parseInt(obj.getString("id")), obj.getString("name"),
                        obj.getString("description"),
                        objImg.getString("path") +"."+ objImg.getString("extension"));

                arCharacters.add(objCharacter);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return arCharacters;
    }
}
