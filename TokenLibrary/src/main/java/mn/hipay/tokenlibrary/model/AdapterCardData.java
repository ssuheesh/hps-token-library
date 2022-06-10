package mn.hipay.tokenlibrary.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mn.hipay.tokenlibrary.R;

public class AdapterCardData extends ArrayAdapter<CardData> {
    private Activity activity;
    private ArrayList<CardData> lCardData;
    private static LayoutInflater inflater = null;

    public AdapterCardData (Activity activity, int textViewResourceId,ArrayList<CardData> _lCardData) {
        super(activity, textViewResourceId, _lCardData);
        try {
            this.activity = activity;
            this.lCardData = _lCardData;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return lCardData.size();
    }

    public static class ViewHolder {
        public TextView display_name;
        public TextView display_number;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.activity_listview, null);
                holder = new ViewHolder();

                holder.display_name = (TextView) vi.findViewById(R.id.display_name);
                holder.display_number = (TextView) vi.findViewById(R.id.display_number);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }



            holder.display_name.setText(lCardData.get(position).tokenId);
            holder.display_number.setText(lCardData.get(position).expiryMonth);
        } catch (Exception e) {

        }
        return vi;
    }
}
