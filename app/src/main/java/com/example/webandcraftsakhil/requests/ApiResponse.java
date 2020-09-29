package com.example.webandcraftsakhil.requests;

import android.util.Log;

import java.io.IOException;

import retrofit2.Response;

public class ApiResponse<T> {
    private static final String TAG = "ApiResponse";
    public ApiResponse<T> create(Response<T> response) {
        if (response.isSuccessful()) {
            T body = response.body();
            if (body == null) {
                return new ApiEmptyResponse<>();
            } else {
                return new ApiSuccesResponse<>(body);
            }
        } else {
            String errorMsg = "";
            try {
                errorMsg = response.errorBody().string();
                Log.d(TAG, "create: " + errorMsg);
            } catch (IOException e) {
                e.printStackTrace();
                errorMsg = response.message();
            }
            return new ApiErrorResponse<>(errorMsg);
        }
    }

    public ApiResponse<T> create(Throwable error) {
        return new ApiErrorResponse<>(!(error.getMessage().equals("")) ? error.getMessage() : "Unknown error \n Check internet connection");
    }

    public class ApiSuccesResponse<T> extends ApiResponse<T> {
        private T body;

        public ApiSuccesResponse(T body) {
            this.body = body;
        }

        public T getBody() {
            return body;
        }
    }

    public class ApiErrorResponse<T> extends ApiResponse<T> {
        private String errorMessage;

        public ApiErrorResponse(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    public class ApiEmptyResponse<T> extends ApiResponse<T> {
    }


}
