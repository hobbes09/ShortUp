package com.shortup.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shortup.R;
import com.shortup.activities.HomeScreen;
import com.shortup.models.entities.MiniUrl;
import com.shortup.services.ShortUrlService;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UrlListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UrlListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UrlListFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MiniUrl>>{

    private OnFragmentInteractionListener mListener;

    private ListView listView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment UrlListFragment.
     */
    public static UrlListFragment newInstance() {
        UrlListFragment fragment = new UrlListFragment();
        return fragment;
    }

    public UrlListFragment() {
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
        return inflater.inflate(R.layout.fragment_url_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listView = (ListView)view.findViewById(R.id.lvUrls);

        getActivity().getSupportLoaderManager().restartLoader(HomeScreen.mApplicationContext.getResources()
                .getInteger(R.integer.URL_LISTING_LOADER), null, this).forceLoad();
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onUrlListFragmentInteraction();
        }
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
    public Loader<ArrayList<MiniUrl>> onCreateLoader(int id, Bundle args) {
        UrlListLoader mUrlListLoader = new UrlListLoader(getActivity());
        return mUrlListLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MiniUrl>> loader, ArrayList<MiniUrl> data) {
        if(data.size() > 0){
            String[] listValues = new String[data.size()];
            for(int index = 0; index < data.size(); index++){
                MiniUrl miniUrl = data.get(index);
                listValues[index] = miniUrl.getLongUrl() + "  ==>>  " + miniUrl.getShortUrl();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, listValues);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MiniUrl>> loader) {
        String[] listValues = new String[]{};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, listValues);
        listView.setAdapter(adapter);
    }

    private static class UrlListLoader extends AsyncTaskLoader<ArrayList<MiniUrl>>{

        public UrlListLoader(Context context) {
            super(context);
        }

        @Override
        public ArrayList<MiniUrl> loadInBackground() {
            return ShortUrlService.getMiniUrlArrayListFromCache();
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
        public void onUrlListFragmentInteraction();
    }

}
