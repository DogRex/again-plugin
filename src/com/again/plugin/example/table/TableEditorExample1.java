package com.again.plugin.example.table;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class TableEditorExample1 {
	private static final String TYPE_PROP = "Validtor Type";
	private static final String VALUE_PROP = "Value";
	private static final String DESC_PROP = "Description";
	private static final String[] VALIDATOR_PROPS = new String[] { TYPE_PROP, VALUE_PROP, DESC_PROP };

	private final List<Validator> validators = new ArrayList<Validator>();

	private TableViewer tableViewer;

	private void createControl(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);
		Label label = new Label(composite, SWT.NONE);
		label.setText("Validation");
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.TOP).grab(false, false).applyTo(label);

		Button button = new Button(composite, SWT.FLAT);
		button.setText("ADD");
		GridDataFactory.fillDefaults().align(SWT.RIGHT, SWT.TOP).grab(true, false).applyTo(button);

		tableViewer = new TableViewer(composite, SWT.NONE);
		final Table table = tableViewer.getTable();
		addColumns(table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setLabelProvider(new ValidatorLabelProvider());
		tableViewer.setColumnProperties(VALIDATOR_PROPS);
		tableViewer.setCellEditors(getCellEditors(table));
		tableViewer.setCellModifier(new ValidatorCellModifier());
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(tableViewer.getControl());

		tableViewer.setInput(validators);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				validators.add(new Validator());
				tableViewer.refresh();
				composite.layout();
			}
		});

	}

	private void addColumns(Table table) {
		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);
		for (String head : VALIDATOR_PROPS) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setResizable(false);
			column.setText(head);
			tableLayout.addColumnData(new ColumnWeightData(1, false));
		}
	}

	private CellEditor[] getCellEditors(Table table) {
		CellEditor typeCellEditor = new ComboBoxCellEditor(table, ValidatorEnum.names(), SWT.BORDER);
		CellEditor valueCellEdiotr = new TextCellEditor(table);
		return new CellEditor[] { typeCellEditor, valueCellEdiotr, null };
	}

	private class ValidatorCellModifier implements ICellModifier {

		@Override
		public boolean canModify(Object element, String property) {
			Validator validtor = (Validator)element;
			if(VALUE_PROP.equals(property))
			{
				return !validtor.getName().equals(ValidatorEnum.Required.name);
			}
			return TYPE_PROP.equals(property) ;
		}

		@Override
		public Object getValue(Object element, String property) {
			Validator validator = (Validator) element;
			if (TYPE_PROP.equals(property)) {
				return ValidatorEnum.indexOf(validator.getName() == null ? "" : validator.getName());
			} else if (VALUE_PROP.equals(property)) {
				return validator.getParams() == null ? "" : validator.getParams();
			}
			return null;
		}

		@Override
		public void modify(Object element, String property, Object value) {
			TableItem item = (TableItem) element;
			Validator validator = (Validator) item.getData();
			if (TYPE_PROP.equals(property)) {
				int index = (int) value;
				if (index >= 0) {
					ValidatorEnum validatorEnum = ValidatorEnum.values()[index];
					validator.setName(validatorEnum.getName());
					validator.setDesc(validatorEnum.getDesc());
				} else {
					validator.setName(null);
					validator.setDesc(null);
				}

			} else if (VALUE_PROP.equals(property)) {
				validator.setParams((String) value);
			}
			tableViewer.update(validator, null);
		}

	}

	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		TableEditorExample1 control = new TableEditorExample1();
		control.createControl(shell);

		shell.setSize(600, 400);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		shell.dispose();

	}

	private static class ValidatorLabelProvider extends LabelProvider implements ITableLabelProvider {

		public String getColumnText(Object element, int columnIndex) {
			Validator validator = (Validator) element;
			switch (columnIndex) {
			case 0:
				return validator.getName();
			case 1:
				return validator.getParams();
			case 2:
				return validator.getDesc();
			default:
				return null;
			}
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

	}

	private static class Validator {
		private String name;

		private String desc;

		private String params;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getParams() {
			return params;
		}

		public void setParams(String params) {
			this.params = params;
		}

	}

	private static enum ValidatorEnum {
		Required("Required", "Required"), Min("Min", "Min"), Max("Max", "Max"), Range("Range", "Range");

		private static Map<String, ValidatorEnum> validators = null;

		private final String name;

		private final String desc;

		private ValidatorEnum(String name, String desc) {
			this.name = name;
			this.desc = desc;
		}

		public String getName() {
			return name;
		}

		public String getDesc() {
			return desc;
		}

		private static void checkValidatorsInited() {
			if (validators == null) {
				validators = new LinkedHashMap<String, ValidatorEnum>(values().length);
				for (ValidatorEnum value : values()) {
					validators.put(value.getName(), value);
				}
			}
		}

		public static String[] names() {
			checkValidatorsInited();
			Set<String> keys = validators.keySet();
			return keys.toArray(new String[keys.size()]);
		}

		public static ValidatorEnum of(String name) {
			checkValidatorsInited();
			return validators.get(name);
		}

		public static int indexOf(String name) {
			ValidatorEnum validatorEnum = of(name);
			if (validatorEnum != null) {
				return validatorEnum.ordinal();
			}
			return -1;
		}
	}
}