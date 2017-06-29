package com.example.sujaynaik.myapplication.interfaces;

/**
 * Created by sujaynaik on 6/28/17.
 */

public interface ListListener<T> {

    public void onItemClick(T t, int position);

    public void onItemLongClick(T t, int position);

    public void onItemSelected(T t, boolean isSelected, int position);
}
