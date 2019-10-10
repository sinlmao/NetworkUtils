# Sinlmao Commons Network Utils

> A Simple, Lightly Java whit HTTP / FTP network integration operational class library.
>
> `update：2019-10-10`  `ver：1.3.7`  `license：Apache 2.0`

*This document is partially translated using a translation tool. If there is something wrong, please forgive me.*

----------

**[中文文档][1]** | English

----------

# I. Introduction

**This is a simple and lightweight Java library for operating networks such as HTTP and FTP**. Originally because of the need to integrate and package small libraries that fit personal habits, they were later used in corporate projects and internally. At that time, the IDE was packaged into a jar for use by the project, but since all the basic projects were built using Maven, the way to use the local reference jar package is not convenient for Maven's recommendation, so it is submitted to the Maven repository and open sourced to GitHub. Just some simple and convenient packaging, ***not a technology***.

Now we plan to maintain Sinlmao Commons Network Utils continuously, with the goal of simplifying Java's code and logic concepts for HTTP and FTP operations. **At the same time, we hope that more people can help Sinlmao Commons Network become better**.

The features currently released (or the operational features provided) are as follows:

>  - Easy to use to send HTTP GET, POST requests;
>  - Support for convenient setting of Header data;
>  - Support for convenient setting of Cookie data;
>  - Support for convenient setting of InputData data;
>  - Support configuration to ignore SSL certificate verification (v1.2.5+);
>  - Support file Upload (v1.3.0+);

**`v1.2.1`** starts to have relatively large changes before, if you upgrade directly, please pay attention to the use. Considering the issue of backward compatibility, the related classes of the `v1.1.x` version are reserved but marked as expired and are no longer maintained (except for BUGs).

# II. Introducing Project

## 2.1 Java（Maven）

Introduced by Maven, set directly in the POM:

    <dependency>
        <groupId>cn.sinlmao.commons</groupId>
        <artifactId>network</artifactId>
        <version>1.3.7</version>
    </dependency>

## 2.2 Android（Gradle）

If used in Android (Java 1.8+), the settings in Gradle:

    implementation 'cn.sinlmao.commons:network:1.3.7'

# III. Instruction Manual

## 3.1 Related class introduction

Since **`v1.2.1`**, the new Sinlmao Commons Network Utils has been switched to the class name at the beginning of `Im`, making it simpler and easier to use.

The composition of the Sinlmao Commons Network Utils is very straightforward, with the following classes forming a complete HTTP service:

>  - **ImHttpClient** Main class used to initiate HTTP requests
>  - **ImRequest** The data wrapper class of the Request that initiated the HTTP request
>  - **ImMethod** Method enumeration class for specifying HTTP Request (ImRequest)
>  - **ImResponse** Data wrapper class that completes the HTTP request

## 3.2 Simple example

### 3.2.1 Initiate a simple request

For example, we need to get data from a URL (assuming we get its home page HTML data from Baidu), and abandon Java's native complex and incomprehensible methods, which can be implemented in just a few lines of code:

    //Build Request
    ImRequest imRequest = new ImRequest("url");
    //Send Request
    ImResponse imResponse = ImHttpClient.send(imRequest);
    //Get return data
    String rs = imResponse.getStringContent();
    //Print return data
    System.out.println(rs);

### 3.2.2 Initiate a request with parameters

If you need to send an HTTP request with parameters to an address, it is also very simple:

    //Packaging parameters
    Map<String, String> pars = new HashMap<String, String>();
    pars.put("q", "baidu");
    pars.put("ie", "utf-8");
    //Build Request
    ImRequest imRequest = new ImRequest("url");
    //Incoming parameters
    imRequest.setInputData(pars);
    //Send Request
    ImResponse imResponse = ImHttpClient.send(imRequest);
    //Get return data
    String rs = imResponse.getStringContent();
    //Print return data
    System.out.println(rs);

### 3.2.3 Request using POST or other methods

Changing the Method requires only one line of code:

    //Set Method
    imRequest.setMethod(ImMethod.POST);

### 3.2.4 Add Header

Many times we need to indicate identity in the Header (that is, pass the value in the Header), then just write the following code:

    //Set identity - Header mode
    imRequest.addHeader("token", "your token");

### 3.2.4 Add Cookie

In addition to the ability to add a Header, of course, you can also add a cookie, which is also very simple:

    //Set Cookies
    imRequest.addCookie("key", "value");

### 3.2.5 Ignore SSL certificate verification (v1.2.5+)

Although SSL certificate verification can greatly increase the credibility of the session, more often we do not need to verify the SSL certificate, especially some self-signed certificates, you can ignore the SSL certificate verification by the following code:

    //Configuring ignore certificates
    imRequest.setIgnoreSSLCertVerify(true);

### 3.2.6 Set multiple types of InputData(v1.2.3+)

We have all experienced the need to submit a series of parameters to the server interface (or some other URL), the number of parameters may be large and the data type is complex. If you turn into a String, it is very embarrassing and troublesome, so in Sinlmao Commons Network Utils, you can receive String, Map<String, String>, JSONObject (fastjson), ImHttpClient will automatically convert the appropriate format. The usage simulation code is as follows:

    //Pass in String type data (simulation)
    imRequest.setInputData(String);
    
    //Pass in data of type Map<String, String> (simulation)
    imRequest.setInputData(Map<String, String>);
    
    //Pass in JSONObject type data (simulation)
    imRequest.setInputData(JSONObject);

### 2.7 File Upload (v1.3.0+)

HTTP file upload is a new feature supported by Sinalmao Commons Network Utils `v1.3.0`. It encapsulates complex and even very abstract file upload code logic and simplifies it into a simple data type. With a consistent call method, only specific settings need to be set. With content types and methods, you can easily implement file upload requirements. code show as below:

    //Build Request
    ImRequest imRequest = new ImRequest("url");
    //Setting method type
    imRequest.setMethod(ImMethod.POST);
    //Set content type
    imRequest.setContentType(ImContentType.MULTIPART_FORM_DATA);
    //Package file upload data
    ImFileData imFileData = new ImFileData(
        "name",
        "file name",
        "file type",
        file bytes
    );
    //Incoming parameters
    imRequest.setInputData(imFileData);
    //Send Request
    ImResponse imResponse = ImHttpClient.send(imRequest);
    //Get return data
    String rs = imResponse.getStringContent();
    //Print return data
    System.out.println(rs);


  [1]: README.md