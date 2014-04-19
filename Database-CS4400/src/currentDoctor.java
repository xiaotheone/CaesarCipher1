
public class currentDoctor {

	
	private String DoctorUsername;
	
	public static currentDoctor cd = new currentDoctor();
	public currentDoctor(){
		DoctorUsername = "";
	}
	public currentDoctor(String name){
		this.DoctorUsername = name;
	}
	
	public String getDoctorUsername() {
		return DoctorUsername;
	}
	public void setDoctorUsername(String DoctorUsername) {
		this.DoctorUsername = DoctorUsername;
	}
	
}
