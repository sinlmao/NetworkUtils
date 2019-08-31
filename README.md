# Sinlmao Commons Network Utils

> 一个简单地、轻量级的 Java HTTP、FTP Network 集成、封装的操作类库。
>
> `update：2019-08-31`  `ver：1.2.4`

----------

# 一、简介

这是**一个简单地并且轻量级的Java用于HTTP、FTP等对Network的操作类库**。最初是因为需要符合个人使用习惯而集成并封装的小型类库，后来在公司项目和内部有一些使用。最初通过IDE打包成jar的方式供项目使用，但是由于基本所有项目都使用Maven构建，使用本地引用jar包的方式方便也不符合Maven的推荐，需要提交至Maven仓库，并且将其开源至GitHub。只是一些简单便利地封装，*不算什么技术*。

目前发布的功能（或提供的操作功能）如下：

>  - 简单易用的发送HTTP GET、POST请求；
>  - 支持快捷设置Header数据；
>  - 支持快捷设置Cookie数据；
>  - 支持快捷设置InData数据；
>  - 支持配置忽略SSL证书验证；

**`v1.2.1`** 开始较之前有相对比较大的变更，如果直接升级还请注意使用情况。考虑到向下兼容的问题，`v1.1.x` 版本的相关类保留但标记为过期，并且不再维护（除BUG外）。

# 二、引入项目

## 1. Java（Maven）

通过Maven引入，直接在POM中设置如下：

    <dependency>
        <groupId>cn.sinlmao.commons</groupId>
        <artifactId>network</artifactId>
        <version>1.2.4</version>
    </dependency>

## 2. Android（Gradle）

如果在Android中使用（Java 1.8+），在Gradle设置如下：

    implementation 'cn.sinlmao.commons:network:1.2.4'

# 二、使用说明

## 1. 相关类介绍

自 **`v1.2.1`** 开始，Sinlmao Commons Network Utils 全新切换为**`Im`**开头的类名，更为简洁易用。

Sinlmao Commons Network Utils 的构成非常简洁明了，分别由下面的类构成一个完整的HTTP业务：

>  - **ImHttpClient** 用于发起HTTP请求的主要类
>  - **ImRequest** 发起HTTP请求的Request的数据包装类
>  - **ImMethod** 用于指定 HTTP Request（ImRequest）的Method枚举类
>  - **ImResponse** 完成HTTP请求的数据包装类

## 2. 简单示例

### 2.1 发起简单请求

例如，我们要从一个URL获得数据（假设从百度获取其首页 HTML 数据），抛弃Java原生复杂和难以理解的方法，只需要几行代码即可实现：

    //构建Request
    ImRequest imRequest = new ImRequest("https://www.baidu.com");
    ///发送请求
    ImResponse imResponse = ImHttpClient.send(imRequest);
    //获得返回数据
    String rs = imResponse.getStringContent();
    //打印返回数据
    System.out.println(rs);

### 2.2 发起带参数请求

如果需要向一个地址发送带参数的HTTP请求，也非常简单：

    //包装参数
    Map<String, String> pars = new HashMap<String, String>();
    pars.put("q", "baidu");
    pars.put("ie", "utf-8");
    //构建Request
    ImRequest imRequest = new ImRequest("https://so.com/s");
    //传入参数
    imRequest.setInputData(pars);
    //发送请求
    ImResponse imResponse = ImHttpClient.send(imRequest);
    //获得返回数据
    String rs = imResponse.getStringContent();
    //打印返回数据
    System.out.println(rs);

### 2.3 使用POST或其它方法（Method）的请求

改变Method只需要一行代码的设置：

    //设置Method
    imRequest.setMethod(ImMethod.POST);

### 2.4 添加Header

很多时候我们需要在Header中表明身份（即传值），此时只需要写如下代码：

    //设置身份 - Header 方式
    imRequest.addHeader("token", "your token");

### 2.4 添加Cookie

除了可以添加Header，当然也可以添加Cookie，也非常地简单：

    //设置Cookie
    imRequest.addCookie("key", "value");

### 2.5 忽略SSL证书验证

尽管SSL证书验证可以极大增加会话的可信度，但更多的时候我们并不需要验证SSL的证书，尤其是一些自签名的证书，可以通过以下代码忽略SSL证书验证：

    //配置忽略证书
    imRequest.setIgnoreSSLCertVerify(true);

文档将在后续尽快完善。