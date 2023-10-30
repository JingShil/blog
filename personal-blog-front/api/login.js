import {myRequest} from './http.js'
 
export function login (user) {  //登录
	return myRequest({
		url:'login',
		method:'post',
		data:user
	})
}

export function register(user) {
	return myRequest({
		url:'register',
		method:'post',
		data:user
	})
}

export function getCode(phone, loginFlag) {
	return myRequest({
		url:'code',
		method:'post',
		data:{
			"phone":phone,
			"loginFlag":loginFlag
		}
	})
}