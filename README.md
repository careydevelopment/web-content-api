![Carey Development Logo](http://careydevelopment.us/img/branding/careydevelopment-logo-sm.png)

# Analytics Library
![](https://img.shields.io/badge/jdk-11-blue.svg) ![license](https://img.shields.io/badge/license-MIT-blue.svg) 
![](https://img.shields.io/badge/maven-4.0.0-blue.svg) ![version](https://img.shields.io/badge/version-1.7.0-blue.svg)

Handles analytics for the [Carey Development Blog](https://careydevelopment.us). It can also be used to handle analytics for other blogs powered by Spring Boot with MongoDB.

## Persisting Analytics
The library persists analytics info whenever a visitor hits a page. The code logs the time of the visit, the URL of the page, its title and other important
info.

Each visit get persisted as a new (small) document in a collection that holds nothing but analytics info. 

It's best to persist the analytics in a separate collections that's in the same database as the blog itself. However, analytics could be stored in separate 
databases as well.

## Retrieving Analytics
This library also handles retrieval of analytics info for display in admin consoles. 

It's up the application that uses this dependency to implement a solution for displaying the data. 
