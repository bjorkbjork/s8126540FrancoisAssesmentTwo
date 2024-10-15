package com.example.s8126540francoisassessmenttwo


import com.example.s8126540francoisassessmenttwo.data.Exceptions
import com.example.s8126540francoisassessmenttwo.network.RestfulApiDevService
import com.example.s8126540francoisassessmenttwo.network.RestfulApiDevRetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)

object AppModule {
    @Singleton
    @Provides
    @Named("StringOne")
    fun provideStringOne():String {return "This is the value of string one, from the Application Module"}

    @Singleton
    @Provides
    @Named("StringTwo")
    fun provideStringTwo():String {return "This is the value of string two, from the Application Module"}

    @Provides
    fun provideRestfulApiDevService(): RestfulApiDevService {
        return RestfulApiDevRetrofitClient().restfulApiDevService
    }

    @Provides
    fun provideHttpExceptions(): List<Exception> {
        return listOf(Exceptions().invalid, Exceptions().timeout, Exceptions().offline)
    }

}