package com.example.adityadesai.cng.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.adityadesai.cng.Objects.ItemDetail;
import com.example.adityadesai.cng.R;

import java.util.ArrayList;

/**
 * Created by adityadesai on 17/02/17.
 */

public class ItemDetailAdapter extends ArrayAdapter<ItemDetail> {
    public ItemDetailAdapter(Context context, ArrayList<ItemDetail> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        ItemDetail currentItem=getItem(position);

        View listItemView=convertView;
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.shop_detail_item, parent, false);
        }

        TextView name=(TextView)listItemView.findViewById(R.id.item_name);
        TextView price=(TextView)listItemView.findViewById(R.id.item_price);
        TextView description=(TextView)listItemView.findViewById(R.id.item_description);

        name.setText(currentItem.getItemName());
        price.setText(currentItem.getItemPrice());
        description.setText(currentItem.getItemDescription());


        return listItemView;
    }
}
