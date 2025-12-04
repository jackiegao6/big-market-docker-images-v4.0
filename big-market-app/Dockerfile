# 基础镜像
FROM swr.cn-north-4.myhuaweicloud.com/ddn-k8s/docker.io/openjdk:8u212-jre-slim

# 作者
MAINTAINER gzc

# 配置
ENV PARAMS=""
ENV JAVA_OPTS=""

# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 添加应用
ADD target/big-market-app.jar /big-market-app.jar

ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /big-market-app.jar $PARAMS"]

# 启动命令，开启 JMX 远程监控
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Dcom.sun.management.jmxremote \
-Dcom.sun.management.jmxremote.port=23456 \
-Dcom.sun.management.jmxremote.rmi.port=23456 \
-Dcom.sun.management.jmxremote.local.only=false \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dcom.sun.management.jmxremote.ssl=false \
-Djava.rmi.server.hostname=0.0.0.0 \
-jar /big-market-app.jar $PARAMS"]