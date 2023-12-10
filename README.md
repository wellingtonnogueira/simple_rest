# simple_rest
This is a simple project implementing the rest API and producing messages to Kafka

# GITHUB
Code published on [Simple Rest](https://github.com/wellingtonnogueira/simple_rest)

# KAFKA
This project requires KAFKA, so it is important to run it first.
Based on the searches I did, it is recommended to have ZOOKEEPER and KAFKA images, running together.

## What is needed:

Docker compose file: _docker-compose.yml_ containing the information below:
```yaml
version: "3"

services:
  kafka:
    image: bitnami/kafka:latest
    restart: on-failure
    ports:
      - 9092:9092
    environment:
      - KAFKA_CFG_BROKER_ID=1
      - KAFKA_CFG_LISTENERS=PLAINTEXT://:9092
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://{{IP_KAFKA_HOST}}:9092
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - KAFKA_CFG_NUM_PARTITIONS=3
      - ALLOW_PLAINTEXT_LISTENER=yes
    depends_on:
      - zookeeper

  zookeeper:
    image: bitnami/zookeeper:latest
    ports:
      - 2181:2181
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes

  kafka-ui:
    image: provectuslabs/kafka-ui
    depends_on:
      - kafka
      - zookeeper
    ports:
      - "8080:8080"
    restart: always
    environment:
      - KAFKA_CLUSTERS_0_NAME=your_name
      - KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=kafka:9092
      - KAFKA_CLUSTERS_0_ZOOKEEPER=zookeeper:2181
```
> Thanks to this topic at 
> <cite> [ Alura Forum (pt-BR)](https://cursos.alura.com.br/forum/topico-sugestao-docker-compose-kafka-zookeeper-e-kakfa-ui-279740) </cite>

## How to run it
```shell
docker swarm init
docker stack deploy -c docker-compose.yml kafka-simple-rest
```
To confirm the cluster is running, access: http://_{{WSL_IP}}_:8080/

To identify the WSL IP, use:
```shell
ip addr show eth0
```

## REST calls
It is possible to find the CURLs on [CURLS.md](CURLS.md)