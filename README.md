![Carey Development Logo](http://careydevelopment.us/img/branding/careydevelopment-logo-sm.png)

# Analytics Library
![](https://img.shields.io/badge/jdk-11-blue.svg) ![license](https://img.shields.io/badge/license-MIT-blue.svg) 
![](https://img.shields.io/badge/maven-3.6.3-blue.svg)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/us.careydevelopment.util/analytics/badge.svg)](https://search.maven.org/artifact/us.careydevelopment.util/analytics/1.7.0-RELEASE/jar)




Handles analytics for the [Carey Development Blog](https://careydevelopment.us). It can also be used to handle analytics for other blogs powered by Spring Boot with MongoDB.

## Persisting Analytics
The library persists analytics info whenever a visitor hits a page. The code logs the time of the visit, the URL of the page, its title and other important
info.

Each visit get persisted as a new (small) document in a collection that holds nothing but analytics info. 

It's best to persist the analytics in a  collection that's in the same database as the blog itself. However, analytics could be stored in separate 
databases as well.

## Retrieving Analytics
This library also handles retrieval of analytics info for display in admin consoles. 

It's up the application that uses this dependency to implement a solution for displaying the data. 

## Service Layer
Almost all the fun stuff happens in [BaseWebPageVisitService](https://github.com/careydevelopment/analytics/blob/main/src/main/java/us/careydevelopment/util/analytics/service/BaseWebPageVisitService.java). So that's where you should turn your attention if you're interested in using this library.

## Latest Release
You can pull it from the central Maven repositories:

```xml
 <dependency>
    <groupId>us.careydevelopment.util</groupId>
    <artifactId>analytics</artifactId>
    <version>1.7.0-RELEASE</version>
 </dependency>
```

## License
This code is under the [MIT License](https://github.com/careydevelopment/analytics/blob/main/LICENSE).
