package com.kg.springboot;


import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.when;
import org.apache.http.HttpStatus;
import com.jayway.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;
import static com.jayway.restassured.RestAssured.given;

/**
* EmployeeControllerIT

*/

@RunWith(SpringRunner.class)

@SpringBootTest(classes=SpringbootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(
locations = "classpath:application.properties")
public class EmployeeControllerIT {
   @Value("${local.server.port}")
   private int serverPort;
   private static final String  FIRST_NAME_FIELD = "firstName";
   private static final String  EVENT_RESOURCE1 = "employees";
   private static final String  EVENT_RESOURCE2 = "employees";
   private static final String  EVENT_RESOURCE3 = "employees/{eventId}";
   private static final String  EVENT_RESOURCE4 = "employees/{eventId}";
   private static final String  EVENT_RESOURCE5 = "employees/{eventId}";

   private static final String firstname="krishna";

   private static final Long EVENT_ID = 1L;
   private static final Employee FIRST_EVENT = new EmployeeBuilder().EmployeeId(EVENT_ID).FirstName("firstname").Build();
   private static final Employee SECOND_EVENT = new EmployeeBuilder().EmployeeId(2L).FirstName("krishna").Build();
   private static final Employee THIRD_EVENT = new EmployeeBuilder().EmployeeId(3L).FirstName("krishna").Build();

   private Employee firstEvent;
   private Employee secondEvent;
   @Autowired
   private EmployeeRepository employeeRepository;
   @Before

   public void setUp() {

       employeeRepository.deleteAll();
       firstEvent = employeeRepository.saveAndFlush(FIRST_EVENT);
       secondEvent = employeeRepository.saveAndFlush(SECOND_EVENT);
       RestAssured.port = serverPort;
   }
   @Test
   //GET ALL EVENTS
   public void getItemsShouldReturnBothItems() {
       when().get(EVENT_RESOURCE1).then().statusCode(HttpStatus.SC_OK).body(FIRST_NAME_FIELD,
               hasItems(firstname, firstname));
   }
   @Test
   //SAVE
   public void addItemShouldReturnSavedItem() {
      given().body(THIRD_EVENT).contentType(ContentType.JSON).
       when().post(EVENT_RESOURCE2).then().statusCode(HttpStatus.SC_OK);
      // System.out.println("**************addItemShouldReturnSavedItem completed !!!******************");
   }

   @Test
   public void updateItemShouldReturnUpdatedItem() {
       // Given an unchecked first item
       //Event item = new EventBuilder().id(1L).edition(2).fulldate("29Aug2017").date("29/8/2017").month("august").location("block2").active(true).agenda(agenda1).build();
       given().body(THIRD_EVENT).contentType(ContentType.JSON).when().put(EVENT_RESOURCE3,firstEvent.getEmployeeId()).then()
               .statusCode(HttpStatus.SC_OK).body(FIRST_NAME_FIELD, is(firstname));
                System.out.println("success");
   }

   @Test
   //DELETE
   public void deleteItemShouldReturnNoContent() {
       when().delete(EVENT_RESOURCE4, secondEvent.getEmployeeId()).then().statusCode(HttpStatus.SC_OK);
   }
   @Test
  public void getOneItemsById() {
      when().get(EVENT_RESOURCE5, firstEvent.getEmployeeId()).then().statusCode(HttpStatus.SC_OK);

  }
}


