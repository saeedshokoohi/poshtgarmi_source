package com.eyeson.poshtgarmi.repository.extended;

import com.eyeson.poshtgarmi.domain.Fund;
import com.eyeson.poshtgarmi.domain.LoanDuration;
import com.eyeson.poshtgarmi.domain.LoanDurationIteration;

import javax.inject.Named;
import java.util.List;

/**
 * Created by majid on 10/13/2016.
 */
@Named
public interface FundExtendedRepository extends BaseExtendedRepository<Fund> {
    List<Fund> getFundByMemberId(Long memberId);
    List<LoanDuration> getLoanDurationByFundId(Long fundId);

    int getFundCreditByFundId(Long id);

    boolean hasMemberPaid(Long id, Long id1);

    List<LoanDurationIteration> getCurrentIterationByDuration(Long durationid);
}
