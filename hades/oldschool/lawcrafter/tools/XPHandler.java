package hades.oldschool.lawcrafter.tools;

import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;

public class XPHandler{
	private boolean usable;
	private int startLevel;
	private int currentLevel;
	private int startXP;
	private int currentXP;
	
	public XPHandler(){
		startLevel = -1;
		currentLevel = -1;
		startXP = -1;
		currentXP = -1;
		setStartVars();
		setCurrentVars(true);
		usable = startXP > -1 && startLevel > -1 && currentXP > -1 && currentLevel > -1;
	}
	
	public void setStartVars(){
		startXP = Skills.getXP(SKILLS.RUNECRAFTING);
		startLevel = Skills.getLevelByXP(startXP);
	}
	
	public void setCurrentVars(boolean copyStart){
		if(copyStart){
			currentLevel = startLevel;
			currentXP = startXP;
			return;
		}
		currentXP = Skills.getXP(SKILLS.RUNECRAFTING);
		currentLevel = Skills.getLevelByXP(currentXP);
	}
	
	public int getStartLevel(){
		return startLevel;
	}
	
	public int getCurrentLevel(){
		return currentLevel;
	}
	
	public int getStartXP(){
		return startXP;
	}
	
	public int getCurrentXP(){
		return currentXP;
	}
	
	public boolean isUsable(){
		return usable;
	}
}