package io.github.lucasbarbosarocha.quarkussocial.core.domain.follower;

import lombok.Data;

import java.util.List;

@Data
public class FollowersPerUserResponse {
    private Integer followersCount;
    private List<FollowerResponse> content;
}
