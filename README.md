# Reading Is Good

## Workflow

1. Register a new merchant
2. Login with merchant via credentials or refresh token
3. Add book/s for authenticated merchant
4. Change book's quantity for authenticated merchant
5. Register a new customer
6. Login with customer via credentials or refresh token
7. Place an order for authenticated customer
8. Placing order decreases book stock/s in a queue with distributed locking algorithm
9. Increasing or decreasing stock by customer or merchant waits each other for atomicity
10. Placing an order builds a projection for order statistics asynchronously
11. We could list these statistics as a customer for given year
12. We could get order/s by different parameters

## Technical Debt
1. DDD based clean architecture with command pattern is used.
2. MongoDb, Redis, RabbitMq is used.
3. All of the request validations are done, DDD validations like loading or creating aggregates not completely done.
4. Redisson lock resolved stock management problem but having a tradeoff, user's actions are waiting each other.
5. Happy path integration tests are added for maximum coverage. Unit tests also should be written, there was no time for me.
6. Test containers should had been added if time duration was enough for me.
7. First, framework module should be built as maven. And then, reading-is-good-api ready for running.
8. Swagger-UI is ready for api.
9. Postman collection is added under tools/postman directory.

## Dockerization

In project home directory open terminal and execute these commands:

**Build Docker File**
`docker build -f tools/docker/dockerfile -t rig-api .`

**Compose up api with mongo,redis,rabbit**
`docker-compose -f tools/docker/docker-compose.yml -p getir up -d`


#### Have a nice review :)


