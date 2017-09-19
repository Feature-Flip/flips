# Flips
Flips is an implementation of the Feature Toggles pattern for Java. [Feature Toggles](https://martinfowler.com/articles/feature-toggles.html) are a very common agile development practices in the context of continuous deployment and delivery. Feature toggles are a powerful technique, allowing teams to modify system behavior without changing code

[![Build Status](https://travis-ci.org/Feature-Flip/flips.svg?branch=master)](https://travis-ci.org/Feature-Flip/flips)
[![Coverage Status](https://coveralls.io/repos/github/Feature-Flip/flips/badge.svg?branch=master)](https://coveralls.io/github/Feature-Flip/flips?branch=master)

## Why another library for feature toggle ?
The idea behind Flips is to let the users implements toggle with minimum configuration and coding. This library is intended to work with Java8, Spring Core / Spring MVC / Spring Boot. 

## Where do I get sample project(s) ?
Sample projects can be found [here](https://github.com/SarthakMakhija/flips-samples).

## Getting Started
Flips provides various annotations to flip a feature, either ON or OFF.

*Let's have a quick walkthrough of all the annotations and their behavior* - 

**@FlipOff** is used to flip a feature off. 

**Usage**

```
@Component
class EmailSender{

  @FlipOff
  public void sendEmail(EmailMessage emailMessage){
  }
}
```
*FeatureNotEnabledException* is thrown if sendEmail in invoked. 

**@FlipOnEnvironmentProperty** is used to flip a feature based on the value of environment property.

**Usage**

```
@Component
class EmailSender{

  @FlipOnEnvironmentProperty(property="feature.send.email", expectedValue="true")
  public void sendEmail(EmailMessage emailMessage){
  }
}
```
*FeatureNotEnabledException* is thrown if sendEmail in invoked and *feature.send.email* property is set to a value other than true in application.properties

**@FlipOnProfiles** is used to flip a feature based on the envinronment in which the application is running.

**Usage**

```
@Component
class EmailSender{

  @FlipOnProfiles(activeProfiles={"dev", "qa"})
  public void sendEmail(EmailMessage emailMessage){
  }
}
```
*FeatureNotEnabledException* is thrown if sendEmail in invoked and the current spring profile is *neither dev nor qa*.

**@FlipOnDaysOfWeek** is used to flip a feature based on the day of the week.

**Usage**

```
@Component
class EmailSender{

  @FlipOnDaysOfWeek(daysOfWeek={DayOfWeek.MONDAY,DayOfWeek.TUESDAY})
  public void sendEmail(EmailMessage emailMessage){
  }
}
```
*FeatureNotEnabledException* is thrown if sendEmail in invoked and current day is other than *MONDAY or TUESDAY*

**@FlipOnDateTime** is used to flip a feature based on date and time.

**Usage**

```
@Component
class EmailSender{

  @FlipOnDateTime(cutoffDateTimeProperty="default.date.enabled")
  public void sendEmail(EmailMessage emailMessage){
  }
}
```
*FeatureNotEnabledException* is thrown if sendEmail in invoked and *default.date.enabled* property is set to a value which is lesser than current date time in UTC. In order to use @FlipOnDateTime, cutoffDateTimeProperty should be set in **ISO8601** format

**@FlipOnSpringExpression** is used to flip a feature based on the evaluation of spring expression.

**Usage**

```
@Component
class EmailSender{

  @FlipOnSpringExpression(expression = "T(java.lang.Math).sqrt(4) * 100.0 < T(java.lang.Math).sqrt(4) * 10.0")
  public void sendEmail(EmailMessage emailMessage){
  }
}
```
*FeatureNotEnabledException* is thrown if sendEmail in invoked since the expression evaluates to FALSE.

**@FlipBean** is used to flip the invocation of a method with another method.

**Usage**

```
@Component
class EmailSender{

  @FlipBean(with=SendGridEmailSender.class)
  public void sendEmail(EmailMessage emailMessage){
  }
}
```
will flip the invocation of sendEmail method with the one (having exactly same signature) defined in **SendGridEmailSender**.


