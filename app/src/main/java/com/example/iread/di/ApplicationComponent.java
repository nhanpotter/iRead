package com.example.iread.di;

import com.example.iread.MainActivity;
import com.example.iread.account.LoginFragment;
import com.example.iread.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {NetworkModule.class})
@Singleton
public interface ApplicationComponent {
    void inject(LoginFragment loginFragment);
}
