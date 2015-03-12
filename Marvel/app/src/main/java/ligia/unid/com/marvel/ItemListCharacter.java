package ligia.unid.com.marvel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by LiMon on 3/11/15.
 */
public class ItemListCharacter extends ArrayAdapter<Character> {
    private ArrayList<Character> Characters;
    private FragmentActivity activity;
    public ImageManager imageManager;

    public int idLayout;

    public ItemListCharacter(FragmentActivity Acti, int Layout,
                          ArrayList<Character> Characters) {
        super(Acti, Layout, Characters);
        this.Characters = Characters;
        activity = Acti;
        idLayout = Layout;
        imageManager = new ImageManager(activity.getApplicationContext());

    }

    public class ViewHolder{
        public TextView Nombre;
        public TextView Descripcion;
        public ImageView Img;
        public ProgressBar progress;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int index = position;
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(idLayout, null);
            final ViewHolder holder = new ViewHolder();
            holder.Nombre = (TextView) v.findViewById(R.id.txtTitulo);
            holder.Descripcion = (TextView) v.findViewById(R.id.txtDesc);
            holder.Img    = (ImageView) v.findViewById(R.id.imgCharacter);
            holder.progress = (ProgressBar) v.findViewById(R.id.progressBar);

            v.setTag(holder);
        }

        ViewHolder holder=(ViewHolder)v.getTag();
        final Character ap = Characters.get(position);
        if (ap != null) {
            if (holder.Nombre != null) {
                holder.Nombre.setText(ap.getNombre());
            }
            if(holder.Descripcion != null) {
                holder.Descripcion.setText(ap.getDesc());
            }
            if(holder.Img != null) {
				holder.Img.setTag(ap.getImg());
                imageManager.displayImage(ap.getImg(), activity, holder.Img,holder.progress); //CHANGED
                //Img.setImageBitmap(getBitmap(Lug.getUrlImagen()));
                //holder.Img.setImageBitmap(ima);
			}
        }
        return v;
    }




}
