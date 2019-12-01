package com.example.a2019_seg2105_project.ui.clinicApp.register;

import androidx.annotation.Nullable;

/**
 * RegisterFormState stores data validation state of the registration form.
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
    @Nullable
    private Integer accountTypeError;
    @Nullable
    private Integer employAccessCodeValid;

    RegisterFormState(@Nullable Integer usernameError, @Nullable Integer passwordError,
                      @Nullable Integer firstNameError, @Nullable Integer lastNameError,
                      @Nullable Integer emailError, @Nullable Integer passwordVerificationError,
                      @Nullable Integer accountTypeError, @Nullable Integer employAccessCodeValid) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.firstNameError = firstNameError;
        this.lastNameError = lastNameError;
        this.emailError = emailError;
        this.passwordVerificationError = passwordVerificationError;
        this.isDataValid = false;
        this.accountTypeError = accountTypeError;
        this.employAccessCodeValid = employAccessCodeValid;
    }

    RegisterFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.firstNameError = null;
        this.lastNameError = null;
        this.emailError = null;
        this.isDataValid = isDataValid;
        this.accountTypeError = null;
        this.employAccessCodeValid = null;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }
    void setUsernameError(Integer error) {
        this.usernameError = error;
        setDataValid(false);
    }
    @Nullable
    Integer getPasswordError() { return passwordError; }
    void setPasswordError(Integer error) {
        this.passwordError = error;
        setDataValid(false);
    }
    @Nullable
    Integer getPasswordVerificationError(){ return passwordVerificationError;}
    void setPasswordVerificationError(Integer error) {
        this.passwordVerificationError = error;
        setDataValid(false);
    }
    Integer getEmailError() {
        return emailError;
    }
    void setEmailError(Integer error) {
        this.emailError = error;
        setDataValid(false);
    }
    @Nullable
    Integer getFirstNameError()
    {
        return firstNameError;
    }
    void setFirstNameError(Integer error) {
        this.firstNameError = error;
        setDataValid(false);
    }
    @Nullable
    Integer getLastNameError(){ return lastNameError;}
    void setLastNameError(Integer error)
    {
        this.lastNameError = error;
        setDataValid(false);
    }
    @Nullable
    boolean isDataValid() {
        return isDataValid;
    }
    void setDataValid(boolean isDataValid)
    {
        this.isDataValid = isDataValid;
    }
    @Nullable
    public Integer getAccountType() { return accountTypeError; }

    public void setAccountTypeError(@Nullable Integer accountTypeError) {
        this.accountTypeError = accountTypeError;
        setDataValid(false);
    }
    @Nullable
    public Integer getEmployAccessCodeError() { return employAccessCodeValid; }

    public void setEmployAccessCodeError(Integer employAccessCodeValidError)
    {
        this.employAccessCodeValid = employAccessCodeValidError;
        setDataValid(false);
    }
    public enum AccountType {
        ADMINISTRATOR,
        EMPLOYEE,
        PATIENT,
        INVALID
    }
}
