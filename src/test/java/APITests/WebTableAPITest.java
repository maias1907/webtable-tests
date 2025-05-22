package APITests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Controller.EmployeeController;
import org.example.Model.Employee;
import org.example.WebTablesApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = WebTablesApplication.class)
@AutoConfigureMockMvc
public class WebTableAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = new Employee("John", "Doe", "john.doe@example.com", "30", "50000");
    }

    @Test
    public void testAddEmployee() throws Exception {
        String response = mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        int id = Integer.parseInt(response);
        assert id > 0;
    }

    @Test
    public void testEditSalary() throws Exception {
        String response = mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andReturn().getResponse().getContentAsString();
        int id = Integer.parseInt(response);

        mockMvc.perform(put("/employees/{id}/salary", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("60000"))
                .andExpect(status().isOk())
                .andExpect(content().string("Salary updated"));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        String response = mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andReturn().getResponse().getContentAsString();
        int id = Integer.parseInt(response);

        mockMvc.perform(delete("/employees/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));

        // Try to delete again and expect 404
        mockMvc.perform(delete("/employees/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSearchEmployees() throws Exception {
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/search")
                        .param("email", "john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")));
    }
}
