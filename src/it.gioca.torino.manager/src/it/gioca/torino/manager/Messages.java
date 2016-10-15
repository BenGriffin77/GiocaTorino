package it.gioca.torino.manager;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "it.gioca.torino.manager.messages"; //$NON-NLS-1$

	private static ResourceBundle RESOURCE_BUNDLE;

	private Messages() {
	}

	public static String getString(String key) {
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, Locale.ITALY);
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
