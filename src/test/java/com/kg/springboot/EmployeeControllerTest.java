package com.kg.springboot;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Arrays;

/**
 * EmployeeControllerTest
 */
@RunWith(SpringRunner.class)

public class EmployeeControllerTest {
    // List<Employee> expectedEmployees;
    @InjectMocks
    public EmployeeController employeeController; 
    @Mock
    public EmployeeRepository employeeRepository;

    private static final long EMPLOYEE_ID = 2L;
	private static final Employee EXISTING_EMPLOYEE = new EmployeeBuilder().EmployeeId(1L).Department("bcd").Build();
	// private static final Employee ANOTHER_EMPLOYEE = new EmployeeBuilder().EmployeeId(2L).Department("def").Build();
    private static final Employee NEW_EMPLOYEE = new EmployeeBuilder().Department("abc").EmployeeId(1L).FirstName("krishna").Build();
    private static final Optional<Employee> EXISTING_EMPLOYEE1=Optional.of(EXISTING_EMPLOYEE);
    
    
    List<Employee> expectedEmployees=Arrays.asList(EXISTING_EMPLOYEE);
    
    @Test
    public void readTest()
     {   
         when(employeeRepository.findAll()).thenReturn(expectedEmployees);
        Iterable<Employee> actEmployee=employeeController.read();
        assertNotNull(actEmployee);
        System.out.println(("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+expectedEmployees));
        // assertEquals(1, actEmployee.size());
        assertEquals(expectedEmployees,actEmployee);
        
    }
    @Test
    public void readOneTest()
     {   
         when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(EXISTING_EMPLOYEE1);
        Optional<Employee> actEmployee=employeeController.readOne(EMPLOYEE_ID);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@"+actEmployee);
        assertNotNull(actEmployee);
        // assertEquals(1, actEmployee.size());
        // assertEquals(expectedEmployees, actEmployee);
    }
    @Test
    public void addCountryTest() {
        when(employeeRepository.saveAndFlush(NEW_EMPLOYEE)).thenReturn(EXISTING_EMPLOYEE);
        Employee AR = employeeController.add(NEW_EMPLOYEE);
        System.out.println("+++++++++++++++++++++++++++++++++++"+AR);
        assertNotNull(AR);
    }
    @Test
    public void deleteemployeeTest() {
        employeeController.deleteemployee(EMPLOYEE_ID);
        verify(employeeRepository).deleteById(EMPLOYEE_ID);
    }
    @Test
    public void updateCountryTest() {
        when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(EXISTING_EMPLOYEE1);
        when(employeeRepository.saveAndFlush(NEW_EMPLOYEE)).thenReturn(NEW_EMPLOYEE);
        Employee AR = employeeController.update(EMPLOYEE_ID,NEW_EMPLOYEE);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$"+AR);
        assertNotNull(AR);
    }
}