package ase.rsse.cookbook;

import ase.rsse.apirec.transactions.ChangeContext;
import ase.rsse.apirec.transactions.Operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aydinli on 02.04.2018.
 */
public class ChangeContextCalculation {
    private static List<ChangeContext> changeContexts;

    public static void main(String[] args) throws IOException {
        changeContexts = new ArrayList<>();

        ChangeContext changeContext1 = new ChangeContext()
                .withIndex(1)
                .withLabel("U()")
                .withOperation(Operation.MOVE)
                .withWeightOfDependency(1f)
                .withWeightOfScope(1);

        ChangeContext changeContext2 = new ChangeContext()
                .withIndex(1);
    }
}
