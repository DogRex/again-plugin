package com.again.jface.viewer;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.swt.graphics.Image;

public abstract class ComplexTableLabelProvider extends CellLabelProvider {
	
	@Override
	protected void initialize(ColumnViewer viewer, ViewerColumn column) {
		super.initialize(viewer, column);
		
	}

	@Override
	public void update(ViewerCell cell) {
		int columnIndex = cell.getColumnIndex();
		CellLabelProvider cellLabelProvider = getCellLabelProvider(columnIndex);
		if(cellLabelProvider != null)
		{
			cellLabelProvider.update(cell);
		}
		else
		{
			Object element = cell.getElement();
			String text = getColumnText(element,columnIndex);
			
		}
	}

	public abstract String getColumnText(Object element, int columnIndex);

	public abstract Image getColumnImage(Object element, int columnIndex);

	public abstract CellLabelProvider getCellLabelProvider(int columnIndex);

}
