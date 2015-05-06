package com.paddlewarz.game.android;

import java.util.ArrayList;
import java.util.List;

public class CreateReturns {

	public List<Returns> change(int[] num,String[] name, String[] date, int[] num2 ){
		
		List<Returns> returnall = new ArrayList<Returns>();
		System.out.println(num.length);
		for(int i=0;i<num.length;i++){
			Returns returns = new Returns();
			  returns.setId(num[i]);
			  returns.setName(name[i]);
			  returns.setDate(date[i]);
			  returns.setScore(num2[i]);
			  returnall.add(returns);
		}
		
		  
		return returnall;
	}
}
