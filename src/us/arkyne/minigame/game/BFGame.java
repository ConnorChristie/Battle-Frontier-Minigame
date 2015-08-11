package us.arkyne.minigame.game;

import java.util.Map;

import us.arkyne.server.game.Game;

public class BFGame extends Game
{
	public void onLoad()
	{
		super.onLoad();
	}
	
	public void onUnload()
	{
		super.onUnload();
	}
	
	public BFGame(Map<String, Object> map)
	{
		super(map);
	}
	
	public Map<String, Object> serialize()
	{
		Map<String, Object> map = super.serialize();
		
		return map;
	}
}