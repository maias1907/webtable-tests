package org.example.Controller;

import org.example.Model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final Map<Integer, Employee> employeeStore = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    // Add a new employee
    @PostMapping
    public ResponseEntity<Integer> addEmployee(@RequestBody Employee employee) {
        int id = idGenerator.getAndIncrement();
        employeeStore.put(id, employee);
        return ResponseEntity.ok(id);
    }

    // Edit employee salary
    @PutMapping("/{id}/salary")
    public ResponseEntity<String> updateSalary(@PathVariable int id, @RequestBody String newSalary) {
        Employee employee = employeeStore.get(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        employee.setSalary(newSalary);
        return ResponseEntity.ok("Salary updated");
    }

    // Delete an employee
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        if (!employeeStore.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        employeeStore.remove(id);
        return ResponseEntity.ok("Deleted");
    }

    // Search employee by email
    @GetMapping("/search")
    public ResponseEntity<Employee> searchByEmail(@RequestParam String email) {
        for (Employee emp : employeeStore.values()) {
            if (emp.getEmail().equalsIgnoreCase(email)) {
                return ResponseEntity.ok(emp);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // Get all employees (optional)
    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(new ArrayList<>(employeeStore.values()));
    }
}
