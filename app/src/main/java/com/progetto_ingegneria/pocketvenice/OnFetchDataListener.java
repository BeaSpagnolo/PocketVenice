package com.progetto_ingegneria.pocketvenice;

import com.progetto_ingegneria.pocketvenice.Models.NewsHeadlines;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {

    void onFetchData(List<NewsHeadlines> list, String message);

    void onError(String message);
}
