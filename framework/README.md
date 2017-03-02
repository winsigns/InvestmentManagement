# 投资管理系统

投资管理系统的框架部分

## 编译方法

在项目根目录下，执行以下命令：

```shell
gradlew clean uploadArchives
```

## 使用方法

在编译成功后，会自动发布到

```shell
http://120.77.178.46/artifactory/winsigns
```
其他服务修改build.gradle
```shell
repositories下增加
    maven { url "http://120.77.178.46/artifactory/winsigns/"}
dependencies下增加
	compile('com.winsigns:investment-framework:0.0.1-SNAPSHOT')
```

## 集成功能
```shell
hal-browser
i18nHelper
measure
KafkaConfiguration
AbstractEntity
```
