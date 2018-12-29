package edu.bjtu.mintfit.ui.fragment;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.bjtu.mintfit.R;
import edu.bjtu.mintfit.data.database.CacheDatabase;
import edu.bjtu.mintfit.data.entity.ExerciseEntity;
import edu.bjtu.mintfit.data.entity.Exercises;
import edu.bjtu.mintfit.data.entity.TrendEntity;
import edu.bjtu.mintfit.data.entity.Trends;
import edu.bjtu.mintfit.ui.adapter.ExerciseAdapter;

/**
 * author : ByteSpider
 * e-mail : algorirhy@gmail.com
 * time   : 2018/10/05
 * desc   : 锻炼页面Fragment
 * version: 1.0
 */

public class ExerciseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private MZBannerView mMZBanner;
//    public static final String TAG = "MZModeBannerFragment";
//    public static final int[] BANNER = new int[]{R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4, R.drawable.banner5};

    private OnFragmentInteractionListener mListener;

    private List<Exercises> exercisesList = new ArrayList<>();

    public ExerciseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExerciseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExerciseFragment newInstance(String param1, String param2) {
        ExerciseFragment fragment = new ExerciseFragment();
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_exercise, container, false);

        exercisesList.clear();
        if (getActivity() != null) {
            CacheDatabase cdb = Room.databaseBuilder(getActivity(), CacheDatabase.class, "bohe").allowMainThreadQueries().build();
            ExerciseEntity[] exerciseEntities = cdb.exerciseDao().loadAllExercise();
            System.out.println("length:" + exerciseEntities.length);
            for (ExerciseEntity exerciseEntity : exerciseEntities) {
                exercisesList.add(new Exercises(exerciseEntity.getExe_name(), exerciseEntity.getExe_image()));
            }
            cdb.close();
        }

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_exercise);
        GridLayoutManager layoutManager = new GridLayoutManager(rootView.getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ExerciseAdapter(exercisesList));
//        initView(rootView);//banner_view
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        mMZBanner.pause();//暂停轮播
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mMZBanner.start();//开始轮播
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
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
        void onFragmentInteraction(Uri uri);
    }

    //Banner
//    private void initView(View view) {
//        mMZBanner = (MZBannerView) view.findViewById(R.id.banner);
//        mMZBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
//            @Override
//            public void onPageClick(View view, int position) {
//                Toast.makeText(getContext(), "click page:" + position, Toast.LENGTH_LONG).show();
//            }
//        });
//        mMZBanner.addPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                //Log.e(TAG, "----->addPageChangeLisnter:" + position + "positionOffset:" + positionOffset + "positionOffsetPixels:" + positionOffsetPixels);
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                //Log.e(TAG, "addPageChangeLisnter:" + position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//        List<Integer> bannerList = new ArrayList<>();
//        for (int i = 0; i < BANNER.length; i++) {
//            bannerList.add(BANNER[i]);
//        }
//        mMZBanner.setIndicatorVisible(false);
//        mMZBanner.setPages(bannerList, new MZHolderCreator<BannerViewHolder>() {
//            @Override
//            public BannerViewHolder createViewHolder() {
//                return new BannerViewHolder();
//            }
//        });
//    }
}
