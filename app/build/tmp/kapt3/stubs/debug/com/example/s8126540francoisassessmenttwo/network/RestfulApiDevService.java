package com.example.s8126540francoisassessmenttwo.network;

import com.example.s8126540francoisassessmenttwo.data.ItemData;
import com.example.s8126540francoisassessmenttwo.data.User;
import com.example.s8126540francoisassessmenttwo.data.Keypass;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Body;
import retrofit2.http.POST;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\b\u0001\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\f\u00a8\u0006\r"}, d2 = {"Lcom/example/s8126540francoisassessmenttwo/network/RestfulApiDevService;", "", "addObject", "Lcom/example/s8126540francoisassessmenttwo/data/Keypass;", "data", "Lcom/example/s8126540francoisassessmenttwo/data/User;", "(Lcom/example/s8126540francoisassessmenttwo/data/User;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllObjects", "", "Lcom/example/s8126540francoisassessmenttwo/data/ItemData;", "keypass", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface RestfulApiDevService {
    
    @retrofit2.http.GET(value = "dashboard/{keypass}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllObjects(@retrofit2.http.Path(value = "keypass")
    @org.jetbrains.annotations.NotNull()
    java.lang.String keypass, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.s8126540francoisassessmenttwo.data.ItemData>> $completion);
    
    @retrofit2.http.POST(value = "footscray/auth")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addObject(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.example.s8126540francoisassessmenttwo.data.User data, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.s8126540francoisassessmenttwo.data.Keypass> $completion);
}