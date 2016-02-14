# An-alytics

[![Release](https://img.shields.io/github/release/jitpack/android-example.svg?label=Jitpack)](https://jitpack.io/#dorukkangal/an-alytics)

This is an annotation based Google Analytics aggregator with using AOP to track events and screens.

An-alytics will minimize the boiler plate code you have to write and hence help you keep your code more readable.

# Installation
In your `build.gradle` file add these lines :
```groovy
buildscript {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
    dependencies {
        classpath 'com.github.dorukkangal.analytics:analytics-plugin:1.0.0'
    }
}
```
```groovy
apply plugin: 'com.android.application'
apply plugin: 'com.dorukkangal.analytics'

...

dependencies {
    ...
    compile 'com.github.dorukkangal.analytics:analytics-runtime:1.0.0'
}
```

# Usage
## Initialization
In the `onCreate()` method of your `Application` class, add this line :
```java
AnalyticsManager.getInstance().init(context, R.xml.analytics_config);
```

## Track event
```java
@TrackEvent(category = R.string.category, action = R.string.action, label = R.string.label)
@Override
public void onClick(View v) {
    ...
}
```

## Track screen
```java
@TrackScreen(name = R.string.screen)
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ...
}
```

## Manual track
Sometimes, you cannot use annotations (if your tracks require a condition for example). In this case, you can use trackers manually :
```java
@Override
public void onClick(View v) {
    if (someCondition) {
        AnalyticsManager.getInstance().trackEvent(R.string.category, R.string.action, R.string.label, 1);
    } else {
        AnalyticsManager.getInstance().trackEvent(R.string.category, R.string.action, R.string.label, 1);
    }
}
```

## Proguard
Make sure your proguard rule set includes following lines: 
```
-keep class com.dorukkangal.analytics.** { *; }
```

License
=======

    Copyright 2016 Doruk Kangal

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.Upda
