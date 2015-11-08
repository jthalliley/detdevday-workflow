# detdevday-workflow project

This is an example of traversing and displaying a dynamic workflow, gathering answers, and saving the result.

Workflows are dynamically fetched from a server, and displayed dynamically on the Android tablet screen as the user answers questions.  Once completed, the user's responses are sent back to the server for storage.

Presented at DetroitDevDay, November 14, 2015, by uhhhh me, Tom Halliley.


## Developer Notes

This is an Android project, built upon API 19, using gradle.

### Build with:
`   gradle clean installDebug`

(make sure you have an attached device first)

### You'll need to:

1. Install the [JDK](http://www.oracle.com/technetwork/java/javase/downloads/java-archive-downloads-javase7-521261.html#jdk-7u80-oth-JPR) - Java Development Kit 7.x (not 8!) 

2. Install the [Android SDK](http://dl.google.com/android/android-sdk_r24.3.3-macosx.zip) into, for example, `/usr/local`<br/>

`   export ANDROID_HOME=/usr/local/android-sdk-macosx`<br/>
`   export PATH=$ANDROID_HOME/tools:$ANDROID_HOME/platform_tools:$PATH`





 
