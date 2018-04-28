//Helper class which  for presenting a simple version of a user
package Users;


import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class UserTempList extends RecursiveTreeObject<UserTempList> {
	private String name;
	private String city;
	private String age;
	private String username;

	public UserTempList(String username,String name,String city,String age){
		this.name=name;
		this.city=city;
		this.age=age;
		this.username=username;
	}
	public String getName() {
		return this.name;
	}
	public String getCity() {
		return this.city;
	}
	public String getAge() {
		return this.age;
	}
	public String getUsername() {
		return this.username;
	}
	
}
