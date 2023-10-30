import request from '@/utils/request'

export function getCode(user) {
	return request({
		url: 'code',
		method: 'post',
		data: user
	})
}

export function login(user) {
	return request({
		url: 'login',
		method: 'post',
		data: user
	})
}

export function register(user) {
	return request({
		url: 'register',
		method: 'post',
		data: user
	})
}