package com.gr8apes.weatherapp_takehomeexam.di;

import android.app.Application;

import com.gr8apes.weatherapp_takehomeexam.di.modules.ApiServiceModule;
import com.gr8apes.weatherapp_takehomeexam.di.modules.AppModule;
import com.gr8apes.weatherapp_takehomeexam.di.modules.DataSourceModule;
import com.gr8apes.weatherapp_takehomeexam.di.modules.DatabaseModule;
import com.gr8apes.weatherapp_takehomeexam.presentation.BaseApplication;


import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBuilder.class,
        AppModule.class,
        ApiServiceModule.class,
        DatabaseModule.class,
        DataSourceModule.class}
)
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(BaseApplication app);


}
