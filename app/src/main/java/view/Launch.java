package view;

import controller.*;
import javax.swing.*;
import java.awt.*;

/**
 * Class used to start the visualization
 */
public class Launch {

    public static void main(String[] args) {
        SetupMenu menu = new SetupMenu();
        menu.create();
    }
}