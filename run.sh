#!/bin/sh

echo "Starting Launch App"
set_launch_app() {
	if [[ $SKYWALKING_AGENT_LAUNCH_FLAG == "Y" || $SKYWALKING_AGENT_LAUNCH_FLAG == "y" ]]; then
		echo "Skywalking Agent Need, Starting..."
		exec java  $JAVA_OPTS  -javaagent:/tmp/skywalking-agent/skywalking-agent.jar -Dskywalking.agent.service_name=$SKYWALKING_AGENT_SERVICE_NAME -Dskywalking.collector.backend_service=$SKYWALKING_COLLECTOR_BACKEND_SERVICE -jar -Duser.timezone=$USER_TIMEZONE  -Djavax.net.ssl.trustStore=$TRUST_STORE -Djavax.net.ssl.trustStorePassword=$TRUST_STORE_PASSWORD cf.webapi.jar
	else
		echo "Skywalking Agent No Need, Starting..."
		exec java  $JAVA_OPTS  -jar -Duser.timezone=$USER_TIMEZONE -Djavax.net.ssl.trustStore=$TRUST_STORE -Djavax.net.ssl.trustStorePassword=$TRUST_STORE_PASSWORD cf.webapi.jar
	fi
}
set_launch_app
echo "End Launch App"
exit 0
