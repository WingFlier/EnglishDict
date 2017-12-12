package com.cs_final.englishdict.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cs_final.com.englishdict.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment
{
    ArrayList<HashMap<String, String>> goodWords;
    ProgressBar pBar;
    Random random;
    ArrayList<HashMap<String, String>> accurateWord;
    String timer = null;
    ArrayList<HashMap<String, String>> words;
    public static List<String> arrayOfWrongWords;

    public QuizFragment()
    {
        // Required empty public constructor
    }

    public static QuizFragment newInstance(String param1, String param2)
    {
        return new QuizFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        goodWords = new ArrayList<>();
        pBar = getView().findViewById(R.id.progress_bar);
        arrayOfWrongWords = new ArrayList<>();
        getView().findViewById(R.id.btn_start_quiz).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                goodWords.clear();
                showProgressBar();
                startQuiz();
            }
        });
    }

    public void showProgressBar()
    {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        pBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar()
    {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        pBar.setVisibility(View.GONE);
    }

    public void startQuiz()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("words").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                try
                {
                    if (dataSnapshot.getValue() != null)
                    {
                        try
                        {
                            accurateWord = new ArrayList<>();
                            random = new Random();
                            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            String wordsCount = defaultSharedPreferences.getString("words", "4");
                            String level = defaultSharedPreferences.getString("level", "beginner");
                            String category = defaultSharedPreferences.getString("category", "communication");
                            if (((CheckBox) getView().findViewById(R.id.checkbox_use_timer)).isChecked())
                                timer = defaultSharedPreferences.getString("timer", "5");


                            words = (ArrayList<HashMap<String, String>>) dataSnapshot.getValue();
                            setWrongWords();
                            for (int i = 0; i < words.size(); i++)
                            {
                                HashMap<String, String> word = words.get(i);
                                if (word.get("level").equals(level) && word.get("category").equals(category))
                                {
                                    accurateWord.add(word);
                                }
                            }
                            filterWords(wordsCount, Integer.parseInt(wordsCount));
                            if (goodWords.isEmpty())
                                return;
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("words", goodWords);
                            bundle.putString("timer", timer);
                            //Toast.makeText(getContext(), bundle.toString(), Toast.LENGTH_SHORT).show();

                            hideProgressBar();
                            ViewPagerFragment viewPagerFragment =
                                    ViewPagerFragment.newInstance(null, null);
                            viewPagerFragment.setArguments(bundle);
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_frame, viewPagerFragment, null)
                                    .commit();
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    } else
                    {
                        Toast.makeText(getContext(), "no Data", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            public int wordCountMatchingCategories(ArrayList<HashMap<String, String>> words, String level, String category)
            {
                int count = 0;
                for (HashMap<String, String> word : words)
                {
                    if (word.get("level").equals(level) && word.get("category").equals(category))
                    {
                        ++count;
                    }
                }
                return count;
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(getActivity(), "error " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setWrongWords()
    {
        arrayOfWrongWords.clear();
        for (int i = 0; i < words.size(); i++)
        {
            arrayOfWrongWords.add(words.get(i).get("translation"));
        }
    }

    private void filterWords(String wordsCount, int leftWordsCount)
    {
        if (accurateWord.isEmpty())
        {
            Toast.makeText(getContext(), "Sorry, not enough words", Toast.LENGTH_SHORT).show();
            hideProgressBar();
            return;
        }

        for (int i = 0; i < leftWordsCount; i++)
        {
            int index = random.nextInt(accurateWord.size() - 1);
            if (!goodWords.contains(accurateWord.get(index)))
                goodWords.add(accurateWord.get(index));
        }
        if (goodWords.size() < Integer.parseInt(wordsCount))
            filterWords(wordsCount, Integer.parseInt(wordsCount) - goodWords.size());
    }
}