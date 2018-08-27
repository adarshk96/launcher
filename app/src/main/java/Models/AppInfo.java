package Models;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

public class AppInfo {

    private ApplicationInfo info;
    private PackageManager pm;

    public AppInfo(ApplicationInfo info, PackageManager pm){
        this.info = info;
        this.pm = pm;
    }

    public String getName(){
        return info.loadLabel(pm).toString();
    }

    public Intent getIntent(){
        return pm.getLaunchIntentForPackage(info.packageName);
    }

    public Drawable getIcon(){
        return pm.getApplicationIcon(info);
    }

    @Override
    public String toString(){
        return info.loadLabel(pm).toString();
    }
}
