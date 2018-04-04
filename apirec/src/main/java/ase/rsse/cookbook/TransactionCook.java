package ase.rsse.cookbook;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

import ase.rsse.utilities.IoUtility;
import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;
import org.apache.commons.io.FileUtils;

public class TransactionCook {

	public static void main(String[] args) throws IOException {
		// get all events of a given user
		List<IIDEEvent> events = IoUtility
				.readEvent("apirec/2.zip");
		System.out.println("Total number of events: " + events.size());
		
		ArrayList<CompletionEvent> completionEvents = new ArrayList<>();
		ArrayList<String> fileNames = new ArrayList<>();
		Map<String,Integer> fileNameNumberOfCE = new HashMap<>();
		
		for (IIDEEvent event : events) {
			if (event instanceof ICompletionEvent) {
				CompletionEvent completionEvent = (CompletionEvent) event;
				completionEvents.add(completionEvent);
				String fileName = completionEvent.getContext().getTypeShape().getTypeHierarchy().getElement().toString();
				fileNames.add(fileName);
				if(fileNameNumberOfCE.get(fileName) == null){
					fileNameNumberOfCE.put(fileName,1);
				} else {
					fileNameNumberOfCE.put(fileName,fileNameNumberOfCE.get(fileName) + 1);
				}
			}
		}
		System.out.println("Total number of completion events: " + completionEvents.size());
		System.out.println("Total number of filenames: " + fileNames.size());
		if (completionEvents.size() > 1) {

			List<CompletionEvent> ceSorted = completionEvents.stream()
					.filter(Objects::nonNull)
					.sorted(Comparator.comparing(CompletionEvent::getTriggeredAt, Comparator.reverseOrder()))
//					.limit(2)
					.collect(Collectors.toList());
			System.out.println("Total number of sorted completion events: " + ceSorted.size());

			Set<String> distinctFiles = new LinkedHashSet<>(fileNames);

			System.out.println("Total number of distinct filenames: " + distinctFiles.size());

			List<CompletionEvent> mockCEs = new ArrayList<>();
			List<CompletionEvent> mockCEsFinal = new ArrayList<>();
			List<ISST> mockSSTs = new ArrayList<>();

			ceSorted.forEach(event -> {
				if(event.getContext().getTypeShape().getTypeHierarchy().getElement().toString().
						toLowerCase().equals("TypeName(MyKidds.Security.ClaimTypes, MyKidds)".toLowerCase())){
					mockCEs.add(event);
				}
			});

			mockCEs.forEach(ce -> {
				if(mockSSTs.size() <=4 && !mockSSTs.contains(ce.getContext().getSST())){
					mockSSTs.add(ce.getContext().getSST());
					System.out.println("SST TIME");
					System.out.println(ce.getTriggeredAt());
				}
			});

			System.out.println("Total number of Ces: " + mockCEs.size());
//			mockCEs.forEach(mockCE -> System.out.println(mockCE.getTriggeredAt()));

			File file = new File("mockSSTs.txt");
			FileWriter fw = new FileWriter(file,true); //the true will append the new data
			PrintWriter writer = new PrintWriter(file);
			writer.print("");
			writer.close();
			mockSSTs.forEach(sst -> {
				try {
					fw.write("=========== SST ==========");
					fw.write(sst.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			fw.close();
//			mockCEs.forEach(ce ->{
//				System.out.println("================= SST ===================");
//				System.out.println();
//			});
		}
	}
}
