package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BlueprintsServicesTest {

    @Autowired
    private BlueprintsServices blueprintsServices;

    @Before
    public void setUp() {
        Point[] pts0 = new Point[]{ new Point(40, 40), new Point(15, 15), new Point(15, 15),
                new Point(15, 15), new Point(15, 15), new Point(30, 25), new Point(35, 35),
                new Point(40, 20), new Point(45, 30), new Point(50, 40), new Point(55, 25),
                new Point(60, 35), new Point(65, 15)};
        Blueprint bp0 = new Blueprint("lionel", "worldcup", pts0);
        try {
            blueprintsServices.addNewBlueprint(bp0);
        } catch (BlueprintPersistenceException e) {
            fail("Unexpected exception");
        }

        Point[] pts1 = new Point[]{ new Point(10, 10), new Point(20, 20), new Point(20, 20),
                new Point(30, 10), new Point(40, 40), new Point(50, 20), new Point(60, 30),
                new Point(60, 30), new Point(70, 10), new Point(80, 40), new Point(90, 20),
                new Point(90, 20), new Point(100, 10)};
        Blueprint bp1 = new Blueprint("cristiano", "champions", pts1);
        try {
            blueprintsServices.addNewBlueprint(bp1);
        } catch (BlueprintPersistenceException e) {
            fail("Unexpected exception");
        }
    }

    @Test
    public void testRedundantBlueprint() {
        try {
            Blueprint blp1 = blueprintsServices.getBlueprint("cristiano", "champions");
            List<Point> points1 = new ArrayList<>(Arrays.asList(
                    new Point(10, 10), new Point(20, 20), new Point(30, 10), new Point(40, 40),
                    new Point(50, 20), new Point(60, 30), new Point(70, 10), new Point(80, 40),
                    new Point(90, 20), new Point(100, 10)
            ));
            List<Point> points2 = blp1.getPoints();
            assertEquals(points1.size(), points2.size());

        } catch (BlueprintNotFoundException e) {
            fail("Unexpected exception");
        }

        try {
            Blueprint lm10 = blueprintsServices.getBlueprint("lionel", "worldcup");
            List<Point> plm10 = lm10.getPoints();
            assertEquals(plm10.size(), 10);

        } catch (BlueprintNotFoundException e) {
            fail("Unexpected exception");
        }
    }

    @Test
    public void testSubsamplingBlueprint() {
        try {
            Blueprint blp1 = blueprintsServices.getBlueprint("cristiano", "champions");
            List<Point> pcr7 = blp1.getPoints();
            assertEquals(pcr7.size(), 7);
        } catch (BlueprintNotFoundException e) {
            fail("Unexpected exception");
        }
        
        try {
            Blueprint lm10 = blueprintsServices.getBlueprint("lionel", "worldcup");
            List<Point> plm10 = lm10.getPoints();
            assertEquals(plm10.size(), 7);
        } catch (BlueprintNotFoundException e) {
            fail("Unexpected exception");
        }
    }
}
