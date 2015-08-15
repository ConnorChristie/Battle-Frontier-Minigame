package us.arkyne.minigame.game;

import java.util.Map;

import us.arkyne.minigame.MinigameMain;
import us.arkyne.minigame.message.SignMessagePreset;
import us.arkyne.server.game.Game;
import us.arkyne.server.minigame.Minigame;

public class BFGame extends Game
{
	public BFGame(Minigame minigame, int id, String mapName)
	{
		super(minigame, id, 20, 2, mapName, SignMessagePreset.BF_GAME);
	}
	
	public void onLoad()
	{
		super.onLoad();
		
		minigame = MinigameMain.getInstance().getBattleFrontier();
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