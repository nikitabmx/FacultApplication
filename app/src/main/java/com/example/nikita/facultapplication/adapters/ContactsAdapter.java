package com.example.nikita.facultapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikita.facultapplication.R;
import com.example.nikita.facultapplication.models.ContactsModel;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context mContext;
    private List<ContactsModel> mListContacts;



    public ContactsAdapter(Context context, List<ContactsModel> listContacts){
        mContext = context;
        mListContacts = listContacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater  = LayoutInflater.from(mContext);

        View view = layoutInflater.inflate(R.layout.item_contact, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;

    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TextView contactNameTV;
        TextView contactNumberTV;
        View labelsTV;

        contactNameTV = viewHolder.contactNameTV;
        contactNumberTV = viewHolder.contactNumberTV;


            // устанавливаем значения из листа
        contactNameTV.setText(mListContacts.get(i).getName());
        contactNumberTV.setText(mListContacts.get(i).getNumber());


    }



    @Override
    public int getItemCount() {
        if (mListContacts != null) {
            return mListContacts.size();
        } else {
            return 0;
        }
    }




    public class  ViewHolder extends  RecyclerView.ViewHolder{

        TextView contactNameTV;
        TextView contactNumberTV;
        View labelsTV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            labelsTV = itemView;
            contactNameTV = itemView.findViewById(R.id.itemContactNameTV);
            contactNumberTV = itemView.findViewById(R.id.itemContactNumberTV);

        }

    }





}
