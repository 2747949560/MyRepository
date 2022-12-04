import java.time.LocalDate;
import java.util.*;
//������
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
//���������ࣺȮ��
class Dog extends Animal{
	boolean isVaccineInjected; // �Ƿ�ע���Ȯ������
	public Dog() {
	}
	public Dog(String name, int age, String sex,boolean isVaccineInjected) {
		super(name, age, sex, 100);
	}	
	public String toString() {
		return "�������֣�"+name+"�����䣺"+age+",�Ա�"+sex+",ע���Ȯ�����磺"+isVaccineInjected;
	}
}
//���������ࣺè��
class Cat extends Animal{  
	public Cat() {
	}
	public Cat(String name, int age, String sex) {
		super(name, age, sex, 200);
	}
	public String toString() {
		return "èè���֣�"+name+"�����䣺"+age+",�Ա�"+sex;
	}
}
//���������ࣺ����
class Bird extends Animal{ 
	public Bird() {
	}
	public Bird(String name, int age, String sex) {
		super(name, age, sex, 80);
	}
	public String toString() {
		return "������֣�"+name+"�����䣺"+age+",�Ա�"+sex;
	}
}
//�˿���
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
	//�˿����µ���ʱ��
	@Override
	public String toString() {
		return name+"����"+count+"�ι��٣�����һ����"+newDate;
	}
}
//�����ӿ�
interface AnimalShop{
	void buyAn(Animal an) throws InsufficientBalanceException;
	void helloCu(Customer cu,Animal buyAn) throws InsufficientBalanceException;
	void offStore(LocalDate localDate);
}
//MyAnimalShop�Լ��ĳ����
class MyAnimalShop implements AnimalShop{ 
	private double balance;
	private double profits;
	ArrayList<Animal> anList=new ArrayList<>();
	ArrayList<Customer> cuList=new ArrayList<>();
	public MyAnimalShop(double balance) {
		this.balance = balance;
	}
	//ʵ�ֽӿ��еķ��������붯��
	public void buyAn(Animal an) throws InsufficientBalanceException{
		if(an.price>balance) {
			throw new InsufficientBalanceException("���㣡");
		}
		else {
			anList.add(an);
		}
	}
	//ʵ�ֽӿ��еķ������д��ͻ�
	public void helloCu(Customer cu,Animal buyAn) throws InsufficientBalanceException{
		cuList.add(cu);
		for(int i=0;i<anList.size();i++) {
			if(buyAn.getClass()!=anList.get(i).getClass()) {	
				continue;
			}
			else{
				cuList.get(i).newSpend=1.5*anList.get(i).price;
				System.out.println("�����Ķ�����Ϣ��");
				anList.get(i).toString();
				balance+=cuList.get(i).newSpend;     //ͳһ�Գɱ��۵�1.5������
				anList.remove(i);
			}
			if(i>=anList.size()) {
				throw new AnimalNotFountException("û���ҵ�"+buyAn.name+"��");
			}
		}
	}
	//ʵ�ֽӿ��еķ�����Ъҵ
	public void offStore(LocalDate localDate) {
		for(int i=0;i<cuList.size();i++) {
			if(localDate.equals(cuList.get(i).localDate)) {
				cuList.get(i).toString();
				profits+=(cuList.get(i).newSpend)/3;
				cuList.get(i).toString();
			}
		}
		System.out.println("����Ӫҵ����"+profits);
	}
}
//û�ҵ������쳣
class AnimalNotFountException extends RuntimeException{ 
	private static final long serialVersionUID=999L;
	AnimalNotFountException(String msg){
		super(msg);
	}
}
//�����쳣
class InsufficientBalanceException extends RuntimeException{ 
	private static final long serialVersionUID=999L;
	InsufficientBalanceException(String msg){
		super(msg);
	}
}
	
//������
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
		//���붯��
		myShop.buyAn(birdA);
		myShop.buyAn(catB);
		myShop.buyAn(dogC);
		myShop.buyAn(new Bird());
		//�д��˿�
		myShop.helloCu(new Customer("�˿ͼ�"),dogA);
		myShop.helloCu(new Customer("�˿���"),catB);
		//Ъҵ
		myShop.offStore(LocalDate.now());
	}
}
