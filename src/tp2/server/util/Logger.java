package tp2.server.util;

import java.util.Calendar;

public final class Logger {

	protected String className;

	private Logger(String className) {
		this.className = className;
	}

	public static Logger getLogger(String className) {
		return new Logger(className);
	}

	public void info(String message, Exception e) {

		StringBuilder infoMess = new StringBuilder()
		.append("["+Calendar.getInstance().getTime().toString()+"]")
		.append("["+this.className+"]")
		.append("[INFO]")
		.append("["+message+"]");

		if (e != null) {
			infoMess.append("["+e.getMessage()+"]");
		}
		System.out.println(infoMess.toString());
	}

	public void warning(String message, Exception e) {

		StringBuilder infoMess = new StringBuilder()
		.append("["+Calendar.getInstance().getTime().toString()+"]")
		.append("["+this.className+"]")
		.append("[WARN]")
		.append("["+message+"]");

		if (e != null) {
			infoMess.append("["+e.getMessage()+"]");
		}
		System.out.println(infoMess.toString());
	}

	public void error(String message, Exception e) {

		StringBuilder infoMess = new StringBuilder()
		.append("["+Calendar.getInstance().getTime().toString()+"]")
		.append("["+this.className+"]")
		.append("[ERROR]")
		.append("["+message+"]");

		if (e != null) {
			infoMess.append("["+e.getMessage()+"]");
		}
		System.err.println(infoMess.toString());
	}
	
	public void info(String message) {

		StringBuilder infoMess = new StringBuilder()
		.append("["+Calendar.getInstance().getTime().toString()+"]")
		.append("["+this.className+"]")
		.append("[INFO]")
		.append("["+message+"]");

		System.out.println(infoMess.toString());
	}

	public void warning(String message) {

		StringBuilder infoMess = new StringBuilder()
		.append("["+Calendar.getInstance().getTime().toString()+"]")
		.append("["+this.className+"]")
		.append("[WARN]")
		.append("["+message+"]");

		System.out.println(infoMess.toString());
	}

	public void error(String message) {

		StringBuilder infoMess = new StringBuilder()
		.append("["+Calendar.getInstance().getTime().toString()+"]")
		.append("["+this.className+"]")
		.append("[ERROR]")
		.append("["+message+"]");

		System.err.println(infoMess.toString());
	}
	
	
	public String getInfo(String message, Exception e) {

		StringBuilder infoMess = new StringBuilder()
		.append("["+Calendar.getInstance().getTime().toString()+"]")
		.append("["+this.className+"]")
		.append("[INFO]")
		.append("["+message+"]");

		if (e != null) {
			infoMess.append("["+e.getMessage()+"]");
		}
		return infoMess.toString();
	}

	public String getWarning(String message, Exception e) {

		StringBuilder infoMess = new StringBuilder()
		.append("["+Calendar.getInstance().getTime().toString()+"]")
		.append("["+this.className+"]")
		.append("[WARN]")
		.append("["+message+"]");

		if (e != null) {
			infoMess.append("["+e.getMessage()+"]");
		}
		return infoMess.toString();
	}

	public String getError(String message, Exception e) {

		StringBuilder infoMess = new StringBuilder()
		.append("["+Calendar.getInstance().getTime().toString()+"]")
		.append("["+this.className+"]")
		.append("[ERROR]")
		.append("["+message+"]");

		if (e != null) {
			infoMess.append("["+e.getMessage()+"]");
		}
		return infoMess.toString();
	}
	
	public String getInfo(String message) {

		StringBuilder infoMess = new StringBuilder()
		.append("["+Calendar.getInstance().getTime().toString()+"]")
		.append("["+this.className+"]")
		.append("[INFO]")
		.append("["+message+"]");

		return infoMess.toString();
	}

	public String getWarning(String message) {

		StringBuilder infoMess = new StringBuilder()
		.append("["+Calendar.getInstance().getTime().toString()+"]")
		.append("["+this.className+"]")
		.append("[WARN]")
		.append("["+message+"]");

		return infoMess.toString();
	}

	public String getError(String message) {

		StringBuilder infoMess = new StringBuilder()
		.append("["+Calendar.getInstance().getTime().toString()+"]")
		.append("["+this.className+"]")
		.append("[ERROR]")
		.append("["+message+"]");

		return infoMess.toString();
	}

}
