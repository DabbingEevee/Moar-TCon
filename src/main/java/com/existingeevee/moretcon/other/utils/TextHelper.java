package com.existingeevee.moretcon.other.utils;

import java.util.ArrayList;
import java.util.List;

public class TextHelper {

	public static List<String> smartSplitString(String toSplit, int max) {
		List<String> ret = new ArrayList<>();
		String temp = "";
		for (String s : toSplit.split(" ")) {
			if (temp.replace("%s%", " ").length() + s.replace("%s%", " ").length() > max) {
				ret.add("" + temp.trim().replace("%s%", " "));
				temp = s + " ";
			} else {
				temp += s + " ";
			}
		}
		ret.add("" + temp.trim().replace("%s%", " "));
		return ret;
	}

	public static boolean allEqualLength(String[] array) {
		int i;
		for (i = 0; i < array.length - 1; i++) {
			if (array[i].length() != array[i + 1].length()) {
				return false;
			}
		}
		return true;
	}

}
