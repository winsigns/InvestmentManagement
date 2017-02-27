# 编码规范

## 简介

## 源文件基础

文件采用UTF-8编码。

## 源文件结构

## 格式

## 命名

## 编程实践

## 文档

## 单元测试

## RESTful API设计规范

所有对象都要建立根节点下的资源endpoint，这些endpoint可以执行GET/POST/PUT/DELETE操作，例如：

```
http://localhost/funds
http://localhost/fund-accounts
http://localhost/external-cash-accounts
```

需要表达层级关系时，可以通过以下格式表达，例如以下的endpoint只有GET操作，代表获取某个基金所有的基金产品账户：

```
http://localhost/funds/1/fund-accounts
```
