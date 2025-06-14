package com.dpa.account.query.api.queries;

import com.dpa.account.query.api.dto.EqualityType;
import com.dpa.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {

    private EqualityType equalityType;
    private double balance;
}
