package com.example.nikita.facultapplication.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nikita.facultapplication.R;
import com.example.nikita.facultapplication.adapters.GitReposAdapter;
import com.example.nikita.facultapplication.helpers.App;
import com.example.nikita.facultapplication.models.GitHubRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReposFragment extends Fragment {


    //Фрагмент отображения репозиториев  гита
    private GitReposAdapter gitReposAdapter;

    private LinearLayoutManager layoutManager;

    private List<GitHubRepo> gitHubRepoList = new ArrayList<>();

    private String LOG = "REPOS_FRAGMENT";

    RecyclerView gitHubReposRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_repos, container, false);
        gitHubReposRecyclerView = view.findViewById(R.id.gitHubReposRecyclerView);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        gitHubReposRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        gitReposAdapter = new GitReposAdapter();
        gitHubReposRecyclerView.setAdapter(gitReposAdapter);





    }


    @Override
    public void onStart() {
        //loadRepos();
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }




    void loadRepos() {
        App.get_serviceGeneraror().getRepos("nikitabmx", new Callback<List<GitHubRepo>>() {


            @Override
            public void onResponse(@NonNull Call<List<GitHubRepo>> call, @NonNull Response<List<GitHubRepo>> response) {
                if(response.isSuccessful()){
                    gitHubRepoList.clear();
                    assert response.body() != null;
                    gitHubRepoList.addAll(response.body());
                    gitReposAdapter.notifyDataSetChanged();
                } else {

                    Log.d(LOG, "Код ошибки = " + response.code());
                    try {
                        Log.d(LOG, "Сообщение ошибки = " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onFailure(@NonNull Call<List<GitHubRepo>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


}
