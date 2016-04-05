package com.gmail.maloef.rememberme.wordlist;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.maloef.rememberme.AbstractRememberMeFragment;
import com.gmail.maloef.rememberme.R;
import com.gmail.maloef.rememberme.RememberMeApplication;
import com.gmail.maloef.rememberme.persistence.WordColumns;
import com.gmail.maloef.rememberme.persistence.WordCursor;
import com.gmail.maloef.rememberme.persistence.WordRepository;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

@FragmentWithArgs
public class WordListFragment extends AbstractRememberMeFragment {

    @Inject WordRepository wordRepository;

    @Bind(R.id.word_list_view) RecyclerView wordsView;

    @Arg int boxId;
    @Arg int compartment;

    private WordCursor wordCursor;
    private WordAdapter wordAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RememberMeApplication.injector().inject(this);

        wordCursor = wordRepository.getWordCursor(boxId, compartment, WordColumns.FOREIGN_WORD);
    }

    @Override
    public void onDestroy() {
        wordCursor.close();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_list, container, false);

        ButterKnife.bind(this, view);
        RememberMeApplication.injector().inject(this);

        wordAdapter = new WordAdapter(wordCursor);
        wordsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        wordsView.setAdapter(wordAdapter);

        return view;
    }
}