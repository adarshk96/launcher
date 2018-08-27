package Activities;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import com.example.ak.launcher.R;

import java.util.ArrayList;
import java.util.Comparator;

import Adapters.CustomGrid;
import Models.AppInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView grid = findViewById(R.id.grid);

        //get package manager
        final PackageManager pm = getPackageManager();


        //iterate through package list and add names of each app list of strings
        ArrayList<AppInfo> apps = new ArrayList<>();
        for(ApplicationInfo x : pm.getInstalledApplications(PackageManager.GET_META_DATA)){
            if ((x.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                apps.add(new AppInfo(x, pm));
            }
        }

        //sort app list by name
        apps.sort(new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo t0, AppInfo t1) {
                return t0.getName().compareTo(t1.getName());
            }
        });

        //create an grid adapter
        final CustomGrid gridAdapter = new CustomGrid(this, apps);

        //attach grid adapter to the grid view
        grid.setAdapter(gridAdapter);

        //add onclick listener
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //get app intent and start activity
                AppInfo app = (AppInfo) adapterView.getItemAtPosition(i);
                startActivity(app.getIntent());
            }
        });

        EditText search = (EditText)findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                gridAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
