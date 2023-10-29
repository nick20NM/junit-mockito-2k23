package com.alpha.www.appname.repository;

import com.alpha.www.appname.entity.Employee;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    // JUnit test case for save employee operation
    @Test
    @DisplayName("JUnit test case for save employee operation")
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){

        // given- precondition or setup
        Employee employee = Employee.builder()
                .firstName("tony")
                .lastName("stark")
                .email("tony@gmail.com")
                .build();

        // when- action or the behavior that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);
        System.out.println("savedEmployee="+savedEmployee);

        // then- verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    // JUnit test case for get all employees operation
    @DisplayName("JUnit test case for get all employees operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenReturnEmployeesList(){

        // given- precondition or setup
        Employee employee = Employee.builder()
                .firstName("john")
                .lastName("cena")
                .email("john@")
                .build();

        Employee employee1 = Employee.builder()
                .firstName("tony")
                .lastName("stark")
                .email("tony@gmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        // when- action or the behavior that we are going to test
        List<Employee> employeeList = employeeRepository.findAll();

        // then- verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    // JUnit test case for get employee by id operation
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObjectFromDB(){

        // given- precondition or setup
        Employee employee = Employee.builder()
                .firstName("tony")
                .lastName("stark")
                .email("tony@gmail.com")
                .build();

        employeeRepository.save(employee);

        // when- action or the behavior that we are going to test
        Employee employeeFromDB = employeeRepository.findById(employee.getId()).get();

        // then- verify the output
        assertThat(employeeFromDB).isNotNull();
    }

    // JUnit test case for get employee by email operation
    @DisplayName("JUnit test case for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObjectFromDB(){

        // given- precondition or setup
        Employee employee = Employee.builder()
                .firstName("tony")
                .lastName("stark")
                .email("tony@gmail.com")
                .build();

        employeeRepository.save(employee);

        // when- action or the behavior that we are going to test
        Employee employeeFromDB = employeeRepository.findByEmail(employee.getEmail()).get();

        // then- verify the output
        assertThat(employeeFromDB).isNotNull();
    }

    // JUnit test case for update employee operation
    @DisplayName("JUnit test case for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){

        // given- precondition or setup
        Employee employee = Employee.builder()
                .firstName("tony")
                .lastName("stark")
                .email("tony@gmail.com")
                .build();

        employeeRepository.save(employee);

        // when- action or the behavior that we are going to test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setFirstName("john");
        savedEmployee.setLastName("cena");
        savedEmployee.setEmail("john@gmail.com");

        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        // then- verify the output
        assertThat(updatedEmployee.getFirstName()).isEqualTo("john");
        assertThat(updatedEmployee.getLastName()).isEqualTo("cena");
        assertThat(updatedEmployee.getEmail()).isEqualTo("john@gmail.com");
    }

    // JUnit test case for delete employee operation
    @Test
    @DisplayName("JUnit test case for delete employee operation")
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee(){

        // given- precondition or setup
        Employee employee = Employee.builder()
                .firstName("tony")
                .lastName("stark")
                .email("tony@gmail.com")
                .build();

        employeeRepository.save(employee);

        // when- action or the behavior that we are going to test
//        employeeRepository.delete(employee);
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());

        // then- verify the output
        assertThat(optionalEmployee).isEmpty();
    }

    // JUnit test case for custom query using JPQL with index params
    @DisplayName("JUnit test case for custom query using JPQL with index params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject(){

        // given- precondition or setup
        Employee employee = Employee.builder()
                .firstName("tony")
                .lastName("stark")
                .email("tony@gmail.com")
                .build();

        employeeRepository.save(employee);

        String firstName = "tony";
        String lastName = "stark";

        // when- action or the behavior that we are going to test
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

        // then- verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test case for custom query using JPQL with named params
    @DisplayName("JUnit test case for custom query using JPQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject(){

        // given- precondition or setup
        Employee employee = Employee.builder()
                .firstName("tony")
                .lastName("stark")
                .email("tony@gmail.com")
                .build();

        employeeRepository.save(employee);

        String firstName = "tony";
        String lastName = "stark";

        // when- action or the behavior that we are going to test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);

        // then- verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test case for custom query using native SQL with index params
    @Test
    @DisplayName("JUnit test case for custom query using native SQL with index params")
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject(){

        // given- precondition or setup
        Employee employee = Employee.builder()
                .firstName("tony")
                .lastName("stark")
                .email("tony@gmail.com")
                .build();

        employeeRepository.save(employee);

        String firstName = "tony";
        String lastName = "stark";

        // when- action or the behavior that we are going to test
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());

        // then- verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test case for custom query using Native SQL with named params
    @DisplayName("JUnit test case for custom query using Native SQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject(){

        // given- precondition or setup
        Employee employee = Employee.builder()
                .firstName("tony")
                .lastName("stark")
                .email("tony@gmail.com")
                .build();

        employeeRepository.save(employee);

        // when- action or the behavior that we are going to test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(employee.getFirstName(), employee.getLastName());

        // then- verify the output
        assertThat(savedEmployee).isNotNull();
    }
}
