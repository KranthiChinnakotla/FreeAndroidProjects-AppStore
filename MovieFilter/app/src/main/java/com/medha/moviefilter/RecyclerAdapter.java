package com.medha.moviefilter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Prathyusha on 1/13/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TrailerHolder> {


    private ArrayList<Trailer> mTrailers;



    public RecyclerAdapter(ArrayList<Trailer> trailers){

        this.mTrailers = trailers;


    }

    @Override
    public RecyclerAdapter.TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout,parent,false);
        return new TrailerHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.TrailerHolder holder, int position) {


        Trailer mTrailer = mTrailers.get(position);
        holder.bindTrailer(mTrailer);



    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    public static class TrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageButton imageButton;
        private TextView textViewTrailer;
        private Trailer mTrailer;
        private Context mContext;





        public TrailerHolder(View itemView) {
            super(itemView);
            imageButton = (ImageButton) itemView.findViewById(R.id.trailer_button);
            textViewTrailer = (TextView) itemView.findViewById(R.id.trailer_text);
            imageButton.setOnClickListener(this);
            mContext = itemView.getContext();
        }

        void bindTrailer(Trailer trailer){

            this.mTrailer = trailer;

            textViewTrailer.setText(mTrailer.getName());

        }

        @Override
        public void onClick(View view) {

            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+mTrailer.getKey()));
            Intent webIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v=" + mTrailer.getKey()));

            try {
               mContext.startActivity(appIntent);
            }catch (ActivityNotFoundException e){
                mContext.startActivity(webIntent);
            }

        }
    }
}
