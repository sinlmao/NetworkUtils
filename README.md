# Sinlmao Commons Network Utils

> 一个简单地、轻量级的 Java HTTP、FTP Network 集成、封装的操作类库。
>
> `update：2019-11-13`  `ver：1.4.5`  `license：Apache 2.0`

----------

中文文档 | **[English][1]**

----------

# 一、简介

这是**一个简单地并且轻量级的Java用于HTTP、FTP等对Network的操作类库**。

最初是因为需要符合个人使用习惯而集成并封装的小型类库，后来在公司项目和内部有一些使用。当时通过IDE打包成jar的方式供项目使用，但是由于基本所有项目都使用Maven构建，使用本地引用jar包的方式方便也不符合Maven的推荐，所以提交至Maven仓库，并且将其开源至GitHub。只是一些简单便利地封装，***不算什么技术***。

现在我们计划持续维护 Sinlmao Commons Network Utils，目标是希望简化Java对HTTP、FTP操作的代码和逻辑概念。**同时我们也希望有更多的人可以帮助 Sinlmao Commons Network 变得更好用**。

目前发布的功能（或提供的操作功能）如下：

>  - 简单易用的发送HTTP GET、POST请求；
>  - 支持快捷设置Header数据；
>  - 支持快捷设置Cookie数据；
>  - 支持快捷设置InData数据；
>  - 支持配置忽略SSL证书验证（v1.2.5+）；
>  - 支持文件上传（v1.3.0+）；
>  - 支持会话状态管理（v1.4.1+）；

**`v1.2.1`** 开始较之前有相对比较大的变更，如果直接升级还请注意使用情况。考虑到向下兼容的问题，`v1.1.x` 版本的相关类保留但标记为过期，并且不再维护（除BUG外）。

# 二、引入项目

## 1. Java（Maven）

通过Maven引入，直接在POM中设置如下：

    <dependency>
        <groupId>cn.sinlmao.commons</groupId>
        <artifactId>network</artifactId>
        <version>1.4.5</version>
    </dependency>

## 2. Android（Gradle）

如果在Android中使用（Java 1.8+），在Gradle设置如下：

    implementation 'cn.sinlmao.commons:network:1.4.5'

# 三、使用说明

## 3.1 相关类介绍

自 **`v1.2.1`** 开始，Sinlmao Commons Network Utils 全新切换为`Im`开头的类名，更为简洁易用。

Sinlmao Commons Network Utils 的构成非常简洁明了，分别由下面的类构成一个完整的HTTP业务：

>  - **ImHttpClient** 用于发起HTTP请求的主要类
>  - **ImRequest** 发起HTTP请求的Request的数据包装类
>  - **ImMethod** 用于指定 HTTP Request（ImRequest）的Method枚举类
>  - **ImResponse** 完成HTTP请求的数据包装类
>  - **ImContentType** 用于指定 HTTP 内容类型（ContentType）的枚举类
>  - **ImCharset** 用于指定 HTTP 常见编码的枚举类
>  - **ImSession** 用于存储会话状态控制的数据对象类
>  - **ImUserAgent** 常见User-Agent（UA）数据类

## 3.2 简单示例

### 3.2.1 发起简单请求

例如，我们要从一个URL获得数据（假设从百度获取其首页 HTML 数据），抛弃Java原生复杂和难以理解的方法，只需要几行代码即可实现：

    //构建Request
    ImRequest imRequest = new ImRequest("url");
    ///发送请求
    ImResponse imResponse = ImHttpClient.send(imRequest);
    //获得返回数据
    String rs = imResponse.getStringContent();
    //打印返回数据
    System.out.println(rs);

### 3.2.2 发起带参数请求

如果需要向一个地址发送带参数的HTTP请求，也非常简单：

    //包装参数
    Map<String, String> pars = new HashMap<String, String>();
    pars.put("q", "baidu");
    pars.put("ie", "utf-8");
    //构建Request
    ImRequest imRequest = new ImRequest("url");
    //传入参数
    imRequest.setInputData(pars);
    //发送请求
    ImResponse imResponse = ImHttpClient.send(imRequest);
    //获得返回数据
    String rs = imResponse.getStringContent();
    //打印返回数据
    System.out.println(rs);

### 3.2.3 使用POST或其它方法（Method）的请求

改变Method只需要一行代码的设置：

    //设置Method
    imRequest.setMethod(ImMethod.POST);

### 3.2.4 添加Header

很多时候我们需要在Header中表明身份（即在Header中传值），此时只需要写如下代码：

    //设置身份 - Header 方式
    imRequest.addHeader("token", "your token");

### 3.2.5 添加Cookie

除了可以添加Header，当然也可以添加Cookie，同样非常地简单：

    //设置Cookie
    imRequest.addCookie("key", "value");

### 3.2.6 忽略SSL证书验证（v1.2.5+）

尽管SSL证书验证可以极大增加会话的可信度，但更多的时候我们并不需要验证SSL的证书，尤其是一些自签名的证书，可以通过以下代码忽略SSL证书验证：

    //配置忽略证书
    imRequest.setIgnoreSSLCertVerify(true);

### 3.2.7 设置多种类型的InputData（v1.2.3+）

我们都经历过需要向服务端接口（或某个其它URL）提交一系列参数，参数可能数量多且数据类型复杂。如果都转成String显得非常的啰嗦和麻烦，所以在 Sinlmao Commons Network Utils 中，可以接收String、Map<String, String>、JSONObject（fastjson），ImHttpClient 会自动转换合适的格式。用法模拟代码如下：

    //传入String类型的数据（模拟）
    imRequest.setInputData(String);
    
    //传入Map<String, String>类型的数据（模拟）
    imRequest.setInputData(Map<String, String>);
    
    //传入JSONObject类型的数据（模拟）
    imRequest.setInputData(JSONObject);

