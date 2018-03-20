package ase.rsse.utils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.utils.io.Directory;
import cc.kave.commons.utils.io.IReadingArchive;
import cc.kave.commons.utils.io.ReadingArchive;

public class IoUtility {

	public static Context readFirstContext(String dir) {
		for (String zip : findAllZips(dir)) {
			List<Context> ctxs = readContext(zip);
			return ctxs.get(0);
		}
		return null;
	}

	public static List<Context> readAll(String dir) {
		LinkedList<Context> res = Lists.newLinkedList();

		for (String zip : findAllZips(dir)) {
			res.addAll(readContext(zip));
		}
		return res;
	}

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

	public static List<IIDEEvent> readEvent(String pathToEvent) {
		LinkedList<IIDEEvent> res = Lists.newLinkedList();
		try {
			IReadingArchive ra = new ReadingArchive(new File(pathToEvent));
			while (ra.hasNext()) {
				res.add(ra.getNext(IIDEEvent.class));
			}
			ra.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public static Set<String> findAllZips(String dir) {
		return new Directory(dir).findFiles(s -> s.endsWith(".zip"));
	}

	public static Set<String> findAllJsons(String dir) {
		return new Directory(dir).findFiles(s -> s.endsWith(".json"));
	}
}
