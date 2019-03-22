package com.example.brunogerassicasamassa.testroompersistence.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.brunogerassicasamassa.testroompersistence.R;
import com.example.brunogerassicasamassa.testroompersistence.model.User;

import java.util.ArrayList;

public class UserListAdapter extends BaseAdapter {
    private ArrayList<User> arrayList;
    private Context context;

    public UserListAdapter(ArrayList<User> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return arrayList.size();

    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        TextView name = v.findViewById(R.id.user_tv_name);
        name.setText(arrayList.get(position).getName());

        TextView mail = v.findViewById(R.id.user_tv_email);
        mail.setText(arrayList.get(position).getMail());

        TextView age = v.findViewById(R.id.user_tv_age);
        age.setText(String.valueOf(arrayList.get(position).getAge()));

        TextView id = v.findViewById(R.id.user_tv_id);
        id.setText(String.valueOf(arrayList.get(position).getId()));
        return v;
    }
}
