
# shop-demo

Shop Kotlin/ktor demo application

## Usage

### Building

`./gradlew clean shadowJar` or `./compile.sh` 

### Run the application locally

`PORT=8080 java -jar build/libs/shop-demo-uberjar.jar` or `./run.sh`

### Docker

```sh
$ docker build -t shop-demo . 
$ docker run -p8080:8080 shop-demo
```

or

```sh
$ ./docker.sh
```

### Deploy to Heroku

```sh
$ heroku create
$ git push heroku master
$ heroku open
```

## License

Copyright ©  kapware.com

