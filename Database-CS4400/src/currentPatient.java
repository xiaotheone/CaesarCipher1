
public class currentPatient {

	
	private String patientUsername;
	
	public static currentPatient cp = new currentPatient();
	public currentPatient(){
		patientUsername = "";
	}
	public currentPatient(String name){
		this.patientUsername = name;
	}
	
	public String getPatientUsername() {
		return patientUsername;
	}
	public void setPatientUsername(String patientUsername) {
		this.patientUsername = patientUsername;
	}
	
}
