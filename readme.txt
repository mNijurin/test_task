
For facebook sdk module import:

Select File | New Module
Choose "Library Module" on the left-side panel.
Fill in the required information:
Module name: "facebook"
Content root: Choose facebook android SDK from project

Module file location: same as Content root

Package name: com.facebook.android
Click finish button
Go to File | Project Structure then select the Modules pages at the left panel

Select facebook module and add jar android-support-v4.jar from project libs to the Dependency.
Select your application module and add jar android-support-v4.jar from project libs to the Dependency.

Select your original android project and add a new Module Dependency,
you will see the facebook module we created above just shown for selection. Select it.
Click the OK button. Then you got the facebook android SDK worked flawlessly in you android application.