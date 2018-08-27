package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ak.launcher.R;

import java.util.ArrayList;

import Controllers.IconLoader;
import Models.AppInfo;

public class CustomGrid extends BaseAdapter implements Filterable {
    private Context mContext;
    private ArrayList<AppInfo> apps;
    private ArrayList<AppInfo> appsFull;

    public CustomGrid(Context c, ArrayList<AppInfo> apps) {
        mContext = c;
        this.apps = apps;
        this.appsFull = apps;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return apps.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return apps.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(     Context.LAYOUT_INFLATER_SERVICE );
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.grid_single, null);

            convertView.setTag(viewHolder);
        } else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.text = (TextView) convertView.findViewById(R.id.grid_text);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.grid_image);

        IconLoader loader = new IconLoader(viewHolder.image, viewHolder.text, apps.get(position));
        loader.execute();

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                apps = (ArrayList<AppInfo>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<AppInfo> FilteredArrList = new ArrayList<AppInfo>();

                if (appsFull == null) {
                    appsFull = new ArrayList<AppInfo>(apps); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = appsFull.size();
                    results.values = appsFull;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < appsFull.size(); i++) {
                        String data = appsFull.get(i).getName();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(appsFull.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    private class ViewHolder{
        public ImageView image;
        public TextView text;
    }
}
