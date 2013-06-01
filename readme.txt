
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

************

For google play service module import:

First add google-play-services.jar to the libraries.

Then add module google-play-services_lib same as facebook sdk.

If you are using ProGuard, add the following lines in the <project_directory>/proguard-project.txt file
to prevent ProGuard from stripping away required classes:
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

API keys
debug: AIzaSyDM4oQeycF9h69YkTHH88XUAhS_hJMtdOw
release: AIzaSyAZxRS4n_m6UfSWoC9_0Iodi3BtW3OUxlc