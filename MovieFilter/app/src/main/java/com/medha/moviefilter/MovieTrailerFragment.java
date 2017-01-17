package com.medha.moviefilter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.medha.moviefilter.data.MovieContract;
import com.medha.moviefilter.data.MovieDBHelper;
import com.medha.moviefilter.data.MovieProvider;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieTrailerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieTrailerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieTrailerFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MovieTrailerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieTrailerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieTrailerFragment newInstance(String param1, String param2) {
        MovieTrailerFragment fragment = new MovieTrailerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_trailer, container, false);
        ArrayList<Trailer> trailers = (ArrayList<Trailer>)getArguments().getSerializable("Trail");
        Movie movie = (Movie) getArguments().getSerializable("Mov");

        TextView textView_Year = (TextView) view.findViewById(R.id.year_textView);
        TextView textView_Duration = (TextView) view.findViewById(R.id.duration_textView);
        TextView textView_Rating = (TextView) view.findViewById(R.id.rating_textView);
        TextView overView = (TextView) view.findViewById(R.id.overView);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView_trailer);

        // Content values to insert in to db through a content provider
        final ContentValues testValues = new ContentValues();
        testValues.put(MovieContract.MovieEntry._ID,movie.getMovieId());
        testValues.put(MovieContract.MovieEntry.COLUMN_BACKDROPURL, movie.getBackdropUrl());
        testValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        testValues.put(MovieContract.MovieEntry.COLUMN_POSTERURL, movie.getPosterUrl());
        testValues.put(MovieContract.MovieEntry.COLUMN_RELEASEDATE, movie.getRelease_date());
        testValues.put(MovieContract.MovieEntry.COLUMN_SYNOPSIS,movie.getSynopsis());
        testValues.put(MovieContract.MovieEntry.COLUMN_RATING,movie.getRating());

        ///////////////////////////////////////////////////////////////////////////////////

        final Uri movieUri = MovieContract.MovieEntry.buildMovieUri();

        Button addToFavs = (Button) view.findViewById(R.id.fav);
        addToFavs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{

                    Uri cpMovieUri = getContext().getContentResolver().insert(movieUri,testValues);
                }catch (SQLException se){
                    Toast.makeText(getContext(),"The record is alread added to favourites",Toast.LENGTH_SHORT).show();
                }




            }
        });

        Picasso.with(getActivity()).load(movie.getPosterUrl()).into(imageView);

        textView_Year.setText(movie.getRelease_date().substring(0,4));
        textView_Duration.setText("120min");
        overView.setText(movie.getSynopsis());
        Double rate = Double.parseDouble(movie.getRating());
        if(rate >= 5 && rate < 7){
            textView_Rating.setTextColor(Color.rgb(192,198,81));
        }else if(rate > 7){
            textView_Rating.setTextColor(Color.rgb(81,198,169));
        }else{
            textView_Rating.setTextColor(Color.rgb(188,59,85));
        }

        textView_Rating.setText(rate+"/10");
        Log.d("Trailers", trailers.toString());

        //Setting up a recycler view
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_trailer);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(trailers);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /////////////////////////////////////////////

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
