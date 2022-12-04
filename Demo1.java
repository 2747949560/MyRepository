import java.time.LocalDate;
import java.util.*;
//动物类
abstract class Animal{     
	protected String name;
	protected int age;
	protected String sex;
	protected double price;
	public Animal() {
		
	}
	public Animal(String name, int age, String sex, double price) {
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.price = price;
	}
	public abstract String toString();
}
//动物类子类：犬类
class Dog extends Animal{
	boolean isVaccineInjected; // 是否注射狂犬病疫苗
	public Dog() {
	}
	public Dog(String name, int age, String sex,boolean isVaccineInjected) {
		super(name, age, sex, 100);
	}	
	public String toString() {
		return "狗狗名字："+name+"，年龄："+age+",性别："+sex+",注射狂犬病疫苗："+isVaccineInjected;
	}
}
//动物类子类：猫类
class Cat extends Animal{  
	public Cat() {
	}
	public Cat(String name, int age, String sex) {
		super(name, age, sex, 200);
	}
	public String toString() {
		return "猫猫名字："+name+"，年龄："+age+",性别："+sex;
	}
}
//动物类子类：鸟类
class Bird extends Animal{ 
	public Bird() {
	}
	public Bird(String name, int age, String sex) {
		super(name, age, sex, 80);
	}
	public String toString() {
		return "鸟儿名字："+name+"，年龄："+age+",性别："+sex;
	}
}
//顾客类
class Customer{			
	private String name;
	private int count=0;
	protected double newSpend;
	String newDate;
	LocalDate localDate=LocalDate.now();
//	String afterNow=newDate.replace("-", "");
	public Customer(String name) {
		this.name = name;
		count++;
		newDate=String.valueOf(localDate);
	}
	//顾客最新到店时间
	@Override
	public String toString() {
		return name+"：第"+count+"次光临，最新一次是"+newDate;
	}
}
//宠物店接口
interface AnimalShop{
	void buyAn(Animal an) throws InsufficientBalanceException;
	void helloCu(Customer cu,Animal buyAn) throws InsufficientBalanceException;
	void offStore(LocalDate localDate);
}
//MyAnimalShop自己的宠物店
class MyAnimalShop implements AnimalShop{ 
	private double balance;
	private double profits;
	ArrayList<Animal> anList=new ArrayList<>();
	ArrayList<Customer> cuList=new ArrayList<>();
	public MyAnimalShop(double balance) {
		this.balance = balance;
	}
	//实现接口中的方法：买入动物
	public void buyAn(Animal an) throws InsufficientBalanceException{
		if(an.price>balance) {
			throw new InsufficientBalanceException("余额不足！");
		}
		else {
			anList.add(an);
		}
	}
	//实现接口中的方法：招待客户
	public void helloCu(Customer cu,Animal buyAn) throws InsufficientBalanceException{
		cuList.add(cu);
		for(int i=0;i<anList.size();i++) {
			if(buyAn.getClass()!=anList.get(i).getClass()) {	
				continue;
			}
			else{
				cuList.get(i).newSpend=1.5*anList.get(i).price;
				System.out.println("卖出的动物信息：");
				anList.get(i).toString();
				balance+=cuList.get(i).newSpend;     //统一以成本价的1.5倍卖出
				anList.remove(i);
			}
			if(i>=anList.size()) {
				throw new AnimalNotFountException("没有找到"+buyAn.name+"！");
			}
		}
	}
	//实现接口中的方法：歇业
	public void offStore(LocalDate localDate) {
		for(int i=0;i<cuList.size();i++) {
			if(localDate.equals(cuList.get(i).localDate)) {
				cuList.get(i).toString();
				profits+=(cuList.get(i).newSpend)/3;
				cuList.get(i).toString();
			}
		}
		System.out.println("当天营业利润"+profits);
	}
}
//没找到动物异常
class AnimalNotFountException extends RuntimeException{ 
	private static final long serialVersionUID=999L;
	AnimalNotFountException(String msg){
		super(msg);
	}
}
//余额不足异常
class InsufficientBalanceException extends RuntimeException{ 
	private static final long serialVersionUID=999L;
	InsufficientBalanceException(String msg){
		super(msg);
	}
}
	
//测试类
public class Demo1 {
	public static void main(String[] args) {
		MyAnimalShop myShop=new MyAnimalShop(500);
		Dog dogA=new Dog("dogA",3,"female",true);
		Dog dogB=new Dog("dogB",5,"male",true);
		Cat catA=new Cat("catA",2,"female");
		Bird birdA=new Bird("birdA",2,"male");
		Cat catB=new Cat("catB",3,"male");
		Dog dogC=new Dog("dogC",4,"male",false);
		myShop.anList.add(dogA);
		myShop.anList.add(dogB);
		myShop.anList.add(catA);
		//买入动物
		myShop.buyAn(birdA);
		myShop.buyAn(catB);
		myShop.buyAn(dogC);
		myShop.buyAn(new Bird());
		//招待顾客
		myShop.helloCu(new Customer("顾客甲"),dogA);
		myShop.helloCu(new Customer("顾客乙"),catB);
		//歇业
		myShop.offStore(LocalDate.now());
	}
}
