# Techincal Report Of Class Assignment 1

## General Introduction
This technical report documents the analysis, design and implementation of a web application. It uses React.js for the frontend part and Spring Data REST for the backend.
The purpose of this class assignment was to use personal repositories to introduce new features to the provided tutorial and introduce the concept of branches and merging, all the while keeping in mind the correct git commands to handle all of the requests in the git bash terminal.
Further details can be found at [https://github.com/AnaSofiaFigueiroaPinto/devops-23-24-JPE-PSM-1231822]. 

## First Week Of The CA1 - No Branches

### To organize all the work properly, several issues were created in the GitHub repository
To do so, the following steps were taken:
1. In the main page of the repository, the "Issues" tab was clicked.
2. Then, the "New issue" button was clicked.
3. The title of the issue was added.
4. Then, the "Submit new issue" button was clicked.
5. The issue was created with an unique number, that should be used in the commit message that shows any editions related to the issue.

In this first part of this assignment, the main goals were to create a new folder with a copy of the tutorial and then develop and test a new feature, while working on the main branch.

1. To respond to the first step, while in the folder for this repository, the command "mkdir CA1" was used. To copy the tutorial to the new folder, the command "cp -r --exclude=".git" " was used to copy everything from the provided tutorial  but the .git file.
2. The tag v1.1.0 was added to the project, using the commands "git tag v1.1.0" and then "git push --tag". Had to make some adjustments with the folder placement, so the tag was placed in the last immediate last commit before starting to implement the code changes.
3. Then, the java code provided in the class "Employee" was altered accordingly to add the feature of "jobYears" as well as its verification methods. Also, the DatabaseLoader and app.js classes were updated to reflect these changes once the application runs. Please check commit 68825a8 for further detail. To push all the updates to this repository, the commands "git add ." followed by "git commit -m "Descriptive message for each commit" " and lastly "git push origin main" were used.
4. Add the new field to the _Employee_ class:
```java
private int jobYears;
```
5. Add the new field to the _Employee_ constructor:
	```java 
	public Employee(String firstName, String lastName, String description, String jobTitle, int jobYears) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.jobTitle = jobTitle;
		this.jobYears = jobYears; 
	}
	```
6. Validations added to the _Employee_ class constructor to check if the parameters are always valid:
	```java
	public Employee(String firstName, String lastName, String description, String jobTitle, int jobYears) throws InstantiationException {
		if (firstName == null || firstName.isEmpty() || firstName.trim().isEmpty() || lastName == null || lastName.isEmpty() || jobTitle == null || jobTitle.isEmpty() || jobTitle.trim().isEmpty() || description == null || description.isEmpty() || description.trim().isEmpty() || jobYears < 0) {
			throw new InstantiationException("Invalid employee");
		}
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.jobTitle = jobTitle;
		this.jobYears = jobYears;

	}
	```
7. A new field was added to the equals and hashcode methods:
	```java
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Employee employee = (Employee) o;
		return Objects.equals(id, employee.id) &&
			Objects.equals(firstName, employee.firstName) &&
			Objects.equals(lastName, employee.lastName) &&
			Objects.equals(description, employee.description) &&
			Objects.equals(jobTitle, employee.jobTitle) &&
			Objects.equals(jobYears, employee.jobYears) &&
			Objects.equals(emailField, employee.emailField);
	}

	public int hashCode() {
		return Objects.hash(id, firstName, lastName, description, jobTitle, jobYears);
	} 
	```
8. New getters and setters were also added, as well as the new field in the toString method:
	```java	
	public int getJobYears() {
		return jobYears;
	}

	public void setJobYears(int jobYears) {
		this.jobYears = jobYears;
	}
	
	public String toString() {
		return "Employee{" +
			"id=" + id +
			", firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			", description='" + description + '\'' +
			", jobTitle='" + jobTitle + '\'' +
			", jobYears='" + jobYears + '\'' +
			'}'; 
	```
9. Added a new field in the methods presented in the app.js Javascript file:
	```javascript
	class EmployeeList extends React.Component{
	render() {
		const employees = this.props.employees.map(employee =>
			<Employee key={employee._links.self.href} employee={employee}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Description</th>
						<th>Job Title</th>
						<th>Job Years</th>
					</tr>
					{employees}
				</tbody>
			</table>
		)
	}
	}
	// end::employee-list[]

	// tag::employee[]
	class Employee extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.employee.firstName}</td>
				<td>{this.props.employee.lastName}</td>
				<td>{this.props.employee.description}</td>
				<td>{this.props.employee.jobTitle}</td>
				<td>{this.props.employee.jobYears}</td>
			</tr>
		)
	}
	}
	```
10. In the DatabaseLoader class, added the new field to the run method:
	```java	
	public void run(String... strings) throws Exception {
		this.repository.save(new Employee("Frodo", "Baggins", "Fellowship of the Ring", "Ring bearer", 2));
	}
 
11. The unit tests were added to validate the parameters to create the Employee object (commit ae9bf9b).
```java
public class EmployeeTest {
    @Test
    public void createValidEmployee() {
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "Fellowship of the Ring";
        String jobTitle = "Ring Bearer";
        int jobYears = 0;
 
        assertDoesNotThrow(() -> new Employee(firstName, lastName, description, jobTitle, jobYears));
    }

    @Test
    public void createInvalidEmployeeWithEmptyFirstName() {
        String firstName = "";
        String lastName = "Baggins";
        String description = "Fellowship of the Ring";
        String jobTitle = "Ring Bearer";
        int jobYears = 2;
 
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears));
    }

    @Test
    public void createInvalidEmployeeWithNullLastName() {
        String firstName = "Frodo";
        String lastName = null;
        String description = "Fellowship of the Ring";
        String jobTitle = "Ring Bearer";
        int jobYears = 2;
 
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears));
    }

    @Test
    public void createInvalidEmployeeWithEmptyDescription() {
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "";
        String jobTitle = "Ring Bearer";
        int jobYears = 3;
 
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears));
    }

    @Test
    public void createInvalidEmployeeWithNullJobTitle() {
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "Fellowship of the Ring";
        String jobTitle = null;
        int jobYears = 3;
 
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears));
    }

    @Test
    public void createInvalidEmployeeWithNegativeJobYears() {
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "Fellowship of the Ring";
        String jobTitle = "Ring Bearer";
        int jobYears = -1;
 
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears));
    }
}
```

12. The debugging took place on both client (using Chrome's "React Developer Tools" extension) and server (using the Intellij IDE).
13. The tag v1.2.0 was pushed to the repository, using the same commands as before.
14. Lastly, the tag ca1-part1 was pushed to the repository, using the same commands as before.

Notes: Since I've started by developing the exercise suggested in the slides 15, 16 and 17 of the theoretical document provided, before starting this class assignment, the code itself present some extra changes that accommodate that exercise, not only the ones requested for this assingment.

## Second Week Of The CA1 - Branches and Merging

For the second part of the assignment, a new branch (email-field branch) was created and the new editions took place there. The goal was to add a new field to the application, which would be the email of the employee. The commands used to do so were:
1. To create the new branch:
```bash
git branch email-field
```
2. To switch to the new branch:
```bash
git checkout email-field
```
3. To add the new field to the _Employee_ class:
```java
private String emailField;
```
4. To add the new field to the _Employee_ constructor:
```java
public Employee(String firstName, String lastName, String description, String jobTitle, int jobYears, String emailField) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.description = description;
	this.jobTitle = jobTitle;
	this.jobYears = jobYears;
	this.emailField = emailField;
}
```
5. To add validations to the _Employee_ class constructor to check if the parameters are always valid:
```java
public Employee(String firstName, String lastName, String description, String jobTitle, int jobYears, String emailField) throws InstantiationException {
	if (firstName == null || firstName.isEmpty() || firstName.trim().isEmpty() || lastName == null || lastName.isEmpty() || jobTitle == null || jobTitle.isEmpty() || jobTitle.trim().isEmpty() || description == null || description.isEmpty() || description.trim().isEmpty() || jobYears < 0 || emailField == null || emailField.isEmpty() || emailField.trim().isEmpty()) {
		throw new InstantiationException("Invalid employee");
	}
	this.firstName = firstName;
	this.lastName = lastName;
	this.description = description;
	this.jobTitle = jobTitle;
	this.jobYears = jobYears;
	this.emailField = emailField;
}
```
6. To add a new field to the _Employee_ class equals and hashcode methods:
```java
public boolean equals(Object o) {
	if (this == o) return true;
	if (o == null || getClass() != o.getClass()) return false;
	Employee employee = (Employee) o;
	return Objects.equals(id, employee.id) &&
		Objects.equals(firstName, employee.firstName) &&
		Objects.equals(lastName, employee.lastName) &&
		Objects.equals(description, employee.description) &&
		Objects.equals(jobTitle, employee.jobTitle) &&
		Objects.equals(jobYears, employee.jobYears) &&
		Objects.equals(emailField, employee.emailField);
}

