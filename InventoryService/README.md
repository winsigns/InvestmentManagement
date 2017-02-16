# 资金持仓服务

紫山资产管理系统的资金持仓服务

# 编译方法

在工程目录下，执行以下命令：

```shell
./gradlew clean build
```

# 使用方法
```shell
localhost:20002/Capital/InCapitalAccount?capital_account=10000&currency=cny&capital_change=1000000
```
```shell
localhost:20002/Capital/OutCapitalAccount?capital_account=10000&currency=cny&capital_change=100000
```
```shell
localhost:20002/Capital/AllocateCapitalAccountFromCapitalAccount?dst_capital_account=10001&src_capital_account=10000&currency=cny&capital_change=100000
```
```shell
localhost:20002/Capital/AssignInFundAccountFromCapitalAccount?dst_capital_account=10000&dst_fund_account=10000&src_capital_account=10000&currency=cny&capital_change=100000
```
```shell
localhost:20002/Capital/AssignOutFundAccountFromCapitalAccount?dst_capital_account=10000&src_capital_account=10000&src_fund_account=10000&currency=cny&capital_change=100000
```
```shell
localhost:20002/Capital/TransferFundAccountFromFundAccount?dst_capital_account=10001&dst_fund_account=10001&src_capital_account=10000&src_fund_account=10000&currency=cny&capital_change=100000
```
