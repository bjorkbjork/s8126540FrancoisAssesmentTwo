package com.example.s8126540francoisassessmenttwo


import android.content.ClipData.Item
import android.content.Entity
import com.example.s8126540francoisassessmenttwo.adapter.EntityJsonAdapter
import com.example.s8126540francoisassessmenttwo.adapter.ItemDataJsonAdapter
import com.example.s8126540francoisassessmenttwo.data.ItemData
import com.example.s8126540francoisassessmenttwo.network.RestfulApiDevService
import com.example.s8126540francoisassessmenttwo.network.RestfulApiDevRetrofitClient
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
    fun provideEntityJsonAdapter(moshi: Moshi): JsonAdapter<Entity> {
        return moshi.adapter(Entity::class.java)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(ItemDataJsonAdapter())
            .add(EntityJsonAdapter())
            .add(KotlinJsonAdapterFactory()) // Add this to support Kotlin data classes
            .build()
    }

    @Provides
    @Singleton
    fun provideItemDataJsonAdapter(moshi: Moshi): JsonAdapter<ItemData> {
        return moshi.adapter(ItemData::class.java)
    }

}