public int hashCode() {
	return Objects.hash(id, firstName, lastName, description, jobTitle, jobYears, emailField);
}
```
7. To add new getters and setters, as well as the new field in the toString method:
```java
public String getEmailField() {
	return emailField;
}

public void setEmailField(String emailField) {
	this.emailField = emailField;
}

public String toString() {
	return "Employee{" +
		"id=" + id +
		", firstName='" + firstName + '\'' +
		", lastName='" + lastName + '\'' +
		", description='" + description + '\'' +
		", jobTitle='" + jobTitle + '\'' +
		", jobYears='" + jobYears + '\'' +
		", emailField='" + emailField + '\'' +
		'}';
}
```
8. To add a new field in the methods presented in the app.js Javascript file:
```javascript
class EmployeeList extends React.Component{
	render() {
		const employees = this.props.employees.map(employee =>
			<Employee key={employee._links.self.href} employee={employee}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Description</th>
						<th>Job Title</th>
						<th>Job Years</th>
						<th>Email</th>
					</tr>
					{employees}
				</tbody>
			</table>
		)
	}
}
// end::employee-list[]
// tag::employee[]
class Employee extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.employee.firstName}</td>
				<td>{this.props.employee.lastName}</td>
				<td>{this.props.employee.description}</td>
				<td>{this.props.employee.jobTitle}</td>
				<td>{this.props.employee.jobYears}</td>
				<td>{this.props.employee.emailField}</td>
			</tr>
		)
	}
}
```
9. To add the new field to the DatabaseLoader class run method:
```java
public void run(String... strings) throws Exception {
	this.repository.save(new Employee("Frodo", "Baggins", "Fellowship of the Ring", "Ring bearer", 2, "frodo_Baggins123@gmail.com"));
}
```
10. To add the unit tests to validate the parameters to create the Employee object (commit ef6923e).
```java
public class EmployeeTest {
	@Test
	public void createValidEmployee() {
		String firstName = "Frodo";
		String lastName = "Baggins";
		String description = "Fellowship of the Ring";
		String jobTitle = "Ring Bearer";
		int jobYears = 0;
		String emailField = "frodo_Baggins123@gmail.com";

		assertDoesNotThrow(() -> new Employee(firstName, lastName, description, jobTitle, jobYears, emailField));
	}

