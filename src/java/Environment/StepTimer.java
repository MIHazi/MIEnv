package Environment;

public class StepTimer {
	
	TimerCallback callback;
	int interval;
	boolean startWithCallback;
	TimerThread thread;
	
	public StepTimer(TimerCallback cb, int interval_ms, boolean initialCallback){
		callback = cb;
		interval = interval_ms;
		startWithCallback = initialCallback;
		thread = new TimerThread();
	}
	
	public StepTimer(TimerCallback cb, int interval_ms){
		callback = cb;
		interval = interval_ms;
		startWithCallback = false;
		thread = new TimerThread();
	}
	
	public void start(){
		
	}
	
	public void stop(){
		
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
