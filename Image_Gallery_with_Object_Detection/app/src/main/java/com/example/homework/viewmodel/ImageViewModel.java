// viewmodel/ImageViewModel.java
package com.example.homework.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.example.homework.api.PixabayApiService;
import com.example.homework.api.RetrofitClient;
import com.example.homework.models.ImageItem;
import com.example.homework.paging.ImagePagingSource;

import java.util.HashMap;
import java.util.Map;

public class ImageViewModel extends ViewModel {
    private final PixabayApiService apiService;
    private final Map<String, LiveData<PagingData<ImageItem>>> searchCache = new HashMap<>();
    private static final int PAGE_SIZE = 20;

    public ImageViewModel() {
        apiService = RetrofitClient.getClient().create(PixabayApiService.class);
    }

    public LiveData<PagingData<ImageItem>> searchImages(String query) {
        // AI Cache implementation - check if query result is already cached
        if (searchCache.containsKey(query)) {
            return searchCache.get(query);
        }

        Pager<Integer, ImageItem> pager = new Pager<>(
                new PagingConfig(PAGE_SIZE, PAGE_SIZE, false),
                () -> new ImagePagingSource(apiService, query)
        );

        LiveData<PagingData<ImageItem>> liveData = PagingLiveData.cachedIn(
                PagingLiveData.getLiveData(pager),
                ViewModelKt.getViewModelScope(this)
        );

        // Cache the result
        searchCache.put(query, liveData);
        return liveData;
    }
}