	@Test
	public void createInvalidEmployeeWithEmptyFirstName() {
		String firstName = "";
		String lastName = "Baggins";
		String description = "Fellowship of the Ring";
		String jobTitle = "Ring Bearer";
		int jobYears = 2;
		String emailField = "frodo_Baggins123@gmail.com";

		assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, emailField));
	}

	@Test
	public void createInvalidEmployeeWithNullLastName() {
		String firstName = "Frodo";
		String lastName = null;
		String description = "Fellowship of the Ring";
		String jobTitle = "Ring Bearer";
		int jobYears = 2;
		String emailField = "frodo_Baggins123@gmail.com";
	
		assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, emailField));
	}

	@Test
	public void createInvalidEmployeeWithEmptyDescription() {
		String firstName = "Frodo";
		String lastName = "Baggins";
		String description = "";
		String jobTitle = "Ring Bearer";
		int jobYears = 3;
		String emailField = "frodo_Baggins123@gmail.com";

		assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, emailField));
	}
	
	@Test
	public void createInvalidEmployeeWithNullJobTitle() {
		String firstName = "Frodo";
		String lastName = "Baggins";
		String description = "Fellowship of the Ring";
		String jobTitle = null;
		int jobYears = 3;
		String emailField = "frodo_Baggins123@gmail.com";

		assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, emailField));
	}

	@Test
	public void createInvalidEmployeeWithNegativeJobYears() {
		String firstName = "Frodo";
		String lastName = "Baggins";
		String description = "Fellowship of the Ring";
		String jobTitle = "Ring Bearer";
		int jobYears = -1;
		String emailField = "frodo_Baggins123@gmail.com";

		assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, emailField));
	}

	@Test
	public void createInvalidEmployeeWithEmptyEmailField() {
		String firstName = "Frodo";
		String lastName = "Baggins";
		String description = "Fellowship of the Ring";
		String jobTitle = "Ring Bearer";
		int jobYears = 3;
		String emailField = "";

		assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, emailField));
	}

	@Test
	public void createInvalidEmployeeWithNullEmailField() {
		String firstName = "Frodo";
		String lastName = "Baggins";
		String description = "Fellowship of the Ring";
		String jobTitle = "Ring Bearer";
		int jobYears = 3;
		String emailField = null;

		assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, emailField));
	}
    
    }
