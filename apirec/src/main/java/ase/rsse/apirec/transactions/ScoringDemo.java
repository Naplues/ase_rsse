package ase.rsse.apirec.transactions;

import java.util.*;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import ase.rsse.apirec.transactions.Transaction;
import ase.rsse.apirec.transactions.changecontext.AtomicChange;
import ase.rsse.utilities.IoUtility;
import ase.rsse.utilities.ScoringUtility;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;

/**
 * Created by Aydinli on 13.05.2018.
 */
public class ScoringDemo {

    public static void main(String[] args){
        MockDataUtility.createTestQueryChangeContext();
        MockDataUtility.createTestTransactions();

        ArrayList<Transaction> allTransactions = ScoringUtility.getAllTransactions();
        Set<AtomicChange> allCandidateChanges = ScoringUtility.getAllCandidateChanges(MockDataUtility.QUERY_CHANGE_CONTEXT,	MockDataUtility.QUERY_CODE_CONTEXT);

        HashMap<String, Double> changeContextScores = ScoringUtility.scoreChangeContext(allCandidateChanges, MockDataUtility.QUERY_CHANGE_CONTEXT);

        changeContextScores.entrySet().stream().sorted((k1,k2) -> k1.getValue().compareTo(k2.getValue()))
                .forEach(k->System.out.println(k.getKey() + ": " +k.getValue()));

        HashMap<String,Double> codeContextScores = ScoringUtility.scoreCodeContext(allCandidateChanges, MockDataUtility.QUERY_CODE_CONTEXT);


    }
}
