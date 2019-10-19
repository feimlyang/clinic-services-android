package com.example.a2019_seg2105_project.ui.clinic.register;

import androidx.annotation.Nullable;

/**
 * RegisterFormState stores data validation state of the registration form.
 * It display error message if the data appears on UI is invalid.
 */
class RegisterFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer passwordVerificationError;
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer firstNameError;
    @Nullable
    private Integer lastNameError;
    @Nullable
    private boolean isDataValid;

    RegisterFormState(@Nullable Integer usernameError, @Nullable Integer passwordError,
                      @Nullable Integer firstNameError, @Nullable Integer lastNameError,
                      @Nullable Integer emailError, @Nullable Integer passwordVerificationError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.firstNameError = firstNameError;
        this.lastNameError = lastNameError;
        this.emailError = emailError;
        this.passwordVerificationError = passwordVerificationError;
        this.isDataValid = false;
    }

    RegisterFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.firstNameError = null;
        this.lastNameError = null;
        this.emailError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }
    @Nullable
    Integer getPasswordVerificationError(){ return passwordVerificationError;}
    Integer getEmailError() {
        return emailError;
    }
    @Nullable
    Integer getFirstNameError()
    {
        return firstNameError;
    }
    @Nullable
    Integer getLastNameError(){ return lastNameError;}

    boolean isDataValid() {
        return isDataValid;
    }
}
