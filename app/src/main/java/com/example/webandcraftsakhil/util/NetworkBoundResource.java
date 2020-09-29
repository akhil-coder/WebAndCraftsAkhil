package com.example.webandcraftsakhil.util;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.webandcraftsakhil.requests.ApiResponse;


public abstract class NetworkBoundResource<CacheObject, RequestObject> {

    private static final String TAG = "NetworkBoundResource";
    private MediatorLiveData<Resource<CacheObject>> results = new MediatorLiveData<>();
    private AppExecutors appExecutors;

    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        init();
    }

    private void init() {
        Log.d(TAG, "init: Inside NetworkBoundResource");
        // Update liveData for loading status
        results.setValue((Resource<CacheObject>) Resource.loading(null));

        // Observe liveData from local DB
        final LiveData<CacheObject> dbSource = loadFromDb();
        results.addSource(dbSource, new Observer<CacheObject>() {
            @Override
            public void onChanged(CacheObject cacheObject) {
                results.removeSource(dbSource);
                if (shouldFetch(cacheObject)) {
                    // Get data from the network
                    fetchFromNetwork(dbSource);
                } else {
                    results.addSource(dbSource, new Observer<CacheObject>() {
                        @Override
                        public void onChanged(CacheObject cacheObject) {
                            setValue(Resource.success(cacheObject));
                        }
                    });
                }
            }
        });
    }

    /*
        1) Observe local db
        2) If condition query the network
        3) Stop observing the local db
        4) Insert new data into local db
        5) Begin observing local db again to see the refreshed data from network
     */
    private void fetchFromNetwork(final LiveData<CacheObject> dbSource) {

        //Update LiveData for loading status
        results.addSource(dbSource, new Observer<CacheObject>() {
            @Override
            public void onChanged(@Nullable CacheObject cacheObject) {
                setValue(Resource.loading(cacheObject));
            }
        });

        final LiveData<ApiResponse<RequestObject>> apiResponse = createCall();

        results.addSource(apiResponse, new Observer<ApiResponse<RequestObject>>() {
            @Override
            public void onChanged(@Nullable final ApiResponse<RequestObject> requestObjectApiResponse) {
                results.removeSource(dbSource);
                results.removeSource(apiResponse);
                if (requestObjectApiResponse instanceof ApiResponse.ApiSuccesResponse) {
                    Log.d(TAG, "onChanged: ApiSuccessResponse");
                    Log.d(TAG, "onChanged: " );
                    appExecutors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            // Save the response to the local db
                            saveCallResult((RequestObject) processResponse((ApiResponse.ApiSuccesResponse) requestObjectApiResponse));
                            appExecutors.mainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    results.addSource(loadFromDb(), new Observer<CacheObject>() {
                                        @Override
                                        public void onChanged(@Nullable CacheObject cacheObject) {
                                            setValue(Resource.success(cacheObject)); // Cache Data successfully loaded for UI
                                        }
                                    });
                                }
                            });
                        }
                    });
                } else if (requestObjectApiResponse instanceof ApiResponse.ApiEmptyResponse) {
                    Log.d(TAG, "onChanged: ApiEmptyResponse");
                    appExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            results.addSource(loadFromDb(), new Observer<CacheObject>() {
                                @Override
                                public void onChanged(@Nullable CacheObject cacheObject) {
                                    setValue(Resource.success(cacheObject));
                                }
                            });
                        }
                    });
                } else if (requestObjectApiResponse instanceof ApiResponse.ApiErrorResponse) {
                    Log.d(TAG, "onChanged: ApiErrorResponse");
                    results.addSource(dbSource, new Observer<CacheObject>() {
                        @Override
                        public void onChanged(@Nullable CacheObject cacheObject) {
                            setValue(Resource.error(((ApiResponse.ApiErrorResponse) requestObjectApiResponse).getErrorMessage(),
                                    cacheObject
                                    )
                            );
                            Log.d(TAG, "onChanged: Error message is " + ((ApiResponse.ApiErrorResponse) requestObjectApiResponse).getErrorMessage());
                        }
                    });
                }
            }
        });
    }

    private CacheObject processResponse(ApiResponse.ApiSuccesResponse response) {
        return (CacheObject) response.getBody();
    }

    private void setValue(Resource<CacheObject> newValue) {
        if (results.getValue() != newValue) {
            results.setValue(newValue);
        }
    }

    // Called to save the result of the API response into the database.
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestObject item);

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract boolean shouldFetch(@Nullable CacheObject data);

    // Called to get the cached data from the database.
    @NonNull
    @MainThread
    protected abstract LiveData<CacheObject> loadFromDb();

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestObject>> createCall();

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    public final LiveData<Resource<CacheObject>> getAsLiveData() {
        return results;
    }

}
