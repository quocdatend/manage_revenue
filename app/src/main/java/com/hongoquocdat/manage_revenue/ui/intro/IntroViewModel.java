package com.hongoquocdat.manage_revenue.ui.intro;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IntroViewModel extends ViewModel {

    private final MutableLiveData<String> welcomeText;

    public IntroViewModel() {
        welcomeText = new MutableLiveData<>();
        welcomeText.setValue("Welcome to Revenue Manager!");
    }

    public LiveData<String> getWelcomeText() {
        return welcomeText;
    }
}
