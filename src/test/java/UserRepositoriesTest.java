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
        Customer expectedResult = new Customer(1,"cookregina","_IfkP$*t@4","311-325-1356","aguilarsara@hotmail.com",null,89);
        User actualResult = userRep.findUserById(1);
        assertEquals(expectedResult,actualResult);
        User notExistResult = userRep.findUserById(100);
        assertEquals(null,notExistResult);
    }

    @Test
    public void findUserByUsername(){
       Employee expectedResult = new Employee("joseph40","kDk3(mDr_5","+1-442-410-1111x483","peter71@perez.org",null, 43694.26F);
       User actualResult = userRep.findUserByUsername("joseph40");
       assertEquals(expectedResult.getEmail(),actualResult.getEmail());
       User notExistResult = userRep.findUserByUsername("Tom123");
       assertEquals(null,notExistResult);
    }

    @Test
    public void getAllUsersTest(){
        List<User> users = userRep.getAllUsers();
        int actualResult = users.size();
        assertEquals(10,actualResult);
    }

    @Test
    public void addUserTest(){
        Employee employee = new Employee("tom666","kDk3(wed_5","+1-666-123-1111x483","tomcat123@outlook.com",null, 332344.26F);
        Boolean expectedResult = userRep.addUser(employee);
        assertTrue(expectedResult);
    }

    public void updateUserTest(){

    }

    @Test
    public void deleteUserTest(){
        Boolean expectedResult = userRep.deleteUser(14);
        assertTrue(expectedResult);
        Boolean notExistResult = userRep.deleteUser(15);
        assertFalse(notExistResult);
    }
}
