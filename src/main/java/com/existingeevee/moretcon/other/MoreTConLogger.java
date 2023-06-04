package com.existingeevee.moretcon.other;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.existingeevee.moretcon.ModInfo;

public class MoreTConLogger {
	private static final Logger LOGGER = LogManager.getLogger(ModInfo.NAME);

	public static void log(String strMessage, Level level) {
		if (level == Level.INFO) {
			LOGGER.info(strMessage);
		}
		if (level == Level.WARN) {
			LOGGER.warn(strMessage);
		}
		if (level == Level.ERROR) {
			LOGGER.error(strMessage);
		}
		if (level == Level.DEBUG) {
			LOGGER.debug(strMessage);
		}
	}

	public static void log(String str) {
		log(str, Level.INFO);
	}

}
