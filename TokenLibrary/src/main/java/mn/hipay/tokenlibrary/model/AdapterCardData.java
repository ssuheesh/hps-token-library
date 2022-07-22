package mn.hipay.tokenlibrary.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import mn.hipay.tokenlibrary.R;
import mn.hipay.tokenlibrary.callback.CardListenerCallback;
import mn.hipay.tokenlibrary.callback.Worker;

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

            vi.findViewById(R.id.hps_button_remove).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Worker worker = new Worker(getContext());
                            worker.setCardRemoveListener(lCardData.get(position), new CardListenerCallback() {
                                @Override
                                public void onSuccess(String successMessage, JsonObject obj) {
                                    Log.i("CARD_REMOVE_SUCCESS", successMessage);
                                    System.out.println("The event has been triggered successfully");
                                }

                                @Override
                                public void onFailure(Throwable error) {
                                    System.err.println("Error: " + error.getMessage());
                                }
                            });
                        }
                    }
            );
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return vi;
    }
}
