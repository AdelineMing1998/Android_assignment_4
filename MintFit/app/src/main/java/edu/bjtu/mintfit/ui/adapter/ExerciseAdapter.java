package edu.bjtu.mintfit.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.bjtu.mintfit.R;
import edu.bjtu.mintfit.data.entity.Exercises;
import edu.bjtu.mintfit.ui.activity.DetailActivity;

/**
 *
 * author : algorirhy
 * e-mail : algorirhy@gmail.com
 * time   : 2018/10/09
 * desc   :
 * version: 1.0
 */

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder> {
    private Context mContext;
    private List<Exercises> mExerciseList;

    public ExerciseAdapter(List<Exercises> exerciseList) {
        this.mExerciseList = exerciseList;
    }

    static class ExerciseHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView exeImage;
        TextView exeName;

        public ExerciseHolder(View view) {
            super(view);
            cardView = (CardView) view;
            exeImage = (ImageView) view.findViewById(R.id.exercise_image);
            exeName = (TextView) view.findViewById(R.id.exercise_name);
        }
    }

    @Override
    public ExerciseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_exercise, parent, false);
        final ExerciseHolder holder = new ExerciseHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if(position==0){
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final ExerciseHolder holder, int position) {
        Exercises exercises = mExerciseList.get(position);
        holder.exeName.setText(exercises.getExe_name());
        Glide.with(mContext).load(exercises.getExe_image()).into(holder.exeImage);
    }

    @Override
    public int getItemCount() {
        return mExerciseList.size();
    }
}
