FROM swr.cn-north-4.myhuaweicloud.com/ddn-k8s/docker.io/azul/zulu-openjdk:8u462-jdk

ENV JAVA_OPTS="-Xms6g -Xmx6g -XX:+useG1GC -XX:MaxGCPauseMillis=150 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/java/app-heap"
ENV PARAMS=""

ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD target/big-market-app.jar /big-market-app.jar

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /big-market-app.jar $PARAMS"]