# 投资管理系统

紫山资产管理系统的原型系统，采用微服务架构构建。

# 使用方法

在根目录下启动docker容器：

```
docker-compose up --build
```

访问以下地址，通过HAL-Browser查看数据模型，进行增删改查。

```
http://localhost:10010
```

关闭所有容器：

```
docker-compose down
```

# 编译方法

在各个工程目录下，执行以下命令：

```
./gradlew clean build
```

# 已集成组件

mariaDB

docker

docker-compose

REST with HATEOAS and HAL browser

actuator