package hades.oldschool.lawcrafter.tools;

import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;

public class LocationHandler{
	private static final String BANK = "Bank booth";
	private static final String CRANE = "Crane";
	private static final String PLANK = "Gangplank";
	private static final String RUINS = "Mysterious ruins";
	private static final String ALTAR = "Altar";
	private static final String SHIP_LADDER = "Ship's ladder";
	private static final String BENCH = "Bench";
	
	public static boolean isAtBank(){
		RSObject objects[] = Objects.find(5, BANK);
		return objects != null && objects.length > 0;
	}
	
	public static boolean isAtPort(boolean sarim){
		RSObject cranes[] = Objects.find(5, CRANE);
		RSObject planks[] = Objects.find(5, PLANK);
		RSObject benches[] = Objects.find(10, BENCH);
		return (planks != null && planks.length > 0 && (sarim ? (cranes != null && cranes.length > 0) : (benches != null && benches.length > 0)));
	}
	
	public static boolean isAtRuins(){
		RSObject objects[] = Objects.find(10, RUINS);
		return objects != null && objects.length > 0;
	}
	
	public static boolean isAtAltar(){
		RSObject objects[] = Objects.find(20, ALTAR);
		return objects != null && objects.length > 0;
	}
	
	public static boolean isOnShip(){
		RSObject objects[] = Objects.find(2, SHIP_LADDER);
		return objects != null && objects.length > 0;
	}
}