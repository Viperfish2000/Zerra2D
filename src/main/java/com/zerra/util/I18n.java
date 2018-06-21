package com.zerra.util;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.google.common.collect.Maps;

public class I18n {

	private static ResourceBundle bundle = ResourceBundle.getBundle("lang/" + (Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry()).toLowerCase(), Locale.getDefault());
	private static Map<String, String> translated = Maps.<String, String>newHashMap();

	public static String format(String key, Object... params) {
		if (translated.containsKey(key)) {
			return String.format(translated.get(key), params);
		}

		try {
			String formatted = bundle.getString(key);
			translated.put(key, formatted);
			return String.format(formatted, params);
		} catch (Exception e) {
		}

		return key;
	}

	public static boolean contains(String key) {
		return translated.containsKey(key);
	}

	public static void setLanguage(Locale locale) {
		try {
			bundle = ResourceBundle.getBundle("lang/" + (locale.getLanguage() + "_" + locale.getCountry()).toLowerCase(), locale);
		} catch (Exception e) {
			bundle = ResourceBundle.getBundle("lang/" + (Locale.ENGLISH.getLanguage() + "_" + Locale.ENGLISH.getCountry()).toLowerCase(), Locale.ENGLISH);
		}
		translated.clear();
	}
}