package com.kg.springboot;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class, secure = false)

public class EmployeeControllerMockMVCIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeRepository employeeRepository;
    public long EMPLOYEE_ID = 1L;
    public static final Employee EXISTING_EMPLOYEE = new EmployeeBuilder().EmployeeId(1L).Department("bcd").Build();
    public static final Employee NEW_EMPLOYEE = new EmployeeBuilder().EmployeeId(2L).FirstName("krishna").LastName("Neelakandan").Salary(7700L).Department("GSS").Build();
    List<Employee> expectedEmployees = Arrays.asList(EXISTING_EMPLOYEE);
    private static final Optional<Employee> EXISTING_EMPLOYEE1 = Optional.of(EXISTING_EMPLOYEE);

    @Test
    public void getAll() throws Exception {
        given(employeeRepository.findAll()).willReturn(expectedEmployees);
        mockMvc.perform(get("/employees").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(
                content().json("[{'employeeId':1,'firstName':null,'lastName':null,'salary':null,'department':'bcd'}]"));
    }

    @Test
    public void postmapping() throws Exception {
        given(employeeRepository.findById(NEW_EMPLOYEE.getEmployeeId())).willReturn(EXISTING_EMPLOYEE1);
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(asJsonString(NEW_EMPLOYEE)))
                .andExpect(status().isOk());

    }
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
//     @Test
//     public void updateByID() throws Exception {
//          when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(EXISTING_EMPLOYEE1);
//         when(employeeRepository.saveAndFlush(NEW_EMPLOYEE)).thenReturn(EXISTING_EMPLOYEE);
//         mockMvc.perform(put("/employees/1", employeeRepository.findById(EXISTING_EMPLOYEE.getEmployeeId())).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(
//             content().json("[{'employeeId':1,'lastName':null,'salary':null,'department':'bcd'}]"));
// }
}