package hades.oldschool.lawcrafter.tools;

import hades.oldschool.lawcrafter.LawCrafter;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.ABCUtil;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Options;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSNPC;

public class IdleActionsHandler{	
	private ABCUtil abcUtil;
	
	public IdleActionsHandler(){
		abcUtil = new ABCUtil();
	}
	
	public boolean checkXP(){
		long time = abcUtil.TIME_TRACKER.CHECK_XP.next();
		if(Timing.currentTimeMillis() < time || time <= 0)
			return false;
		General.println("IDLE ACTION: Checking XP.");
		return abcUtil.performXPCheck(SKILLS.RUNECRAFTING);
	}
	
	public boolean openInventory(){
		if(GameTab.getOpen() == GameTab.TABS.INVENTORY)
			return true;
		return GameTab.open(GameTab.TABS.INVENTORY);
	}
	
	public boolean checkRandomObject(){
		long time = abcUtil.TIME_TRACKER.EXAMINE_OBJECT.next();
		if(Timing.currentTimeMillis() < abcUtil.TIME_TRACKER.EXAMINE_OBJECT.next() || time <= 0)
			return false;
		General.println("IDLE ACTION: Checking random object.");
		return abcUtil.performExamineObject();
	}
	
	public boolean rotateCam(){
		long time = abcUtil.TIME_TRACKER.ROTATE_CAMERA.next();
		if(Timing.currentTimeMillis() < abcUtil.TIME_TRACKER.ROTATE_CAMERA.next() || time <= 0)
			return false;
		General.println("IDLE ACTION: Rotating the camera.");
		return abcUtil.performRotateCamera();
	}
	
	public boolean leaveGame(){
		long time = abcUtil.TIME_TRACKER.LEAVE_GAME.next();
		if(Timing.currentTimeMillis() >= abcUtil.TIME_TRACKER.LEAVE_GAME.next() && time > 0)
			General.println("IDLE ACTION: Leaving game.");
		return abcUtil.performLeaveGame();
	}
	
	public boolean pickUpMouse(){
		long time = abcUtil.TIME_TRACKER.PICKUP_MOUSE.next();
		if(Timing.currentTimeMillis() >= abcUtil.TIME_TRACKER.PICKUP_MOUSE.next() && time > 0)
			General.println("IDLE ACTION: Picking up the mouse.");
		return abcUtil.performPickupMouse();
	}
	
	public boolean randomMouseMovement(){
		long time = abcUtil.TIME_TRACKER.RANDOM_MOUSE_MOVEMENT.next();
		if(Timing.currentTimeMillis() >= abcUtil.TIME_TRACKER.RANDOM_MOUSE_MOVEMENT.next() && time > 0)
			General.println("IDLE ACTION: Moving mouse randomly.");
		return abcUtil.performRandomMouseMovement();
	}
	
	public boolean randomRightClick(){
		long time = abcUtil.TIME_TRACKER.RANDOM_RIGHT_CLICK.next();
		if(Timing.currentTimeMillis() >= abcUtil.TIME_TRACKER.RANDOM_RIGHT_CLICK.next() && time > 0)
			General.println("IDLE ACTION: Right clicking randomly.");
		return abcUtil.performRandomRightClick();
	}
	
	public boolean setRun(){
		int run = abcUtil.INT_TRACKER.NEXT_RUN_AT.next();
		if(Game.getRunEnergy() < run || run <= 0 || Game.isRunOn())
			return false;
		abcUtil.INT_TRACKER.NEXT_RUN_AT.reset();
		return Options.setRunOn(true);
	}
	
	public boolean takeBoat(RSNPC npcs[]){
		boolean useClosest = abcUtil.BOOL_TRACKER.USE_CLOSEST.next();
		abcUtil.BOOL_TRACKER.USE_CLOSEST.reset();
		RSNPC npc = ((npcs.length == 1 || !useClosest) ? npcs[0] : (npcs[0].getPosition().distanceTo(npcs[1].getPosition()) < 3 ? npcs[1] : npcs[0]));
		int failures = 0;
		while(!DynamicClicking.clickRSNPC(npc, "Take-boat"))
			if(++failures == LawCrafter.MAX_FAILURES)
				return false;
		if(!Timing.waitCondition(new Condition(){
			public boolean active(){
				leaveGame();
				pickUpMouse();
				randomMouseMovement();
				randomRightClick();
				return LocationHandler.isOnShip();
			}
		}, General.random(15000, 16000)))
			return false;
		return MovementHandler.crossGangPlank();
	}
}