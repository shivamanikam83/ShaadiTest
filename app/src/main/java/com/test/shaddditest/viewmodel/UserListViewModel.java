package com.test.shaddditest.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.test.shaddditest.repository.remote.RetrofitClient;
import com.test.shaddditest.json.UserResponseJson;

public class UserListViewModel extends AndroidViewModel {
    private MutableLiveData<UserResponseJson> userListObservable;

    public UserListViewModel(Application application) {
        super(application);
        if(userListObservable==null)
        {
            userListObservable = RetrofitClient.getInstance().getUsers();
        }
    }

    /**
     * Expose the LiveData so the UI can observe it.
     */
    public LiveData<UserResponseJson> getUserListObservable() {
        return userListObservable;
    }
}
