package com.shortup.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.shortup.R;
import com.shortup.activities.HomeScreen;
import com.shortup.models.entities.MiniUrl;
import com.shortup.services.ShortUrlService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddUrlFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddUrlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddUrlFragment extends Fragment implements ButtonRectangle.OnClickListener, LoaderManager.LoaderCallbacks<MiniUrl>{


    private OnFragmentInteractionListener mListener;

    private ButtonRectangle bAddUrl;
    private ButtonFloat buttonFloat;
    private EditText etUrl;
    private ProgressBarCircularIndeterminate progressBarCircularIndeterminate;
    private TextView tvShortUrl;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddUrlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddUrlFragment newInstance() {
        AddUrlFragment fragment = new AddUrlFragment();
        return fragment;
    }

    public AddUrlFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_url, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        etUrl = (EditText)view.findViewById(R.id.etUrl);
        bAddUrl = (ButtonRectangle)view.findViewById(R.id.bAddUrl);
        buttonFloat = (ButtonFloat)view.findViewById(R.id.buttonFloat);
        progressBarCircularIndeterminate = (ProgressBarCircularIndeterminate)view.findViewById(R.id.progressBarCircularIndeterminate);
        tvShortUrl = (TextView)view.findViewById(R.id.tvShortUrl);
        progressBarCircularIndeterminate.setVisibility(View.INVISIBLE);
        bAddUrl.setOnClickListener(this);
        buttonFloat.setOnClickListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if((view.getId() == R.id.bAddUrl) && (etUrl.getText().length() > 0)){
            progressBarCircularIndeterminate.setVisibility(View.VISIBLE);
            getActivity().getSupportLoaderManager().restartLoader(HomeScreen.mApplicationContext.getResources()
                    .getInteger(R.integer.BITLY_CALL_LOADER), null, this).forceLoad();
        }else {
            tvShortUrl.setText("");
        }

        if(view.getId() == R.id.buttonFloat){
            if (mListener != null) {
                mListener.onAddUrlFragmentInteraction();
            }
        }
    }

    @Override
    public Loader<MiniUrl> onCreateLoader(int id, Bundle args) {
        MiniUrlLoader mMiniUrlLoader = new MiniUrlLoader(getActivity(), etUrl.getText().toString());
        return mMiniUrlLoader;
    }

    @Override
    public void onLoadFinished(Loader<MiniUrl> loader, MiniUrl data) {
        progressBarCircularIndeterminate.setVisibility(View.INVISIBLE);
        etUrl.setText("");
        if(data != null){
            tvShortUrl.setText(data.getShortUrl());
        }else{
            Toast.makeText(HomeScreen.mApplicationContext, "Unable to connect", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onLoaderReset(Loader<MiniUrl> loader) {
        tvShortUrl.setText("");
        progressBarCircularIndeterminate.setVisibility(View.INVISIBLE);
        etUrl.setText("");
    }

    private static class MiniUrlLoader extends AsyncTaskLoader<MiniUrl>{

        String longUrl;

        public MiniUrlLoader(Context context, String url) {
            super(context);
            longUrl = url;
        }

        @Override
        public MiniUrl loadInBackground() {
            MiniUrl mMiniUrl = ShortUrlService.getShortUrl(longUrl);
            return mMiniUrl;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onAddUrlFragmentInteraction();
    }

}
