package ru.vlsu.anttrail.event;

import java.util.EventListener;

public interface TrailListener extends EventListener {
	void modelChanged(TrailEvent event);
}
