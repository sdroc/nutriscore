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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.sdroc.nutriscore.domain.Score;

/**
 * Test selection model
 * @author steph
 */
public class DefaultNutriScoreSelectionModelTest {

    public DefaultNutriScoreSelectionModelTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of select method, of class DefaultNutriScoreSelectionModel.
     */
    @Test
    public void testSelect() {
        System.out.println("select");
        Score score = Score.A;
        DefaultNutriScoreSelectionModel instance = new DefaultNutriScoreSelectionModel();

        boolean result = instance.setSelection(score);
        assertEquals(false, result);
        score = Score.B;
        result = instance.setSelection(score);
        assertEquals(true, result);
    }

    /**
     * Test of getSelected method, of class DefaultNutriScoreSelectionModel.
     */
    @Test
    public void testGetSelected() {
        System.out.println("getSelected");
        DefaultNutriScoreSelectionModel instance = new DefaultNutriScoreSelectionModel();

        Score result = instance.getSelection();
        assertEquals(Score.A, result);
        instance.setSelection(Score.D);
        result = instance.getSelection();
        assertEquals(Score.D, result);

    }

}
