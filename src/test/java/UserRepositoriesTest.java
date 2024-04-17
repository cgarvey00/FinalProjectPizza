import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.Employee;
import com.finalprojectcoffee.entities.Status;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoriesTest {
    private EntityManagerFactory factory;
    private UserRepositories userRep;

    @BeforeEach
    public void setUp() {
        factory = Persistence.createEntityManagerFactory("test_pizza_shop");
        userRep = new UserRepositories(factory);
    }

    @AfterEach
    public void destroy() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }

    @Test
    public void findUserByIdTest() {
        User expectedResult = userRep.findUserById(1);
        assertNotNull(expectedResult);
        User notExistResult = userRep.findUserById(100);
        assertNull(notExistResult);
    }

    @Test
    public void findUserByUsernameTest() {
        User expectedResult = userRep.findUserByUsername("test");
        assertNotNull(expectedResult);
        User notExistResult = userRep.findUserByUsername("Tom123");
        assertNull(notExistResult);
    }

    @Test
    public void findExistingEmailTest() {
        Boolean result = userRep.findExistingEmail("aguilarsara@hotmail.com");
        assertTrue(result);
        Boolean notExistedResult = userRep.findExistingEmail("test@email.com");
        assertFalse(notExistedResult);
    }

    @Test
    public void findExistingPhoneNumberTest() {
        Boolean result = userRep.findExistingPhoneNumber("(842)795-0469");
        assertTrue(result);
        Boolean notExistedResult = userRep.findExistingPhoneNumber("1234567890");
        assertFalse(notExistedResult);
    }

    @Test
    public void getAllUsersTest() {
        List<User> users = userRep.getAllUsers();
        assertEquals(10, users.size());
    }

    @Test
    public void getAllEmployeesTest() {
        List<Employee> employees = userRep.getAllEmployees();
        assertEquals(5, employees.size());
    }

    @Test
    public void switchEmployeeStatusTest() {
        Boolean result1 = userRep.switchEmployeeStatus(2);
        assertTrue(result1);
        User user = userRep.findUserById(2);
        if (user instanceof Employee) {
            Employee employee = (Employee) user;
            assertEquals(Status.Unavailable, employee.getStatus());
        }

        Boolean result2 = userRep.switchEmployeeStatus(2);
        assertTrue(result2);
        user = userRep.findUserById(2);
        if (user instanceof Employee) {
            Employee employee = (Employee) user;
            assertEquals(Status.Available, employee.getStatus());
        }
    }

    @Test
    public void setAllEmployeeAvailableTest(){
        Boolean result = userRep.setAllEmployeeAvailable();
        assertTrue(result);
        User user1 = userRep.findUserById(2);
        User user2 = userRep.findUserById(4);
        if(user1 instanceof Employee){
            Employee employee1 = (Employee) user1;
            assertEquals(Status.Available, employee1.getStatus());
        }
        if(user2 instanceof Employee){
            Employee employee2 = (Employee) user2;
            assertEquals(Status.Available, employee2.getStatus());
        }
    }

    @Test
    public void setAllEmployeeUnavailableTest(){
        Boolean result = userRep.setAllEmployeesUnavailable();
        assertTrue(result);
        User user1 = userRep.findUserById(2);
        User user2 = userRep.findUserById(4);
        if(user1 instanceof Employee){
            Employee employee1 = (Employee) user1;
            assertEquals(Status.Unavailable, employee1.getStatus());
        }
        if(user2 instanceof Employee){
            Employee employee2 = (Employee) user2;
            assertEquals(Status.Unavailable, employee2.getStatus());
        }
    }

    @Test
    public void addUserTest() {
        User user = new User("test", "123", "123", "test@email.com", "Test");
        Boolean result = userRep.addUser(user);
        assertTrue(result);
    }

    @Test
    public void updateUserTest() {
        Boolean expectedResult = userRep.updateUser(9, "test123", "test@outlook.com");
        assertTrue(expectedResult);
        User user = userRep.findUserById(9);
        assertEquals("test123", user.getPhoneNumber());
        assertEquals("test@outlook.com", user.getEmail());
    }

    @Test
    public void changePasswordTest(){
        Boolean result = userRep.changePassword(9, "111");
        assertTrue(result);
    }

    @Test
    public void addAddressTest() {
        Address address = new Address("Dublin Road", 0, "Dundalk", "Louth", "DY767P1");
        Boolean result = userRep.addAddress(9, address);
        assertTrue(result);
    }

    @Test
    public void updateAddressTest() {
        Address address = userRep.getAddressById(4);
        address.setTown("test");
        Boolean result = userRep.updateAddress(address);
        assertTrue(result);
        assertEquals("test", address.getTown());
    }

    @Test
    public void setDefaultAddress(){
        Address address = userRep.getAddressById(4);
        Boolean result = userRep.setDefaultAddress(address);
        assertTrue(result);
        assertEquals(1, address.getIsDefault());
    }

    @Test
    public void getAddressesByUserIdTest() {
        List<Address> result = userRep.getAddressesByUserId(3);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void getDefaultAddressTest() {
        Address result = userRep.getDefaultAddress(3);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void deleteAddressTest(){
        Boolean result = userRep.deleteAddress(9, 4);
        assertTrue(result);
        Address notExistedAddress = userRep.getAddressById(4);
        assertNull(notExistedAddress);
    }

    @Test
    public void deleteUserTest() {
        Boolean result = userRep.deleteUser(11);
        assertTrue(result);
        User user = userRep.findUserById(11);
        assertNull(user);
    }
}