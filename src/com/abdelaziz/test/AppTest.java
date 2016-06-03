package com.abdelaziz.test;

public class AppTest {

	public static void main(String[] args) {
		// try {
		// InputStream in = new FileInputStream(new File("j:\\input.png"));
		// OutputStream out = new FileOutputStream(new File("j:\\output.png"));
		// IOUtils.copy(in, out);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// String dateStr = "Mon Jan 06 00:00:00 GMT+01:00 2016";
		// DateFormat readFormat = new SimpleDateFormat(
		// "EEE MMM dd hh:mm:ss z yyyy");
		// Date date = null;
		// try {
		// date = readFormat.parse(dateStr);
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		// if (date.compareTo(new Date()) < 0) {
		// System.out.println("asghir");
		// } else if(date.compareTo(new Date()) > 0) {
		// System.out.println("akbir");
		// } else {
		// System.out.println("9Ad9AD");
		// }
		// @SuppressWarnings("resource")
		// ApplicationContext ctx = new ClassPathXmlApplicationContext(
		// "ApplicationContext.xml");
		// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// EmployeeService es = (EmployeeService)
		// ctx.getBean("employeeService");
		// JobPositionService jps = (JobPositionService) ctx
		// .getBean("jobPositionSerice");
		// ProjectService ps = (ProjectService) ctx.getBean("projectService");
		// ProjectTypeService pts = (ProjectTypeService) ctx
		// .getBean("projectTypeService");
		// UsersService us = (UsersService) ctx.getBean("usersService");
		// UserRolesService urs = (UserRolesService) ctx
		// .getBean("userRolesService");
		// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// List<Role> roles = new
		// ArrayList<Role>(us.findUsersById(1).getRoles());
		// System.out.println(roles.get(0).getRoleLabel());
		// System.out.println(pts.findProjectTypeById(1L).getProjectTypeLabel());
		// Employee e = es.findEmplyeeById("07421571");
		// Project p = ps.findProjectById(32871);
		// e.getProjects().add(p);
		// es.updateEmployee(e);
		// JobPosition jp = new JobPosition(32672, "Architect", null);
		// jps.addJobPosition(jp);
		// ProjectType pt = new ProjectType(32442, "Mobile Phone Application",
		// null);
		// pts.addProjectType(pt);
		// Project p = new Project(32371, pt, "Geo Localisation Android",
		// "A cool Mobile application that flys and cooks pasta",
		// new Date(), new Date(), null);
		// Employee e = new Employee("04432372", jp, "Rawend", "Achache",
		// new Date(), "rawend.achache@gmail.com", null);
		// Set<Project> s = new HashSet<Project>();
		// s.add(p);
		// e.setProjects(s);
		// es.addEmployee(e);
		// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Employee e = es.findEmplyeeById("04432372");
		// Project p = ps.findProjectById(32371);
		// Set<Project> projects = new HashSet<Project>();
		// projects.add(p);
		// e.setProjects(projects);
		// es.updateEmployee(e);
		// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Employee e = es.findEmplyeeById("04432372");
		// Project p = ps.findProjectById(32371);
		// Set<Employee> employees = new HashSet<Employee>();
		// employees.add(e);
		// p.setEmployees(employees);
		// ps.updateProject(p);
		// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Employee e = es.findEmplyeeById("04432372");
		// ProjectType pt = new ProjectType(442, "Wow Application", null);
		// pts.addProjectType(pt);
		// Project p = new Project(32, pt, "wow", "Really, wow!", new Date(),
		// new Date(), null);
		// ps.addProject(p);
		// e.getProjects().add(p);
		// es.updateEmployee(e);
		// es.deleteEmployee(e);
		// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// ProjectType pt = pts.findProjectTypeById(442);
		// pts.deleteProjectType(p);
		// JobPosition j = jps.findJobPositionById(32672);
		// jps.deleteJobPosition(j);
		// Set<Employee> s = new HashSet<Employee>();
		// s.add(e);
		// Project p = new Project(2312, pt, "hahan", "hahahahahaha", new
		// Date(),
		// new Date(), s);
		// ps.addProject(p);
		// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// short en = 1;
		// Users u = new Users("Hamma", "azerty", en);
		// us.addUsers(u);
		// UserRoles ur = new UserRoles(u, "ROLE_ADMIN");
		// UserRoles ur1 = new UserRoles(u, "ROLE_USER");
		// urs.addUserRoles(ur);
		// urs.addUserRoles(ur1);
		// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}
}