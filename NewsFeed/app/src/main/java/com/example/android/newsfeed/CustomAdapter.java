package com.example.android.newsfeed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MohamedFadel on 2/2/2018.
 */

public class CustomAdapter extends ArrayAdapter<Event> {
    public CustomAdapter(Context context, ArrayList<Event> songs) {
        super(context, 0, songs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Event currentEvent = getItem(position);

        TextView titleTextView = listItemView.findViewById(R.id.list_txt_title);
        titleTextView.setText(currentEvent.getTitle());

        TextView dateTextView = listItemView.findViewById(R.id.list_txt_date);
        dateTextView.setText(formatDate(currentEvent.getDate()));

        TextView typeTextView = listItemView.findViewById(R.id.list_txt_type);
        typeTextView.setText(currentEvent.getType());

        TextView contriputerTextView = listItemView.findViewById(R.id.list_txt_contriputer);
        if(currentEvent.getContriputer() != null && !currentEvent.getContriputer().isEmpty())
            contriputerTextView.setText(currentEvent.getContriputer());
        else
            contriputerTextView.setVisibility(View.GONE);

        LinearLayout linearLayout = listItemView.findViewById(R.id.list_item_root_view);
        linearLayout.setBackgroundColor(getTyprColor(currentEvent.getType()));

        return listItemView;
    }

    /**
     *  decides the bckground color of the list item
     * @param type the current item title
     * @return int color to use
     */
    private int getTyprColor(String type){
        int color = -1;
        switch (type){
            case "UK news":
            case "Fashion":
            case "Sport":
                color = ContextCompat.getColor(getContext(), R.color.sport);
                break;
            case "Film":
            case "Music":
            case "Technology":
                color = ContextCompat.getColor(getContext(), R.color.technology);
                break;
            case "Global":
            case "Business":
            case "Politics":
                color = ContextCompat.getColor(getContext(), R.color.politics);
                break;
            case "World news":
            case "News":
            case "Environment":
                color = ContextCompat.getColor(getContext(), R.color.environment);
                break;
            default:
                color = ContextCompat.getColor(getContext(), R.color.default_color);
        }
        return color;
    }

    /**
     * cuts of the un wanted part from the date which is the puplication time
     * @param date the current item date
     * @return the date to display
     */
    private String formatDate(String date){
        String tokens[] = date.split("T");
        return tokens[0];
    }
}
