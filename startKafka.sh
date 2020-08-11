#!/bin/bash

if [[ $# -eq 1 ]]; then
        NUM_BROKERS=$1
else
        NUM_BROKERS=1
fi

echo "Start zookeeper"
`zookeeper-server-start.sh -daemon ~/kafka/config/zookeeper.properties` &
sleep 10
echo "Checking zookeeper state"
a1=`echo ruok| nc localhost 2181`
if [[ $a1 = "imok" ]]; then
        echo "zookeeper fine"
else
        echo "error:zookeeper not ok"
fi

# Start brokers
echo "Get ec2 public IP"
myip=`curl --silent http://checkip.amazonaws.com`
echo $myip

echo "Replace ip to advertise listener"
for (( c=1; c<=$NUM_BROKERS; c++ ))
do
        filename=~/myconfig/s$c.properties
        #echo $filename
        sed "s/__MY_IP__/`echo $myip`/; s/__ID__/`echo $c`/" ~/myconfig/s_template.properties > $filename
        echo "Properties file modified"
        #cat $filename

        echo "Starting broker with updates"
        `kafka-server-start.sh -daemon $filename` &
        sleep 10
        if [[ $? -eq 0 ]]; then
                echo "Broker with ID=$c started successfully"
        else
                echo "Broker service faced error"
        fi

        abc=$(~/kafka/bin/zookeeper-shell.sh localhost:2181 get /brokers/ids/$c) 2>&1
        sleep 5
        echo $abc
done


echo "Start schema registry"
schema_file=~/myconfig/sr_1.properties
sed "s/__IP__/`echo $myip`/g" ~/myconfig/sr_template.properties > $schema_file
echo "Configuration modified"
#cat $schema_file
echo "Starting schema registry"
`~/confluent/bin/schema-registry-start -daemon $schema_file` 2>&1

if [[ $? -eq 0 ]]; then
        echo "Schema registry started"
else
        echo "faced issue during start, schema registry"
fi

echo "Get list of subjects"
abc=$(curl -X GET --silent http://localhost:8081/subjects) 2>&1
echo $abc|jq .