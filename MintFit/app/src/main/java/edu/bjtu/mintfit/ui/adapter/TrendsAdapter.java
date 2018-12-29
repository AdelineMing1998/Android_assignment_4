package edu.bjtu.mintfit.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.bjtu.mintfit.R;
import edu.bjtu.mintfit.data.entity.Trends;

/**
 * author : algorirhy
 * e-mail : algorirhy@gmail.com
 * time   : 2018/10/05
 * desc   : 动态页面Adapter
 * version: 1.0
 */

public class TrendsAdapter extends RecyclerView.Adapter<TrendsAdapter.TrendsHolder> {
    private Context mContext;
    private List<Trends> mExerciseList;

    public TrendsAdapter(List<Trends> exerciseList) {
        this.mExerciseList = exerciseList;
    }

    static class TrendsHolder extends RecyclerView.ViewHolder {
        CardView mRootView;
        CircleImageView head_portrait;
        ImageView image;
        TextView name;
        TextView content;

        public TrendsHolder(View view) {
            super(view);
            mRootView = (CardView) view;
            image = (ImageView) view.findViewById(R.id.trends_image);
            head_portrait = (CircleImageView) view.findViewById(R.id.trneds_head_portrait);
            name = (TextView) view.findViewById(R.id.trends__name);
            content = (TextView) view.findViewById(R.id.trends_content);
        }
    }

    @Override
    public TrendsHolder onCreateViewHolder(ViewGroup parent, int pos) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trend, parent, false);
        return new TrendsHolder(view);
    }

    @Override
    public void onBindViewHolder(TrendsHolder holder, int position) {
        Trends exercise = mExerciseList.get(position);
        Glide.with(mContext).load(exercise.getTrends_image()).into(holder.image);
        Glide.with(mContext).load(exercise.getTrends_head_portrait()).into(holder.head_portrait);
        holder.name.setText(exercise.getTrends_name());
        holder.content.setText(exercise.getTrends_content());
    }

    @Override
    public int getItemCount() {
        return mExerciseList.size();
    }
}
