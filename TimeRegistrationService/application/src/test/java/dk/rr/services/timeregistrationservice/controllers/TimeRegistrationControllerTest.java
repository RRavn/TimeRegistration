package dk.rr.services.timeregistrationservice.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("unittest")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TimeRegistrationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void departmentTest() throws Exception {
        String serverUrl = "http://localhost:" + port;
        System.out.println(this.restTemplate.getForObject(serverUrl + "/Department",
                String.class));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        //Test of GET /Department. Get List of Departments
        ResponseEntity<String> response = this.restTemplate.exchange(serverUrl + "/Department", HttpMethod.GET, entity, String.class);
        String expectedJson = "{\"_embedded\":{\"departmentList\":[{\"id\":1,\"name\":\"IT\",\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Department/id/1\"},\"departments\":{\"href\":\"" + serverUrl + "/Department\"}}},{\"id\":2,\"name\":\"HR\",\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Department/id/2\"},\"departments\":{\"href\":\"" + serverUrl + "/Department\"}}},{\"id\":3,\"name\":\"Logistik\",\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Department/id/3\"},\"departments\":{\"href\":\"" + serverUrl + "/Department\"}}}]},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Department\"}}}";
                //"{\"departmentList\":[{\"id\":1,\"name\":\"IT\",\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Department/id/1\"},\"departments\":{\"href\":\"" + serverUrl + "/Department\"}}}],\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Department\"},\"departments\":{\"href\":\"" + serverUrl + "/Department\"}}}";

        System.out.println("GET /Department: " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        response = this.restTemplate.exchange(serverUrl + "/Department/id/10", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        //Test of POST /Department. Creates a Department
        HttpEntity<String> departmentPostEntity = new HttpEntity<String>("{\"name\":\"Test department\"}", headers);
        response = this.restTemplate.exchange(serverUrl + "/Department", HttpMethod.POST, departmentPostEntity, String.class);
        expectedJson = "{\"id\":4,\"name\":\"Test department\",\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Department/id/4\"},\"departments\":{\"href\":\"" + serverUrl + "/Department\"}}}";

        System.out.println("POST /Department: " + response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        departmentPostEntity = new HttpEntity<String>("{\"id\":1,\"namer\":\"Test department\"}", headers);
        response = this.restTemplate.exchange(serverUrl + "/Department", HttpMethod.POST, departmentPostEntity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        //Test of GET /Department/id/{id}. Get Department by id
        response = this.restTemplate.exchange(serverUrl + "/Department/id/4", HttpMethod.GET, entity, String.class);
        expectedJson = "{\"id\":4,\"name\":\"Test department\",\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Department/id/4\"},\"departments\":{\"href\":\"" + serverUrl + "/Department\"}}}";

        System.out.println("GET /Department/id/{id}: " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        response = this.restTemplate.exchange(serverUrl + "/Department/id/10", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        //Test of PUT /Department/{id}. Replaces a Department
        HttpEntity<String> departmentPutEntity = new HttpEntity<String>("{\"name\":\"Test department replaced\"}", headers);
        response = this.restTemplate.exchange(serverUrl + "/Department/4", HttpMethod.PUT, departmentPutEntity, String.class);
        expectedJson = "{\"id\":4,\"name\":\"Test department replaced\",\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Department/id/4\"},\"departments\":{\"href\":\"" + serverUrl + "/Department\"}}}";

        System.out.println("PUT /Department/{id}: " + response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        departmentPutEntity = new HttpEntity<String>("{\"namefr\":\"Test department not replaced\"}", headers);
        response = this.restTemplate.exchange(serverUrl + "/Department/4", HttpMethod.PUT, departmentPutEntity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        //Test of DELETE /Department/{id}. Deletes a Department
        response = this.restTemplate.exchange(serverUrl + "/Department/4", HttpMethod.DELETE, entity, String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        response = this.restTemplate.exchange(serverUrl + "/Department/100", HttpMethod.DELETE, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void timeCategoryTest() throws Exception {
        String serverUrl = "http://localhost:" + port;
        System.out.println(this.restTemplate.getForObject(serverUrl + "/TimeCategory",
                String.class));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        //Test of GET /TimeCategory. Get List of TimeCategorys
        ResponseEntity<String> response = this.restTemplate.exchange(serverUrl + "/TimeCategory", HttpMethod.GET, entity, String.class);
        String expectedJson = "{\"_embedded\":{\"timeCategoryList\":[{\"id\":1,\"name\":\"Normal\",\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/TimeCategory/id/1\"},\"timecategory\":{\"href\":\"" + serverUrl + "/TimeCategory\"}}},{\"id\":2,\"name\":\"Overarbejde udbetalt\",\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/TimeCategory/id/2\"},\"timecategory\":{\"href\":\"" + serverUrl + "/TimeCategory\"}}},{\"id\":3,\"name\":\"Overarbejde afspadsering\",\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/TimeCategory/id/3\"},\"timecategory\":{\"href\":\"" + serverUrl + "/TimeCategory\"}}}]},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/TimeCategory\"}}}";

        System.out.println("GET /TimeCategory: " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        response = this.restTemplate.exchange(serverUrl + "/TimeCategory/id/10", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        //Test of POST /TimeCategory. Creates a TimeCategory
        HttpEntity<String> timeCategoryPostEntity = new HttpEntity<String>("{\"name\":\"Test TimeCategory\"}", headers);
        response = this.restTemplate.exchange(serverUrl + "/TimeCategory", HttpMethod.POST, timeCategoryPostEntity, String.class);
        expectedJson = "{\"id\":4,\"name\":\"Test TimeCategory\",\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/TimeCategory/id/4\"},\"timecategory\":{\"href\":\"" + serverUrl + "/TimeCategory\"}}}";

        System.out.println("POST /TimeCategory: " + response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        timeCategoryPostEntity = new HttpEntity<String>("{\"id\":1,\"namer\":\"Test TimeCategory\"}", headers);
        response = this.restTemplate.exchange(serverUrl + "/TimeCategory", HttpMethod.POST, timeCategoryPostEntity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        //Test of GET /TimeCategory/id/{id}. Get TimeCategory by id
        response = this.restTemplate.exchange(serverUrl + "/TimeCategory/id/4", HttpMethod.GET, entity, String.class);
        expectedJson = "{\"id\":4,\"name\":\"Test TimeCategory\",\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/TimeCategory/id/4\"},\"timecategory\":{\"href\":\"" + serverUrl + "/TimeCategory\"}}}";

        System.out.println("GET /TimeCategory/id/{id}: " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        response = this.restTemplate.exchange(serverUrl + "/TimeCategory/id/10", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        //Test of PUT /TimeCategory/{id}. Replaces a TimeCategory
        HttpEntity<String> timeCategoryPutEntity = new HttpEntity<String>("{\"name\":\"Test TimeCategory replaced\"}", headers);
        response = this.restTemplate.exchange(serverUrl + "/TimeCategory/4", HttpMethod.PUT, timeCategoryPutEntity, String.class);
        expectedJson = "{\"id\":4,\"name\":\"Test TimeCategory replaced\",\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/TimeCategory/id/4\"},\"timecategory\":{\"href\":\"" + serverUrl + "/TimeCategory\"}}}";

        System.out.println("PUT /TimeCategory/{id}: " + response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        timeCategoryPutEntity = new HttpEntity<String>("{\"namefr\":\"Test TimeCategory not replaced\"}", headers);
        response = this.restTemplate.exchange(serverUrl + "/TimeCategory/4", HttpMethod.PUT, timeCategoryPutEntity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        //Test of DELETE /TimeCategory/{id}. Deletes a TimeCategory
        response = this.restTemplate.exchange(serverUrl + "/TimeCategory/4", HttpMethod.DELETE, entity, String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        response = this.restTemplate.exchange(serverUrl + "/TimeCategory/100", HttpMethod.DELETE, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void coWorkerTest() throws Exception {
        String serverUrl = "http://localhost:" + port;
        System.out.println(this.restTemplate.getForObject(serverUrl + "/CoWorker",
                String.class));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        //Test of GET /CoWorker. Get List of CoWorkers
        ResponseEntity<String> response = this.restTemplate.exchange(serverUrl + "/CoWorker", HttpMethod.GET, entity, String.class);
        String expectedJson = "{\"_embedded\":{\"coWorkerList\":[{\"id\":1,\"name\":\"Rene\",\"department\":{\"id\":1,\"name\":\"IT\"},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/CoWorker/id/1\"},\"coworker\":{\"href\":\"" + serverUrl + "/CoWorker\"}}},{\"id\":2,\"name\":\"Knold\",\"department\":{\"id\":2,\"name\":\"HR\"},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/CoWorker/id/2\"},\"coworker\":{\"href\":\"" + serverUrl + "/CoWorker\"}}},{\"id\":3,\"name\":\"Tot\",\"department\":{\"id\":3,\"name\":\"Logistik\"},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/CoWorker/id/3\"},\"coworker\":{\"href\":\"" + serverUrl + "/CoWorker\"}}}]},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/CoWorker\"}}}";

        System.out.println("GET /CoWorker: " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        response = this.restTemplate.exchange(serverUrl + "/CoWorker/id/10", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        //Test of POST /CoWorker. Creates a CoWorker
        HttpEntity<String> coWorkerPostEntity = new HttpEntity<String>("{\"name\":\"Hugo\",\"department\":{\"id\":2,\"name\":\"HR\"}}", headers);
        response = this.restTemplate.exchange(serverUrl + "/CoWorker", HttpMethod.POST, coWorkerPostEntity, String.class);
        expectedJson = "{\"id\":4,\"name\":\"Hugo\",\"department\":{\"id\":2,\"name\":\"HR\"},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/CoWorker/id/4\"},\"coworker\":{\"href\":\"" + serverUrl + "/CoWorker\"}}}";

        System.out.println("POST /CoWorker: " + response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        coWorkerPostEntity = new HttpEntity<String>("{\"id\":1,\"namer\":\"Test CoWorker\"}", headers);
        response = this.restTemplate.exchange(serverUrl + "/CoWorker", HttpMethod.POST, coWorkerPostEntity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        //Test of GET /CoWorker/id/{id}. Get CoWorker by id
        response = this.restTemplate.exchange(serverUrl + "/CoWorker/id/4", HttpMethod.GET, entity, String.class);
        expectedJson = "{\"id\":4,\"name\":\"Hugo\",\"department\":{\"id\":2,\"name\":\"HR\"},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/CoWorker/id/4\"},\"coworker\":{\"href\":\"" + serverUrl + "/CoWorker\"}}}";

        System.out.println("GET /CoWorker/id/{id}: " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        response = this.restTemplate.exchange(serverUrl + "/CoWorker/id/10", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        //Test of GET /CoWorker/name/{name}. Get CoWorker by name
        response = this.restTemplate.exchange(serverUrl + "/CoWorker/name/Hugo", HttpMethod.GET, entity, String.class);
        expectedJson = "{\"id\":4,\"name\":\"Hugo\",\"department\":{\"id\":2,\"name\":\"HR\"},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/CoWorker/id/4\"},\"coworker\":{\"href\":\"" + serverUrl + "/CoWorker\"}}}";

        System.out.println("GET /CoWorker/name/{name}: " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        response = this.restTemplate.exchange(serverUrl + "/CoWorker/name/Yvonne", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        //Test of PUT /CoWorker/{id}. Replaces a CoWorker
        HttpEntity<String> coWorkerPutEntity = new HttpEntity<String>("{\"id\":4,\"name\":\"Morten\",\"department\":{\"id\":2,\"name\":\"HR\"}}", headers);
        response = this.restTemplate.exchange(serverUrl + "/CoWorker/3", HttpMethod.PUT, coWorkerPutEntity, String.class);
        expectedJson = "{\"id\":4,\"name\":\"Morten\",\"department\":{\"id\":2,\"name\":\"HR\"},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/CoWorker/id/4\"},\"coworker\":{\"href\":\"" + serverUrl + "/CoWorker\"}}}";

        System.out.println("PUT /CoWorker/{id}: " + response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        coWorkerPutEntity = new HttpEntity<String>("{\"namefr\":\"Test CoWorker not replaced\"}", headers);
        response = this.restTemplate.exchange(serverUrl + "/CoWorker/4", HttpMethod.PUT, coWorkerPutEntity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        //Test of DELETE /CoWorker/{id}. Deletes a CoWorker
        response = this.restTemplate.exchange(serverUrl + "/CoWorker/4", HttpMethod.DELETE, entity, String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        response = this.restTemplate.exchange(serverUrl + "/CoWorker/100", HttpMethod.DELETE, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void coWorkerPropertiesTest() throws Exception {
        String serverUrl = "http://localhost:" + port;
        System.out.println(this.restTemplate.getForObject(serverUrl + "/CoWorkerProperties",
                String.class));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        //Test of GET /CoWorkerProperties. Get List of CoWorkerProperties
        ResponseEntity<String> response = this.restTemplate.exchange(serverUrl + "/CoWorkerProperties", HttpMethod.GET, entity, String.class);
        String expectedJson = "{\"_embedded\":{\"coWorkerPropertiesList\":[{\"id\":1,\"fixedArriveTime\":\"09:00:00\",\"fixedLeaveTime\":\"15:00:00\",\"flexTimeMin\":-15,\"flexTimeMax\":30,\"overTimeRate\":2,\"coworker\":{\"id\":1,\"name\":\"Rene\",\"department\":{\"id\":1,\"name\":\"IT\"}},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/CoWorkerProperties/id/1\"},\"coworkerproperties\":{\"href\":\"" + serverUrl + "/CoWorkerProperties\"}}},{\"id\":2,\"fixedArriveTime\":\"10:00:00\",\"fixedLeaveTime\":\"16:00:00\",\"flexTimeMin\":-25,\"flexTimeMax\":40,\"overTimeRate\":2,\"coworker\":{\"id\":2,\"name\":\"Knold\",\"department\":{\"id\":2,\"name\":\"HR\"}},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/CoWorkerProperties/id/2\"},\"coworkerproperties\":{\"href\":\"" + serverUrl + "/CoWorkerProperties\"}}}]},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/CoWorkerProperties\"}}}";

        System.out.println("GET /CoWorkerProperties: " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        response = this.restTemplate.exchange(serverUrl + "/CoWorkerProperties/id/10", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        //Test of POST /CoWorkerProperties. Creates a CoWorkerProperties
        HttpEntity<String> coWorkerPropertiesPostEntity = new HttpEntity<String>("{\"fixedArriveTime\":\"10:00:00\",\"fixedLeaveTime\":\"16:00:00\",\"flexTimeMin\":-20,\"flexTimeMax\":50,\"overTimeRate\":3,\"coworker\":{\"id\":3,\"name\":\"Tot\",\"department\":{\"id\":3,\"name\":\"Logistik\"}}}", headers);
        response = this.restTemplate.exchange(serverUrl + "/CoWorkerProperties", HttpMethod.POST, coWorkerPropertiesPostEntity, String.class);
        expectedJson = "{\"id\":3,\"fixedArriveTime\":\"10:00:00\",\"fixedLeaveTime\":\"16:00:00\",\"flexTimeMin\":-20,\"flexTimeMax\":50,\"overTimeRate\":3,\"coworker\":{\"id\":3,\"name\":\"Tot\",\"department\":{\"id\":3,\"name\":\"Logistik\"}},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/CoWorkerProperties/id/3\"},\"coworkerproperties\":{\"href\":\"" + serverUrl + "/CoWorkerProperties\"}}}";

        System.out.println("POST /CoWorkerProperties: " + response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        coWorkerPropertiesPostEntity = new HttpEntity<String>("{\"fixedArriveTimer\":\"10:00:00\",\"fixedLeaveTime\":\"17:00:00\",\"flexTimeMin\":-20,\"flexTimeMax\":50,\"overTimeRate\":3,\"coworker\":{\"id\":1,\"name\":\"Rene\",\"department\":{\"id\":1,\"name\":\"IT\"}}}", headers);
        response = this.restTemplate.exchange(serverUrl + "/CoWorkerProperties", HttpMethod.POST, coWorkerPropertiesPostEntity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        //Test of GET /CoWorkerProperties/id/{id}. Get CoWorkerProperties by id
        response = this.restTemplate.exchange(serverUrl + "/CoWorkerProperties/id/3", HttpMethod.GET, entity, String.class);
        expectedJson = "{\"id\":3,\"fixedArriveTime\":\"10:00:00\",\"fixedLeaveTime\":\"16:00:00\",\"flexTimeMin\":-20,\"flexTimeMax\":50,\"overTimeRate\":3,\"coworker\":{\"id\":3,\"name\":\"Tot\",\"department\":{\"id\":3,\"name\":\"Logistik\"}},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/CoWorkerProperties/id/3\"},\"coworkerproperties\":{\"href\":\"" + serverUrl + "/CoWorkerProperties\"}}}";

        System.out.println("GET /CoWorkerProperties/id/{id}: " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        response = this.restTemplate.exchange(serverUrl + "/CoWorkerProperties/id/10", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        //Test of GET /CoWorkerProperties/coworkerid/{coWorkerId}. Get CoWorkerProperties by coWorkerId
        response = this.restTemplate.exchange(serverUrl + "/CoWorkerProperties/coworkerid/3", HttpMethod.GET, entity, String.class);
        expectedJson = "{\"id\":3,\"fixedArriveTime\":\"10:00:00\",\"fixedLeaveTime\":\"16:00:00\",\"flexTimeMin\":-20,\"flexTimeMax\":50,\"overTimeRate\":3,\"coworker\":{\"id\":3,\"name\":\"Tot\",\"department\":{\"id\":3,\"name\":\"Logistik\"}},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/CoWorkerProperties/id/3\"},\"coworkerproperties\":{\"href\":\"" + serverUrl + "/CoWorkerProperties\"}}}";

        System.out.println("GET /CoWorkerProperties/coworkerid/{coWorkerId}: " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        response = this.restTemplate.exchange(serverUrl + "/CoWorkerProperties/coWorkerId/10", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        //Test of PUT /CoWorkerProperties/{coWorkerId}. Replaces a CoWorkerProperties
        HttpEntity<String> coWorkerPropertiesPutEntity = new HttpEntity<String>("{\"id\":3,\"fixedArriveTime\":\"11:00:00\",\"fixedLeaveTime\":\"17:00:00\",\"flexTimeMin\":-25,\"flexTimeMax\":40,\"overTimeRate\":3,\"coworker\":{\"id\":3,\"name\":\"Tot\",\"department\":{\"id\":3,\"name\":\"Logistik\"}}}", headers);
        response = this.restTemplate.exchange(serverUrl + "/CoWorkerProperties/2", HttpMethod.PUT, coWorkerPropertiesPutEntity, String.class);
        expectedJson = "{\"id\":3,\"fixedArriveTime\":\"11:00:00\",\"fixedLeaveTime\":\"17:00:00\",\"flexTimeMin\":-25,\"flexTimeMax\":40,\"overTimeRate\":3,\"coworker\":{\"id\":3,\"name\":\"Tot\",\"department\":{\"id\":3,\"name\":\"Logistik\"}},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/CoWorkerProperties/id/3\"},\"coworkerproperties\":{\"href\":\"" + serverUrl + "/CoWorkerProperties\"}}}";

        System.out.println("PUT /CoWorkerProperties/{coWorkerId}: " + response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        coWorkerPropertiesPutEntity = new HttpEntity<String>("{\"id\":3,\"fixedArriveTimer\":\"11:00:00\",\"fixedLeaveTime\":\"17:00:00\",\"flexTimeMin\":-25,\"flexTimeMax\":40,\"overTimeRate\":3,\"coworker\":{\"id\":3,\"name\":\"Tot\",\"department\":{\"id\":3,\"name\":\"Logistik\"}}}", headers);
        response = this.restTemplate.exchange(serverUrl + "/CoWorkerProperties/3", HttpMethod.PUT, coWorkerPropertiesPutEntity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        //Test of DELETE /CoWorkerProperties/{id}. Deletes a CoWorkerProperties
        response = this.restTemplate.exchange(serverUrl + "/CoWorkerProperties/3", HttpMethod.DELETE, entity, String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        response = this.restTemplate.exchange(serverUrl + "/CoWorkerProperties/100", HttpMethod.DELETE, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void registrationTest() throws Exception {
        String serverUrl = "http://localhost:" + port;
        System.out.println(this.restTemplate.getForObject(serverUrl + "/Registration",
                String.class));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        //Test of POST /Registration. Creates a Registration
        HttpEntity<String> registrationPostEntity = new HttpEntity<String>("{\"id\":1,\"arriveTime\":\"2019-12-01T08:10:10.100\",\"leaveTime\":\"2019-12-01T16:10:10.100\",\"timeCategory\":{\"id\":1,\"name\":\"Normal\"},\"coworker\":{\"id\":1,\"name\":\"Rene\",\"department\":{\"id\":1,\"name\":\"IT\"}}}", headers);
        ResponseEntity<String> response = this.restTemplate.exchange(serverUrl + "/Registration", HttpMethod.POST, registrationPostEntity, String.class);
        String expectedJson = "{\"id\":1,\"arriveTime\":\"2019-12-01T08:10:10.100+0000\",\"leaveTime\":\"2019-12-01T16:10:10.100+0000\",\"timeCategory\":{\"id\":1,\"name\":\"Normal\"},\"coworker\":{\"id\":1,\"name\":\"Rene\",\"department\":{\"id\":1,\"name\":\"IT\"}},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Registration/id/1\"},\"registration\":{\"href\":\"" + serverUrl + "/Registration\"}}}";

        System.out.println("POST /Registration: " + response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        registrationPostEntity = new HttpEntity<String>("{\"id\":1,\"arriveTime\":\"2019-12-01T08:10:10.100\",\"leaveTime\":\"2019-12-01T16:10:10.100\",\"timeCategory\":{\"id\":1,\"name\":\"Normal\"},\"coworker\":{\"id\":1,\"name\":\"Rene\",\"department\":{\"id\":1,\"name\":\"IT\"}}", headers);
        response = this.restTemplate.exchange(serverUrl + "/Registration", HttpMethod.POST, registrationPostEntity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        //Test of GET /Registration. Get List of Registrations
        response = this.restTemplate.exchange(serverUrl + "/Registration", HttpMethod.GET, entity, String.class);
        expectedJson = "{\"_embedded\":{\"registrationList\":[{\"id\":1,\"arriveTime\":\"2019-12-01T08:10:10.100+0000\",\"leaveTime\":\"2019-12-01T16:10:10.100+0000\",\"timeCategory\":{\"id\":1,\"name\":\"Normal\"},\"coworker\":{\"id\":1,\"name\":\"Rene\",\"department\":{\"id\":1,\"name\":\"IT\"}},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Registration/id/1\"},\"registration\":{\"href\":\"" + serverUrl + "/Registration\"}}}]},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Registration\"}}}";

        System.out.println("GET /Registration: " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        response = this.restTemplate.exchange(serverUrl + "/Registration/id/10", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        //Test of GET /Registration/id/{id}. Get Registration by id
        response = this.restTemplate.exchange(serverUrl + "/Registration/id/1", HttpMethod.GET, entity, String.class);
        expectedJson = "{\"id\":1,\"arriveTime\":\"2019-12-01T08:10:10.100+0000\",\"leaveTime\":\"2019-12-01T16:10:10.100+0000\",\"timeCategory\":{\"id\":1,\"name\":\"Normal\"},\"coworker\":{\"id\":1,\"name\":\"Rene\",\"department\":{\"id\":1,\"name\":\"IT\"}},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Registration/id/1\"},\"registration\":{\"href\":\"" + serverUrl + "/Registration\"}}}";

        System.out.println("GET /Registration/id/{id}: " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        response = this.restTemplate.exchange(serverUrl + "/Registration/id/10", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        //Test of GET /Registration/coworkerid/{coWorkerId}. Get Registration by coWorkerId
        response = this.restTemplate.exchange(serverUrl + "/Registration/coworkerid/1", HttpMethod.GET, entity, String.class);
        expectedJson = "{\"_embedded\":{\"registrationList\":[{\"id\":1,\"arriveTime\":\"2019-12-01T08:10:10.100+0000\",\"leaveTime\":\"2019-12-01T16:10:10.100+0000\",\"timeCategory\":{\"id\":1,\"name\":\"Normal\"},\"coworker\":{\"id\":1,\"name\":\"Rene\",\"department\":{\"id\":1,\"name\":\"IT\"}},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Registration/id/1\"},\"registration\":{\"href\":\"" + serverUrl + "/Registration\"}}}]},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Registration/coworkerid/1\"}}}";

        System.out.println("GET /Registration/coworkerid/{coWorkerId}: " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        response = this.restTemplate.exchange(serverUrl + "/Registration/coworkerid/10", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        //Test of GET /Registration/timecategoryid/{timeCategoryId}. Get Registration by timeCategoryId
        response = this.restTemplate.exchange(serverUrl + "/Registration/timecategoryid/1", HttpMethod.GET, entity, String.class);
        expectedJson = "{\"_embedded\":{\"registrationList\":[{\"id\":1,\"arriveTime\":\"2019-12-01T08:10:10.100+0000\",\"leaveTime\":\"2019-12-01T16:10:10.100+0000\",\"timeCategory\":{\"id\":1,\"name\":\"Normal\"},\"coworker\":{\"id\":1,\"name\":\"Rene\",\"department\":{\"id\":1,\"name\":\"IT\"}},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Registration/id/1\"},\"registration\":{\"href\":\"" + serverUrl + "/Registration\"}}}]},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Registration/timecategoryid/1\"}}}";

        System.out.println("GET /Registration/timecategoryid/{timeCategoryId}: " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        response = this.restTemplate.exchange(serverUrl + "/Registration/timecategoryid/10", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        //Test of GET /Registration/timeinterval/{startTime}/{endTime}. Get Registration by arriveTime and leaveTime
        response = this.restTemplate.exchange(serverUrl + "/Registration/timeinterval/2019-12-01 05:10:10.100/2019-12-31 19:10:10.100", HttpMethod.GET, entity, String.class);
        expectedJson = "{\"_embedded\":{\"registrationList\":[{\"id\":1,\"arriveTime\":\"2019-12-01T08:10:10.100+0000\",\"leaveTime\":\"2019-12-01T16:10:10.100+0000\",\"timeCategory\":{\"id\":1,\"name\":\"Normal\"},\"coworker\":{\"id\":1,\"name\":\"Rene\",\"department\":{\"id\":1,\"name\":\"IT\"}},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Registration/id/1\"},\"registration\":{\"href\":\"" + serverUrl + "/Registration\"}}}]},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Registration/timeinterval/2019-12-01%2005:10:10.1/2019-12-31%2019:10:10.1\"}}}";

        System.out.println("GET /Registration/timeinterval/{startTime}/{endTime}: " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        response = this.restTemplate.exchange(serverUrl + "/Registration/timeinterval/2011-12-01/2011-12-31 19:10:10.100", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        //Test of GET /Registration/coworkerid_timeinterval/{coWorkerId}/{startTime}/{endTime}. Get Registration by coWorkerId, startTime and endTime}
        response = this.restTemplate.exchange(serverUrl + "/Registration/coworkerid_timeinterval/1/2019-12-01 04:10:10.100/2019-12-31 19:10:10.100", HttpMethod.GET, entity, String.class);
        expectedJson = "{\"_embedded\":{\"registrationList\":[{\"id\":1,\"arriveTime\":\"2019-12-01T08:10:10.100+0000\",\"leaveTime\":\"2019-12-01T16:10:10.100+0000\",\"timeCategory\":{\"id\":1,\"name\":\"Normal\"},\"coworker\":{\"id\":1,\"name\":\"Rene\",\"department\":{\"id\":1,\"name\":\"IT\"}},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Registration/id/1\"},\"registration\":{\"href\":\"" + serverUrl + "/Registration\"}}}]},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Registration/coworkerid_timeinterval/1/2019-12-01%2004:10:10.1/2019-12-31%2019:10:10.1\"}}}";

        System.out.println("GET /Registration/coworkerid_timeinterval/{coWorkerId}/{startTime}/{endTime}: " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        response = this.restTemplate.exchange(serverUrl + "/Registration/arrivetime_leavetime/10/2019-12-01 08:10:10.100/2019-12-01 16:10:10.100", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        //Test of PUT /Registration/{id}. Replaces a Registration
        HttpEntity<String> registrationPutEntity = new HttpEntity<String>("{\"id\":1,\"arriveTime\":\"2019-12-01T08:11:11.100\",\"leaveTime\":\"2019-12-01T17:17:17.100\",\"timeCategory\":{\"id\":1,\"name\":\"Overarbejde udbetalt\"},\"coworker\":{\"id\":1,\"name\":\"Rene\",\"department\":{\"id\":1,\"name\":\"IT\"}}}", headers);
        response = this.restTemplate.exchange(serverUrl + "/Registration/1", HttpMethod.PUT, registrationPutEntity, String.class);
        expectedJson = "{\"id\":1,\"arriveTime\":\"2019-12-01T08:11:11.100+0000\",\"leaveTime\":\"2019-12-01T17:17:17.100+0000\",\"timeCategory\":{\"id\":1,\"name\":\"Normal\"},\"coworker\":{\"id\":1,\"name\":\"Rene\",\"department\":{\"id\":1,\"name\":\"IT\"}},\"_links\":{\"self\":{\"href\":\"" + serverUrl + "/Registration/id/1\"},\"registration\":{\"href\":\"" + serverUrl + "/Registration\"}}}";

        System.out.println("PUT /Registration/{id}: " + response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        registrationPutEntity = new HttpEntity<String>("{\"namefr\":\"Test Registration not replaced\"}", headers);
        response = this.restTemplate.exchange(serverUrl + "/Registration/4", HttpMethod.PUT, registrationPutEntity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        //Test of DELETE /Registration/{id}. Deletes a Registration
        response = this.restTemplate.exchange(serverUrl + "/Registration/1", HttpMethod.DELETE, entity, String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        response = this.restTemplate.exchange(serverUrl + "/Registration/100", HttpMethod.DELETE, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
