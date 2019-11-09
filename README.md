# Android Project: Walk in Clinics Services App

### Group10 Members:
> * Manlin Guo 6602848
> * Tong Zhao 300037013
> * Daniel Xu 300030558
> * Wen Bin Pang 8882970

[Our Repository](https://github.com/SEG2105-uottawa/seg2x05-project-f19-10.git)

##### Administrator Login Information
> username: admin | password: 5T5ptQ 

## DELIVERABLE 1
#### Features:
* Can register employee account or patient account with choice.
* Add employee authentication on register to increase safety performance.
* Can login with username and password.
* Can see the ‘Welcome screen’ with name and user role after successful authentication.
* Implement 'search' and 'add' functions onto Firebase.
* Passwrods stored as hash values using SHA-256.


#### Important Notes on UI:

* **Registration Access Code (Employee)**

For *security* reasons, we requires user to enter an access code if he/she attempts to be registered as *Employee*. Currently, to simplify testing, the access code is set as **1207049**. Please note that incorrect code will inhibit user from registering as *Employee*.
* **Informaiton validation**

Our criteria of 'valid informaiton' are the following:

| Informaiton 		| Constraints | 
| :-------------:	| :------------------------------------| 
| Username  		|  Should be <= 10 characters. Should not contain symbols. Case insensitive. |
| Names(first and last) |  Should be letters only. |  
| Password      	|  Should be between 5~16 characters. | 
| Password Verification |  Should match password initially entered. |
| Email			|  Should match standard email format. |

Apart from these, duplicated username will **not** be able to be registered in database. An error message will be displayed.
* **Welcome Message**

The welcome message is disigned to be displayed directly on the initial page (containing the login/ register button). After this, user will be send to a main activity page where features of the app is displayed.
In this delivery, for testing purpose, the main activity page is not yet linked to the initial page.

* **Device Independance**

The UI is **NOT**(yet) resizeable. Do *not* run it on small screen device, or some of the feature won' t be displayable.


## DELIVERABLE 2
#### Features:(for admin user)
* Can delete Employee and Patient Accounts (but cannot create/edit account)
* Can add, edit and delete Services.
* Service is stored by key of serviceName, and has three attributes: category, subcategory, rolePerforming.

#### Unit Test:

| Unit Test 		| Constraints | 
| :-------------:	| :------------------------------------| 
| PasswordLengthTest |  The length of password should not be bigger than 16 and smaller than 5. |
| UsernameLengthTest |  The length of username should not be bigger than 10. Username should not be null and should not have space. |  
| EmailValidatorTest |  Email should not be null, and it should not ignore "@" and ".com". | 
| SamePasswordTest |  The password re-entered should be the same than previous one. |
| UsernameFormatTest |  Username should not have symbol such as ".!#@()" |


## DELIVERABLE 3




## DELIVERABLE 4



