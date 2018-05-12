package ase.rsse.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import ase.rsse.apirec.transactions.ITransactionConstants;
import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;
import cc.kave.commons.utils.io.IReadingArchive;
import cc.kave.commons.utils.io.ReadingArchive;

public final class IoUtility {


	public static List<Context> readContext(String pathToContext) {
		LinkedList<Context> res = Lists.newLinkedList();
		try {
			IReadingArchive ra = new ReadingArchive(new File(pathToContext));
			while (ra.hasNext()) {
				res.add(ra.getNext(Context.class));
			}
			ra.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public static List<ICompletionEvent> readEvent(String pathToEvent) {
		LinkedList<ICompletionEvent> res = Lists.newLinkedList();
		try {
			IReadingArchive ra = new ReadingArchive(new File(pathToEvent));
			int counter = 0;
			while (ra.hasNext() && counter < 20000) {
				IIDEEvent next = ra.getNext(IIDEEvent.class);
				if (next instanceof ICompletionEvent) {
					res.add((ICompletionEvent) next);
					counter++;
					if (counter % 1000 == 0) {
						System.out.println(counter);
					}
					
				}
			}
			ra.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static int getEventDataSize(String pathToEvent) {
		ReadingArchive ra = new ReadingArchive(new File(pathToEvent));
		int size = ra.getNumberOfEntries();
		ra.close();
		return size;
	}

	public static List<IIDEEvent> readEventChunk(String pathToEvent, int chunkSize) {
		LinkedList<IIDEEvent> res = Lists.newLinkedList();
		try {
			int counter = 0;
			IReadingArchive ra = new ReadingArchive(new File(pathToEvent));
			while (ra.hasNext() && counter < chunkSize) {
				res.add(ra.getNext(IIDEEvent.class));
				counter++;
			}
			ra.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public static void writeTransactionToFile(String fileName, String json) throws IOException {
		File file = new File(ITransactionConstants.TRANSACTION_DIRECTORY, fileName + ".json");
		FileUtils.writeStringToFile(file, json);
	}

	public static String readContent(String parentDir, String fileName) throws IOException {
		File file = new File(parentDir, fileName);
		return FileUtils.readFileToString(file);
	}
	
	public static String readContentOfTransaction(String fileName) throws IOException {
		return readContent(ITransactionConstants.TRANSACTION_DIRECTORY, fileName);
	}
	
	public static File[] findAllTransactions() {
		return Paths.get(ITransactionConstants.TRANSACTION_DIRECTORY)
		.toFile()
		.listFiles(file -> !file.isHidden() 
				&&  file.isFile() 
				&& file.getName().endsWith(".json")
				&& !file.getName().startsWith("test"));
	}
	
	public static void deleteTransactionFile(String fileName) throws IOException {
		File file = new File(ITransactionConstants.TRANSACTION_DIRECTORY, fileName);
		if (!file.delete()) {
			System.out.println("Could not delete: " + file.getAbsolutePath());
		}
	}
	
	public static Set<String> findAllFiles(String rootDir, Predicate<String> predicate) {
		IOFileFilter fileFilter = new AbstractFileFilter() {
			@Override
			public boolean accept(File file) {
				return predicate.apply(file.getAbsolutePath());
			}
		};
		IOFileFilter allDirs = FileFilterUtils.trueFileFilter();
		Iterator<File> it = FileUtils.iterateFiles(new File(rootDir), fileFilter, allDirs);

		Set<String> files = Sets.newLinkedHashSet();
		while (it.hasNext()) {
			files.add(it.next().getAbsolutePath());
		}

		return files;
	}
}
