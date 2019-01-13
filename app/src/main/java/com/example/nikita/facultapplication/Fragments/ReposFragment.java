package com.example.nikita.facultapplication.Fragments;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikita.facultapplication.LoginActivity;
import com.example.nikita.facultapplication.R;
import com.example.nikita.facultapplication.adapters.GitReposAdapter;
import com.example.nikita.facultapplication.helpers.App;
import com.example.nikita.facultapplication.models.GitHubRepoModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReposFragment extends Fragment {


    //Фрагмент отображения репозиториев  гита
    private GitReposAdapter gitReposAdapter;
    private List<GitHubRepoModel> gitHubRepoList = new ArrayList<>();
    private String LOG = "REPOS_FRAGMENT";
    RecyclerView gitHubReposRecyclerView;

    private TextView tvTittleRepo;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_repos, container, false);
        gitHubReposRecyclerView = view.findViewById(R.id.gitHubReposRecyclerView);
        tvTittleRepo = view.findViewById(R.id.tvTittleRepo);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        gitHubReposRecyclerView.setLayoutManager(linearLayoutManager);
        gitReposAdapter = new GitReposAdapter(gitHubRepoList);
        gitHubReposRecyclerView.setAdapter(gitReposAdapter);


        if (App.getAccessToken() == null){

            tvTittleRepo.setText("Нажмите, чтобы авторизоваться ");
            tvTittleRepo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }});

        } else {

            tvTittleRepo.setText("Мои репозитории");
            tvTittleRepo.setClickable(false);
        }


    }








    @Override
    public void onStart() {
        loadRepos();
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }




    void loadRepos() {

        App.get_serviceGeneraror().getRepos(App.getUsername(), new Callback<List<GitHubRepoModel>>() {

            @Override
            public void onResponse(@NonNull Call<List<GitHubRepoModel>> call, @NonNull Response<List<GitHubRepoModel>> response) {
                if(response.isSuccessful()){
                    gitHubRepoList.clear();
                    assert response.body() != null;
                    gitHubRepoList.addAll(response.body());
                    gitReposAdapter.notifyDataSetChanged();

                } else {

                    try {
                        Toast.makeText(getActivity(), "Нет подключения к интернету, повторите позже" + App.getUsername(),Toast.LENGTH_SHORT).show();
                        assert response.errorBody() != null;
                        Log.d(LOG,  response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onFailure(@NonNull Call<List<GitHubRepoModel>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });

    }


}
