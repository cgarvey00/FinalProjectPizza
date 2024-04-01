import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.Employee;
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
    public void setUp(){
        factory = Persistence.createEntityManagerFactory("testpizzashop");
        userRep = new UserRepositories(factory);
    }

    @AfterEach
    public void destroy(){
        if(factory != null && factory.isOpen()){
            factory.close();
        }
    }

    @Test
    public void findUserByIdTest(){
        User expectedResult = userRep.findUserById(1);
        assertNotNull(expectedResult);
        User notExistResult = userRep.findUserById(100);
        assertNull(notExistResult);
    }

    @Test
    public void findUserByUsername(){
       User expectedResult = userRep.findUserByUsername("joseph40");
       assertNotNull(expectedResult);
       User notExistResult = userRep.findUserByUsername("Tom123");
       assertNull(notExistResult);
    }

    @Test
    public void getAllUsersTest(){
        List<User> users = userRep.getAllUsers();
        int actualResult = users.size();
        assertEquals(10,actualResult);
    }

    @Test
    public void addUserTest(){
        Employee employee = new Employee("tom666","kDk3(wed_5","+1-666-123-1111x483","tomcat123@outlook.com",332344.26F);
        Boolean expectedResult = userRep.addUser(employee);
        assertTrue(expectedResult);
    }

    @Test
    public void updateUserTest(){
        Boolean expectedResult = userRep.updateUser(2,"+1-442-410-1231x3","wef234@outlook.com");
        assertTrue(expectedResult);
    }

    @Test
    public void deleteUserTest(){
        Boolean expectedResult = userRep.deleteUser(12);
        assertTrue(expectedResult);
        Boolean notExistResult = userRep.deleteUser(12);
        assertFalse(notExistResult);
    }

    @Test
    public void addAddressTest(){
        Address address = new Address("Dublin Road", "Dundalk", "Louth", "DY767P1");
        Boolean expectedResult = userRep.addAddress(1, address);
        assertTrue(expectedResult);
    }

    @Test
    public void updateAddressTest(){
//        Boolean actualResult = userRep.updateAddress("Dublin Road", "Dundalk", "Louth", "AD983D");
//        assertTrue(actualResult);
    }

    @Test
    public void getAddressesByUserIdTest(){
        List<Address> expectedResult = userRep.getAddressesByUserId(1);
        assertNotNull(expectedResult);
        assertEquals(2,expectedResult.size());
    }

    @Test
    public void getDefaultAddressTest(){
        Address expectedResult = userRep.getDefaultAddress(1);
        assertNotNull(expectedResult);
        assertEquals(expectedResult.getId(),1);
    }
}