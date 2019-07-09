# Book App [![Build Status](https://travis-ci.com/stefanvassilev/book-app-uebung2.svg?branch=master)](https://travis-ci.com/stefanvassilev/book-app-uebung2)

Book retrieval API
# Prerequisites

```
mvn 3.0
```

# Installation

```
mvn clean install -Pdev
```

# Deploy 
```
cf push 
```


# Swagger documentation

In order to generate: 
```bash
go get -u github.com/swaggo/swag/cmd/swag
swag init 
```