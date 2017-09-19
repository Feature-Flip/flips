# Flips
Flips is an implementation of the Feature Toggles pattern for Java. [Feature Toggles](https://martinfowler.com/articles/feature-toggles.html) are a very common agile development practices in the context of continuous deployment and delivery. Feature toggles are a powerful technique, allowing teams to modify system behavior without changing code

[![Build Status](https://travis-ci.org/Feature-Flip/flips.svg?branch=master)](https://travis-ci.org/Feature-Flip/flips)
[![Coverage Status](https://coveralls.io/repos/github/Feature-Flip/flips/badge.svg?branch=master)](https://coveralls.io/github/Feature-Flip/flips?branch=master)

## Why another library for feature toggle ?
The idea behind Flips is to let the users implements toggle with minimum configuration and coding. This library is intended to work with Java8, Spring Core / Spring MVC / Spring Boot. 

## Where do I get sample project(s) ?
Sample projects can be found [here](https://github.com/SarthakMakhija/flips-samples).

## Getting Started
Include the necessary dependency -
```
  <dependency>
    <groupId>com.github.feature-flip</groupId>
    <artifactId>flips-web</artifactId>
    <version>1.0.1</version>
  </dependency>
```
Or,

```
  <dependency>
    <groupId>com.github.feature-flip</groupId>
    <artifactId>flips-core</artifactId>
    <version>1.0.1</version>
  </dependency>
```

Flips provides various annotations to flip a feature, either ON or OFF. *Let's have a quick walkthrough of all the annotations and their behavior* - 

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

3. What date format is accepted in FlipOnDateTime ?  
**ISO-8601**. 

    **Usage**

    ```
    @Component
    class EmailSender{

      @FlipOnDateTime(cutoffDateTimeProperty = "sendemail.feature.active.after")
      public void sendEmail(EmailMessage emailMessage){
      }
    }
    ```
Assuming, today is 20th Sep 2018, one could set **sendemail.feature.active.after** to a value equal to before 20th Sep 2018. sendemail.feature.active.after=2018-09-16T00:00:00Z

4. What happens on invoking a feature which is disabled ?  
**FeatureNotEnabledException** is thrown if a disabled feature is invoked. In case of a WEB application, one could use 
flips-web dependency which also provides ```ControllerAdvice``` meant to handle this exception. It returns a default response and a status code of 501.

5. Is it possible for the client of this library to override the response returned by ```ControllerAdvice``` ?  
**Yes**, this is doable. You can register a ```ControllerAdvice``` with an exception handler meant for handling **FeatureNotEnabledException**. Please refer [Sample Project](https://github.com/SarthakMakhija/flips-samples/tree/master/flips-sample-spring-boot/src/main/java/com/finder/article/advice).

6. What should be the signature of target method while using @FlipBean
The target method should have exactly the same signature as the method which is annotated with @FlipBean annotation. Please refer "getArticleStatisticsByYear" method here [Sample Project](https://github.com/SarthakMakhija/flips-samples/blob/master/flips-sample-spring-boot/src/main/java/com/finder/article/controller/ArticleController.java).

7. How do I load Spring Configuration related to Flips ?
In order to bring all Flips related annotations in effect, FlipConfiguration needs to be imported. 

    **Usage**

    ```
    @SpringBootApplication
    @Import(FlipWebContextConfiguration.class)
    class ApplicationConfig{
      public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
      }
    }
    ```
you will need to import FlipWebContextConfiguration as mentioned above. Please refer [Sample Project](https://github.com/SarthakMakhija/flips-samples/blob/master/flips-sample-spring-boot/src/main/java/com/finder/article/ApplicationConfig.java). 

8. Is there a way to create custom annotation(s) to flip a feature ?
**Yes**. You can create a custom annotation to meet your requirements. Create a custom annotation at METHOD level which has a meta-annotation of type @FlipOnOff.
    
    ```
      @Target({ElementType.METHOD})
      @Retention(RetentionPolicy.RUNTIME)
      @FlipOnOff(value = MyCustomCondition.class)
      public @interface MyCustomAnnotation {
      }
    ```
As a part of this annotation, specify the condition which will evaluate the result of this annotation.

      ```
      @Component
      public class MyCustomCondition implements FlipCondition {

        @Override
        public boolean evaluateCondition(FeatureContext featureContext, 
                                         FlipAnnotationAttributes flipAnnotationAttributes) {
          return false;
        }
      }
      ```
Condition class needs to implement FlipCondition and **MUST be a Spring Component**. This is it !!

## Want to contribute?
1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -am 'Add some feature')
3. Push to the branch (git push origin my-new-feature)
4. Create new Pull Request


## Credits
1. A Very big Thank you to [Sunit Parekh](https://github.com/sunitparekh/) for providing guidance







