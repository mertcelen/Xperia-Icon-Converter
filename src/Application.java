
public class Application {
	public String location;
	public String packageName;
	public String appName;
	
	public Application(String location, String packageName, String appName) {
		super();
		this.location = location;
		this.packageName = packageName;
		this.appName = appName;
	}
	public String getLocation() {
		return location;
	}
	public String getPackageName() {
		return packageName;
	}
	public String getAppName() {
		return appName;
	}
	
}
