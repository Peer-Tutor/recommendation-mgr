package com.peertutor.TutorMgr.util.model.viewmodel.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AccountRegistrationReq {
    @NotNull
    @NotEmpty
    public String name;

    @NotNull
    @NotEmpty
    public String password;

    @NotNull
    @NotEmpty
    public String usertype;
}
