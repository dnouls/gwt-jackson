/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.shared.advanced.jsontype;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class PolymorphismIdMinimalClassAsWrapperArrayTester extends AbstractTester {

    @JsonTypeInfo( use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.WRAPPER_ARRAY )
    @JsonPropertyOrder( alphabetic = true )
    public static abstract class Person {

        public String name;
    }

    public static class Employee extends Person {

        public int id;

        public String title;
    }

    public static class Manager extends Employee {

        public List<Employee> managedEmployees;
    }

    public static class Customer extends Person {

        public int satisfaction;
    }

    public static final PolymorphismIdMinimalClassAsWrapperArrayTester INSTANCE = new PolymorphismIdMinimalClassAsWrapperArrayTester();

    private PolymorphismIdMinimalClassAsWrapperArrayTester() {
    }

    public void testSerialize( ObjectWriterTester<Person[]> writer ) {
        Person[] persons = new Person[4];

        Employee employee2 = new Employee();
        employee2.name = "Thomas";
        employee2.id = 2;
        employee2.title = "Waiter";
        persons[0] = employee2;

        Employee employee3 = new Employee();
        employee3.name = "Patricia";
        employee3.id = 3;
        employee3.title = "Cook";
        persons[1] = employee3;

        Manager manager = new Manager();
        manager.name = "Bob";
        manager.id = 1;
        manager.title = "Boss";
        manager.managedEmployees = Arrays.asList( employee2, employee3 );
        persons[2] = manager;

        Customer customer = new Customer();
        customer.name = "Brad";
        customer.satisfaction = 90;
        persons[3] = customer;

        String result = writer.write( persons );

        String expected = "[" +
                "[" +
                "\".PolymorphismIdMinimalClassAsWrapperArrayTester$Employee\"," +
                "{" +
                "\"id\":2," +
                "\"name\":\"Thomas\"," +
                "\"title\":\"Waiter\"" +
                "}" +
                "]," +
                "[" +
                "\".PolymorphismIdMinimalClassAsWrapperArrayTester$Employee\"," +
                "{" +
                "\"id\":3," +
                "\"name\":\"Patricia\"," +
                "\"title\":\"Cook\"" +
                "}" +
                "]," +
                "[" +
                "\".PolymorphismIdMinimalClassAsWrapperArrayTester$Manager\"," +
                "{" +
                "\"id\":1," +
                "\"managedEmployees\":" +
                "[" +
                "[" +
                "\".PolymorphismIdMinimalClassAsWrapperArrayTester$Employee\"," +
                "{" +
                "\"id\":2," +
                "\"name\":\"Thomas\"," +
                "\"title\":\"Waiter\"" +
                "}" +
                "]," +
                "[" +
                "\".PolymorphismIdMinimalClassAsWrapperArrayTester$Employee\"," +
                "{" +
                "\"id\":3," +
                "\"name\":\"Patricia\"," +
                "\"title\":\"Cook\"" +
                "}" +
                "]" +
                "]," +
                "\"name\":\"Bob\"," +
                "\"title\":\"Boss\"" +
                "}" +
                "]," +
                "[" +
                "\".PolymorphismIdMinimalClassAsWrapperArrayTester$Customer\"," +
                "{" +
                "\"name\":\"Brad\"," +
                "\"satisfaction\":90" +
                "}" +
                "]" +
                "]";

        assertEquals( expected, result );
    }

    public void testDeserialize( ObjectReaderTester<Person[]> reader ) {
        String input = "[" +
                "[" +
                "\".PolymorphismIdMinimalClassAsWrapperArrayTester$Employee\"," +
                "{" +
                "\"id\":2," +
                "\"name\":\"Thomas\"," +
                "\"title\":\"Waiter\"" +
                "}" +
                "]," +
                "[" +
                "\".PolymorphismIdMinimalClassAsWrapperArrayTester$Employee\"," +
                "{" +
                "\"id\":3," +
                "\"name\":\"Patricia\"," +
                "\"title\":\"Cook\"" +
                "}" +
                "]," +
                "[" +
                "\".PolymorphismIdMinimalClassAsWrapperArrayTester$Manager\"," +
                "{" +
                "\"id\":1," +
                "\"managedEmployees\":" +
                "[" +
                "[" +
                "\".PolymorphismIdMinimalClassAsWrapperArrayTester$Employee\"," +
                "{" +
                "\"id\":2," +
                "\"name\":\"Thomas\"," +
                "\"title\":\"Waiter\"" +
                "}" +
                "]," +
                "[" +
                "\".PolymorphismIdMinimalClassAsWrapperArrayTester$Employee\"," +
                "{" +
                "\"id\":3," +
                "\"name\":\"Patricia\"," +
                "\"title\":\"Cook\"" +
                "}" +
                "]" +
                "]," +
                "\"name\":\"Bob\"," +
                "\"title\":\"Boss\"" +
                "}" +
                "]," +
                "[" +
                "\".PolymorphismIdMinimalClassAsWrapperArrayTester$Customer\"," +
                "{" +
                "\"name\":\"Brad\"," +
                "\"satisfaction\":90" +
                "}" +
                "]" +
                "]";

        Person[] result = reader.read( input );
        {
            // Employee
            Employee employee = (Employee) result[0];
            assertEquals( 2, employee.id );
            assertEquals( "Waiter", employee.title );
            assertEquals( "Thomas", employee.name );
        }
        {
            // Employee
            Employee employee = (Employee) result[1];
            assertEquals( 3, employee.id );
            assertEquals( "Cook", employee.title );
            assertEquals( "Patricia", employee.name );
        }
        {
            // Manager
            Manager manager = (Manager) result[2];
            assertEquals( 1, manager.id );
            assertEquals( "Boss", manager.title );
            assertEquals( "Bob", manager.name );
            assertEquals( 2, manager.managedEmployees.size() );

            Employee employee1 = manager.managedEmployees.get( 0 );
            assertEquals( 2, employee1.id );
            assertEquals( "Waiter", employee1.title );
            assertEquals( "Thomas", employee1.name );

            Employee employee2 = manager.managedEmployees.get( 1 );
            assertEquals( 3, employee2.id );
            assertEquals( "Cook", employee2.title );
            assertEquals( "Patricia", employee2.name );
        }
        {
            // Customer
            Customer customer = (Customer) result[3];
            assertEquals( "Brad", customer.name );
            assertEquals( 90, customer.satisfaction );
        }
    }

}
