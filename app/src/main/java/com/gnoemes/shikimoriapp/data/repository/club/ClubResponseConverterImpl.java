package com.gnoemes.shikimoriapp.data.repository.club;

import com.gnoemes.shikimoriapp.entity.app.domain.ClubPolicy;
import com.gnoemes.shikimoriapp.entity.club.data.ClubImageResponse;
import com.gnoemes.shikimoriapp.entity.club.data.ClubResponse;
import com.gnoemes.shikimoriapp.entity.club.domain.Club;
import com.gnoemes.shikimoriapp.entity.club.domain.ClubImage;
import com.gnoemes.shikimoriapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ClubResponseConverterImpl implements ClubResponseConverter {

    @Inject
    public ClubResponseConverterImpl() {
    }

    @Override
    public List<Club> apply(List<ClubResponse> clubResponses) {
        List<Club> clubs = new ArrayList<>();

        for (ClubResponse response : clubResponses) {
            clubs.add(convertResponse(response));
        }

        return clubs;
    }

    @Override
    public Club convertResponse(ClubResponse response) {
        if (response == null) {
            return null;
        }

        return new Club(response.getId(),
                response.getName(),
                converImage(response.getClubImageResponse()),
                response.isCensored(),
                convertPolicy(response.getJoinPolicy()),
                convertPolicy(response.getCommentPolicy()));
    }

    //TODO fix images
    private ClubImage converImage(ClubImageResponse response) {
        return new ClubImage(
                Utils.appendHostIfNeed(response.getOriginal()),
                Utils.appendHostIfNeed(response.getMain()),
                Utils.appendHostIfNeed(response.getX96()),
                Utils.appendHostIfNeed(response.getX73()),
                Utils.appendHostIfNeed(response.getX48())
        );
    }

    private ClubPolicy convertPolicy(String policyString) {

        for (ClubPolicy clubPolicy : ClubPolicy.values()) {
            if (clubPolicy.isEqualType(policyString)) {
                return clubPolicy;
            }
        }

        return null;
    }
}
