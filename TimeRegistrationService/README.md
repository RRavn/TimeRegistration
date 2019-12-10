# TimeRegistrationService

Service to register working time, get some simple statistik and info.

## Documentation

### Project used libraries
Spring Boot, LiquiBase, Swagger, JPA, REST Controller, HATEOAS, Microsoft SQLServer database, H2 inmemory database and Docker

### Running the project
1. docker-compose up --build (if you wan't to run it outside a Docker container you have to change the configuration application-local.yml spring.datasource.url has to point to localhost instead of the Docker router 172.17.0.1)
2. in browser open http://localhost:8080/swagger-ui.html

### Short user stories
#### Employers needs
- Defining fix time, flex account max. hours, flex account min. hours, overtime course and compensatory leave course.
- Following up on the coworkers compliance with the fix time
- Monthly status on coworkers flex time accounts, overtime accounts and compensatory leave status


#### Coworkers needs
- Registration of working time based on arriving and leaving times
- Marking time as overtime for compensatory leave or overtime for payout
- See status on flex time account and overtime account


| Coworker no | Name | Department | Arriving time | Leaving time | Time category |
| ----------- | ----- | ---------- | ------------- | ------------ | ------------- |
| 1 | Hugo | IT | 2019-10-21 08:00 | 2019-10-21 15:00 | Normal |
| 2 | Jens | HR | 2019-10-21 07:00 | 2019-10-21 14:00 | Normal |
| 2 | Jens | HR | 2019-10-21 18:00 | 2019-10-21 20:00 | Overtime payout |
| 3 | Hans | IT | 2019-10-21 03:00 | 2019-10-21 08:00 | Overtime comp. leave |


#### Normalization
##### 1NF - No changes.
##### 2NF

| Coworker no | Name | Department |
| ------------ | ---- | ---------- |
| 1 | Hugo | IT |
| 2 | Jens | HR |
| 3 | Hans | IT |

| Registration id | Arriving time | Leaving time | Time category |
| --------------- | ------------- | ------------ | ------------- |
| 1 | 2019-10-21 08:00 | 2019-10-21 15:00 | Normal |
| 2 | 2019-10-21 07:00 | 2019-10-21 14:00 | Normal |
| 3 | 2019-10-21 18:00 | 2019-10-21 20:00 | Overtime payout |
| 4 | 2019-10-21 03:00 | 2019-10-21 08:00 | Overtime comp. leave |

| Coworker no | Registration id |
| ------------ | --------------- |
| 1| 1 |
| 2 | 2 |
| 2 | 3 |
| 3 | 4 |


##### 3NF

| Coworker no | Name | Department id |
| --------------- | ---- | --------- |
| 1 | Hugo | 1 |
| 2 | Jens | 2 |
| 3 | Hans | 1 |

| Department id | Department |
| ------------- | ---------- |
| 1 | IT |
| 2 | HR |

| Registration id | Arrive time | Leave time | Time category id |
| --------------- | ----------- | ---------- | ---------------- |
| 1 | 2019-10-21 08:00 | 2019-10-21 15:00 | 1 |
| 2 | 2019-10-21 07:00 | 2019-10-21 14:00 | 1 |
| 3 | 2019-10-21 18:00 | 2019-10-21 20:00 | 2 |
| 4 | 2019-10-21 03:00 | 2019-10-21 08:00 | 3 |

| Coworker no | Registration id |
| ------------ | --------------- |
| 1 | 1 |
| 2 | 2 |
| 2 | 3 |
| 3 | 4 |

| Time category id | Time category |
| ---------------- | ------------- |
| 1 | Normal |
| 2 | Overtime payout |
| 3 | Overtime comp. leave |


#### Java objekt model

![Java object model](https://raw.githubusercontent.com/RRavn/TimeRegistration/master/TimeRegistrationService/doc/TimeRegistrationServiceModel.png)
