package anavs;

import java.util.concurrent.Semaphore;

public class Thread_2  extends Thread{
private Semaphore[] sems;

public Thread_2 (Semaphore[] sems, String name){
	super(name);
	this.sems = sems;
	start();
}

private void Activity_2(){
	System.out.println("Activity_2 running");
}

public void run (){
	try {
		sems[0].acquire();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Activity_2();
	sems[3].release();
	
}
}
