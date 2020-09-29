package com.example.webandcraftsakhil.util;

import androidx.lifecycle.LiveData;


import com.example.webandcraftsakhil.requests.ApiResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {
    /*
        This method performs a number of checks and then returns the Response type for the Retrofit requests.
        @bodyType is the ResponseType. It can be RecipeResponse or RecipeSearchResponse

        Check 1) return type returns LiveData
        Check 2) Type LiveData<T> is of ApiResponse.class
        Check 3) Make sure ApiResponse is parametrized. AKA: ApiResponse<T> exists
     */
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {

        // Check 1
        if(CallAdapter.Factory.getRawType(returnType) != LiveData.class){
            return null;
        }
        // Check 2
        // Type that LiveData is wrapping is extracted
        Type observableType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) returnType);

        Type rawObservableType = CallAdapter.Factory.getRawType(observableType);
        if(rawObservableType != ApiResponse.class){
            throw new IllegalArgumentException("Type must be a defined resource");
        }

        // Check 3
        // Check if ApiResponse is parametrized.
        if(!(observableType instanceof ParameterizedType)){
            throw new IllegalArgumentException("resource must be parametrized");
        }

        Type bodyType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType)observableType);
        return new LiveDataCallAdapter<Type>(bodyType);
    }
}
