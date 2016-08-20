package com.again.plugin.example.clabel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class CLabelLeftShadowIn {
  public static void main(String[] args) {
    Display display = new Display();
    Shell shell = new Shell(display);
    shell.setText("CLabel Test");

    shell.setLayout(new GridLayout(1, false));

    CLabel left = new CLabel(shell, SWT.LEFT | SWT.SHADOW_OUT);
    left.setText("Left and Shadow In");
    left.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    display.dispose();
  }
}
