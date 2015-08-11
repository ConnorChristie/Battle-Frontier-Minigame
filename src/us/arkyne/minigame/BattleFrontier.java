package us.arkyne.minigame;

import java.util.Map;

import us.arkyne.minigame.command.BattleFrontierCommand;
import us.arkyne.server.minigame.Minigame;

public class BattleFrontier extends Minigame
{
	public BattleFrontier()
	{
		super(MinigameMain.getInstance(), "BattleFrontier", "BF-G");
		
		//Try not to use this method
	}
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		
		getMain().getCommandHandler().registerCommand(BattleFrontierCommand.class);
	}

	@Override
	public void onUnload()
	{
		
		
		super.onUnload();
	}
	
	public BattleFrontier(Map<String, Object> map)
	{
		super(map);
		
		
	}
	
	public Map<String, Object> serialize()
	{
		Map<String, Object> map = serialize();
		
		return map;
	}
}