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
Feature ```sendEmail``` is always DISABLED.

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
Feature ```sendEmail``` is enabled if the property **feature.send.email** is set to true.

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
Feature ```sendEmail``` is enabled if the current profile is either **dev or qa**.

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
Feature ```sendEmail``` is enabled if current day is either **MONDAY or TUESDAY**.

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
Feature ```sendEmail``` is enabled if current datetime is equal to or greater than the value defined by the **default.date.enabled** property.

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
Feature ```sendEmail``` is enabled if the expression evaluates to TRUE.

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
will flip the invocation of ```sendEmail``` method with the one (having exactly same signature) defined in **SendGridEmailSender**.

## FAQs
1. Is there a way to combine these annotations ? Eg; I want a feature to be enabled only on PROD after a given date.
**Yes**, these annotations can be combined. Currently, such combinations are treated as AND operations, meaning all the conditions MUST evaluate to TRUE for a feature to be enabled.

    **Usage**

    ```
    @Component
    class EmailSender{

      @FlipOnProfiles(activeProfiles = "PROD")
      @FlipOnDateTime(cutoffDateTimeProperty = "sendemail.feature.active.after")
      public void sendEmail(EmailMessage emailMessage){
      }
    }
    ```
this will throw FeatureNotEnabledException is either of the conditions evaluate to FALSE

2. Is there a way to flip a bean based on conditions ? Eg; I want a feature to be ```flipped with``` only in DEV.
**Yes**, @FlipBean can be used with conditions. If used with conditions, flip bean would be activated if all the conditions evaluate to TRUE

    **Usage**

    ```
    @Component
    class EmailSender{

      @FlipBean(with=SendGridEmailSender.class)
      @FlipOnProfiles(activeProfiles = "DEV")
      public void sendEmail(EmailMessage emailMessage){
      }
    }
    ```
this will flip the implementation of sendEmail with the same method defined in ```SendGridEmailSender```if active profile is DEV.





## Want to contribute?
1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -am 'Add some feature')
3. Push to the branch (git push origin my-new-feature)
4. Create new Pull Request


## Credits
1. A Very big Thank you to [Sunit Parekh](https://github.com/sunitparekh/) for providing guidance







