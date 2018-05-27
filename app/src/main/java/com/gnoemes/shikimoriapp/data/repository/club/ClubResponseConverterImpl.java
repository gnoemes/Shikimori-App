package com.gnoemes.shikimoriapp.data.repository.club;

import com.gnoemes.shikimoriapp.entity.app.domain.ClubPolicy;
import com.gnoemes.shikimoriapp.entity.club.data.ClubResponse;
import com.gnoemes.shikimoriapp.entity.club.domain.Club;

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

    private Club convertResponse(ClubResponse response) {
        return new Club(response.getId(),
                response.getName(),
                response.getClubImageResponse().getX96(),
                response.getClubImageResponse().getX73(),
                response.isCensored(),
                convertPolicy(response.getJoinPolicy()),
                convertPolicy(response.getCommentPolicy()));
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
