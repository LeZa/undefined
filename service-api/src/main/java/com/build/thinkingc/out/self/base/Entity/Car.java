package base.Entity;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.Set;
import java.util.HashSet;

public class Car {

	private final int id;
	private boolean engine = false,driveTrain = false, wheels = false;
	
	public Car(int idn){
		id = idn;
	}
	public Car(){
		id = -1;
	}
	public synchronized int getId(){
		return id;
	}
	public synchronized void addEngine(){
		engine = true;
	}
	
	public synchronized void addDriveTrain(){
		driveTrain = true;
	}
	
	public synchronized void addWheels(){
		wheels = true;
	}
	
	public synchronized String toString(){
		return "Car "+ id +" ["+" engine: "+engine+" driveTrain: "+driveTrain+" wheels: "+wheels+" ]";
	} 
	
}

	class CarQueue extends LinkedBlockingQueue<Car>{}
	
	
	/*
	 * Chassis:�������ģ� ����; ���ɻ�ģ� �����; �ڵ׼�; �����ߵ硢���ӵȵģ� �׼�;
	 */
	class ChassisBuilder implements Runnable{
		
		private CarQueue carQueue;
		private int counter = 0;
		public ChassisBuilder (CarQueue cq){
			this.carQueue = cq;
		}
		
		public void run(){
			while( !Thread.interrupted() ){
				try {
					TimeUnit.MILLISECONDS.sleep(500);
					Car c = new Car(counter++);
					System.out.println("ChassisBuilder created " +c);
					carQueue.put(c);
				} catch (InterruptedException e) {
					System.out.println("Interrupted: ChassisBuilder");
				}
				System.out.println("ChassisBuilder off");
			}
		}
	}


	/*
	 *  Assembler:װ�乤
	 */
	class Assembler implements Runnable{
		
		private CarQueue chassisQueue,finishingQueue;
		private Car car;
		private CyclicBarrier barrier =  new CyclicBarrier(4);
//		private CountDownLatch  barrier = new CountDownLatch(4);
		private RobotPool robotPool;
		public Assembler( CarQueue cq,CarQueue fq,RobotPool rp){
			this.chassisQueue  = cq;
			this.finishingQueue = fq;
			this.robotPool = rp;
		}
		
		public Car car(){
			return car;
		}
		
		public CyclicBarrier barrier(){
			return barrier;
		}
		
		public void run(){
			try {
				car = chassisQueue.take();
				robotPool.hire(EngineRobot.class,this);
				robotPool.hire(DriveTrainRobot.class,this);
				robotPool.hire(WheelRobot.class,this);
				barrier.await();
				finishingQueue.put(car);
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			System.out.println("Assembler off");
		}
	}
	
	
	class Reporter implements Runnable{
		
		private CarQueue carQueue;
		public Reporter( CarQueue cq){
			this.carQueue = cq;
		}
		
		public void run(){
			while(!Thread.interrupted()){
				try {
					System.out.println(carQueue.take());
				} catch (InterruptedException e) {
					System.out.println("Exiting Reporter via interrupt");
				}
				System.out.println("Reporter off");
			}
		}
	}


	abstract class Robot implements Runnable{
		
		private RobotPool pool;
		protected Assembler assembler;
		private boolean engage = false;
		public Robot( RobotPool rp){
			this.pool = rp;
		}
		public Robot assignAssembler( Assembler assembler){
			this.assembler = assembler;
			return this;
		}
		public synchronized void engage(){
			engage = true;
			notifyAll();
		}
		
		abstract protected void performService();
		public void run(){
			try {
				powerDown();
				while( !Thread.interrupted()){
					performService();
					assembler.barrier().await();
					powerDown();
				}
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			
		}
		
		private synchronized void powerDown() throws InterruptedException{
			
			engage = false;
			assembler = null;
			pool.release(this);
			while(engage == false){
				wait();
			}
		}
		
		public  String toString(){
			return getClass().getName();
		}
	}
	
	class EngineRobot extends Robot{

		public EngineRobot(RobotPool rp) {
			super(rp);
		}

		@Override
		protected void performService() {
			System.out.println(this +" installing engine.");
			assembler.car().addEngine();
		}
	}
	
	class DriveTrainRobot extends Robot{

		public DriveTrainRobot(RobotPool rp) {
			super(rp);
		}

		@Override
		protected void performService() {
			System.out.println(this +" installing driveTrain");
			assembler.car().addDriveTrain();
		}
		
	}
	
	class WheelRobot extends Robot{

		public WheelRobot(RobotPool rp) {
			super(rp);
		}

		@Override
		protected void performService() {
			System.out.println(this +" installing wheel");
			assembler.car().addWheels();
		}
		
	}
	
	class RobotPool {
		private Set<Robot> pool = new HashSet<Robot>();
		
		public synchronized void add(Robot r){
			pool.add(r);
			notifyAll();
		}
		
		public  synchronized void hire( Class<? extends Robot> robotType,Assembler d)
				throws InterruptedException{
			for( Robot r : pool){
				if( r.getClass().equals(robotType)){
					pool.remove(r);
					r.assignAssembler(d);
					r.engage();
					return;
				}
			}
			wait();
			hire(robotType,d);
		}
		
		public  synchronized  void  release(Robot r){
			add(r);
		}
	}
	