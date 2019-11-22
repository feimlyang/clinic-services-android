# Android Project: Walk in Clinics Services App

### Group10 Members:
> * Manlin Guo 6602848
> * Tong Zhao 300037013
> * Daniel Xu 300030558
> * Wen Bin Pang 8882970 (dropped)

[Our Repository](https://github.com/SEG2105-uottawa/seg2x05-project-f19-10.git)  
[Our Firebase](https://console.firebase.google.com/u/0/project/clinicservice-f449a/database/clinicservice-f449a/data)  
[Our Report](https://docs.google.com/document/d/1bB7Uej0rPMWGUeHd7TDuLa58uLVkGKgfUs-umDlaNGA/edit#heading=h.maef8e7hh3pg)

##### Employee Register Access Code
> 1207049  
##### Predefined Administrator Login Information
> username: admin  
> password: 5T5ptQ   
##### Predefined Employee Login Information 
> username: imclinic  
> password: test123  
##### Predefined Patient Login Information
> username: imsick  
> password: test123  


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
* Can delete Employee and Patient Accounts by username.
* Can add, edit and delete Services.
* Service is stored by key of serviceName, and has three attributes: category, subcategory, rolePerforming.  **CLICK** on each service in **service list** to see attribute information.
* Integrated with CircleCI.
* Developed 5 unit tests:

| Unit Test 		| Constraints | 
| :-------------:	| :------------------------------------| 
| PasswordLengthTest |  The length of password should not be bigger than 16 and smaller than 5. |
| UsernameLengthTest |  The length of username should not be bigger than 10. Username should not be null and should not have space. |  
| EmailValidatorTest |  Email should not be null, and it should not ignore "@" and ".com". | 
| SamePasswordTest |  The password re-entered should be the same than previous one. |
| UsernameFormatTest |  Username should not have symbol such as ".!#@()" |


## DELIVERABLE 3
#### Features:(for employee user)
* Clinic profile shows the current information, includes enter address, phone number, name of the clinic, insurance type and payment method accepted. Click EDIT then CONFIRM to update the profile. 
* Can add avaliable services created by admin from a list of services. Click each element to ADD. 
* Can delete services that currently offered in the clinic. Click each element to DELETE. 
* Can specify clinic working hours in a calendar view. 
* Can see the working hours by select a date. Click EDIT then CONFIRM to update the working hours. 
* Developed 2 unit tests: 

| Unit Test 		| Constraints | 
| :-------------:	| :------------------------------------| 
| RegisteredNameLengthTest |  The length of username should not be bigger than 10 when register. Username should not be null and should not have space when register. The length of username can be exactly 10 when register. |
| PasswordWithinRangeTest |  The length of password should not be bigger than 16 and smaller than 5. The length of password can be exactly 5 or 16. |  


## DELIVERABLE 4
#### Features:(for patient user)
* Can search for a walk in clinic by address, working hours, type of services offered.
* Can book an appointment.
* Can view the booking appointed information and the waiting hours.
* Can check in the appointment.
* Can rate a clinic by a score from 1-5 and write commemt.
* Build CircleCI button in the Github
* Developed 10 unit tests that is **only relevant the D4**:

|  #    | Unit Test 		| Constraints | 
| :---: | :-------------:	| :------------------------------------| 
| 1 |  |  |
| 2 |  |  | 
| 3 |  |  |
| 4 |  |  | 
| 5 |  |  |
| 6 |  |  | 
| 7 |  |  |
| 8 |  |  | 
| 9 |  |  |
| 10|  |  | 

### CircleCI
[![CircleCI](https://circleci.com/gh/SEG2105-uottawa/seg2x05-project-f19-10/tree/master.svg?style=svg&circle-token=dfc8cf14869d1c662016f9038e259c3505c4074a)](https://circleci.com/gh/SEG2105-uottawa/seg2x05-project-f19-10/tree/master)
