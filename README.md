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
  postgres:
    image: postgres
    ports:
      - 5432:5432
    volumes:
      - ./01-client_dml.sql:/docker-entrypoint-initdb.d/01-client_dml.sql
      - ~/apps/postgres:/var/lib/postgresql/data
    environment:
      - POSTGRES_PASSWORD=cl13ntS3cret
      - POSTGRES_USER=client_user
      - POSTGRES_DB=client_db

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

## PostgreSQL
There will be a container running a Postgres instance.
If it does not automatically create the table, it is possible to run the following command:
To get the _CONTAINER_ID_:
```shell
docker container ps
```
Identify the respective container and copy its ID. Then

```shell
docker exec -it [CONTAINER_ID] psql -U client_user -d client_db -a -f /docker-entrypoint-initdb.d/01-client_dml.sql
```

If you have more SQL files, just do the same.

If there's any problems connecting to the database or even the container is not running, please reach out the logs by using the following command. Maybe it will give you some idea of what has happened:
```shell
docker service logs kafka-simple-rest_postgres
```



## References
[spring kafka publish messages to topic consume messages from topic](https://medium.com/enterprise-java/spring-kafka-publish-messages-to-topic-consume-messages-from-topic-2905873dd107)
[topico sugestao docker compose kafka zookeeper e kakfa ui](https://cursos.alura.com.br/forum/topico-sugestao-docker-compose-kafka-zookeeper-e-kakfa-ui-279740)
[postgresql docker compose criando rapidamente ambientes e populando bases para testes](https://renatogroffe.medium.com/postgresql-docker-compose-criando-rapidamente-ambientes-e-populando-bases-para-testes-6c4b9a4de313)
[docker how to install postgresql using docker compose](https://medium.com/@agusmahari/docker-how-to-install-postgresql-using-docker-compose-d646c793f216)
[spring boot page deserialization pageimpl no constructor](https://stackoverflow.com/questions/52490399/spring-boot-page-deserialization-pageimpl-no-constructor)