### 3.2.8 文件上传（v1.3.0+）

HTTP文件上传是 Sinlmao Commons Network Utils `v1.3.0` 开始支持的新特性，将复杂甚至非常抽象的文件上传代码逻辑封装并将其简化成一个简单地数据类型，通过一致的调用方法，只需要设置特定的内容类型和方法，你可以很简单的实现文件上传需求。代码如下：

    //构建Request
    ImRequest imRequest = new ImRequest("url");
    //设置方法类型
    imRequest.setMethod(ImMethod.POST);
    //设置内容类型
    imRequest.setContentType(ImContentType.MULTIPART_FORM_DATA);
    //包装文件上传数据
    ImFileData imFileData = new ImFileData(
        "name",
        "file name",
        "file type",
        file bytes
    );
    //装入数据
    imRequest.setInputData(imFileData);
    //发送请求
    ImResponse imResponse = ImHttpClient.send(imRequest);
    //获得返回数据
    String rs = imResponse.getStringContent();
    //打印返回数据
    System.out.println(rs);

### 3.2.9 使用QueryString请求（v1.4.0+）

从 `v1.4.0` 我们开始支持QueryString请求的新特性，用于更好的支持或专项用于URL传参的场景。代码如下：

    //包装参数
    Map<String, String> pars = new HashMap<String, String>();
    pars.put("q", "baidu");
    pars.put("ie", "utf-8");
    //构建Request
    ImRequest imRequest = new ImRequest("url");
    //传入QueryString参数
    imRequest.setQueryParams(pars);
    //发送请求
    ImResponse imResponse = ImHttpClient.send(imRequest);
    //获得返回数据
    String rs = imResponse.getStringContent();
    //打印返回数据
    System.out.println(rs);

### 3.2.10 会话状态管理（v1.4.1+）

会话状态管理，这是从 Sinlmao Commons Network Utils `v1.4.1` 开始支持的新特性，使用时请注意更新版本。会话状态管理会自动帮您管理与服务端的状态或身份数据，简化在业务代码上的复杂逻辑。使用时只需要配置甚至简单构造一个 ImSession 即可，完全不需要您的额外参与，示例代码如下：

    //构建Session
    ImSession imSession = new ImSession();
    //构建Request
    ImRequest imRequest = new ImRequest("url");
    //发送请求
    ImResponse imResponse = ImHttpClient.send(imRequest, imSession);
    
### 3.2.11 自定义用户代理（User-Agent，UA）信息（v1.4.1+）

`v1.4.1` 还有有一个新的特性，就是可以自定义用户代理（User-Agent，UA）信息，示例代码如下：

    //自定义User-Agent
    imRequest.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1");

你也可用通过 `ImUserAgent` 类快捷设置。

### 3.2.12 异步模式支持（v1.4.3+）

异步模式的访问可能很多业务上都可能会需要，之前你需要自行实现，而在 `v1.4.3` 以后，我们新增了异步的模式。简单到直接设置一个标记即可，不过执行异步请求后，你的代码将不会在过程中同步，返回的ImResponse是一个空值，你需要实现ImHttpCallback获得相应正确的ImResponse。示例代码如下：

    imRequest.setAsync(true);   //设置异步标识

### 3.2.13 业务回调接口（v1.4.3+）

业务回调接口主要用在需要嵌入自有业务逻辑的场景。目前支持会话状态控制业务回调和HTTP请求业务回调（从 `v1.4.3` 开始支持）。使用回调接口前，你需要先实现接口。

假设现在要实现一个带登录的会话状态控制业务，那么应该先实现 `ImSessionCallback` 接口，示例：

    public class TestSessionCallbackImpl implements ImSessionCallback {
    
        @Override
        public boolean isAuthentication(ImSession imSession, ImRequest request) {
            return imSession.getExtraValue("isLogin") != null && (boolean) imSession.getExtraValue("isLogin");
        }
    
        @Override
        public boolean doAuthentication(ImSession imSession, ImRequest imRequest) {
            try {
                //包装参数
                Map<String, String> pars = new HashMap<String, String>();
                
                ....相关逻辑代码
                
                //构建Request
                ImRequest request = new ImRequest("xxxxxxx");
                //传入参数
                request.setInputData(pars);
                //发送请求，务必传入会话控制对象
                ImResponse imResponse = ImHttpClient.send(request, imSession);
    
                //获得返回数据
                JSONObject rs = JSON.parseObject(imResponse.getStringContent());
    
                //模拟判断登录成功
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
        
        ....相关逻辑代码
    }

业务代码简单示例如下：

    //构建会话状态控制对象类
    ImSession imSession = new ImSession();
    
    //必须配置才能生效业务接口回调
    imSession.setNeedAuthentication(true);
    imSession.setAutoAuthentication(true);
    
    //设置回调接口
    imSession.setCallback(new TestSessionCallbackImpl());
    
    //包装参数
    Map<String, String> pars = new HashMap<String, String>();
    
    ....相关逻辑代码
    
    ImRequest imRequest = new ImRequest("xxxxxxx");

    ....相关逻辑代码
    
    //发送请求，务必传入会话控制对象
    ImResponse imResponse = ImHttpClient.send(imRequest, imSession);
    
    ....相关逻辑代码

### 3.2.14 代理/抓包支持（v1.3.7+）

我们其实在 `v1.3.7` 的时候就添加了一项实验性功能，支持可配置被抓包（任意抓包代理工具），用以调试抓包，也用在一些特殊的网络环境的时候。经过了这么多版本的迭代，已经相对比较可用，现在我们决定公开这个特性。要开启这个特性非常简单，只需要如下代码：

    //配置代理支持
    imRequest.enableProxyServer(端口号);


  [1]: README.en.md