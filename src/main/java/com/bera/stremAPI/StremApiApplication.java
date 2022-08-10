package com.bera.stremAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class StremApiApplication {

	static List<Employee> employeeList = new ArrayList<>();
	static{
		employeeList.add(new Employee("soumyadeep","Bera", 20000.0, List.of("project1","project2")));
		employeeList.add(new Employee("chandan","singha",30000.00, List.of("Project3", "Project4")));
		employeeList.add(new Employee("Rakesh","Bera", 10000.0, List.of("project5","project6")));
		employeeList.add(new Employee("Souvik","Bera",5000.00, List.of("Project7", "Project8")));
	}

	public static void main(String[] args) {
		//	SpringApplication.run(StremApiApplication.class, args);

		//it will transform a list to Stream
		Stream.of(employeeList);

		//forEach
		employeeList.stream()
				.forEach(employee -> System.out.println(employee));

		//map it will map one object to different type of object
		//collect
		List<Employee> incresedSal = employeeList.stream()
				.map(employee -> new Employee(
						employee.getFirstName(),
						employee.getLastName(),
						employee.getSalary() * 1.10,
						employee.getProjects()
				)).collect(Collectors.toList());
		System.out.println("Incresed salary for every employee"+incresedSal);
		//inside collect we also can use set Collectors.toSet();

		//filter instead of if else we can use filter to filter out data
		List<Employee> filterEmployee = employeeList.stream()
				.filter(employee -> employee.getSalary() < 11000)
				.map(employee -> new Employee(
						employee.getFirstName(),
						employee.getLastName(),
						employee.getSalary() + 1000,
						employee.getProjects()))
				.collect(Collectors.toList());
		System.out.println("Filter Employee based on Salary"+filterEmployee);

		//optional -> it might return a object or it might not return any object
		Employee findFirstEmployee = employeeList.stream()
				.filter(employee -> employee.getSalary()<11000)
				.map(employee -> new Employee(
						employee.getFirstName(),
						employee.getLastName(),
						employee.getSalary()+1000,
						employee.getProjects()))
				.findFirst()
						.orElse(null);
		System.out.println("find First Employee"+findFirstEmployee);

		//flatMap ->  it merges all streams into a single resultant stream. In short, it is used to convert a Stream of Stream into a list of values.
		String projects = employeeList.stream()
				.map(employee -> employee.getProjects())
				.flatMap(strings -> strings.stream())
				.collect(Collectors.joining(","));
		System.out.println("All the project Separated by comma-> "+projects);

		//short Circuit operation -> it will limit the data

		List<Employee> limitEmployee = employeeList.stream()
				.skip(1)
				.limit(1)
				.collect(Collectors.toList());
		System.out.println("limit out the Employee"+limitEmployee);

		//sorting -> sorting the employee by name
		List<Employee> sortedEmployee = employeeList.stream()
				.sorted((o1,o2) -> o1.getFirstName()
						.compareToIgnoreCase(o2.getFirstName()))
				.collect(Collectors.toList());
		System.out.println("sorted the employee by name"+sortedEmployee);

		//min or max data using stream API
		employeeList.stream()
				.max(Comparator.comparing(Employee::getSalary))
				.orElseThrow(NoSuchElementException::new);

		//reduce -> if we want to accumulate all the salary to sum or multipication and other then we can use reduce
		Double totalSal = employeeList.stream()
				.map(employee -> employee.getSalary())
				.reduce(0.0,Double::sum);

		System.out.println("Total Salary of all employee -> "+totalSal);
	}

}
