<<<<<<< Updated upstream
package com.project.sightseeing;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sysuser")
public class Sysuser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer user_id=5;
	private String login;
	private String passwd;
	private String email;
	private String f_name;
	private String l_name;
	private String avatar_path;
	private Integer comment_num;
	
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(int id) {
		this.user_id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getF_name() {
		return f_name;
	}
	public void setF_name(String f_name) {
		this.f_name = f_name;
	}
	public String getL_name() {
		return l_name;
	}
	public void setL_name(String l_name) {
		this.l_name = l_name;
	}
	public String getAvatar_path() {
		return avatar_path;
	}
	public void setAvatar_path(String avatar_path) {
		this.avatar_path= avatar_path;
	}
	public Integer getComment_num() {
		return comment_num;
	}
	public void setComment_num(Integer comment_num) {
		this.comment_num = comment_num;
	}
}
=======
package com.project.sightseeing;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//
//@Entity
//@Table(name = "sysuser")
//public class Sysuser {
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	
//	private Integer user_id;
//	private String login;
//	private String passwd;
//	private String email;
//	private String f_name;
//	private String l_name;
//	private String avatar_path;
//	private Integer comment_num;
//	
//	public Integer getId() {
//		return user_id;
//	}
//	public void setId(Integer id) {
//		this.user_id = id;
//	}
//	public String getLogin() {
//		return login;
//	}
//	public void setLogin(String login) {
//		this.login = login;
//	}
//	public String getPasswd() {
//		return passwd;
//	}
//	public void setPasswd(String passwd) {
//		this.passwd = passwd;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	public String getF_name() {
//		return f_name;
//	}
//	public void setF_name(String f_name) {
//		this.f_name = f_name;
//	}
//	public String getL_name() {
//		return l_name;
//	}
//	public void setL_name(String l_name) {
//		this.l_name = l_name;
//	}
//	public String getAvatar_path() {
//		return avatar_path;
//	}
//	public void setAvatar_path(String avatar_path) {
//		this.avatar_path= avatar_path;
//	}
//	public Integer getComment_num() {
//		return comment_num;
//	}
//	public void setComment_num(Integer comment_num) {
//		this.comment_num = comment_num;
//	}
//}
>>>>>>> Stashed changes
