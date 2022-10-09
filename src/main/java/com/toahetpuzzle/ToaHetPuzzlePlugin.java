package net.runelite.client.plugins.toahetpuzzle;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.GraphicsObjectCreated;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;


@Slf4j
@PluginDescriptor(
		name = "Toa Het Puzzle"
)
public class ToaHetPuzzlePlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ToaHetPuzzleConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ToaHetPuzzleOverlay overlay;

	@Override
	protected void startUp() throws Exception
	{
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onGraphicsObjectCreated(GraphicsObjectCreated graphicsObject)
	{
		if (graphicsObject.getGraphicsObject().getId() == 2114)
		{
			startCountdown();
		}
	}

	@Subscribe
	public void onGameTick(GameTick gametick)
	{
		int t = overlay.getTimer();
		if (t == 0){return;}
		overlay.setTimer(t-1);
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event)
	{
		GameObject object = event.getGameObject();
		if (object.getId() == 45486)
		{
			overlay.setObject(object);
		}
	}

	@Provides
	ToaHetPuzzleConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ToaHetPuzzleConfig.class);
	}

	private void startCountdown()
	{
		overlayManager.add(overlay);
		overlay.setTimer(9);
	}
}
