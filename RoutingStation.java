import java.util.Random;

public class RoutingStation implements Runnable
{
	//declare
	private Random gen = new Random();
	private int stationID;
	private int workload;
	private Conveyor InCon;
	private Conveyor OutCon;
	
	//create the object
	public RoutingStation(int stationID, int workload, Conveyor InCon, Conveyor OutCon) 
	{
		this.stationID = stationID;
		this.workload = workload;
		this.InCon = InCon;
		this.setOutCon(OutCon);
	}

	//when the object is created run this
	@Override
	public void run() 
	{
		//intro text
		System.out.println("ROUTING STATION " + this.stationID + " IS COMING ONLINE    : BEGINNING TO INITIALIZE CONVEYORS");
		System.out.println("ROUTING STATION " + this.stationID + ": Input Conveyor set to Conveyor number C" + this.InCon.getIdCon());
		System.out.println("ROUTING STATION " + this.stationID + ": Output Conveyor set to Conveyor number C" + this.OutCon.getIdCon());
		System.out.println("ROUTING STATION " + this.stationID + ": Workload Set with a total of " + this.workload + " package groups to move");
		System.out.println();
		
		//save original workload
		int count = this.workload;
		
		//iterate for workload
		for(int i = 0; i < count; i++)
		{
			int temp = 0;
			System.out.println("ROUTING STATION " + this.stationID + ": " + "is entering lock acquisition phase");
			//a value that checks if both locks have been aquired
			while(temp == 0)
			{
				//check input
				if(this.InCon.getLock())
				{
					System.out.println("ROUTING STATION " + this.stationID + ": " + "holds lock on input conveyor " + this.InCon.getIdCon());
					
					//check output, if station has both do work and decrment workload and release the locks
					if(this.getOutCon().getLock())
					{
						System.out.println("ROUTING STATION " + this.stationID + ": " + "holds lock on output conveyor " + this.getOutCon().getIdCon());
						temp = 1;
						
						doWork();
						
						this.getOutCon().unlockConveyer();
						this.getInCon().unlockConveyer();
						System.out.println("ROUTING STATION " + this.stationID + " : Entering Lock Release Phase");
						System.out.println("ROUTING STATION " + this.stationID + " : unlocks/releases input conveyor C" + this.InCon.getIdCon());
						System.out.println("ROUTING STATION " + this.stationID + " : unlocks/releases output conveyor C" + this.OutCon.getIdCon());
						System.out.println();
						
						System.out.println("ROUTING STATION " + this.stationID + ": has " + this.workload + " package groups to move");
						System.out.println();
						
						//checks if the workload is 0 and if so deactivates the station
						if(this.workload == 0)
						{
							System.out.println("ROUTING STATION " + this.stationID + " : Workload Completed Station going offline");
							System.out.println("ROUTING STATION " + this.stationID + " : Entering Lock Release Phase");
							System.out.println("ROUTING STATION " + this.stationID + " : unlocks/releases input conveyor C" + this.InCon.getIdCon());
							System.out.println("ROUTING STATION " + this.stationID + " : unlocks/releases output conveyor C" + this.OutCon.getIdCon());
							System.out.println("*************** ROUTING STATION " + this.stationID + ": OFFLINE ****************************");
							
						}
					}
					
					//if output cannot be claimed release the input and sleep
					else
					{
						System.out.println("ROUTING STATION " + this.stationID + ": " + "unable to lock output conveyor " + this.getOutCon().getIdCon() + " unlocks input conveyor " + this.InCon.getIdCon());
						System.out.println();
						
						this.getInCon().unlockConveyer();
						
						goToSleep();
					}
					
				}
			}
		}
	}
	
	//decrements the workload and puts the thread to sleep
	public void doWork()
	{
		System.out.println("ROUTING STATION " + this.stationID + " : Currently hard at work moving packages");
		System.out.println("ROUTING STATION " + this.stationID + " : Successfully moved packages into station on input conveyor C"+ this.InCon.getIdCon());
		System.out.println("ROUTING STATION " + this.stationID + " : Successfully moved packages out of station on output conveyor C"+ this.getOutCon().getIdCon());
		System.out.println();
		
		goToSleep();
		
		this.workload--;
		
	}

	//puts the thread to sleep for a num between 0-500 ms
	public void goToSleep() {
		try 
		{
			Thread.sleep(gen.nextInt(500));
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}

	//getter/setters that are created
	public Conveyor getOutCon() {
		return OutCon;
	}
	public Conveyor getInCon() {
		return InCon;
	}

	public void setOutCon(Conveyor outCon) {
		OutCon = outCon;
	}
	
	
}
