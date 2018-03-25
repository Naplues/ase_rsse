package ase.rsse.cookbook;

import java.util.ArrayList;

/**
 * Created by Aydinli on 25.03.2018.
 */
public class TransactionObject {
    private ArrayList<CodeContextObject> codeContextObjects;
    private ArrayList<ChangeContextObject> changeContextObjects;

    public ArrayList<CodeContextObject> getCodeContextObjects() {
        return codeContextObjects;
    }

    public void setCodeContextObjects(ArrayList<CodeContextObject> codeContextObjects) {
        this.codeContextObjects = codeContextObjects;
    }

    public ArrayList<ChangeContextObject> getChangeContextObjects() {
        return changeContextObjects;
    }

    public void setChangeContextObjects(ArrayList<ChangeContextObject> changeContextObjects) {
        this.changeContextObjects = changeContextObjects;
    }
}
