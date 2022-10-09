package net.runelite.client.plugins.toahetpuzzle;

import java.awt.*;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;

public class ToaHetPuzzleOverlay extends Overlay{

    private final Client client;
    private final ToaHetPuzzleConfig config;
    private int timer;
    private Color color;
    private GameObject object;

    @Inject
    private ToaHetPuzzleOverlay(Client client, ToaHetPuzzleConfig config)
    {
        this.client = client;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        setPriority(OverlayPriority.MED);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        Point point = this.object.getSceneMinLocation();
        WorldPoint statue = WorldPoint.fromScene(client, point.getX()+4, point.getY()+1, 0);
        LocalPoint dest = LocalPoint.fromWorld(client, statue);

        // text overlay
        Polygon poly = this.object.getCanvasTilePoly();
        Point canvasTextLocation = Perspective.getCanvasTextLocation(client, graphics, dest, Integer.toString(timer), 0);
        if (canvasTextLocation != null) {
            if (timer == 1){color = Color.GREEN;}
            else if (timer == 2){color = Color.YELLOW;}
            else {color = Color.RED;}
            //OverlayUtil.renderPolygon(graphics, poly, Color.CYAN);
            OverlayUtil.renderTextLocation(graphics, canvasTextLocation, Integer.toString(timer), color);
        }
        return null;
    }

    public void setTimer(int timer){this.timer = timer;}

    public int getTimer(){return timer;}

    public void setObject(GameObject object){this.object = object;}
}
