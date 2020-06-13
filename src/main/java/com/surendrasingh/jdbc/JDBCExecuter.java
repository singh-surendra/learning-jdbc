package com.surendrasingh.jdbc;

import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JDBCExecuter {


    @Test
    //1. simple jDBC connection
    public void simpleJDBCConnection() {

        System.out.println("Hello learning JDBC");
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "hplussport", "postgres", "password");
        try {
            Connection connection = dcm.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM CUSTOMER");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Test
    //2 Creating Data using DAO and DTOs
    public void createDataWithDTOandDAOConcept() {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "hplussport", "postgres", "password");
        try {
            Connection connection = dcm.getConnection();
            CustomerDAO customerDAO = new CustomerDAO(connection);
            Customer customer = new Customer();
            customer.setFirstName("George");
            customer.setLastName("Washington");
            customer.setEmail("george.washington@wh.gov");
            customer.setPhone("(555) 555-6543");
            customer.setAddress("1234 Main St");
            customer.setCity("Mount Vernon");
            customer.setState("VA");
            customer.setZipCode("22121");

            customerDAO.create(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    //2 Creating Data using DAO and DTOs
    public void readingData() {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "hplussport", "postgres", "password");
        try {
            Connection connection = dcm.getConnection();
            CustomerDAO customerDAO = new CustomerDAO(connection);
            Customer customer = customerDAO.findById(1000);
            System.out.println(customer.getFirstName() + " " + customer.getLastName());

            customerDAO.create(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateCustomer() {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "hplussport", "postgres", "password");
        try {
            Connection connection = dcm.getConnection();
            CustomerDAO customerDAO = new CustomerDAO(connection);
            Customer customer = customerDAO.findById(10000);
            System.out.println(customer.getFirstName() + " " + customer.getLastName() + " " +
                    customer.getEmail());
            customer.setEmail("gwashington@wh.gov");
            customer = customerDAO.update(customer);
            System.out.println(customer.getFirstName() + " " + customer.getLastName() + " " +
                    customer.getEmail());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteCustomer() {

        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "hplussport", "postgres", "password");
        try {
            Connection connection = dcm.getConnection();
            CustomerDAO customerDAO = new CustomerDAO(connection);
            Customer customer = new Customer();
            customer.setFirstName("John");
            customer.setLastName("Adams");
            customer.setEmail("jadams.wh.gov");
            customer.setAddress("1234 Main St");
            customer.setCity("Arlington");
            customer.setState("VA");
            customer.setPhone("(555) 555-9845");
            customer.setZipCode("01234");

            Customer dbCustomer = customerDAO.create(customer);
            System.out.println(dbCustomer);
            dbCustomer = customerDAO.findById(dbCustomer.getId());
            System.out.println(dbCustomer);
            dbCustomer.setEmail("john.adams@wh.gov");
            dbCustomer = customerDAO.update(dbCustomer);
            System.out.println(dbCustomer);
            customerDAO.delete(dbCustomer.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test(description = "code challenge- reading data from orders")
    public void readDataFromOrderChallenge() {

        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "hplussport", "postgres", "password");
        try {
            Connection connection = dcm.getConnection();
            OrderDAO orderDAO = new OrderDAO(connection);
            Order order = orderDAO.findById(1000);
            System.out.println(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test(description = "with stored procedures")
    public void getOrdersWithStoredProcedures() {

        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "hplussport", "postgres", "password");
        try {
            Connection connection = dcm.getConnection();
            OrderDAO orderDAO = new OrderDAO(connection);
            List<Order> orders = orderDAO.getOrdersForCustomer(987);
            orders.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test(description = "get data with order, limit and paged data")
    public void getDataSortedAndLimited() {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "hplussport", "postgres", "password");
        try {
            Connection connection = dcm.getConnection();
            CustomerDAO customerDAO = new CustomerDAO(connection);
            customerDAO.findAllSorted(20).forEach(System.out::println);
            System.out.println("Paged");
            for (int i = 1; i < 3; i++) {
                System.out.println("Page number: " + i);
                customerDAO.findAllPaged(10, i).forEach(System.out::println);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


