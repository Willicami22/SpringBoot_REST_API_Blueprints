package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class blueprintsApplication implements CommandLineRunner {

    @Autowired
    BlueprintsServices blueprintsServices;

    public static void main(String[] args) {
        SpringApplication.run(blueprintsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("========= Comprobar que guarda el plano =======");
        System.out.println("Plano a guardar: ");
        System.out.println(new Blueprint("author", "bpname", new Point[]{new Point(10, 20), new Point(20, 30)}));
        blueprintsServices.addNewBlueprint(new Blueprint("author", "bpname", new Point[]{new Point(10, 20), new Point(20, 30)}));
        System.out.println("Plano guardado");
        System.out.println("Mirar plano guardado");
        System.out.println(blueprintsServices.getBlueprint("author", "bpname").toString());

        System.out.println("========= Comprobar que varios planos del mismo autor =======");
        System.out.println("Planos a guardar: ");
        System.out.println(new Blueprint("author1", "bpname1", new Point[]{new Point(10,20), new Point(20,30)}));
        blueprintsServices.addNewBlueprint(new Blueprint("author1", "bpname1", new Point[]{new Point(10,20), new Point(20,30)}));
        System.out.println(new Blueprint("author1", "bpname2", new Point[]{new Point(20,30), new Point(30,40)}));
        blueprintsServices.addNewBlueprint(new Blueprint("author1", "bpname2", new Point[]{new Point(20,30), new Point(30,40)}));
        System.out.println(new Blueprint("author1", "bpname3", new Point[]{new Point(40,50), new Point(50,60)}));
        blueprintsServices.addNewBlueprint(new Blueprint("author1", "bpname3", new Point[]{new Point(40,50), new Point(50,60)}));

        System.out.println("Planos del autor: ");
        System.out.println(blueprintsServices.getBlueprintsByAuthor("author1").toString());

        System.out.println("====== Comprobar todos los planos: ========");
        System.out.println(blueprintsServices.getAllBlueprints().toString());

    }
}
