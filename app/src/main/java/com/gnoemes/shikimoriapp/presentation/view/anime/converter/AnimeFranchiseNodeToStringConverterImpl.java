package com.gnoemes.shikimoriapp.presentation.view.anime.converter;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeFranchiseNode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AnimeFranchiseNodeToStringConverterImpl implements AnimeFranchiseNodeToStringConverter {

    private Context context;

    @Inject
    public AnimeFranchiseNodeToStringConverterImpl(Context context) {
        this.context = context;
    }

    @Override
    public List<String> convertList(List<AnimeFranchiseNode> nodes) {
        List<String> items = new ArrayList<>();

        if (nodes != null && !nodes.isEmpty()) {
            int i = 0;
            for (AnimeFranchiseNode node : nodes) {
                boolean hasYear = node.getYear() != null;
                i++;
                items.add("#" +
                        i +
                        ' ' +
                        node.getName() +
                        (hasYear ? ',' : "") +
                        (hasYear ? ' ' : "") +
                        (hasYear ? node.getYear() : ""));
            }
        } else {
            items.add(context.getString(R.string.no_chronology));
        }

        return items;
    }

}
