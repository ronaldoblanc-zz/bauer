package com.github.ronaldoblanc.bauer.api;

/**
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public interface Notification {

	String getEventMessage(); // The message and other desired info

	String getRawMessage(); // Just the message

	boolean isSemaphoreStop(); // Red

	boolean isSemaphoreWait(); // Yellow

	boolean isSemaphoreGo(); // Green

	int getProgress();
}
