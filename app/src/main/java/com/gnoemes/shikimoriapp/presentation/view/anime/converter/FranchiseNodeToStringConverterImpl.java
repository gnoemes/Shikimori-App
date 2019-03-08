package com.gnoemes.shikimoriapp.presentation.view.anime.converter;

import android.content.Context;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.domain.FranchiseNode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FranchiseNodeToStringConverterImpl implements FranchiseNodeToStringConverter {

    private Context context;

    @Inject
    public FranchiseNodeToStringConverterImpl(Context context) {
        this.context = context;
    }

    @Override
    public List<String> convertList(List<FranchiseNode> nodes) {
        List<String> items = new ArrayList<>();

        if (nodes != null && !nodes.isEmpty()) {
            int i = 0;
            for (FranchiseNode node : nodes) {
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
