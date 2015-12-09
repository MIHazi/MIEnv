package Environment;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import jason.stdlib.intend;

public class StepTimer {
	
	TimerCallback callback;
	int interval;
	boolean startWithCallback, timed;
	TimerThread thread;
	KeySteppedListener keyListener;
	
	public StepTimer(TimerCallback cb, int interval_ms, boolean initialCallback){
		callback = cb;
		interval = interval_ms;
		startWithCallback = initialCallback;
		timed = true;
		thread = new TimerThread();
	}
	
	public StepTimer(TimerCallback cb, int interval_ms){
		callback = cb;
		interval = interval_ms;
		startWithCallback = false;
		timed = true;
		thread = new TimerThread();
	}
	
	public StepTimer(TimerCallback cb, int interval_ms, Component comp){
		callback = cb;
		interval = interval_ms;
		startWithCallback = false;
		timed = false;
		keyListener = new KeySteppedListener();
		comp.addKeyListener(keyListener);
	}
	
	public void start(){
		if(timed)
			thread.start();
		else
			keyListener.enabled = true;
			
	}
	
	public void stop(){
		if(timed)
			thread.interrupt();
		else
			keyListener.enabled = false;
	}
	
	class KeySteppedListener implements KeyListener{

		public boolean enabled = false;
		
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyCode() == KeyEvent.VK_SPACE && enabled)
				callback.callback(interval);
		}

		public void keyReleased(KeyEvent arg0) { }
		public void keyTyped(KeyEvent arg0) { }
	}
	
	class TimerThread extends Thread {
		
		public void run(){
			if(startWithCallback)
				callback.callback(interval);
			try {
				while(true) {
					sleep(interval);
					callback.callback(interval);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
