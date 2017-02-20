# 投资管理系统

投资管理系统的原型系统，采用微服务架构构建。

# 编译方法

在项目根目录下，执行以下命令：

```shell
./build.sh
```

# 使用方法

在根目录下启动docker容器：

```shell
docker-compose up --build
```

访问以下地址，通过HAL-Browser查看数据模型，进行增删改查。

```shell
http://localhost:8080/api
```

关闭所有容器：

```shell
docker-compose down
```

# 已集成组件

mariaDB

docker

docker-compose

REST with HATEOAS and HAL browser

actuator

zuul(api gateway)
