package ase.rsse.apirec.transactions;

import java.util.*;
import java.util.stream.Collectors;

import ase.rsse.apirec.transactions.query.QueryAtomicChange;
import ase.rsse.apirec.transactions.query.QueryChangeContext;
import ase.rsse.apirec.transactions.query.QueryCodeContext;
import ase.rsse.apirec.transactions.query.QueryTransaction;
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
        System.out.println("Size of all Transactions - " + allTransactions.size());
        QueryTransaction tempQueryTransaction = new QueryTransaction();

        for(Transaction trans : allTransactions){
            if(trans.getFileName().equalsIgnoreCase("Gugel.FEMain")){
                tempQueryTransaction.setFileName(trans.getFileName());
                QueryCodeContext queryCodeContext = new QueryCodeContext();
                queryCodeContext.setTokens(trans.getCodeContext().getTokens());
//                tempQueryTransaction.setQueryCodeContext((QueryCodeContext) trans.getCodeContext());
                QueryChangeContext queryChangeContext = new QueryChangeContext();
                ArrayList<QueryAtomicChange> qac = new ArrayList<>();
                for (AtomicChange ac: trans.getChangeContex().getAtomicChanges()) {
                    qac.add(new QueryAtomicChange()
                            .withLabel(ac.getLabel())
                            .withNodeType(ac.getNodeType())
                            .withOperation(ac.getOperation()));
                }
                queryChangeContext.setQueryAtomicChanges(qac);
                tempQueryTransaction.setChangeContex(queryChangeContext);
                tempQueryTransaction.setQueryCodeContext(queryCodeContext);
                break;
            }
        }

        Set<AtomicChange> allCandidateChanges = ScoringUtility.getAllCandidateChanges(
                tempQueryTransaction.getQueryChangeContex(),
                tempQueryTransaction.getQueryCodeContext());

        System.out.println("Size of all Candidate Changes - " + allCandidateChanges.size());

        System.out.println("======== Begin Scoring ========");
        System.out.println("================================");
        System.out.println("======== Scoring Change Context ========");
        HashMap<String, Double> changeContextScores = ScoringUtility.scoreChangeContext(allCandidateChanges,
                tempQueryTransaction.getQueryChangeContex());
        changeContextScores.entrySet().stream().sorted((k1,k2) -> k1.getValue().compareTo(k2.getValue()))
                .forEach(k->System.out.println(k.getKey() + ": " +k.getValue()));

        System.out.println("======== Scoring Code Context ========");
        HashMap<String, Double> codeContextScores = ScoringUtility.scoreCodeContext(allCandidateChanges,
                tempQueryTransaction.getQueryCodeContext());
        codeContextScores.entrySet().stream().sorted((k1,k2) -> k1.getValue().compareTo(k2.getValue()))
                .forEach(k->System.out.println(k.getKey() + ": " +k.getValue()));
    }
}
