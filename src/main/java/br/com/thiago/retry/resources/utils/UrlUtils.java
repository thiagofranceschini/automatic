package br.com.thiago.retry.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class UrlUtils {

	public static List<Integer> decodeIntList(String string) {
		String[] vet = string.split(",");
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		return list;
		// return Arrays.asList(string.split(",")).stream().map(x ->
		// Integer.parseInt(x)).collect(Collectors.toList());
	}

	public static String decodeParam(String string) {
		try {
			return URLDecoder.decode(string, "UTf-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

}
