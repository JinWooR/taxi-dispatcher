package com.taxidispatcher;

import com.taxidispatcher.modules.dispatcher.adapter.web.dto.request.WriteDispatchRequest;
import com.taxidispatcher.shared.security.AccountPrincipal;

import com.taxidispatcher.modules.account.adapter.web.controller.*;
import com.taxidispatcher.modules.account.adapter.web.dto.request.*;

import com.taxidispatcher.modules.dispatcher.adapter.web.controller.*;

import com.taxidispatcher.modules.driver.adapter.web.controller.*;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.*;

import com.taxidispatcher.modules.user.adapter.web.controller.*;
import com.taxidispatcher.modules.user.adapter.web.dto.request.*;

import java.util.UUID;

public class ApiUrls {

    public static class Account {
        /** {@link AuthController#register(RegisterBasicRequest)} */
        public static final String REGISTER = "/auth/register";
        
        /** {@link AuthController#login(PasswordLoginRequest)} */
        public static final String LOGIN_BASIC = "/auth/login";
        
        /** {@link AuthController#loginUser(PasswordLoginRequest)} */
        public static final String LOGIN_BASIC_USER = "/auth/login/user";
        
        /** {@link AuthController#loginDriver(PasswordLoginRequest)} */
        public static final String LOGIN_BASIC_DRIVER = "/auth/login/driver";
    }

    public static class Dispatch {
        public static class Driver {
        }

        public static class User {
            /** {@link UserDispatcherController#info(AccountPrincipal, UUID)} */
            public static final String INFO = "/users/me/dispatches/{dispatchId}";

            /** {@link UserDispatcherController#writeDispatch(AccountPrincipal, WriteDispatchRequest)} */
            public static final String WRITE = "/users/me/dispatches/write";

            /** {@link UserDispatcherController#cancel(AccountPrincipal, UUID)} */
            public static final String CANCEL = "/users/me/dispatches/{dispatchId}/cancel";
        }
    }

    public static class Driver {
        /** {@link DriverController#register(AccountPrincipal, RegisterDriverRequest)} */
        public static final String REGISTER = "/drivers/register";

        /** {@link DriverController#changeTaxi(AccountPrincipal, UpdateDriverTaxiRequest)} */
        public static final String UPDATE_TAXI = "/drivers/taxi";

        /** {@link DriverController#changeActiveStatus(AccountPrincipal, UpdateDriverActiveStatusRequest)} */
        public static final String UPDATE_ACTIVE_STATUS = "/drivers/active-status";
        
        /** {@link DriverController#updateGeo(AccountPrincipal, UpdateDriverGeoRequest)} */
        public static final String UPDATE_GEO = "/drivers/geo";

        /** {@link DriverController#delete(AccountPrincipal)} */
        public static final String DELETE = "/drivers/delete";

        public static class Internal {
            /** {@link InternalDriverController#accountId(String)} */
            public static final String ACCOUNT = "/internal/drivers/account/{id}";

            /** {@link InternalDriverController#nearbyGeo(InternalDriverNearbyGeoRequest)} */
            public static final String NEARBY_GEO = "/internal/drivers/nearby-geo";
        }
    }

    public static class User {
        /** {@link UserController#register(AccountPrincipal, RegisterUserRequest)} */
        public static final String REGISTER = "/users/register";

        /** {@link UserController#changeName(AccountPrincipal, UpdateUserNameRequest)} */
        public static final String UPDATE_NAME = "/users/name";
        
        /** {@link UserController#changeAddress(AccountPrincipal, UpdateUserAddressRequest)} */
        public static final String UPDATE_ADDRESS = "/users/address";
        
        /** {@link UserController#delete(AccountPrincipal)} */
        public static final String DELETE = "/users/delete";
    }
}
