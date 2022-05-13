/* 
  Name: Gabriel Couto 
  Course: CNT 4714 Spring 2022 
  Assignment title: Project 2 – Multi-threaded programming in Java 
  Date:  February 13, 2022 
 
  Class:  Enterprise Computing 
*/ 

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainDriver {
static int MAX = 10;
	
	public static void main(String args[])
	{
		try 
		{
			//intro words
			System.out.println("*****************PACKAGE MANAGEMENT SYSTEM BEGINS***************");
			System.out.println();
			System.out.println();
			
			//declare
			Scanner file = new Scanner(new File("config.txt"));
			ArrayList<Integer> config = new ArrayList<Integer>();
			ArrayList<Conveyor> Conveyors = new ArrayList<Conveyor>();
			ArrayList<RoutingStation> RoutingStations = new ArrayList<RoutingStation>();
			ExecutorService app = Executors.newFixedThreadPool(MAX);
			
			//fill config from the input file
			while(file.hasNext())
			{
				int number = file.nextInt();
				config.add(number);
			}
			file.close();
			
			//print the workloads
			for(int i = 0; i < config.get(0); i++)
			{
				System.out.println("Rounting Station " + i + " has a total Workload of " + config.get(i + 1));
			}
			System.out.println();
			
			
			/*create Conveyors
			for(int i = 0; i < config.get(0); i++)
			{
				Conveyors.add(new Conveyor(i));
			}*/
			
			//create RoutingStations
			for(int j = 0; j < config.get(0); j++)
			{
				//create first and last conveyors with first station
				if(j == 0)
				{
					RoutingStations.add(new RoutingStation(j,config.get(j + 1), new Conveyor(config.get(0) - 1), new Conveyor(0)));
					app.execute(RoutingStations.get(j));
				}
				// On the last station the conveyors are already created so just assign them 
				else if(j == (config.get(0) - 1))
				{
					RoutingStations.add(new RoutingStation(j,config.get(j + 1), RoutingStations.get(j - 1).getOutCon(), RoutingStations.get(0).getInCon()));
					app.execute(RoutingStations.get(j));
				}
				// create a new station while creating a new output conveyor and assigning the previous stations output conveyor as input
				else
				{
					RoutingStations.add(new RoutingStation(j,config.get(j + 1), RoutingStations.get(j - 1).getOutCon(), new Conveyor(j)));
					app.execute(RoutingStations.get(j));
				}
			}
			//stop the creation and starting of new threads
			app.shutdown();
			
			//check if all threads are done and if so send the end of simulatiuon text
			boolean done = app.awaitTermination(1, TimeUnit.MINUTES);
			if(done == true)
			{
				System.out.println();
				System.out.println("*******************END OF SIMULATION*************************");
			}
		}
		
		//execption
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
