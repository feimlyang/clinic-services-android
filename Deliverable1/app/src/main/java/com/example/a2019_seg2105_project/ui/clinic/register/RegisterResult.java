package com.example.a2019_seg2105_project.ui.clinic.register;

import androidx.annotation.Nullable;


/**
 * Authentication result : success (user details) or error message.
 */
class RegisterResult {
    @Nullable
    private Integer returnCode;

    RegisterResult(@Nullable Integer returnCode) {
        this.returnCode = returnCode;
    }
    @Nullable
    Integer getReturnCode() {
        return returnCode;
    }
}
