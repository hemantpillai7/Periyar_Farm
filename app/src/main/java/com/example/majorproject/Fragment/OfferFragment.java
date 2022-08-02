package com.example.majorproject.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.majorproject.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class OfferFragment extends BottomSheetDialogFragment
{

    public OfferFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_offer, container, false);
    }
}