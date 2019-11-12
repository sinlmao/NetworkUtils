# Sinlmao Commons Network Utils

> A Simple, Lightly Java whit HTTP / FTP network integration operational class library.
>
> `update：2019-11-13`  `ver：1.4.5`  `license：Apache 2.0`

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
>  - Support session state management (v1.4.1+);

**`v1.2.1`** starts to have relatively large changes before, if you upgrade directly, please pay attention to the use. Considering the issue of backward compatibility, the related classes of the `v1.1.x` version are reserved but marked as expired and are no longer maintained (except for BUGs).

# II. Introducing Project

## 2.1 Java（Maven）

Introduced by Maven, set directly in the POM:

    <dependency>
        <groupId>cn.sinlmao.commons</groupId>
        <artifactId>network</artifactId>
        <version>1.4.5</version>
    </dependency>

## 2.2 Android（Gradle）

If used in Android (Java 1.8+), the settings in Gradle:

    implementation 'cn.sinlmao.commons:network:1.4.5'

# III. Instruction Manual

## 3.1 Related class introduction

Since **`v1.2.1`**, the new Sinlmao Commons Network Utils has been switched to the class name at the beginning of `Im`, making it simpler and easier to use.

The composition of the Sinlmao Commons Network Utils is very straightforward, with the following classes forming a complete HTTP service:

>  - **ImHttpClient** Main class for initiating HTTP requests
>  - **ImRequest** Data wrapper class for Request that initiates HTTP request
>  - **ImMethod** Method enumeration class for specifying HTTP request (ImRequest)
>  - **ImResponse** Data wrapper class for completing HTTP requests
>  - **ImContentType** An enumeration class for specifying the HTTP content type (ContentType)
>  - **ImCharset** is used to specify the enumeration class for HTTP common encoding
>  - **ImSession** Data object class for storing session state control
>  - **ImUserAgent** Common User-Agent (UA) data class

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

### 3.2.5 Add Cookie

In addition to the ability to add a Header, of course, you can also add a cookie, which is also very simple:

    //Set Cookies
    imRequest.addCookie("key", "value");

### 3.2.6 Ignore SSL certificate verification (v1.2.5+)

Although SSL certificate verification can greatly increase the credibility of the session, more often we do not need to verify the SSL certificate, especially some self-signed certificates, you can ignore the SSL certificate verification by the following code:

    //Configuring ignore certificates
    imRequest.setIgnoreSSLCertVerify(true);

### 3.2.7 Set multiple types of InputData(v1.2.3+)

We have all experienced the need to submit a series of parameters to the server interface (or some other URL), the number of parameters may be large and the data type is complex. If you turn into a String, it is very embarrassing and troublesome, so in Sinlmao Commons Network Utils, you can receive String, Map<String, String>, JSONObject (fastjson), ImHttpClient will automatically convert the appropriate format. The usage simulation code is as follows:

    //Pass in String type data (simulation)
    imRequest.setInputData(String);
    
    //Pass in data of type Map<String, String> (simulation)
    imRequest.setInputData(Map<String, String>);
    
    //Pass in JSONObject type data (simulation)
    imRequest.setInputData(JSONObject);

### 3.2.8 File Upload (v1.3.0+)

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

### 3.2.9 Using QueryString Request (v1.4.0+)

From `v1.4.0` we started supporting the new features of QueryString requests for better support or special scenarios for URL passing. code show as below:

    //Packaging parameters
    Map<String, String> pars = new HashMap<String, String>();
    Pars.put("q", "baidu");
    Pars.put("ie", "utf-8");
    //Build Request
    ImRequest imRequest = new ImRequest("url");
    //Incoming QueryString parameter
    imRequest.setQueryParams(pars);
    //send request
    ImResponse imResponse = ImHttpClient.send(imRequest);
    //Get the return data
    String rs = imResponse.getStringContent();
    //Print return data
    System.out.println(rs);

