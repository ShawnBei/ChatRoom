
public class Credentials {
	private String username;
	private String nickname;
	private String gender;
	private String status;
	private String rStatus="TRUE";

	public Credentials(String usernameIn, String nicknameIn,String genderIn,String statusIn ) {
		this.username = usernameIn;
		this.nickname = nicknameIn;
		this.gender=genderIn;
		this.status=statusIn;
		
	}
	

	public String getUsername() {
		return this.username;
	}
	public String getNickname() {
		return this.nickname;
	}
	public String getGender() {
		return this.gender;
	}
	public String getStatus() {
		return this.status;
	}
	
	public String getRStatus() {
		return this.rStatus;
	}
	
	public void  setStatus(String s) {
		this.status=s;
	}
	
	public void  setRStatus(String s) {
		this.rStatus=s;
	}

	public boolean equals(Object obj) {
		Credentials c = (Credentials) obj;
		if (this.username.equals(c.username))
			return true;
		return false;
	}
}

