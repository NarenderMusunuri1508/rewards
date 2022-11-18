package com.rewards.repository;

import com.rewards.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {

    Reward getRewardByName(String rewardName);
}
