package com.softcore.vtpsales.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.softcore.vtpsales.Network.RemoteRepository;


public class MasterViewModel extends AndroidViewModel {
    protected RemoteRepository remoteRepository;
    public MasterViewModel(@NonNull Application application) {
        super(application);
        remoteRepository=RemoteRepository.getRepository();
    }
}
