package Controllers;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import Models.AppInfo;

public class IconLoader extends AsyncTask <Void, Void, Void>{

    private ImageView image;
    private TextView text;
    private AppInfo info;
    private Drawable icon;
    private String name;

    public IconLoader(ImageView image, TextView text, AppInfo info){
        this.image = image;
        this.text = text;
        this.info = info;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            name = info.getName();

        }catch(Exception e){

        }
        try{
            icon = info.getIcon();

        }catch (Exception e){

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        text.setText(name);
        image.setImageDrawable(icon);

    }
}
