package edu.bjtu.mintfit.ui.fragment;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import edu.bjtu.mintfit.R;
import edu.bjtu.mintfit.data.database.CacheDatabase;
import edu.bjtu.mintfit.data.entity.TrendEntity;
import edu.bjtu.mintfit.data.entity.Trends;
import edu.bjtu.mintfit.ui.adapter.TrendsAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrendsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrendsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * author : algorirhy
 * e-mail : algorirhy@gmail.com
 * time   : 2018/10/05
 * desc   : 动态页面Fragment
 * version: 1.0
 */

public class TrendsFragment extends Fragment {
    //     TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private MZBannerView mMZBanner;
    public static final int[] BANNER = new int[]{R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4, R.drawable.banner5};

    private List<Trends> trendsList = new ArrayList<>();

    private Trends[] trendsArr = {
            new Trends("薄荷小助手", "我还能跑10KM！！！", R.drawable.head_portrait, R.drawable.football),
            new Trends("薄荷小助手", "我还能跑20KM！！！", R.drawable.head_portrait, R.drawable.basketball),
            new Trends("薄荷小助手", "我还能跑30KM！！！", R.drawable.head_portrait, R.drawable.table_tennis),
            new Trends("薄荷小助手", "我还能跑10KM！！！", R.drawable.head_portrait, R.drawable.football),
            new Trends("薄荷小助手", "我还能跑20KM！！！", R.drawable.head_portrait, R.drawable.basketball),
            new Trends("薄荷小助手", "我还能跑30KM！！！", R.drawable.head_portrait, R.drawable.table_tennis)
    };


    public TrendsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrendsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrendsFragment newInstance(String param1, String param2) {
        TrendsFragment fragment = new TrendsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_trends, container, false);

        //TODO 向服务端发送请求，获取Trends列表, 如get_trends
        trendsList.clear();
        //trendsArr为假设的从服务端接收到的数据

        if (getActivity() != null) {
            CacheDatabase cdb = Room.databaseBuilder(getActivity(), CacheDatabase.class, "bohe").allowMainThreadQueries().build();
            TrendEntity[] trendEntities = cdb.trendDao().loadAllTrends();
            for (TrendEntity trendEntity : trendEntities) {
                trendsList.add(new Trends(trendEntity.getTrends_name(), trendEntity.getTrends_content(), trendEntity.getTrends_head_portrait(), trendEntity.getTrends_image()));
            }
            cdb.close();
        }

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new TrendsAdapter(trendsList));
        initView(rootView);//banner_view
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMZBanner.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();//开始轮播
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_banner, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }

    //Banner
    private void initView(View view) {
        mMZBanner = (MZBannerView) view.findViewById(R.id.banner);
        mMZBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Toast.makeText(getContext(), "click page:" + position, Toast.LENGTH_LONG).show();
            }
        });
        mMZBanner.addPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.e(TAG, "----->addPageChangeLisnter:" + position + "positionOffset:" + positionOffset + "positionOffsetPixels:" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                //Log.e(TAG, "addPageChangeLisnter:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        List<Integer> bannerList = new ArrayList<>();
        for (int banner : BANNER) {
            bannerList.add(banner);
        }
        mMZBanner.setIndicatorVisible(false);
        mMZBanner.setPages(bannerList, new MZHolderCreator<TrendsFragment.BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
    }
}