```

11. After all the editions were made, I've used the following commands to push the new branch to the repository:
```bash
git add .
git commit -m "Descriptive message for this commit"
git push origin email-field
```
12. To finally switch back to the main branch and then merge this branch to the main branch, the following commands were used (for this command, one step was wrongfully not added, since there were no commits made in the main branch all the while the changes in the email-field branch were being made, so the merge is not explicit in the "network" field of the repository, however, the merge was still successful):
```bash
git checkout main
git merge email-field
git push origin main
```
11. The tag v1.3.0 was pushed to the repository, using the same commands as before.


#### The second requirement for this second week was to create a new branch (fix-invalid-email branch) and add a new feature to the application. The feature was to validate the previously introduced email. The commands used to do so were:
1. To create the new branch:
```bash
git branch fix-invalid-email
```
2. To switch to the new branch:
```bash
git checkout fix-invalid-email
```
3. To add the new validation to the constructor of the _Employee_ class:
```java
public Employee(String firstName, String lastName, String description, String jobTitle, int jobYears, String emailField) throws InstantiationException {
		if (firstName == null || firstName.isEmpty() || firstName.trim().isEmpty() || lastName == null || lastName.isEmpty() || jobTitle == null || jobTitle.isEmpty() || jobTitle.trim().isEmpty() || description == null || description.isEmpty() || description.trim().isEmpty() || jobYears < 0 || emailField == null || emailField.isEmpty() || emailField.trim().isEmpty() || !emailField.contains("@")){
			throw new InstantiationException("Invalid employee");
		}
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.jobTitle = jobTitle;
		this.jobYears = jobYears;
		this.emailField = emailField;

	}
```
4. To add the unit tests to validate the parameters to create the Employee object (commit f7da509).
```java
@Test
    public void createInvalidEmployeeWithInvalidEmailField() {
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "Fellowship of the Ring";
        String jobTitle = "Ring Bearer";
        int jobYears = 2;
        String emailField = "frodo_Baggins123gmail.com";

        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, emailField));
    }
