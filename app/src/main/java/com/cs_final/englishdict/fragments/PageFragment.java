package com.cs_final.englishdict.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cs_final.englishdict.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cs_final.com.englishdict.R;

import static com.cs_final.englishdict.fragments.ViewPagerFragment.viewPagerSlider;


public class PageFragment extends Fragment implements View.OnClickListener
{
    TextView optionA, optionB, optionC, optionD;
    List<TextView> txt;
    int correctWordId;
    MediaPlayer mPlayerCorrect;
    MediaPlayer mPlayerWrong;
    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor editor;

    public static PageFragment newInstance(int page)
    {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("page", page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment, null);

        mySharedPreferences = getActivity().getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        editor = mySharedPreferences.edit();

        mPlayerCorrect = MediaPlayer.create(getContext(), R.raw.correct);
        mPlayerWrong = MediaPlayer.create(getContext(), R.raw.wrong);

        optionA = view.findViewById(R.id.txt_option_1);
        optionB = view.findViewById(R.id.txt_option_2);
        optionC = view.findViewById(R.id.txt_option_3);
        optionD = view.findViewById(R.id.txt_option_4);

        txt = new ArrayList<>();
        txt.add(optionA);
        txt.add(optionB);
        txt.add(optionC);
        txt.add(optionD);

        optionA.setOnClickListener(this);
        optionB.setOnClickListener(this);
        optionC.setOnClickListener(this);
        optionD.setOnClickListener(this);

        TextView tvPage = view.findViewById(R.id.tvPage);
        HashMap<String, String> pageData = ViewPagerFragment.words.get(getArguments().getInt("page"));
        tvPage.setText(pageData.get("word"));
        tvPage.setBackgroundColor(getResources().getColor(R.color.white));

        setWords(pageData);
        return view;
    }

    private void setWords(HashMap<String, String> pageData)
    {
        List<String> arrayOfWrongWords = new ArrayList<>(QuizFragment.arrayOfWrongWords);
        Random random = new Random();
        String correctWord = pageData.get("translation");
        arrayOfWrongWords.remove(correctWord);

        String optionBWord = arrayOfWrongWords.get(random.nextInt(arrayOfWrongWords.size()));
        String optionCWord = arrayOfWrongWords.get(random.nextInt(arrayOfWrongWords.size()));
        String optionDWord = arrayOfWrongWords.get(random.nextInt(arrayOfWrongWords.size()));

        TextView textView = txt.get(random.nextInt(txt.size()));
        textView.append(correctWord);
        correctWordId = textView.getId();
        txt.remove(textView);

        TextView textView1 = txt.get(random.nextInt(txt.size()));
        textView1.append(optionBWord);
        arrayOfWrongWords.remove(optionBWord);
        txt.remove(textView1);

        TextView textView2 = txt.get(random.nextInt(txt.size()));
        textView2.append(optionCWord);
        arrayOfWrongWords.remove(optionCWord);
        txt.remove(textView2);

        TextView textView3 = txt.get(random.nextInt(txt.size()));
        textView3.append(optionDWord);
        arrayOfWrongWords.remove(optionDWord);
        txt.remove(textView3);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        onDestroy();
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == correctWordId)
        {
            mPlayerCorrect.start();
            Toast.makeText(getContext(), "good", Toast.LENGTH_SHORT).show();
            int correct = mySharedPreferences.getInt("correct", 0);
            editor.putInt("correct", ++correct);
        } else
        {
            mPlayerWrong.start();
            Toast.makeText(getContext(), "wrong", Toast.LENGTH_SHORT).show();
            int wrong = mySharedPreferences.getInt("wrong", 0);
            editor.putInt("wrong", ++wrong);
        }
        editor.apply();
        if (ViewPagerFragment.words.size() - 1 == ViewPagerFragment.pager.getCurrentItem())
        {
            getParentFragment().getFragmentManager().popBackStackImmediate();
        }
        viewPagerSlider.slide();
//        switch (view.getId())
//        {
//            case R.id.txt_option_1:
//                Toast.makeText(getContext(), optionA.getText(), Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.txt_option_2:
//                Toast.makeText(getContext(), optionB.getText(), Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.txt_option_3:
//                Toast.makeText(getContext(), optionC.getText(), Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.txt_option_4:
//                Toast.makeText(getContext(), optionD.getText(), Toast.LENGTH_SHORT).show();
//                break;
//        }
    }
}