package com.again.gc.nibble;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Nibbles {
    
    private final int WIDTH = 600;
    private final int HEIGHT = 600;

    public Nibbles(Display display) {
        
        initUI(display);
    }
    
    @SuppressWarnings("unused")
    private void initUI(Display display) {

        Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);
        
        FillLayout layout = new FillLayout();
        shell.setLayout(layout);

        Board board = new Board(shell);        

        shell.setText("Nibbles");
        int borW = shell.getSize().x - shell.getClientArea().width;
        int borH = shell.getSize().y - shell.getClientArea().height;
        shell.setSize(WIDTH + borW, HEIGHT + borH);
        
        shell.open();

        while (!shell.isDisposed()) {
          if (!display.readAndDispatch()) {
            display.sleep();
          }
        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        
        Display display = new Display();
        Nibbles ex = new Nibbles(display);
        display.dispose();
    }
}
