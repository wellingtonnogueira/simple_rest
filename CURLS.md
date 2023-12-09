# CURLs
This is a small catalog of the verbs this project can call

## POST
```shell
curl -v -X POST http://172.27.55.252:8080/clients/ -H "Content-Type: application/json" -d '{"fullName":"wellington", "description":"um bom cliente"}'
```

## DELETE
```shell
curl -v -X DELETE http://172.27.55.252:8080/clients/683e54ec-bbef-4657-bdb6-57296215930f
```

## GET {id}
```shell
curl -v -X GET http://172.27.55.252:8080/clients/683e54ec-bbef-4657-bdb6-57296215930f
```

## GET
```shell
curl -v -X GET http://172.27.55.252:8080/clients/ -H "Accept: application/json"
```
