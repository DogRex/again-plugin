package com.again.plugin.example.emf.databinding;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.FeaturePath;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.again.model.ModelFactory;
import com.again.model.ModelPackage;
import com.again.model.Person;
import com.again.model.Phone;

public class DatabindingExample {

	public static final String ID = "com.again.emf.databinding.view";
	private Text firstName;

	public void createPartControl(Composite parent) {
		final Person person = createPerson();

		Layout layout = new GridLayout(2, false);
		parent.setLayout(layout);

		firstName = new Text(parent, SWT.NONE);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		firstName.setLayoutData(gridData);
		Text phoneNumber = new Text(parent, SWT.NONE);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		phoneNumber.setLayoutData(gridData);

		Button button1 = new Button(parent, SWT.PUSH);
		button1.setText("Write model");
		button1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(person.getFirstName());
				System.out.println(person.getPhone().getNumber());
			}
		});

		Button button2 = new Button(parent, SWT.PUSH);
		button2.setText("Change model");
		button2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				person.setFirstName("Lars2");
				String reversedNumber = new StringBuffer(person.getPhone().getNumber()).reverse().toString();
				person.getPhone().setNumber(reversedNumber);
			}
		});

		DataBindingContext bindingContext = new DataBindingContext();
		bindingContext.bindValue(WidgetProperties.text(SWT.Modify).observe(firstName),
				EMFProperties.value(ModelPackage.Literals.PERSON__FIRST_NAME).observe(person));

		FeaturePath feature = FeaturePath.fromList(ModelPackage.Literals.PERSON__PHONE,
				ModelPackage.Literals.PHONE__NUMBER);
		bindingContext.bindValue(WidgetProperties.text(SWT.Modify).observe(phoneNumber), EMFProperties.value(feature)
				.observe(person));
	}

	private Person createPerson() {
		// Initialize the model
		ModelPackage.eINSTANCE.eClass();
		// Retrieve the default factory singleton
		ModelFactory factory = ModelFactory.eINSTANCE;

		final Person person = factory.createPerson();
		person.setFirstName("Lars");
		person.setLastName("Vogel");
		person.setGender("m");
		Phone phone = factory.createPhone();
		phone.setNumber("0123456789");
		person.setPhone(phone);
		return person;
	}

	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setSize(280, 300);
		shell.setText("Table Example");

		final DatabindingExample control = new DatabindingExample();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				control.createPartControl(shell);
			}
		});

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
