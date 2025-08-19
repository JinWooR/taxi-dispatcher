package com.taxidispatcher.modules.user.application.port.out;

public interface AddressInfoSearcher {
    AddressInfo search(String address);

    record AddressInfo(
            String sd,
            String sgg,
            String emd
    ) {
    }
}
