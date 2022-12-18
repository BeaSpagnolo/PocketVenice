package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Listeners;

import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Model.Event;

import java.util.List;

public interface OnFetchDataListener {
    void onFetchData(List<Event> list);

    void onError(int errorCode);
}
