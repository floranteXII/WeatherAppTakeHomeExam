package com.gr8apes.weatherapp_takehomeexam.di.modules;

import android.app.Application;
import android.content.Context;

import com.gr8apes.weatherapp_takehomeexam.di.AppScope;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class AppModule {

    @Provides
    public Context provideContext(Application application) {
        return application;
    }

    @Provides
    @AppScope
    public CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

}
