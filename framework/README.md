# Framework
It has usually abstract layers, bring reusabilty for new projects.

## Modules

1. root -> Set parent as spring boot and some common dependencies for applications.

2. api -> Gives application instances(api,consumer,job) to spring-web and sub dependencies. It includes http handlers, web configurations, api error response and building.

3. application -> It gives pipelineR(command pattern for clean architecture) dependency to application layer(application services,use cases etc)

4. bus -> Distributed message bus implementations goes here

5. data -> It includes spring-data dependency, AbstractEntity and Pageable implementations

6. data-mongo -> It depends data module and spring-mongo

7. domain -> It includes abstractions for project's domain layer, such as AggregateRoot, AbstractEvent, event dispatching aspect

8. httpclient -> It includes spring-web for resttemplate and httpclient abstractions 

9. jwt -> It includes jwtbuilder, jwtresolver and company's claims model

10. test-> It includes spring-test, javafaker libraries, abstract test models. It should be just used for project's test packages.


