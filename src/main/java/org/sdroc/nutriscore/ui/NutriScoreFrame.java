/* 
 * Copyright (C) 2020 steph.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package org.sdroc.nutriscore.ui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * The application main frame
 * @author steph
 */
public class NutriScoreFrame extends JFrame {

    /**
     * The constructor
     * initialise title, icon, close operation
     */
    public NutriScoreFrame() {
        setTitle("Nutri Score");
        try {
            setIconImage(ImageIO.read(NutriScoreFrame.class.getResourceAsStream("/image/nutri.png")));
        } catch (IOException ex) {
            Logger.getLogger(NutriScoreFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    
}