### 3.2.10 Session State Management (v1.4.1+)

Session state management, which is a new feature supported from Sinlmao Commons Network Utils `v1.4.1`. Please pay attention to the updated version when using it. Session state management automatically manages the state or identity data with the server and simplifies the complex logic on the business code. You only need to configure or even simply construct an ImSession when you use it. You don't need your extra participation at all. The sample code is as follows:

    //Build Session
    ImSession imSession = new ImSession();
    //Build Request
    ImRequest imRequest = new ImRequest("url");
    //send request
    mResponse imResponse = ImHttpClient.send(imRequest, imSession);

### 3.2.11 Custom User Agent (UA) Information (v1.4.1+)

There is also a new feature in `v1.4.1`, which is to customize the User-Agent (UA) information. The sample code is as follows:

    //Custom User-Agent
    imRequest.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1");
    
You can also use the `ImUserAgent` class to quickly set up.

### 3.2.12 Asynchronous mode support (v1.4.3+)

Access in asynchronous mode may be required in many business situations. You need to implement it yourself before, and after `v1.4.3` , we have added asynchronous mode. Simply set a flag directly, but after executing the asynchronous request, your code will not be synchronized in the process, the returned ImResponse is a null value, you need to implement ImHttpCallback to get the correct ImResponse. The sample code is as follows:

    //Set the asynchronous
    imRequest.setAsync(true);
    
### 3.2.13 Business Callback Interface (v1.4.3+)

The service callback interface is mainly used in scenarios where you need to embed your own business logic. Session state control service callbacks and HTTP request service callbacks are currently supported (supported from `v1.4.3` ). Before using the callback interface, you need to implement the interface first.

Assuming that you want to implement a session state control service with login, you should implement the `ImSessionCallback` interface first. Example:

    public class TestSessionCallbackImpl implements ImSessionCallback {
    
        @Override
        public boolean isAuthentication(ImSession imSession, ImRequest request) {
            return imSession.getExtraValue("isLogin") != null && (boolean) imSession.getExtraValue("isLogin");
        }
    
        @Override
        public boolean doAuthentication(ImSession imSession, ImRequest imRequest) {
            try {
                //Packaging parameters
                Map<String, String> pars = new HashMap<String, String>();
                
                ....Related logic code
                
                //Build Request
                ImRequest request = new ImRequest("xxxxxxx");
                //Incoming parameters
                request.setInputData(pars);
                //Send a request, be sure to pass in the session control object
                ImResponse imResponse = ImHttpClient.send(request, imSession);
    
                //Get return data
                JSONObject rs = JSON.parseObject(imResponse.getStringContent());
    
                //Simulation judges successful login
                if (rs.getIntValue("status") == 200) {
                    imSession.addExtra("isLogin", true);
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        
        ....Related logic code
    }
    
A simple example of a business code is as follows:
    
    //Build session state control object class
    ImSession imSession = new ImSession();
    
    //Must be configured to validate the business interface callback
    imSession.setNeedAuthentication(true);
    imSession.setAutoAuthentication(true);
    
    //Set the callback interface
    imSession.setCallback(new TestSessionCallbackImpl());
    
    //Packaging parameters
    Map<String, String> pars = new HashMap<String, String>();
    
    ....Related logic code
    
    ImRequest imRequest = new ImRequest("xxxxxxx");

    ....Related logic code
    
    //Send a request, be sure to pass in the session control object
    ImResponse imResponse = ImHttpClient.send(imRequest, imSession);
    
    ....Related logic code

### 3.2.14 Proxy/Capture Support (v1.3.7+)

We actually added an experimental function in `v1.3.7` to support configurable captured packets (any packet capture agent tool) to debug capture packets, and also used in some special network environments. After so many versions of the iteration, it has been relatively available, and now we decided to expose this feature. To enable this feature is very simple, just the following code:

    //Configuring proxy support
    imRequest.enableProxyServer(Port);

  [1]: README.md