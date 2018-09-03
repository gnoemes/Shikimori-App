package com.gnoemes.shikimoriapp.presentation.view.search.filter;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeType;
import com.gnoemes.shikimoriapp.entity.anime.domain.Genre;
import com.gnoemes.shikimoriapp.entity.anime.domain.Status;
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem;
import com.gnoemes.shikimoriapp.entity.search.domain.SearchConstants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FilterResourceConverterImpl implements FilterResourceConverter {

    @Inject
    public FilterResourceConverterImpl() {
    }

    @Override
    public List<FilterItem> convertFrom(List<String> values, List<String> names, Enum[] type) {
        List<FilterItem> itemList = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            String localizedName = names.get(i);
            String value = values.get(i);
            itemList.add(new FilterItem(processAction(type), processValue(value, type), localizedName));
        }
        return itemList;
    }

    private String processAction(Enum[] type) {
        if (isGenre(type)) {
            return SearchConstants.GENRE;
        } else if (isType(type)) {
            return SearchConstants.TYPE;
        } else if (isStatus(type)) {
            return SearchConstants.STATUS;
        } else if (isSeason(type)) {
            return SearchConstants.SEASON;
        } else if (isOrder(type)) {
            return SearchConstants.ORDER;
        } else if (isDuration(type)) {
            return SearchConstants.DURATION;
        }
        return null;
    }

    private String processValue(String typeName, Enum[] type) {
        if (isGenre(type)) {
            for (Genre genre : Genre.values()) {
                if (genre.equalsName(typeName)) {
                    return genre.getId();
                }
            }
        } else if (isType(type)) {
            for (AnimeType animeType : AnimeType.values()) {
                if (animeType.equalsType(typeName)) {
                    return animeType.toString();
                }
            }
        } else if (isStatus(type)) {
            for (Status status : Status.values()) {
                if (status.equalsStatus(typeName)) {
                    return status.toString();
                }
            }
        } else if (isSeason(type)) {
            for (SearchConstants.SEASONS season : SearchConstants.SEASONS.values()) {
                if (season.equalsSeason(typeName)) {
                    return season.toString();
                }
            }
        } else if (isOrder(type)) {
            for (SearchConstants.ORDER_BY order : SearchConstants.ORDER_BY.values()) {
                if (order.equalsType(typeName)) {
                    return order.toString();
                }
            }
        } else if (isDuration(type)) {
            for (SearchConstants.DURATIONS duration : SearchConstants.DURATIONS.values()) {
                if (duration.equalsDuration(typeName)) {
                    return duration.toString();
                }
            }
        }


        return null;
    }

    private boolean isGenre(Enum[] enums) {
        return enums instanceof Genre[];
    }

    private boolean isType(Enum[] enums) {
        return enums instanceof AnimeType[];
    }

    private boolean isStatus(Enum[] enums) {
        return enums instanceof Status[];
    }

    private boolean isSeason(Enum[] enums) {
        return enums instanceof SearchConstants.SEASONS[];
    }

    private boolean isOrder(Enum[] enums) {
        return enums instanceof SearchConstants.ORDER_BY[];
    }

    private boolean isDuration(Enum[] enums) {
        return enums instanceof SearchConstants.DURATIONS[];
    }

}
