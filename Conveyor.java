import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class Conveyor 
{
	//declare
	private int IdCon;
	private Lock lock;
	
	//create the object
	public Conveyor(int IdCon)
	{
		this.setIdCon(IdCon);
		this.lock = new ReentrantLock();
	}
	
	//getter/setters that are created
	public boolean getLock() 
	{
		boolean bool = this.lock.tryLock();
		return bool;
	}

	public int getIdCon() 
	{
		return IdCon;
	}

	public void setIdCon(int idCon) 
	{
		IdCon = idCon;
	}
	
	//unlock the lock of the conveyor
	public void unlockConveyer()
	{
		this.lock.unlock();
	}
	
	
	
	
}
