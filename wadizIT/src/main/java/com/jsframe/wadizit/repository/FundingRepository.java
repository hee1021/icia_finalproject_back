package com.jsframe.wadizit.repository;

import com.jsframe.wadizit.entity.Funding;
import com.jsframe.wadizit.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FundingRepository extends CrudRepository<Funding, Long> {
    List<Funding> findAllByMemberNum(Member member);
    Page<Funding> findByFundingNumGreaterThanOrderByFundingNumDesc(long fundingNum, Pageable pageable);
}
