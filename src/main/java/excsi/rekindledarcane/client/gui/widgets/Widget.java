package excsi.rekindledarcane.client.gui.widgets;

import net.minecraft.client.gui.GuiButton;

import java.util.ArrayList;
import java.util.List;

public abstract class Widget extends GuiButton {

    protected List<String> descriptionTooltip = new ArrayList<>();

    public Widget(int id, int x, int y, int width, int height, String label) {
        super(id, x, y, width, height, label);
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
    }

    public List<String> getDescriptionTooltip() {
        return descriptionTooltip;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof GuiButton))
            return false;
        GuiButton widget = (GuiButton) obj;
        return id == widget.id;
    }
}
