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

    @Provides
    fun provideRestfulApiDevService(): RestfulApiDevService {
        return RestfulApiDevRetrofitClient().restfulApiDevService
    }

    @Singleton
    @Provides
    fun provideHttpExceptions(): Exceptions {
        return Exceptions()
    }

}