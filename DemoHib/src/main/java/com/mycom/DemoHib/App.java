package com.mycom.DemoHib;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.internal.SessionFactoryServiceRegistryBuilderImpl;
import org.hibernate.service.spi.SessionFactoryServiceRegistryBuilder;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	lesson8();
    	
    	
    }
    
    private static void lesson8() {
		Configuration con = new Configuration().configure().addAnnotatedClass(Tablet.class);
		SessionFactory sf = con.buildSessionFactory();
		Session session = sf.openSession();
		
		Transaction tx = session.beginTransaction();
		//Tablet t = session.get(Tablet.class, 1);
		
		Tablet t = session.load(Tablet.class, 101);
		/*try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		System.out.println(t);
		
		tx.commit();
    }
    
    private static void lesson7() {
		Configuration con = new Configuration().configure().addAnnotatedClass(Tablet.class);
		SessionFactory sf = con.buildSessionFactory();
		Session session = sf.openSession();
		
		Transaction tx = session.beginTransaction();
		/*Random r = new Random();
		
		for(int i=1; i<=50; i++) {
			Tablet t = new Tablet();
			t.setId(i);
			t.setBrand("brand"+i);
			t.setPrice(r.nextInt(1000));

			session.save(t);	
		}*/
		
		Tablet t = new Tablet();
		t.setId(52);
		t.setBrand("T2000");
		t.setPrice(800);
		
		session.save(t);
		t.setPrice(750);
		
		tx.commit();
		
		session.detach(t);
		t.setPrice(650);
    }
    
    private static void lesson6Sql() {
		Configuration con = new Configuration().configure().addAnnotatedClass(Player.class);
		//ServiceRegistry serviceRegistry = new SessionFactoryServiceRegistryBuilderImpl()
		SessionFactory sf = con.buildSessionFactory();
		Session session = sf.openSession();
		
		Transaction tx = session.beginTransaction();
		
		/*NativeQuery<Player> query = session.createSQLQuery("select * from player where marks > 60");
		query.addEntity(Player.class);
		List<Player> players = query.list();
		
		for(Player player : players) {
			System.out.println(player);
		}*/
		
		NativeQuery<Player> query = session.createSQLQuery("select name, marks from player where marks > 60");
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List players = query.list();
		
		for(Object obj : players) {
			Map<String, Object> m = (Map<String, Object>)obj;
			System.out.println((String)m.get("name") + " : " + (Integer)m.get("marks"));
		}
		
		tx.commit();
    }
    
    private static void lesson5Hql() {
		Configuration con = new Configuration().configure().addAnnotatedClass(Player.class);
		//ServiceRegistry serviceRegistry = new SessionFactoryServiceRegistryBuilderImpl()
		SessionFactory sf = con.buildSessionFactory();
		Session session = sf.openSession();
		
		Transaction tx = session.beginTransaction();
		/*Random r = new Random();
		
		for(int i=1; i<=50; i++) {
			Player p = new Player();
			p.setPlayerNo(i);
			p.setName("Name:" + i);
			p.setMarks(r.nextInt(100));
			session.save(p);		
		}*/
		
		/*Query<Player> q = session.createQuery("from Player where marks > 50");
		List<Player> players = q.list();
		for(Player p : players) {
			System.out.println(p);
		}
		*/
		
		/*		
		Query<Player> q = session.createQuery("from Player where playerNo = 9");
		Player player = (Player) q.uniqueResult();
		System.out.println(player);
		*/
		/*
		Query<Object> q = session.createQuery("select playerNo, marks, name from Player where playerNo = 9");
		Object[] player = (Object[]) q.uniqueResult();
		
		for(Object o:player) {
			System.out.println(o);
		}*/
		/*
		Query<Object[]> q = session.createQuery("select playerNo, marks, name from Player");
		List<Object[]> players = (List<Object[]>) q.list();
		
		for(Object[] player:players) {
			System.out.println(player[0] + " : " + player[1] + " : " + player[2]);
		}*/
		
		int b = 60;
		Query<Object> q = session.createQuery("select sum(marks) from Player p where p.marks>:b");
		q.setParameter("b", b);
		Long marks = (Long) q.uniqueResult();
		
		System.out.println(marks);
		
		
		tx.commit();
    }
    
    private static void lesson4SecondLevelCaching() {
    	Employee e = null;
		Configuration con = new Configuration().configure().addAnnotatedClass(Employee.class);
		
		SessionFactory sf = con.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		Query<Employee> q1 = session.createQuery("from Employee where id = 101");
		q1.setCacheable(true);
		e = (Employee)q1.uniqueResult();
		
		//e = (Employee)session.get(Employee.class, 101);
		System.out.println(e);
		tx.commit();
		session.close();
		
		Session session2 = sf.openSession();
		Transaction tx2 = session2.beginTransaction();
		Query<Employee> q2 = session2.createQuery("from Employee where id = 101");
		q2.setCacheable(true);
		e = (Employee)q2.uniqueResult();
		
		//e = (Employee)session2.get(Employee.class, 101);
		System.out.println(e);
		tx2.commit();
		session2.close();
    }
    
    private static void lesson4a() {
		EmployeeName empName = new EmployeeName();
    	empName.setFname("Joe");
    	empName.setMname("Bill");
    	empName.setLname("Delan");
    	
    	Employee emp = new Employee();
    	emp.setId(102);
    	emp.setAge(24);
    	emp.setName(empName);
    	
		EmployeeName empName2 = new EmployeeName();
    	empName2.setFname("Carl");
    	empName2.setMname("Ken");
    	empName2.setLname("Jonwa");
    	
    	Employee emp2 = new Employee();
    	emp2.setId(103);
    	emp2.setAge(25);
    	emp2.setName(empName2);
    	
    	Configuration con = new Configuration().configure().addAnnotatedClass(Employee.class);
    	
    	SessionFactory sf = con.buildSessionFactory();
    	Session session = sf.openSession();
    	
    	Transaction tx = session.beginTransaction();
    	session.save(emp);
    	session.save(emp2);

    	tx.commit();
    }
    
    private static void lesson3a() {
    	Configuration con = new Configuration().configure().addAnnotatedClass(Desktop.class).addAnnotatedClass(User.class);
    	
    	SessionFactory sf = con.buildSessionFactory();
    	Session session = sf.openSession();
    	
    	Transaction tx = session.beginTransaction();
    	
    	User u1 = session.get(User.class, 101);
    	System.out.println(u1.getName());
    	/*for(Desktop d : u1.getDesktops()) {
    		System.out.println(d.getBrand());
    	}
    	*/
    	tx.commit();
    }
    
    private static void lesson3() {
		Desktop desktop = new Desktop();
		desktop.setDesktopId(3001);
		desktop.setBrand("Dell");
		desktop.setPrice(3000);
		
		Desktop desktop2 = new Desktop();
		desktop2.setDesktopId(4001);
		desktop2.setBrand("Hp");
		desktop2.setPrice(4000);
		
		Desktop desktop3 = new Desktop();
		desktop3.setDesktopId(5001);
		desktop3.setBrand("Mac");
		desktop3.setPrice(5000);
		
		Desktop desktop4 = new Desktop();
		desktop4.setDesktopId(2001);
		desktop4.setBrand("Acer");
		desktop4.setPrice(2000);
		
		User user = new User();
		user.setName("Max");
		user.setUserId(101);
		user.getDesktops().add(desktop);
		
		User user2 = new User();
		user2.setName("Pam");
		user2.setUserId(102);
		user2.getDesktops().add(desktop2);
		user2.getDesktops().add(desktop3);
		
		User user3 = new User();
		user3.setName("Ken");
		user3.setUserId(103);
		user3.getDesktops().add(desktop4);

		desktop.setUser(user);
		desktop2.setUser(user2);
		//desktop3.setUser(user2);
		desktop3.setUser(user2);
		desktop4.setUser(user3);
		
    	Configuration con = new Configuration().configure().addAnnotatedClass(Desktop.class).addAnnotatedClass(User.class);
    	
    	SessionFactory sf = con.buildSessionFactory();
    	Session session = sf.openSession();
    	
    	Transaction tx = session.beginTransaction();
    	session.save(desktop);
    	session.save(desktop2);
    	session.save(desktop3);
    	session.save(desktop4);
    	session.save(user);
    	session.save(user2);
    	session.save(user3);
    	
    	tx.commit();
    	
    	//System.out.println(desktop);
    	//System.out.println(desktop2);
    	//System.out.println(desktop3);
	}
    
	private static void lessonTwo() {
		Laptop laptop = new Laptop();
		laptop.setLaptopId(101);
		laptop.setLaptopName("Dell");
		
		
		Student student = new Student();
		student.setStudentNo(1001);
		student.setName("Mores");
		student.setMarks(89);
		student.getLaptop().add(laptop); //.setLaptop(laptop);
		laptop.setStudent(student);
		
    	Configuration con = new Configuration().configure().addAnnotatedClass(Laptop.class).addAnnotatedClass(Student.class);
    	
    	SessionFactory sf = con.buildSessionFactory();
    	Session session = sf.openSession();
    	
    	Transaction tx = session.beginTransaction();
    	session.save(laptop);
    	session.save(student);
    	
    	tx.commit();
    	
    	System.out.println(laptop);
	}

	private static void lessonOne() {
		EmployeeName empName = new EmployeeName();
    	empName.setFname("Jack");
    	empName.setMname("Mon");
    	empName.setLname("Lamon");
    	
    	Employee emp = new Employee();
    	emp.setId(101);
    	emp.setAge(23);
    	emp.setName(empName);
    	
    	/*emp.setId(2);
    	emp.setLastName("Jill");
    	emp.setAge(32);*/
    	
    	Configuration con = new Configuration().configure().addAnnotatedClass(Employee.class);
    	
    	//ServiceRegistry reg = new SessionFactoryServiceRegistryBuilderImpl().
    			
    	SessionFactory sf = con.buildSessionFactory();
    	Session session = sf.openSession();
    	
    	Transaction tx = session.beginTransaction();
    	session.save(emp);
    	
    	//emp = (Employee)session.get(Employee.class, 1);
    	tx.commit();
    	
    	System.out.println(emp);
	}
    
    
}
