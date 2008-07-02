package net.sourceforge.jpotpourri.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.sourceforge.jpotpourri.tools.UserSniffer;

public class TableEmptyRowsPainter {
	
	private final ITableFillEmptyRowsReceiver table;
	
	
	public TableEmptyRowsPainter(ITableFillEmptyRowsReceiver table) {
		if(table == null) {
			throw new NullPointerException("table");
		}
		this.table = table;
	}
	
	/**
	 * !!! must have invoked super.paint(g); before !!!
	 */
    public void delegatePaint(Graphics g) {
        final int rowCount = table.getRowCount();
        final Rectangle clip = g.getClipBounds();
        final int height = clip.y + clip.height;
        if (rowCount * table.getRowHeight() < height) {
            for (int i = rowCount; i <= height/table.getRowHeight(); ++i) {
                g.setColor(colorForRow(i));
                g.fillRect(clip.x, i * table.getRowHeight(), clip.width, table.getRowHeight());
            }

            // Mac OS' Aqua LAF never draws vertical grid lines, so we have to draw them ourselves.
            if (UserSniffer.isMacOSX() && table.getShowVerticalLines()) {
                g.setColor(IMacColors.MAC_UNFOCUSED_UNSELECTED_VERTICAL_LINE_COLOR);
                TableColumnModel model = table.getColumnModel();
                int x = 0;
                for (int i = 0; i < model.getColumnCount(); ++i) {
                    TableColumn column = model.getColumn(i);
                    x += column.getWidth();
                    g.drawLine(x - 1, rowCount * table.getRowHeight(), x - 1, height);
                }
            }
        }
    }

    protected Color colorForRow(int row) {
        return (row % 2 == 1) ? this.table.getColorRowBackgroundOdd() : this.table.getColorRowBackgroundEven();
    }
}
