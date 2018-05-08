package com.qth.util;

import java.util.Comparator;

public class AccountLogRateChangeComparator implements Comparator<AccountLogRateChangeComparatorHelper> {


    @Override
    public int compare(AccountLogRateChangeComparatorHelper o1, AccountLogRateChangeComparatorHelper o2) {
        return o1.compareDateElement().compareTo(o2.compareDateElement());
    }
}
