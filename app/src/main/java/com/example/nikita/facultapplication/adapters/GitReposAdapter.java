package com.example.nikita.facultapplication.adapters;



import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikita.facultapplication.R;
import com.example.nikita.facultapplication.models.GitHubRepoModel;

import java.util.List;

public class GitReposAdapter extends RecyclerView.Adapter<GitReposAdapter.RepoViewHolder>{

    private List<GitHubRepoModel> gitHubRepoList;

    public GitReposAdapter(List<GitHubRepoModel> gitHubRepoList) {
        this.gitHubRepoList = gitHubRepoList;
    }










    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_repo, viewGroup, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder repoViewHolder, int i) {
        repoViewHolder.itemRepo_tv.setText(gitHubRepoList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        if (gitHubRepoList != null) {
            return gitHubRepoList.size();
        } else {
            return 0;
        }

    }









    class RepoViewHolder extends RecyclerView.ViewHolder {

        TextView itemRepo_tv;


        public RepoViewHolder(@NonNull View itemView) {

            super(itemView);
            itemRepo_tv = itemView.findViewById(R.id.itemRepoName_TV);


        }

    }




}
