package hades.oldschool.lawcrafter.tools;

import hades.oldschool.lawcrafter.LawCrafter;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;

public class MovementHandler{
	private static final String PLANK = "Gangplank";
	private static DPathNavigator dNav = new DPathNavigator();
	
	public static boolean walkToPort(final IdleActionsHandler antiBan, boolean sarim){
		if(antiBan != null)
			antiBan.setRun();
		RSTile end = sarim ? new RSTile(3047, 3235, 0, RSTile.TYPES.WORLD) : new RSTile(2839, 3335, 0, RSTile.TYPES.WORLD);
		LawCrafter.drawTiles = dNav.findPath(end);
		if(!Walking.walkPath(LawCrafter.drawTiles))
			return false;
		return LocationHandler.isAtPort(sarim);
	}
	
	public static boolean walkToBank(final IdleActionsHandler antiBan){
		if(antiBan != null)
			antiBan.setRun();
		RSTile end = new RSTile(3092, 3245, 0, RSTile.TYPES.WORLD);
		LawCrafter.drawTiles = dNav.findPath(end);
		if(!Walking.walkPath(LawCrafter.drawTiles))
			return false;
		return LocationHandler.isAtBank();
	}
	
	public static boolean walkToRuins(IdleActionsHandler antiBan){
		if(antiBan != null)
			antiBan.setRun();
		RSTile end = new RSTile(2858, 3378, 0, RSTile.TYPES.WORLD);
		LawCrafter.drawTiles = dNav.findPath(end);
		if(!dNav.traverse(end))
			return false;
		return LocationHandler.isAtRuins();
	}
	
	public static boolean walkToAltar(IdleActionsHandler antiBan){
		if(antiBan != null)
			antiBan.setRun();
		final RSTile dest = new RSTile(2464, 4829, 0);
		if(!WebWalking.walkTo(dest, new Condition(){
			public boolean active(){
				//CameraHandler.faceTile(Game.getDestination());
				return false;
			}
		}, 0))
			return false;
		return Timing.waitCondition(new Condition(){
			public boolean active(){
				RSObject objects[] = Objects.findNearest(5, "Altar");
				if(objects != null && objects.length > 0 && objects[0].isOnScreen())
					return DynamicClicking.clickRSObject(objects[0], 1);
				return !Player.isMoving();
			}
		}, General.random(4000, 5000));
	}
	
	public static boolean walkToPortal(IdleActionsHandler antiBan){
		if(antiBan != null)
			antiBan.setRun();
		final RSTile dest = new RSTile(2464, 4818, 0);
		if(!WebWalking.walkTo(dest, new Condition(){
			public boolean active(){
				//CameraHandler.faceTile(Game.getDestination());
				return false;
			}
		}, 0))
			return false;
		return Timing.waitCondition(new Condition(){
			public boolean active(){
				RSObject objects[] = Objects.findNearest(5, "Portal");
				if(objects != null && objects.length > 0 && objects[0].isOnScreen())
					return DynamicClicking.clickRSObject(objects[0], 1);
				return !Player.isMoving();
			}
		}, General.random(4000, 5000));
	}
	
	public static boolean takeBoat(IdleActionsHandler antiBan){
		if(antiBan == null)
			return false;
		RSNPC npcs[] = NPCs.findNearest("Monk of Entrana");
		if(npcs == null || npcs.length == 0)
			return false;
		return antiBan.takeBoat(npcs);
	}
	
	public static boolean crossGangPlank(){
		RSObject objects[] = Objects.findNearest(5, PLANK);
		if(objects == null || objects.length == 0)
			return false;
		DynamicClicking.clickRSObject(objects[0], 1);
		return Timing.waitCondition(new Condition(){
			public boolean active(){
				return !LocationHandler.isOnShip();
			}
		}, General.random(2500, 3000));
	}
}