```
5. After all the editions were made, I've used the following commands to push the new branch to the repository:
```bash
git add .
git commit -m "Descriptive message for this commit"
git push origin fix-invalid-email
```
6. To finally switch back to the main branch and then merge this branch to the main branch, the following commands were used (this time the correct command was added):
```bash
git checkout main
git merge fix-invalid-email
git merge --no-ff fix-invalid-email
git push origin main
```

6. The debugging took place on both client (using Chrome's "React Developer Tools" extension) and server (using the Intellij IDE).
7. The tag v1.3.1 was pushed to the repository, using the same commands as before.
8. To submit the last update of the README.md file, the following commands were used:
```bash
git add .
git commit -m "Descriptive message for this commit. Close #18".
git push origin main
```
9. In this last commit, the issue #18 was closed.
10. Lastly, the tag ca1-part2 was pushed to the repository, using the same commands as before. Together with the commit of this tag, the issue #19 was also closed using the command used in the previous step.

## Alternative version using SVN

The alternative version of this assignment was made using SVN. Here are the main differences:
1. Git uses a distributed version control system (DVCS) where each user has a complete copy of the repository with its full history. Meanwhile, SVN has a centralized version control system (CVCS) with a central repository that users check out from and commit to.
2. Regarding merging and branching, Git is known for its powerful branching and merging capabilities, which make it suitable for complex development workflows. SVN supports branching and merging, but the process is generally considered less flexible and more linear compared to Git.
3. Also, Git is generally faster and more efficient, particularly when it comes to cloning repositories and handling branching and merging operations. SVN can be perceived as slower, especially with larger repositories and complex branching structures.
4. Finally, Git commits are atomic and treated as a single, indivisible unit, all the while the SVN commits are not inherently atomic, but SVN provides a transactional commit process.	

If the implementation was to use the SVN, the main commands used in the Git version would be replaced by the following:

1. To create the new branch:
```bash
svn mkdir CA1
svn copy original-repo-url/trunk CA1
```
2. To commit changes:
```bash
cd CA1

svn add --force *
svn commit -m "Initial commit: Tutorial React.js and Spring Data REST Application"
```

3. Tag Versions
```bash
svn copy trunk tags/v1.1.0
svn commit -m "Tagging version 1.1.0"
```
4. Develop a new feature
```bash
svn switch ^/trunk
# Make changes
svn commit -m "Add jobYears field to Employee"
svn copy trunk tags/v1.2.0
svn commit -m "Tagging version 1.2.0"
```
5. Tag repository
```bash
svn copy trunk tags/ca1-part1
svn commit -m "Tagging version ca1-part1"
```
For the second part of the assignment, the commands would be similar, but the branch would be created using the following command:
1. To create the new branch:
```bash
svn copy file:///path/to/svn-repo/trunk file:///path/to/svn-repo/branches/email-field -m "Creating branch for email-field feature"
svn switch ^/branches/email-field
# Make changes
svn commit -m "Add emailField field to Employee"
```
2. Merge into main and Tag:
```bash
svn switch ^/trunk
svn merge ^/branches/email-field
svn commit -m "Merge email-field branch into main"

svn copy trunk tags/v1.3.0
svn commit -m "Tagging version 1.3.0"
```
3. To create the new branch:
```bash
svn copy file:///path/to/svn-repo/trunk file:///path/to/svn-repo/branches/fix-invalid-email -m "Creating branch for fix-invalid-email feature"
svn switch ^/branches/fix-invalid-email
# Make changes
svn commit -m "Add validation for emailField in Employee"
```
4. Merge into main and Tag:
```bash
svn switch ^/trunk
svn merge ^/branches/fix-invalid-email
svn commit -m "Merge fix-invalid-email branch into main"

svn copy trunk tags/v1.3.1
svn commit -m "Tagging version 1.3.1"
```
5. Tag repository
```bash
svn copy trunk tags/ca1-part2
svn commit -m "Tagging version ca1-part2"
```



