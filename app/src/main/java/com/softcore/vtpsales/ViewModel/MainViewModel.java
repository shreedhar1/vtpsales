package com.softcore.vtpsales.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.softcore.vtpsales.Network.RemoteRepository;

public class MainViewModel extends AndroidViewModel {

    RemoteRepository repository;


    public MainViewModel(@NonNull Application application) {
        super(application);
        if(repository==null)
        {
            repository=RemoteRepository.getRepository();
        }
    }
}
