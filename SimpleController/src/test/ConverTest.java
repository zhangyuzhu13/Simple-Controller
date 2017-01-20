package test;

import ormapping.Conversation;
import bean.UserBean;
import util.*;

public class ConverTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Conversation<UserBean> conver = new Conversation<UserBean>(UserBean.class,"UserBean");
		UserBean user = conver.getUser("userName", "java");
		System.out.println(user.getPassword());
	}

}
