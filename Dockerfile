FROM adoptopenjdk/openjdk8-openj9:alpine-slim
MAINTAINER WesChen(chenxuhua0530@163.com)

VOLUME /tmp
COPY cf.webapi/target/cf.webapi.jar ./

# 复制证书 server.jks
# COPY server.jks ./

# 复制Shell脚本
COPY run.sh ./
# 授权Shell脚本
RUN chmod a+x run.sh

#设置变量
# 声明Skywalking配置变量
ENV SKYWALKING_AGENT_LAUNCH_FLAG="Y"
ENV SKYWALKING_AGENT_SERVICE_NAME=""
ENV SKYWALKING_COLLECTOR_BACKEND_SERVICE=""

# 默认东八区
ENV USER_TIMEZONE="GMT+08"

# 证书配置
# -Djavax.net.ssl.trustStore=$TRUST_STORE -Djavax.net.ssl.trustStorePassword=$TRUST_STORE_PASSWORD
ENV TRUST_STORE=""
ENV TRUST_STORE_PASSWORD=""

# JAVA_OPS配置
ENV JAVA_OPTS=""

#公开端口
EXPOSE 18081

#设置启动命令
# 增加Skywalking Java Agent
# ENTRYPOINT ["sh","-c","java $JAVA_OPTS -javaagent:/tmp/skywalking-agent/skywalking-agent.jar -Dskywalking.agent.service_name=$SKYWALKING_AGENT_SERVICE_NAME   -Dskywalking.collector.backend_service=$SKYWALKING_COLLECTOR_BACKEND_SERVICE -jar -Duser.timezone=$USER_TIMEZONE cf.webapi.jar "]
# ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar -Duser.timezone=$USER_TIMEZONE -Djavax.net.ssl.trustStore=$TRUST_STORE -Djavax.net.ssl.trustStorePassword=$TRUST_STORE_PASSWORD cf.webapi.jar "]

# 运行Shell脚本
CMD ["./run.sh"]
