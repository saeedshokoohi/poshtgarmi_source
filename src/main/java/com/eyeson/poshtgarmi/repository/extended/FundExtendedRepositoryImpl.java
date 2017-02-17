package com.eyeson.poshtgarmi.repository.extended;

import com.eyeson.poshtgarmi.domain.Fund;
import com.eyeson.poshtgarmi.domain.LoanDuration;
import com.eyeson.poshtgarmi.domain.Payment;
import com.eyeson.poshtgarmi.domain.enumeration.PaymentType;

import javax.inject.Named;
import java.util.List;

/**
 * Created by majid on 10/13/2016.
 */
@Named
public class FundExtendedRepositoryImpl
    extends BaseExtendedRepositoryImpl<Fund>
    implements FundExtendedRepository

{

    @Override
    public List<Fund> getFundByMemberId(Long memberId) {
        return getEm().createQuery("select f from Fund f join f.members ms where ms.id=:memberId and f.status='ACTIVE'").setParameter("memberId", memberId).getResultList();
    }
    public List<LoanDuration> getLoanDurationByFundId(Long fundId)
    {
        return getEm().createQuery("select ld from LoanDuration ld where ld.fund.id=:fundId and ld.status='ACTIVE'").setParameter("fundId", fundId).getResultList();

    }

    @Override
    public int getFundCreditByFundId(Long id) {
        Integer result=0;
        List<LoanDuration> ds = getLoanDurationByFundId(id);
        if(ds.size()>0) {
            List<Payment> payments = getEm().createQuery("select p from Payment p join p.loanDurationIterations d  where d.id=:durationId").setParameter("durationId", ds.get(0).getId()).getResultList();
            for (Payment p:payments)
            {
                if(p.getAmount()!=null) {
                    if (p.getType() == PaymentType.INCOME)
                        result += p.getAmount();
                    if (p.getType() == PaymentType.OUTCOME)
                        result += p.getAmount();
                }

            }
        }
        return result;
    }
}
