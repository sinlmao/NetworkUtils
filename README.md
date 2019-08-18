# Sinlmao Commons Network Utils

> 一个简单地、轻量级的 Java HTTP、FTP Network 集成、封装的操作类库。
>
>`update：2019-08-19` `ver：1.0.0`

----------

这是**一个简单地并且轻量级的Java用于HTTP、FTP等对Network的操作类库**。最初是因为需要符合个人使用习惯而集成并封装的小型类库，后来在公司项目和内部有一些使用。最初通过IDE打包成jar的方式供项目使用，但是由于基本所有项目都使用Maven构建，使用本地引用jar包的方式方便也不符合Maven的推荐，需要提交至Maven仓库并开源至GitHub。只是一些简单便利地封装，*不算什么技术*。

目前发布的功能（或提供的操作功能）如下：

>  - 简单易用的发送HTTP GET、POST请求；
>  - 支持设置Header数据；
>  - 支持设置Cookie数据；

通过Maven引入，直接在POM中设置如下：

    <dependency>
        <groupId>cn.sinlmao.commons</groupId>
        <artifactId>network</artifactId>
        <version>1.0.0</version>
    </dependency>

如果在Android中使用（Java 1.8+），在Gradle设置如下：

    implementation 'cn.sinlmao.commons:network:1.0.0'

文档将在后续尽快完善。