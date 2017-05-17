# TimeSkyBackgroundView for Android
Interactive sky background widget, only declare on XML in your development app!

<div>
<img src="https://github.com/dsa28s/TimeSkyBackground/raw/master/art/main.gif" width="400" style="float:left">
<img src="https://github.com/dsa28s/TimeSkyBackground/raw/master/art/button.gif" width="400" style="float:left">
</div>

# Usage

For a working implementation of this project see the `timeskybackgroundsample/` folder.

0. Include the following URL in your root `build.gradle` file.

```groovy
buildscript {
    repositories {
        ...
        maven {
            url 'https://dl.bintray.com/leeshoon1344/TimeSkyBackgroundView'
        }
    }
    ...
}
```

1. Include the following dependency in your `build.gradle` file.
   
   (Tomorrow, will be register in jcenter)

```groovy
compile 'me.sangs:TimeSkyBackgroundView:1.0.0'
```

Or add the library as a project.

2. Include the `SkyTimeBackgroundView` widget in your layout. (Recommend - Your app background)

```xml
<me.sangs.time.sky.view.SkyTimeBackgroundView
        android:id="@+id/timeBackgroundView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:autoStart="true"
        app:planetVisible="true"
        app:planetPosition="70"
        app:planetAnimation="true"
        app:planetSpeed="100"
        app:starVisible="false"/>
```
Complete!

# Customization - XML
* `app:autoStart` (boolean) - Auto start background animation (default : true)
* `app:planetVisible` (boolean) - Visibility planet (SUN / RED SUN / MOON)
* `app:planetPosition` (integer) - Set planet position (Position range : 0 ≤ position ≤ 120)
* `app:planetAnimation` (boolean) - Enable(Disable) planet move animation
* `app:planetSpeed` (integer) - Animation repeat duration (millisec)
* `app:starVisible` (boolean) - Visibility star particles
* `app:starLineisible` (boolean) - Visibility star line particles
* `app:planetType` (string) - Set planet (sun, redSun, moon)

# Customization - Java
Change Background Gradient
```java
mBackgroundView.setBackgroundGradient(R.drawable.red_gradient1, R.drawable.red_gradient2, R.drawable.red_gradient3); 
```
Change Time
```java
mBackgroundView.changeTime(SkyTimeBackgroundView.Time.AFTERNOON); //morning
mBackgroundView.changeTime(SkyTimeBackgroundView.Time.EARLY_NIGHT); //early night
mBackgroundView.changeTime(SkyTimeBackgroundView.Time.NIGHT); //night
```

Change star visibility
```java
mBackgroundView.setStarVisibility(true); //Visible Star
```

Change planet visibility
```java
mBackgroundView.setPlanetVisibility(true); //Visible planet
```

Set planet position
```java
mBackgroundView.setPlanetPosition(70); //Position range : 0 ≤ position ≤ 120
```

Set planet speed
```java
mBackgroundView.setPlanetSpeed(2000);
```

Change planet animation enabled
```java
mBackgroundView.usePlanetAnimation(true);
```

Set star line visibility
```java
mBackgroundView.setStarLineVisibility(true);
```
Set planet
```java
mBackgroundView.setPlanet(SkyTimeBackgroundView.Planet.SUN); //SUN
mBackgroundView.setPlanet(SkyTimeBackgroundView.Planet.RED_SUN); //RED SUN
mBackgroundView.setPlanet(SkyTimeBackgroundView.Planet.MOON); //MOON
```

# Developed By
 * LEE SANG HUN(MR.LEE) - <leeshoon1344@gmail.com / dsa28s@naver.com>

# Contributions
 * Please, read the README file before opening an issue, thanks.
 * Please, all the Pull Request must be sent to the dev branch, thanks..

# Credits
 * Thanks for ParticleDrawable - Docdoro

# License
    Copyright 2017 LEE SANG HUN(MR.LEE) - leeshoon1344@gmail.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
