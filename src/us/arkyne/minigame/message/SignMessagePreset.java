package us.arkyne.minigame.message;

import org.bukkit.ChatColor;

import us.arkyne.server.message.SignMessage;

public enum SignMessagePreset implements SignMessage
{
	BF_LOBBY(
		ChatColor.DARK_BLUE +   "{lobby}",
		ChatColor.DARK_PURPLE + "{lobby-id}",
		ChatColor.DARK_GREEN +  "{count} Players",
		ChatColor.BLUE +   "Minigame Lobby"
	),
	BF_PREGAME_LOBBY(
		ChatColor.DARK_BLUE +   "{lobby}",
		ChatColor.DARK_PURPLE + "{lobby-id}",
		ChatColor.DARK_GREEN +  "{count}/{max} Players",
		ChatColor.BLUE +        "{map}"
	);
	
	private String[] lines;
	
	private SignMessagePreset(String... lines)
	{
		this.lines = lines;
	}
	
	public String getLine(int line)
	{
		return lines[line];
	}
	
	public String replace(int line, String search, String replace)
	{
		return getLine(line).replace(search, replace);
	}

	@Override
	public String[] getLines()
	{
		return lines;
	}
}