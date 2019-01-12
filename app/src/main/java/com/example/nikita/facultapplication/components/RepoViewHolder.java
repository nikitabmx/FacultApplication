package com.example.nikita.facultapplication.components;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.nikita.facultapplication.R;

public class RepoViewHolder extends RecyclerView.ViewHolder {

    public TextView itemRepo_tv;

    public RepoViewHolder(View itemView){

        super(itemView);
        itemRepo_tv = itemView.findViewById(R.id.itemRepoName_TV);


    }



}
