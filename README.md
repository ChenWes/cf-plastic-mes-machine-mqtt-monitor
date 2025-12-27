# cf-plastic-mes-machine-mqtt-monitor
CF MES Backend

2022-05-26

`注意，目前使用Skywalking监控API应用的性能，需要在外部将Java Agent文件复制到/tmp/skywalking-agent文件夹`

### Docker
编译镜像
```shell script
docker build -t weschen/cf-plastic-mes-machine-mqtt-monitor:20210407.1 .
```
保存镜像
```shell script
docker save -o cf-plastic-mes-machine-mqtt-monitor-new-1 weschen/cf-plastic-mes-machine-mqtt-monitor:20210407.1
```
运行镜像
```shell script
docker run
```
